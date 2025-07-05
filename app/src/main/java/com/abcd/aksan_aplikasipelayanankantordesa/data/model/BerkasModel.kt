package com.abcd.aksan_aplikasipelayanankantordesa.data.model

import com.google.gson.annotations.SerializedName

// Jenis Dokumen ini akan terus dipanggil di setiap desa
// Jenis dokumen setiap desa bisa sama dan berbeda
// Desa yang ambil jenis dokumen
class BerkasModel (
    @SerializedName("id_berkas")
    var id_berkas: Int? = null,

    @SerializedName("id_user")
    var id_user: String? = null,

    @SerializedName("id_desa")
    var id_desa: String? = null,

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

    @SerializedName("desa")
    var desa: DesaModel? = null,

    @SerializedName("jenis_berkas")
    var jenis_berkas: JenisBerkasModel? = null,
)