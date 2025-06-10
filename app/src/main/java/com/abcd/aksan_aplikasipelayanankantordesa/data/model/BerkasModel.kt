package com.abcd.aksan_aplikasipelayanankantordesa.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

// Jenis Dokumen ini akan terus dipanggil di setiap desa
// Jenis dokumen setiap desa bisa sama dan berbeda
// Desa yang ambil jenis dokumen
class BerkasModel (
    @SerializedName("id_berkas")
    var id_berkas: Int? = null,

    @SerializedName("id_user")
    var id_user: String? = null,

    @SerializedName("id_kelurahan")
    var id_kelurahan: String? = null,

    @SerializedName("id_jenis_berkas")
    var id_jenis_berkas: String? = null,

    @SerializedName("tanggal")
    var tanggal: String? = null,

    @SerializedName("waktu")
    var waktu: String? = null,

    @SerializedName("file")
    var file: String? = null,

    @SerializedName("user")
    var user: UserModel? = null,

    @SerializedName("kelurahan")
    var kelurahan: KelurahanModel? = null,

    @SerializedName("jenis_berkas")
    var jenis_berkas: JenisBerkasModel? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(UserModel::class.java.classLoader),
        parcel.readParcelable(KelurahanModel::class.java.classLoader),
        parcel.readParcelable(JenisBerkasModel::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_berkas)
        parcel.writeString(id_user)
        parcel.writeString(id_kelurahan)
        parcel.writeString(id_jenis_berkas)
        parcel.writeString(tanggal)
        parcel.writeString(waktu)
        parcel.writeString(file)
        parcel.writeParcelable(user, flags)
        parcel.writeParcelable(kelurahan, flags)
        parcel.writeParcelable(jenis_berkas, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BerkasModel> {
        override fun createFromParcel(parcel: Parcel): BerkasModel {
            return BerkasModel(parcel)
        }

        override fun newArray(size: Int): Array<BerkasModel?> {
            return arrayOfNulls(size)
        }
    }
}