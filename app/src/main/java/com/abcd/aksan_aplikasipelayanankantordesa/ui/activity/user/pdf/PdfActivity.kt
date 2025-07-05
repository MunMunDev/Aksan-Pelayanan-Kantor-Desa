package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.pdf

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.abcd.aksan_aplikasipelayanankantordesa.R
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ActivityPdfBinding
import com.rajat.pdfviewer.PdfEngine
import com.rajat.pdfviewer.PdfQuality

class PdfActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPdfBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDataSebelumnya()
    }

    private fun setButtonDownload(link: String) {
        binding.apply {
            btnDownload.setOnClickListener {
                val intent = Intent(Intent.ACTION_WEB_SEARCH)
                intent.putExtra(
                    SearchManager.QUERY,
                    "https://e-portofolio.web.id/pelayanan-kantor-desa/print.php?$link"
                )
                startActivity(intent)
            }
        }
    }

    private fun setDataSebelumnya() {
        val i = intent.extras
        if(i != null){
            val link = i.getString("pdf")!!
            val check = i.getString("check")!!
            binding.apply {
                pdfView.initWithUrl(
                    url = link,
                    PdfQuality.ENHANCED,
                    PdfEngine.GOOGLE,
                )

                if(check=="proses"){
                    btnDownload.visibility = View.GONE
                } else if(check=="riwayat"){
                    btnDownload.visibility = View.VISIBLE
                }

                setButtonDownload(link)
            }
        }
    }
}