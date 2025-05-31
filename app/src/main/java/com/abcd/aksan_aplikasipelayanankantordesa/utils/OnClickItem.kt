package com.abcd.aksan_aplikasipelayanankantordesa.utils

import android.view.View
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.UserModel

interface OnClickItem {
    interface ClickPesanan{
        fun clickPesanan(
            idPemesanan: Int
        )
    }

    interface ClickRiwayatPesanan{
        fun clickRiwayatPesanan(
            idPemesanan: Int
        )
    }

    interface ClickAkun{
        fun clickItemSetting(akun: UserModel, it: View)
        fun clickItemAlamat(alamat: String, it: View)
    }

    // Wedding organizer
    // Vendor

    interface ClickAdminWeddingOrganizer{
        fun clickItemNama(title:String, nama: String)
        fun clickItemAlamat(title:String, alamat: String)
        fun clickItemUsername(title:String, username: String)
        fun clickItemDeskripsi(title:String, deskripsi: String)
        fun clickItemGambar(title:String, gambar: String)
        fun clickItemSetting(wo: UserModel, it: View)
    }

    interface ClickAdminUser{
        fun clickItemNama(title:String, nama: String)
        fun clickItemAlamat(title:String, alamat: String)
        fun clickItemUsername(title:String, username: String)
        fun clickItemSetting(wo: UserModel, it: View)
    }

}