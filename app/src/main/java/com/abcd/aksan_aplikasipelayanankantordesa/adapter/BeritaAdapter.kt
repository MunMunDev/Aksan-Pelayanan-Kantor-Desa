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
import com.bumptech.glide.Glide

class BeritaAdapter(
    private val list: List<BeritaModel>,
    private val click: OnClickItem.ClickBerita,
    private val home: Boolean   // If true = home
): RecyclerView.Adapter<BeritaAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemListBeritaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBeritaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if(home){
            if(list.size>2){
                3
            } else{
                list.size
            }
        } else{
            list.size
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val berita = list[position]

        holder.binding.apply {
            tvJudul.text = berita.judul
            tvIsiSingkat.text = berita.isi
            tvKelurahan.text = "Kel. ${berita.kelurahan!!.kelurahan}"

            Glide.with(holder.itemView.context)
                .load("${Constant.LOCATION_GAMBAR_BERITA}${berita.gambar}") // URL Gambar
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_berita_error)
                .into(ivGambar) // imageView mana yang akan diterapkan

            Log.d("DetailTAG", "onBindViewHolder: ${Constant.LOCATION_GAMBAR_BERITA}${berita.gambar}")
        }
        holder.itemView.setOnClickListener{
            click.clickBerita(berita)
        }
    }
}