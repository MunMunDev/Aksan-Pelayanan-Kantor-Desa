package com.abcd.aksan_aplikasipelayanankantordesa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.aksan_aplikasipelayanankantordesa.R
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BerkasModel
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ItemListBerkasTersimpanBinding
import com.abcd.aksan_aplikasipelayanankantordesa.utils.Constant
import com.abcd.aksan_aplikasipelayanankantordesa.utils.OnClickItem
import com.abcd.aksan_aplikasipelayanankantordesa.utils.TanggalDanWaktu
import com.bumptech.glide.Glide
import java.util.Locale

class BerkasAdapter(
    private val listBerkas: List<BerkasModel>,
    private val click: OnClickItem.ClickBerkas,
): RecyclerView.Adapter<BerkasAdapter.ViewHolder>() {
    private var tanggalDanWaktu = TanggalDanWaktu()
    private var tempBerkas = listBerkas

    @SuppressLint("NotifyDataSetChanged", "DefaultLocale")
    fun searchData(kata: String){
        val vKata = kata.lowercase(Locale.getDefault()).trim()
        val data = listBerkas.filter {
            (
                it.kelurahan!!.kelurahan!!.lowercase().trim().contains(vKata)
                    or
                (tanggalDanWaktu.konversiBulan(it.tanggal!!.lowercase().trim())).contains(vKata)
            )
        }
        tempBerkas = data
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemListBerkasTersimpanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBerkasTersimpanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tempBerkas.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val berita = tempBerkas[position]

        holder.binding.apply {
            tvKelurahan.text = "Kel. ${berita.kelurahan!!.kelurahan}"
            tvTanggal.text = tanggalDanWaktu.konversiBulanSingkatan(berita.tanggal!!)

        }

        holder.itemView.setOnClickListener{
            click.clickBerkas(berita)
        }
    }
}