package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.proses.detail

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.OpenableColumns
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.abcd.aksan_aplikasipelayanankantordesa.R
import com.abcd.aksan_aplikasipelayanankantordesa.adapter.DokumenAdapter
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BerkasModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.DokumenModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ActivityProsesBerkasDetailBinding
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.AlertDialogImageBinding
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.AlertDialogUploadDokumenBinding
import com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.pdf.PdfActivity
import com.abcd.aksan_aplikasipelayanankantordesa.utils.Constant
import com.abcd.aksan_aplikasipelayanankantordesa.utils.LoadingAlertDialog
import com.abcd.aksan_aplikasipelayanankantordesa.utils.OnClickItem
import com.abcd.aksan_aplikasipelayanankantordesa.utils.SharedPreferencesLogin
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.URLConnection
import java.util.ArrayList

@AndroidEntryPoint
class ProsesBerkasDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProsesBerkasDetailBinding
    private val viewModel: ProsesBerkasDetailViewModel by viewModels()
    private lateinit var dokumenAdapter : DokumenAdapter
    private var berkas: BerkasModel? = null
    private var idBerkas: Int = 0
    private lateinit var sharedPreferences: SharedPreferencesLogin
    private var file: MultipartBody.Part? = null
    private var tempView: AlertDialogUploadDokumenBinding? = null
    private var loading = LoadingAlertDialog()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProsesBerkasDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = SharedPreferencesLogin(this@ProsesBerkasDetailActivity)
        permissionDocument()
        retrievePreviousData()
        setNavTopBar()
        getDokumen()
        getUpdateDokumen()
    }

    private fun permissionDocument() {
        if(checkPermission()){
//            Toast.makeText(this@ProsesBerkasDetailActivity, "Granted", Toast.LENGTH_SHORT).show()
        } else{
            requestPermission()
        }
    }

    @Suppress("DEPRECATION")
    private fun retrievePreviousData() {
        val bundle = intent.extras
        if(bundle != null){
            berkas = bundle.getParcelable("berkas")
            idBerkas = berkas?.id_berkas!!
            fetchDokumen(idBerkas)
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
                    setShowUploadDokumen(dokumen)
                }
            }, berkas?.id_berkas!!)
            rvDokumen.apply {
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = dokumenAdapter
            }
        }
    }

    private fun setShowUploadDokumen(dokumen: DokumenModel) {
        val view = AlertDialogUploadDokumenBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this@ProsesBerkasDetailActivity)
        alertDialog.setView(view.root)
            .setCancelable(false)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        tempView = view
        view.apply {
            tvTitle.text = dokumen.dokumen
            if(dokumen.catatan!!.isNotEmpty()) tvPesanDesa.text = dokumen.catatan
            tvDokumen.setOnClickListener {
                if(checkPermission()){
                    pickFile()
                }
            }
            btnSimpan.setOnClickListener {
                var cek = false
                if (tvDokumen.text.toString() == resources.getString(R.string.upload_dokumen)) {
                    tvDokumen.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(!cek){
                    val post = convertStringToMultipartBody("post_upload_dokumen")
                    val idDokumen = convertStringToMultipartBody(dokumen.id_dokumen.toString())
                    val idBerkas = convertStringToMultipartBody(dokumen.id_berkas.toString())
                    val noKtp = convertStringToMultipartBody(sharedPreferences.getNoKtp())
                    val tDokumen = convertStringToMultipartBody(dokumen.dokumen!!)

                    Log.d("ProsesBerkasTAG", "setShowUploadDokumen: ${dokumen.id_dokumen.toString()}")
                    Log.d("ProsesBerkasTAG", "setShowUploadDokumen: ${dokumen.id_berkas.toString()}")
                    Log.d("ProsesBerkasTAG", "setShowUploadDokumen: ${sharedPreferences.getNoKtp()}")

                    postUpdateDokumen(post, idDokumen, idBerkas, noKtp, tDokumen, file!!)
                    dialogInputan.dismiss()
                }
            }
            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun postUpdateDokumen(
        post: RequestBody,
        idDokumen: RequestBody,
        idBerkas: RequestBody,
        noKtp: RequestBody,
        dokumen: RequestBody,
        file: MultipartBody.Part
    ) {
        viewModel.postUploadDokumen(post, idDokumen, idBerkas, noKtp, dokumen, file)
    }

    private fun getUpdateDokumen(){
        viewModel.getUploadDokumen.observe(this@ProsesBerkasDetailActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@ProsesBerkasDetailActivity)
                is UIState.Success-> setSuccessUpdateDokumen(result.data)
                is UIState.Failure-> setFailureUpdateDokumen(result.message)
            }
        }
    }

    private fun setFailureUpdateDokumen(message: String) {
        Toast.makeText(this@ProsesBerkasDetailActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessUpdateDokumen(data: ResponseModel) {
        loading.alertDialogCancel()
        if(data.status == "0"){
            Toast.makeText(this@ProsesBerkasDetailActivity, "Berhasil. Silahkan Menunggu Verifikasi Dari Desa", Toast.LENGTH_SHORT).show()
            fetchDokumen(idBerkas)
        } else{
            Toast.makeText(this@ProsesBerkasDetailActivity, data.message_response, Toast.LENGTH_SHORT).show()
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

    //Permission
    private fun checkPermission(): Boolean{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            //Android is 11(R) or above
            Environment.isExternalStorageManager()
        }
        else{
            //Android is below 11(R)
            val write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun getNameFile(uri: Uri): String {
        val cursor = contentResolver.query(uri, null, null, null, null)
        val nameIndex = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor?.moveToFirst()
        val name = cursor?.getString(nameIndex!!)
        cursor?.close()
        return name!!
    }

    @SuppressLint("Recycle")
    private fun uploadImageToStorage(pdfUri: Uri?, pdfFileName: String): MultipartBody.Part? {
        var pdfPart : MultipartBody.Part? = null
        pdfUri?.let {
            val file = contentResolver.openInputStream(pdfUri)?.readBytes()

            if (file != null) {
                pdfPart = convertFileToMultipartBody(file, pdfFileName)
            }
        }
        return pdfPart
    }

    private fun convertFileToMultipartBody(file: ByteArray, fileName: String): MultipartBody.Part {
        // Tentukan MIME type dari ekstensi nama file
        val mimeType = URLConnection.guessContentTypeFromName(fileName) ?: "application/octet-stream"

        // Buat RequestBody dari ByteArray
        val requestFile = file.toRequestBody(mimeType.toMediaTypeOrNull())

        // Bungkus ke dalam MultipartBody.Part
        return MultipartBody.Part.createFormData("file", fileName, requestFile)
    }

    private fun convertStringToMultipartBody(data: String): RequestBody {
        return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), data)
    }

    private fun requestPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if (Environment.isExternalStorageManager()) {
                startActivity(Intent(this@ProsesBerkasDetailActivity, ProsesBerkasDetailActivity::class.java))
            } else { //request for the permission
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        } else{
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                Constant.STORAGE_PERMISSION_CODE
            )
        }
    }

    private fun pickFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "application/pdf"))
        }
        startActivityForResult(intent, Constant.STORAGE_PERMISSION_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constant.STORAGE_PERMISSION_CODE && resultCode == Activity.RESULT_OK && data != null) {
            // Mendapatkan URI file PDF yang dipilih
            val fileUri = data.data!!

            val nameImage = getNameFile(fileUri)

            tempView?.let {
                it.tvDokumen.text = nameImage
            }

            // Mengirim file PDF ke website menggunakan Retrofit
            file = uploadImageToStorage(fileUri, nameImage)
        }
    }

}