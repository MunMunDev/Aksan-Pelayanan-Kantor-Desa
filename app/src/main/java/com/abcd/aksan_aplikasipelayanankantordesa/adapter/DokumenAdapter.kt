package com.abcd.aksan_aplikasipelayanankantordesa.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.abcd.aksan_aplikasipelayanankantordesa.R
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.DokumenModel
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ItemListDokumenBinding
import com.abcd.aksan_aplikasipelayanankantordesa.utils.Constant
import com.abcd.aksan_aplikasipelayanankantordesa.utils.OnClickItem
import com.bumptech.glide.Glide

class DokumenAdapter(
    private val listDokumen: List<DokumenModel>,
    private val click: OnClickItem.ClickDokumen,
): RecyclerView.Adapter<DokumenAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemListDokumenBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListDokumenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listDokumen.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dokumen = listDokumen[position]

        holder.binding.apply {
            tvDokumen.text = dokumen.dokumen

            Glide.with(holder.itemView.context)
                .load("${Constant.LOCATION_GAMBAR_BERITA}${dokumen.file}") // URL Gambar
                .placeholder(R.drawable.loading)
                .error(R.drawable.icon_dokumen)
                .into(ivDokumen) // imageView mana yang akan diterapkan

            // Button
            ivDokumen.setOnClickListener{
                click.clickGambarDokumen(dokumen)
            }
            btnUploadDokumen.setOnClickListener{
                click.clickUploadDokumen(dokumen)
            }
        }
    }
}