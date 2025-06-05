package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.berita

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abcd.aksan_aplikasipelayanankantordesa.R
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BeritaModel
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ActivityBeritaBinding
import com.abcd.aksan_aplikasipelayanankantordesa.utils.Constant
import com.abcd.aksan_aplikasipelayanankantordesa.utils.TanggalDanWaktu
import com.bumptech.glide.Glide

@Suppress("DEPRECATION")
class BeritaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeritaBinding
    private lateinit var berita : BeritaModel
    private var tanggalDanWaktu = TanggalDanWaktu()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeritaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavTopBar()
        setDataSebelumnya()
    }

    @SuppressLint("SetTextI18n")
    private fun setNavTopBar() {
        binding.navTopBar.apply {
            tvTitle.text = "Detail Berita"
            ivNavDrawer.visibility = View.GONE
            ivBack.visibility = View.VISIBLE

            ivBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun setDataSebelumnya() {
        val bundle = intent.extras
        if(bundle!=null){
            berita = bundle.getParcelable("berita")!!
            setBerita(berita)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setBerita(berita: BeritaModel) {
        binding.apply {
            try {
                tvJudul.text = berita.judul
                tvKelurahan.text = "Penerbit : Kel. ${berita.kelurahan!!.kelurahan}"
                tvTanggal.text = "|  ${tanggalDanWaktu.konversiBulanSingkatan(berita.tanggal!!)}"
                tvBerita.text = berita.isi

                Glide.with(this@BeritaActivity)
                    .load("${Constant.LOCATION_GAMBAR_BERITA}${berita.gambar}") // URL Gambar
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_berita_error)
                    .into(ivBerita) // imageView mana yang akan diterapkan

            } catch(ex: Exception){
                Toast.makeText(this@BeritaActivity, ex.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}