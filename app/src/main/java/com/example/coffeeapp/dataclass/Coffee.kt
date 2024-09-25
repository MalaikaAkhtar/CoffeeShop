package com.example.coffeeapp.dataclass

import android.os.Parcel
import android.os.Parcelable

data class Coffee(
    val id : Int,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val image : String,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readString() ?: ""
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeStringList(ingredients)  // Write the List<String> properly
        parcel.writeString(image)  // Write the String for image
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Coffee> {
        override fun createFromParcel(parcel: Parcel): Coffee {
            return Coffee(parcel)
        }

        override fun newArray(size: Int): Array<Coffee?> {
            return arrayOfNulls(size)
        }
    }
}
