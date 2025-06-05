package com.abcd.aksan_aplikasipelayanankantordesa.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class BeritaModel (
    @SerializedName("id_berita")
    var id_berita: Int? = null,

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
    var kelurahan: KelurahanModel? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(KelurahanModel::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_berita)
        parcel.writeString(id_kelurahan)
        parcel.writeString(judul)
        parcel.writeString(isi)
        parcel.writeString(tanggal)
        parcel.writeString(narasumber)
        parcel.writeString(gambar)
        parcel.writeParcelable(kelurahan, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BeritaModel> {
        override fun createFromParcel(parcel: Parcel): BeritaModel {
            return BeritaModel(parcel)
        }

        override fun newArray(size: Int): Array<BeritaModel?> {
            return arrayOfNulls(size)
        }
    }
}