package com.pelsinkaplan.upschoolcapstoneproject.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Pelşin KAPLAN on 17.06.2022.
 */
@Entity(tableName = "favorite_product_table")
data class Product(
    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "user")
    var user: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "price")
    var price: Double,
    @ColumnInfo(name = "category")
    var category: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "image")
    var image: String,
    @ColumnInfo(name = "rate")
    var rate: Double,
    @ColumnInfo(name = "count")
    var count: Int,
    @ColumnInfo(name = "sale_state")
    var sale_state: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(user)
        parcel.writeString(title)
        parcel.writeDouble(price)
        parcel.writeString(category)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeDouble(rate)
        parcel.writeInt(count)
        parcel.writeInt(sale_state)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }

}
