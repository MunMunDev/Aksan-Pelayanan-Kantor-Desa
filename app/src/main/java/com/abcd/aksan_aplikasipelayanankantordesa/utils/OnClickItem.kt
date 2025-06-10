package com.abcd.aksan_aplikasipelayanankantordesa.utils

import android.view.View
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BeritaModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BerkasModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.DokumenModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.UserModel

interface OnClickItem {
    interface ClickBerita{
        fun clickBerita(
            berita: BeritaModel
        )
    }

    interface ClickBerkas{
        fun clickBerkas(
            berkas: BerkasModel
        )
    }

    interface ClickDokumen{
        fun clickGambarDokumen(
            dokumen: DokumenModel
        )
        fun clickUploadDokumen(
            dokumen: DokumenModel
        )
    }
}