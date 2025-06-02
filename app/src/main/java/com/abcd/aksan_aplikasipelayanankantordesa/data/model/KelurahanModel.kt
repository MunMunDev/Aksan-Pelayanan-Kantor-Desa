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
)