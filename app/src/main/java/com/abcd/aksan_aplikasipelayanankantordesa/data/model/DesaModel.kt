package com.abcd.aksan_aplikasipelayanankantordesa.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class DesaModel (
    @SerializedName("id_desa")
    var id_desa: Int? = null,

    @SerializedName("desa")
    var desa: String? = null,

    @SerializedName("alamat")
    var alamat: String? = null,

    @SerializedName("nomor_hp")
    var nomor_hp: String? = null,

    @SerializedName("kecamatan")
    var kecamatan: String? = null,

    @SerializedName("kode_pos")
    var kode_pos: String? = null,

    @SerializedName("nama_kades")
    var nama_kades: String? = null,

    @SerializedName("jumlah_penduduk")
    var jumlah_penduduk: String? = null,

    @SerializedName("kode_bps")
    var kode_bps: String? = null,

    @SerializedName("username")
    var username: String? = null,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("sebagai")
    var sebagai: String? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
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
        parcel.writeValue(id_desa)
        parcel.writeString(desa)
        parcel.writeString(alamat)
        parcel.writeString(nomor_hp)
        parcel.writeString(kecamatan)
        parcel.writeString(kode_pos)
        parcel.writeString(nama_kades)
        parcel.writeString(jumlah_penduduk)
        parcel.writeString(kode_bps)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(sebagai)
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