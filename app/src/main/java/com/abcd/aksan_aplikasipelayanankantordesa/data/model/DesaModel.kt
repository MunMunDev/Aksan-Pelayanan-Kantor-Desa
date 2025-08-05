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
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }
}