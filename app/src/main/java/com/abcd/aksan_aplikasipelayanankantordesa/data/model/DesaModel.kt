package com.abcd.aksan_aplikasipelayanankantordesa.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class DesaModel (
    @SerializedName("id_desa")
    var id_desa: String? = null,

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

    @SerializedName("desa")
    var desa: String? = null,
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
        parcel.writeString(id_desa)
        parcel.writeString(judul)
        parcel.writeString(isi)
        parcel.writeString(tanggal)
        parcel.writeString(narasumber)
        parcel.writeString(gambar)
        parcel.writeString(desa)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DesaModel> {
        override fun createFromParcel(parcel: Parcel): DesaModel {
            return DesaModel(parcel)
        }

        override fun newArray(size: Int): Array<DesaModel?> {
            return arrayOfNulls(size)
        }
    }
}