package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.proses.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.abcd.aksan_aplikasipelayanankantordesa.R
import com.abcd.aksan_aplikasipelayanankantordesa.adapter.DokumenAdapter
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BerkasModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.DokumenModel
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ActivityProsesBerkasDetailBinding
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.AlertDialogImageBinding
import com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.pdf.PdfActivity
import com.abcd.aksan_aplikasipelayanankantordesa.utils.Constant
import com.abcd.aksan_aplikasipelayanankantordesa.utils.LoadingAlertDialog
import com.abcd.aksan_aplikasipelayanankantordesa.utils.OnClickItem
import com.abcd.aksan_aplikasipelayanankantordesa.utils.SharedPreferencesLogin
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import javax.inject.Inject

@AndroidEntryPoint
class ProsesBerkasDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProsesBerkasDetailBinding
    private val viewModel: ProsesBerkasDetailViewModel by viewModels()
    private lateinit var dokumenAdapter : DokumenAdapter
    private var berkas: BerkasModel? = null
    private lateinit var sharedPreferences: SharedPreferencesLogin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProsesBerkasDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = SharedPreferencesLogin(this@ProsesBerkasDetailActivity)
        retrievePreviousData()
        setNavTopBar()
        getDokumen()
    }

    @Suppress("DEPRECATION")
    private fun retrievePreviousData() {
        val bundle = intent.extras
        if(bundle != null){
            berkas = bundle.getParcelable("berkas")
            fetchDokumen(berkas?.id_berkas!!)
        }
    }

    private fun setNavTopBar(){
        binding.navTopBar.apply {
            ivNavDrawer.visibility = View.GONE
            ivBack.visibility = View.VISIBLE
            tvTitle.text = berkas!!.jenis_berkas!!.jenis_berkas

            ivBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun fetchDokumen(idBerkas: Int) {
        viewModel.fetchProsesBerkasDetail(idBerkas)
    }

    private fun getDokumen(){
        viewModel.getProsesBerkasDetail.observe(this@ProsesBerkasDetailActivity){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Failure-> setFailureFetchDokumen(result.message)
                is UIState.Success-> setSuccessFetchDokumen(result.data)
            }
        }
    }

    private fun setFailureFetchDokumen(message: String) {
        Toast.makeText(this@ProsesBerkasDetailActivity, message, Toast.LENGTH_SHORT).show()
        setStopShimmer()
    }

    private fun setSuccessFetchDokumen(data: ArrayList<DokumenModel>) {
        if(data.isNotEmpty()){
            setAdapter(data)
        } else{
            Toast.makeText(this@ProsesBerkasDetailActivity, "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
        setStopShimmer()
    }

    private fun setAdapter(data: ArrayList<DokumenModel>) {
        binding.apply {
            dokumenAdapter = DokumenAdapter(data, object: OnClickItem.ClickDokumen{
                override fun clickGambarDokumen(dokumen: DokumenModel) {
                    if(dokumen.format == "jpg" || dokumen.format == "jpeg" || dokumen.format == "png"){
                        setShowImage(dokumen.dokumen!!, "${Constant.LOCATION_FILE}/${sharedPreferences.getNoKtp()}/${berkas?.id_berkas}/${dokumen.file}")
                    } else if(dokumen.format == "pdf"){
                        // Show pdf
                        val i = Intent(this@ProsesBerkasDetailActivity, PdfActivity::class.java)
                        val linkPdf = "${sharedPreferences.getNoKtp()}/${dokumen.id_berkas}/${dokumen.file}"
                        Log.d("ProsesTAG", "clickGambarDokumen: $linkPdf")
                        i.putExtra("pdf", linkPdf)
                        i.putExtra("check", "proses")
                        startActivity(i)
                    }
                }

                override fun clickUploadDokumen(dokumen: DokumenModel) {

                }
            }, berkas?.id_berkas!!)
            rvDokumen.apply {
                layoutManager = GridLayoutManager(this@ProsesBerkasDetailActivity, 2)
                adapter = dokumenAdapter
            }
        }
    }

    private fun setShowImage(deskripsi: String, image: String) {
        val view = AlertDialogImageBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@ProsesBerkasDetailActivity)
        alertDialog.setView(view.root)
            .setCancelable(false)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            tvTitle.text = deskripsi
            btnClose.setOnClickListener {
                dialogInputan.dismiss()
            }
        }

        Glide.with(this@ProsesBerkasDetailActivity)
            .load(image) // URL Gambar
            .error(R.drawable.icon_dokumen)
            .placeholder(R.drawable.loading)
            .into(view.ivShowImage) // imageView mana yang akan diterapkan

    }

    private fun setStartShimmer(){
        binding.apply {
            smDokumen.startShimmer()
            smDokumen.visibility = View.VISIBLE
            rvDokumen.visibility = View.GONE
        }
    }
    private fun setStopShimmer(){
        binding.apply {
            smDokumen.stopShimmer()
            smDokumen.visibility = View.GONE
            rvDokumen.visibility = View.VISIBLE
        }
    }
}