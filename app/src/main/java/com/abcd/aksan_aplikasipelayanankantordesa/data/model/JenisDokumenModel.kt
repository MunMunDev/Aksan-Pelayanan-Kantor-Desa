package com.abcd.aksan_aplikasipelayanankantordesa.data.model

import com.google.gson.annotations.SerializedName

// Jenis Dokumen ini akan terus dipanggil di setiap desa
// Jenis dokumen setiap desa bisa sama dan berbeda
// Desa yang ambil jenis dokumen
class JenisDokumenModel (
    @SerializedName("id_jenisd_dokumen")
    var id_jenisd_dokumen: Int? = null,

    @SerializedName("jenis_dokumen")
    var jenis_dokumen: String? = null,
)