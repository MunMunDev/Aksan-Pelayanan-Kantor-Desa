package com.abcd.aksan_aplikasipelayanankantordesa.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.aksan_aplikasipelayanankantordesa.R
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BeritaModel
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ItemListBeritaBinding
import com.abcd.aksan_aplikasipelayanankantordesa.utils.Constant
import com.abcd.aksan_aplikasipelayanankantordesa.utils.OnClickItem
import com.abcd.aksan_aplikasipelayanankantordesa.utils.TanggalDanWaktu
import com.bumptech.glide.Glide
import java.util.Locale

class BeritaAdapter(
    private val listBerita: List<BeritaModel>,
    private val click: OnClickItem.ClickBerita,
    private val home: Boolean   // If true = home
): RecyclerView.Adapter<BeritaAdapter.ViewHolder>() {
    private var tanggalDanWaktu = TanggalDanWaktu()
    private var tempBerita = listBerita

    @SuppressLint("NotifyDataSetChanged", "DefaultLocale")
    fun searchData(kata: String){
        val vKata = kata.lowercase(Locale.getDefault()).trim()
        val data = listBerita.filter {
            (
                it.judul!!.lowercase().trim().contains(vKata)
                    or
                it.kelurahan!!.kelurahan!!.lowercase().trim().contains(vKata)
                    or
                (tanggalDanWaktu.konversiBulanSingkatan(it.tanggal!!.lowercase().trim())).contains(vKata)
            )
        }
        tempBerita = data
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemListBeritaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBeritaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if(home){
            if(tempBerita.size>2){
                3
            } else{
                tempBerita.size
            }
        } else{
            tempBerita.size
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val berita = tempBerita[position]

        holder.binding.apply {
            tvJudul.text = berita.judul
            tvIsiSingkat.text = berita.isi
            tvKelurahan.text = "Kel. ${berita.kelurahan!!.kelurahan}"
            tvTanggal.text = tanggalDanWaktu.konversiBulanSingkatan(berita.tanggal!!)

            Glide.with(holder.itemView.context)
                .load("${Constant.LOCATION_GAMBAR_BERITA}${berita.gambar}") // URL Gambar
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_berita_error)
                .into(ivGambar) // imageView mana yang akan diterapkan
        }

        holder.itemView.setOnClickListener{
            click.clickBerita(berita)
        }
    }
}