package com.abcd.aksan_aplikasipelayanankantordesa.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class UserModel (
    @SerializedName("id_user")
    var idUser: Int? = null,

    @SerializedName("nama")
    var nama: String? = null,

    @SerializedName("alamat")
    var alamat: String? = null,

    @SerializedName("nomor_hp")
    var nomorHp: String? = null,

    @SerializedName("no_ktp")
    var no_ktp: String? = null,

    @SerializedName("no_kk")
    var no_kk: String? = null,

    @SerializedName("tempat_lahir")
    var tempat_lahir: String? = null,

    @SerializedName("tanggal_lahir")
    var tanggal_lahir: String? = null,

    @SerializedName("jenis_kelamin")
    var jenis_kelamin: String? = null,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("sebagai")
    var sebagai: String? = null,
)