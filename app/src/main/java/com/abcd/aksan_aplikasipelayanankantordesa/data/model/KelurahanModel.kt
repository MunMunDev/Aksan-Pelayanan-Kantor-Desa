package com.abcd.aksan_aplikasipelayanankantordesa.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class KelurahanModel (
    @SerializedName("id_kelurahan")
    var id_kelurahan: String? = null,

    @SerializedName("judul")
    var judul: String? = null,

    @SerializedName("isi")
    var isi: String? = null,

    @SerializedName("tanggal")
    var tanggal: String? = null,

    @SerializedName("narasumber")
    var narasumber: String? = null,

    @SerializedName("gambar")
    var gambar: String? = null,

    @SerializedName("kelurahan")
    var kelurahan: String? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id_kelurahan)
        parcel.writeString(judul)
        parcel.writeString(isi)
        parcel.writeString(tanggal)
        parcel.writeString(narasumber)
        parcel.writeString(gambar)
        parcel.writeString(kelurahan)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<KelurahanModel> {
        override fun createFromParcel(parcel: Parcel): KelurahanModel {
            return KelurahanModel(parcel)
        }

        override fun newArray(size: Int): Array<KelurahanModel?> {
            return arrayOfNulls(size)
        }
    }
}