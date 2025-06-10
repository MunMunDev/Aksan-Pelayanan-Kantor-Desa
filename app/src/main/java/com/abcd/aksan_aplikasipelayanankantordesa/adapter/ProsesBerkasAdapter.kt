package com.abcd.aksan_aplikasipelayanankantordesa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.aksan_aplikasipelayanankantordesa.R
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BerkasModel
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ItemListBerkasBinding
import com.abcd.aksan_aplikasipelayanankantordesa.utils.Constant
import com.abcd.aksan_aplikasipelayanankantordesa.utils.OnClickItem
import com.abcd.aksan_aplikasipelayanankantordesa.utils.TanggalDanWaktu
import com.bumptech.glide.Glide
import java.util.Locale

class ProsesBerkasAdapter(
    private val listBerkas: List<BerkasModel>,
    private val click: OnClickItem.ClickBerkas,
): RecyclerView.Adapter<ProsesBerkasAdapter.ViewHolder>() {
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

    class ViewHolder(val binding: ItemListBerkasBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBerkasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tempBerkas.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val berkas = tempBerkas[position]

        holder.binding.apply {
            tvJenisBerkas.text = "Surat Pengantar ${berkas.jenis_berkas!!.jenis_berkas}"
            tvKelurahan.text = "Kelurahan ${berkas.kelurahan!!.kelurahan}"
            tvTanggal.text = tanggalDanWaktu.konversiBulanSingkatan(berkas.tanggal!!)

            Glide.with(holder.itemView.context)
                .asGif()
                .load(R.drawable.gif_loading) // URL Gambar
                .error(R.drawable.gif_loading)
                .into(ivBerkas) // imageView mana yang akan diterapkan

        }

        holder.itemView.setOnClickListener{
            click.clickBerkas(berkas)
        }
    }
}