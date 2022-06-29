package com.pelsinkaplan.upschoolcapstoneproject.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.google.firebase.auth.FirebaseAuth
import com.pelsinkaplan.upschoolcapstoneproject.R
import com.pelsinkaplan.upschoolcapstoneproject.databinding.ActivityAfterLoginBinding
import com.pelsinkaplan.upschoolcapstoneproject.databinding.ActivityBeforeLoginBinding
import com.pelsinkaplan.upschoolcapstoneproject.service.workmanager.BagWorker
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.AfterLoginViewModel
import com.pelsinkaplan.upschoolcapstoneproject.ui.viewmodel.afterlogin.ChartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class AfterLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAfterLoginBinding
    private lateinit var viewModel: AfterLoginViewModel
    private lateinit var request: WorkRequest
    lateinit var view: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: AfterLoginViewModel by viewModels()
        this.viewModel = tempViewModel
        binding = ActivityAfterLoginBinding.inflate(layoutInflater)
        binding.apply {
            toolbar.title = ""
            setContentView(root)
            setSupportActionBar(toolbar)
            val navHostFragment =
                supportFragmentManager.findFragmentById(fragmentContainerView.id) as NavHostFragment
            val navController = navHostFragment.navController
            NavigationUI.setupWithNavController(bottomNav, navController)
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.homeFragment,
                    R.id.categoriesFragment,
                    R.id.chartFragment,
                    R.id.favoritesFragment,
                    R.id.profileFragment
                )
            )
            toolbar.setupWithNavController(
                navController,
                appBarConfiguration
            )

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.categoriesFragment -> {
                        pageTitleTextView.text = getString(R.string.categories)
                    }
                    R.id.chartFragment -> {
                        pageTitleTextView.text = getString(R.string.bag)
                    }
                    R.id.favoritesFragment -> {
                        pageTitleTextView.text = getString(R.string.favorites)
                    }
                    R.id.profileFragment -> {
                        pageTitleTextView.text = getString(R.string.profile)
                    }
                    else -> {
                        pageTitleTextView.text = ""
                    }
                }
            }
        }


        WorkManager.getInstance(this@AfterLoginActivity).cancelAllWorkByTag("reminder")
    }

    override fun onStop() {
        super.onStop()
        Timber.tag("STOP").e("stop")
        CoroutineScope(Dispatchers.Main).launch {
            try {
                if (viewModel.service(FirebaseAuth.getInstance().currentUser!!.uid)!!
                        .isNotEmpty()
                ) {
                    try {
                        WorkManager.getInstance(this@AfterLoginActivity).cancelAllWork()
                    } catch (e: Exception) {
                        Timber.e("No Worker")
                    }
                    val workCondition = Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                    request = PeriodicWorkRequestBuilder<BagWorker>(15, TimeUnit.MINUTES)
                        .setConstraints(workCondition)
                        .addTag("reminder")
                        .setInitialDelay(15, TimeUnit.MINUTES)
                        .build()

                    WorkManager.getInstance(this@AfterLoginActivity)
                        .enqueue(request)
                } else {
                    try {
                        WorkManager.getInstance(this@AfterLoginActivity)
                            .cancelAllWorkByTag("reminder")
                    } catch (e: Exception) {
                        Timber.e("No Worker")
                    }
                }
            } catch (e: Exception) {
                WorkManager.getInstance(this@AfterLoginActivity)
                    .cancelAllWorkByTag("reminder")
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        applicationContext
        findNavController(R.id.fragmentContainerView).popBackStack()
        return true
    }
}