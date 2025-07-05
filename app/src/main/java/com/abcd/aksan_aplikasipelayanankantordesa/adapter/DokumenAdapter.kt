package com.abcd.aksan_aplikasipelayanankantordesa.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.abcd.aksan_aplikasipelayanankantordesa.R
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.DokumenModel
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ItemListDokumenBinding
import com.abcd.aksan_aplikasipelayanankantordesa.utils.Constant
import com.abcd.aksan_aplikasipelayanankantordesa.utils.OnClickItem
import com.abcd.aksan_aplikasipelayanankantordesa.utils.SharedPreferencesLogin
import com.bumptech.glide.Glide

class DokumenAdapter(
    private val listDokumen: List<DokumenModel>,
    private val click: OnClickItem.ClickDokumen,
    private val idBerkas: Int
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
        val sharedPreferences = SharedPreferencesLogin(holder.itemView.context)

        holder.binding.apply {
            tvDokumen.text = dokumen.dokumen

            if(dokumen.format == "jpg" || dokumen.format=="jpeg" || dokumen.format=="png"){
                Glide.with(holder.itemView.context)
                    .load("${Constant.LOCATION_FILE}/${sharedPreferences.getNoKtp()}/$idBerkas/${dokumen.file}") // URL Gambar
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.icon_dokumen)
                    .into(ivDokumen) // imageView mana yang akan diterapkan

                Log.d("DokumenTAG", "onBindViewHolder: ${Constant.LOCATION_FILE}${sharedPreferences.getNoKtp()}/$idBerkas/${dokumen.file}")
            }

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