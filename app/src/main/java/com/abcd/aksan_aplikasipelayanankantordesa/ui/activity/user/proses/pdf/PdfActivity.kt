package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.proses.pdf

import android.os.Bundle
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

    private fun setDataSebelumnya() {
        val i = intent.extras
        if(i != null){
            val link = i.getString("pdf")!!
            binding.apply {
//                pdfView.initWithUrl(
//                    url = "https://e-portofolio.web.id/pelayanan-kantor-desa/document/7371234567890/106/7371234567890-106-1.pdf",
//                    PdfQuality.FAST,
//                )

                pdfView.initWithUrl(
                    url = link,
                    PdfQuality.ENHANCED,
                    PdfEngine.GOOGLE
                )
            }
        }
    }
}