package com.abcd.aksan_aplikasipelayanankantordesa.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

// Jenis Dokumen ini akan terus dipanggil di setiap desa
// Jenis dokumen setiap desa bisa sama dan berbeda
// Desa yang ambil jenis dokumen
class JenisBerkasModel (
    @SerializedName("id_jenis_berkas")
    var id_jenis_berkas: Int? = null,

    @SerializedName("jenis_berkas")
    var jenis_berkas: String? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_jenis_berkas)
        parcel.writeString(jenis_berkas)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<JenisBerkasModel> {
        override fun createFromParcel(parcel: Parcel): JenisBerkasModel {
            return JenisBerkasModel(parcel)
        }

        override fun newArray(size: Int): Array<JenisBerkasModel?> {
            return arrayOfNulls(size)
        }
    }
}