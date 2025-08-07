package com.abcd.aksan_aplikasipelayanankantordesa.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

// Brekas Tersimpan
class DokumenModel (
    @SerializedName("id_dokumen")
    var id_dokumen: Int? = null,

    @SerializedName("id_user")
    var id_user: String? = null,

    @SerializedName("id_desa")
    var id_desa: String? = null,

    @SerializedName("id_berkas")
    var id_berkas: String? = null,

    @SerializedName("dokumen")
    var dokumen: String? = null,

    @SerializedName("format")
    var format: String? = null,

    @SerializedName("file")
    var file: String? = null,

    @SerializedName("ket")
    var ket: Int? = null,

    @SerializedName("pesan_desa")
    var pesan_desa: String? = null,

    @SerializedName("user")
    var user: UserModel? = null,

    @SerializedName("desa")
    var desa: DesaModel? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readParcelable(UserModel::class.java.classLoader),
        parcel.readParcelable(DesaModel::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_dokumen)
        parcel.writeString(id_user)
        parcel.writeString(id_desa)
        parcel.writeString(id_berkas)
        parcel.writeString(dokumen)
        parcel.writeString(format)
        parcel.writeString(file)
        parcel.writeValue(ket)
        parcel.writeString(pesan_desa)
        parcel.writeParcelable(user, flags)
        parcel.writeParcelable(desa, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DokumenModel> {
        override fun createFromParcel(parcel: Parcel): DokumenModel {
            return DokumenModel(parcel)
        }

        override fun newArray(size: Int): Array<DokumenModel?> {
            return arrayOfNulls(size)
        }
    }
}