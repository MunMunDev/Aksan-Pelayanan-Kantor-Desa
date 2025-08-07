package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.layanan

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.OpenableColumns
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ActivityLayananBinding
import com.abcd.aksan_aplikasipelayanankantordesa.utils.Constant
import com.abcd.aksan_aplikasipelayanankantordesa.utils.LoadingAlertDialog
import com.abcd.aksan_aplikasipelayanankantordesa.utils.SharedPreferencesLogin
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@AndroidEntryPoint
class LayananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLayananBinding
    private val viewModel: LayananViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferencesLogin
    private var layanan: String? = null
    @Inject lateinit var loading: LoadingAlertDialog

    private var ktpUri: MultipartBody.Part? = null
    private var ktpOrangTuaUri: MultipartBody.Part? = null
    private var kkUri: MultipartBody.Part? = null
    private var suratPengantarRtRwUri: MultipartBody.Part? = null
    private var aktaKelahiranUri: MultipartBody.Part? = null
    private var keteranganLahirDariBidanUri: MultipartBody.Part? = null
    private var buktiKepemilikanUsahaUri: MultipartBody.Part? = null
    private var keteranganPenghasilanUri: MultipartBody.Part? = null
    private var keteranganKematianUri: MultipartBody.Part? = null
    private var fotoAlmarhumUri: MultipartBody.Part? = null
    private var buktiKeteranganPindahDariTempatAsalUri: MultipartBody.Part? = null
    private var buktiKepemilikanTempatTinggalUri: MultipartBody.Part? = null
    private var pasFotoUri: MultipartBody.Part? = null

    private var idUser : RequestBody? = null
    private var rencanaKegiatan : RequestBody? = null

//    private fun setResultActivityLauncher(layanan:Int, btn:TextView): ActivityResultLauncher<String>{
//        return registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//            uri?.let {
//                when(layanan){
//                    ktp -> ktpUri = it
//                    ktpOrangTua -> ktpOrangTuaUri = it
//                    kk -> kkUri = it
//                    suratPengantarRtRw -> suratPengantarRtRwUri = it
//                    aktaKelahiran -> aktaKelahiranUri = it
//                    keteranganLahirDariBidan -> keteranganLahirDariBidanUri = it
//                    buktiKepemilikanUsaha -> buktiKepemilikanUsahaUri = it
//                    keteranganPenghasilan -> keteranganPenghasilanUri = it
//                    keteranganKematian -> keteranganKematianUri = it
//                    fotoAlmarhum -> fotoAlmarhumUri = it
//                    buktiKeteranganPindahDariTempatAsal -> buktiKeteranganPindahDariTempatAsalUri = it
//                    buktiKepemilikanTempatTinggal -> buktiKepemilikanTempatTinggalUri = it
//                    pasFoto -> pasFotoUri = it
//                }
//                btn.text = getNameFile(it)
//            }
//        }
//    }

    private fun capitalizeWords(words:String):String{
        return words.split(" ").joinToString(" ") {
            it.lowercase().replaceFirstChar { c -> c.uppercase() }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDataSebelumnya()
        setSharedPreferences()
        setButton()
        permissionDocument()
        getResponse()
    }

    private fun setSharedPreferences() {
        sharedPreferences = SharedPreferencesLogin(this@LayananActivity)
        idUser = convertStringToMultipartBody(sharedPreferences.getIdUser().toString())
    }

    private fun permissionDocument() {
        if(checkPermission()){
            Toast.makeText(this@LayananActivity, "Granted", Toast.LENGTH_SHORT).show()
        } else{
            requestPermission()
        }
    }

    private fun setButton() {
        setButtonUpload()
        setButtonLayanan()
    }

    private fun setButtonUpload() {
        binding.apply {
            btnUploadBerkas.setOnClickListener {
                rencanaKegiatan = convertStringToMultipartBody(tvRencanaKegiatan.text.toString())

                when(layanan){
                    Constant.KETERANGAN_NIKAH -> {
                        if(checkFile(Constant.KETERANGAN_NIKAH)){
                            val post = convertStringToMultipartBody(capitalizeWords(Constant.KETERANGAN_NIKAH))
                            postKeteranganNikah(
                                post, idUser!!, ktpUri!!, kkUri!!, suratPengantarRtRwUri!!,
                                aktaKelahiranUri!!, pasFotoUri!!
                            )
                        }
                    }
                    Constant.KETERANGAN_LAHIR -> {
                        if(checkFile(Constant.KETERANGAN_LAHIR)){
                            val post = convertStringToMultipartBody(capitalizeWords(Constant.KETERANGAN_LAHIR))
                            postKeteranganLahir(
                                post, idUser!!, ktpOrangTuaUri!!, kkUri!!, keteranganLahirDariBidanUri!!
                            )
                        }
                    }
                    Constant.KETERANGAN_USAHA -> {
                        if(checkFile(Constant.KETERANGAN_USAHA)){
                            val post = convertStringToMultipartBody(capitalizeWords(Constant.KETERANGAN_USAHA))
                            postKeteranganUsaha(
                                post, idUser!!, ktpUri!!, kkUri!!, suratPengantarRtRwUri!!,
                                buktiKepemilikanUsahaUri!!, pasFotoUri!!
                            )
                        }
                    }
                    Constant.KETERANGAN_TIDAK_MAMPU -> {
                        if(checkFile(Constant.KETERANGAN_TIDAK_MAMPU)){
                            val post = convertStringToMultipartBody(capitalizeWords(Constant.KETERANGAN_TIDAK_MAMPU))
                            postKeteranganTidakMampu(
                                post, idUser!!, ktpUri!!, kkUri!!, suratPengantarRtRwUri!!,
                                keteranganPenghasilanUri!!, pasFotoUri!!
                            )
                        }
                    }
                    Constant.KETERANGAN_AKTE_KEMATIAN -> {
                        if(checkFile(Constant.KETERANGAN_AKTE_KEMATIAN)){
                            val post = convertStringToMultipartBody(capitalizeWords(Constant.KETERANGAN_AKTE_KEMATIAN))
                            postKeteranganAkteKematian(
                                post, idUser!!, ktpUri!!, kkUri!!, suratPengantarRtRwUri!!,
                                keteranganKematianUri!!, fotoAlmarhumUri!!
                            )
                        }
                    }
                    Constant.KETERANGAN_PINDAH -> {
                        if(checkFile(Constant.KETERANGAN_PINDAH)){
                            val post = convertStringToMultipartBody(capitalizeWords(Constant.KETERANGAN_PINDAH))
                            postKeteranganPindah(
                                post, idUser!!, ktpUri!!, kkUri!!, buktiKeteranganPindahDariTempatAsalUri!!,
                                pasFotoUri!!
                            )
                        }
                    }
                    Constant.KETERANGAN_IZIN_KERAMAIAN -> {
                        if(checkFile(Constant.KETERANGAN_IZIN_KERAMAIAN)){
                            val post = convertStringToMultipartBody(capitalizeWords(Constant.KETERANGAN_IZIN_KERAMAIAN))
                            postKeteranganIzinKeramaian(
                                post, idUser!!, ktpUri!!, kkUri!!, suratPengantarRtRwUri!!,
                                rencanaKegiatan!!
                            )
                        }
                    }
                    Constant.KETERANGAN_DOMISILI -> {
                        if(checkFile(Constant.KETERANGAN_DOMISILI)){
                            val post = convertStringToMultipartBody(capitalizeWords(Constant.KETERANGAN_DOMISILI))
                            postKeteranganDomisili(
                                post, idUser!!, ktpUri!!, kkUri!!, suratPengantarRtRwUri!!,
                                buktiKepemilikanTempatTinggalUri!!, pasFotoUri!!
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setButtonLayanan() {
        binding.apply {
            tvKtp.setOnClickListener {
                pickImageFile(KTP)
            }
            tvKtpOrangTua.setOnClickListener {
                pickImageFile(KTP_ORANG_TUA)
            }
            tvKk.setOnClickListener {
                pickImageFile(KK)
            }
            tvSuratPengantarRtRw.setOnClickListener {
                pickImageFile(SURAT_PENGANTAR_RT_RW)
            }
            tvAktaKelahiran.setOnClickListener {
                pickImageFile(AKTE_KELAHIRAN)
            }
            tvKeteranganLahirDariBidan.setOnClickListener {
                pickImageFile(KETERANGAN_LAHIR_DARI_BIDAN)
            }
            tvBuktiKepemilikanUsaha.setOnClickListener {
                pickImageFile(BUKTI_KEPEMILIKAN_USAHA)
            }
            tvKeteranganPenghasilan.setOnClickListener {
                pickImageFile(KETERANGAN_PENGHASILAN)
            }
            tvKeteranganKematian.setOnClickListener {
                pickImageFile(KETERANGAN_KEMATIAN)
            }
            tvFotoAlmarhum.setOnClickListener {
                pickImageFile(FOTO_ALMARHUM)
            }
            tvBuktiKeteranganPindahDariTempatAsal.setOnClickListener {
                pickImageFile(BUKTI_KETERANGAN_PINDAH_DARI_TEMPAT_ASAL)
            }
            tvBuktiKepemilikanTempatTinggal.setOnClickListener {
                pickImageFile(BUKTI_KEPEMILIKAN_TEMPAT_TINGGAL)
            }
            tvPasFoto.setOnClickListener {
                pickImageFile(PAS_FOTO)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setNavTopBar(suratPengantar: String) {
        binding.navTopBar.apply {
            tvTitle.text = "Surat Pengantar ${capitalizeWords(suratPengantar)}"
            ivNavDrawer.visibility = View.GONE
            ivBack.visibility = View.VISIBLE

            ivBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun setDataSebelumnya() {
        val bundle = intent.extras
        if(bundle!=null){
            layanan = bundle.getString("layanan")
            layanan?.let {
                setNavTopBar(layanan!!)
                setLayanan(layanan!!)
            }
        }
    }

    private fun setLayanan(layanan: String) {
        when(layanan){
            Constant.KETERANGAN_NIKAH -> setKeteranganNikah()
            Constant.KETERANGAN_LAHIR -> setKeteranganLahir()
            Constant.KETERANGAN_USAHA -> setKeteranganUsaha()
            Constant.KETERANGAN_TIDAK_MAMPU -> setKeteranganTidakMampu()
            Constant.KETERANGAN_AKTE_KEMATIAN -> setKeteranganAkteKematian()
            Constant.KETERANGAN_PINDAH -> setKeteranganPindah()
            Constant.KETERANGAN_IZIN_KERAMAIAN -> setKeteranganIzinKeramaian()
            Constant.KETERANGAN_DOMISILI -> setKeteranganDomisili()
            else->{
                Toast.makeText(this@LayananActivity, "Error, Layanan Not Found", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setKeteranganNikah() {
        binding.apply {
            llBerkasKtp.visibility = View.VISIBLE
            llBerkasKk.visibility = View.VISIBLE
            llBerkasSuratPengantarRtRw.visibility = View.VISIBLE
            llBerkasAktaKelahiran.visibility = View.VISIBLE
            llBerkasPasFoto.visibility = View.VISIBLE
        }
    }

    private fun setKeteranganLahir() {
        binding.apply {
            llBerkasKtpOrangTua.visibility = View.VISIBLE
            llBerkasKk.visibility = View.VISIBLE
            llBerkasKeteranganLahirDariBidan.visibility = View.VISIBLE
        }
    }

    private fun setKeteranganUsaha() {
        binding.apply {
            llBerkasKtp.visibility = View.VISIBLE
            llBerkasKk.visibility = View.VISIBLE
            llBerkasSuratPengantarRtRw.visibility = View.VISIBLE
            llBerkasBuktiKepemilikanUsaha.visibility = View.VISIBLE
            llBerkasPasFoto.visibility = View.VISIBLE
        }
    }

    private fun setKeteranganTidakMampu() {
        binding.apply {
            llBerkasKtp.visibility = View.VISIBLE
            llBerkasKk.visibility = View.VISIBLE
            llBerkasSuratPengantarRtRw.visibility = View.VISIBLE
            llBerkasKeteranganPenghasilan.visibility = View.VISIBLE
            llBerkasPasFoto.visibility = View.VISIBLE
        }
    }

    private fun setKeteranganAkteKematian() {
        binding.apply {
            llBerkasKtp.visibility = View.VISIBLE
            llBerkasKk.visibility = View.VISIBLE
            llBerkasSuratPengantarRtRw.visibility = View.VISIBLE
            llBerkasKeteranganKematian.visibility = View.VISIBLE
            llBerkasFotoAlmarhum.visibility = View.VISIBLE
        }
    }

    private fun setKeteranganPindah() {
        binding.apply {
            llBerkasKtp.visibility = View.VISIBLE
            llBerkasKk.visibility = View.VISIBLE
            llBerkasBuktiKeteranganPindahDariTempatAsal.visibility = View.VISIBLE
            llBerkasPasFoto.visibility = View.VISIBLE
        }
    }

    private fun setKeteranganIzinKeramaian() {
        binding.apply {
            llBerkasKtp.visibility = View.VISIBLE
            llBerkasKk.visibility = View.VISIBLE
            llBerkasSuratPengantarRtRw.visibility = View.VISIBLE
            llBerkasRencanaKegiatan.visibility = View.VISIBLE
        }
    }

    private fun setKeteranganDomisili() {
        binding.apply {
            llBerkasKtp.visibility = View.VISIBLE
            llBerkasKk.visibility = View.VISIBLE
            llBerkasSuratPengantarRtRw.visibility = View.VISIBLE
            llBerkasBuktiKepemilikanTempatTinggal.visibility = View.VISIBLE
            llBerkasPasFoto.visibility = View.VISIBLE
        }
    }

    private fun postKeteranganNikah(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, aktaKelahiran: MultipartBody.Part,
        pasFoto: MultipartBody.Part,
    ){
        viewModel.postKeteranganNikah(
            post, idUser, ktp, kk, suratPengantarRtRw, aktaKelahiran, pasFoto
        )
    }

    private fun postKeteranganLahir(
        post: RequestBody, idUser: RequestBody, ktpOrangTua: MultipartBody.Part, kk: MultipartBody.Part,
        keteranganLahirDariBidan: MultipartBody.Part,
    ){
        viewModel.postKeteranganLahir(
            post, idUser, ktpOrangTua, kk, keteranganLahirDariBidan
        )
    }

    private fun postKeteranganUsaha(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, buktiKepemilikanUsaha: MultipartBody.Part,
        pasFoto: MultipartBody.Part
    ){
        viewModel.postKeteranganUsaha(
            post, idUser, ktp, kk, suratPengantarRtRw, buktiKepemilikanUsaha, pasFoto
        )
    }

    private fun postKeteranganTidakMampu(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, buktiKepemilikanUsaha: MultipartBody.Part,
        pasFoto: MultipartBody.Part
    ){
        viewModel.postKeteranganTidakMampu(
            post, idUser, ktp, kk, suratPengantarRtRw, buktiKepemilikanUsaha, pasFoto
        )
    }

    private fun postKeteranganAkteKematian(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        keteranganRtRw: MultipartBody.Part, keteranganKematian: MultipartBody.Part,
        fotoAlmarhum: MultipartBody.Part,
    ){
        viewModel.postKeteranganAkteKematian(
            post, idUser, ktp, kk, keteranganRtRw, keteranganKematian, fotoAlmarhum
        )
    }

    private fun postKeteranganPindah(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        keteranganPindahDariTempatAsal: MultipartBody.Part, pasFoto: MultipartBody.Part
    ){
        viewModel.postKeteranganPindah(
            post, idUser, ktp, kk, keteranganPindahDariTempatAsal, pasFoto
        )
    }

    private fun postKeteranganIzinKeramaian(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part,kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, rencanaKegiatan: RequestBody
    ){
        viewModel.postKeteranganIzinKeramaian(
            post, idUser, ktp, kk, suratPengantarRtRw, rencanaKegiatan
        )
    }

    private fun postKeteranganDomisili(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, buktiKepemilikanTempatTinggal: MultipartBody.Part,
        pasFoto: MultipartBody.Part,
    ){
        viewModel.postKeteranganDomisili(
            post, idUser, ktp, kk, suratPengantarRtRw, buktiKepemilikanTempatTinggal, pasFoto
        )
    }


    private fun getResponse(){
        viewModel.getResponse.observe(this@LayananActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@LayananActivity)
                is UIState.Success-> setSuccessPostSuratPengantar(result.data)
                is UIState.Failure-> setFailurePostSuratPengantar(result.message)
            }
        }
    }

    private fun setFailurePostSuratPengantar(message: String) {
        loading.alertDialogCancel()
        Toast.makeText(this@LayananActivity, message, Toast.LENGTH_SHORT).show()
        Log.d("LayananTAG", "setSuccessPostSuratPengantar: $message")
    }

    private fun setSuccessPostSuratPengantar(data: ResponseModel) {
        try{
            if(data.status == "0"){
                Toast.makeText(
                    this@LayananActivity,
                    "Berhasil. Silahkan Tunggu Dokumen Anda Dari Kantor Desa. Pantau Terus pada halaman progress",
                    Toast.LENGTH_SHORT
                ).show()
            } else{
                Toast.makeText(this@LayananActivity, data.message_response, Toast.LENGTH_SHORT).show()
                Log.d("LayananTAG", "setSuccessPostSuratPengantar: ${data.message_response}")
            }
        } catch (ex: Exception){
            Toast.makeText(this@LayananActivity, ex.message, Toast.LENGTH_SHORT).show()
            Log.d("LayananTAG", "setSuccessPostSuratPengantar: ${ex.message}")
        }
        loading.alertDialogCancel()
    }


    // Storage
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
    private fun requestPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if (Environment.isExternalStorageManager()) {
                startActivity(Intent(this, LayananActivity::class.java))
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

    @Suppress("DEPRECATION")
    private fun pickImageFile(layanan: Int) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "application/pdf"))
        }
        startActivityForResult(intent, layanan)
    }

    @Suppress("DEPRECATION")
    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n    " +
            "  which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            val nameImage = getNameFile(data.data!!)
            val uri = data.data
            // Mendapatkan URI file PDF yang dipilih
            binding.apply {
                when(requestCode){
                    KTP -> {
                        ktpUri = uploadDataToStorage(uri, nameImage, "ktp")
                        tvKtp.text = nameImage
                    }
                    KTP_ORANG_TUA -> {
                        ktpOrangTuaUri = uploadDataToStorage(uri, nameImage, "ktp_orang_tua")
                        tvKtpOrangTua.text = nameImage
                    }
                    KK -> {
                        kkUri = uploadDataToStorage(uri, nameImage, "kk")
                        tvKk.text = nameImage
                    }
                    SURAT_PENGANTAR_RT_RW -> {
                        suratPengantarRtRwUri = uploadDataToStorage(uri, nameImage, "surat_pengantar_rt_rw")
                        tvSuratPengantarRtRw.text = nameImage
                    }
                    AKTE_KELAHIRAN -> {
                        aktaKelahiranUri = uploadDataToStorage(uri, nameImage, "akta_kelahiran")
                        tvAktaKelahiran.text = nameImage
                    }
                    KETERANGAN_LAHIR_DARI_BIDAN -> {
                        keteranganLahirDariBidanUri = uploadDataToStorage(uri, nameImage, "keterangan_lahir_dari_bidan")
                        tvKeteranganLahirDariBidan.text = nameImage
                    }
                    BUKTI_KEPEMILIKAN_USAHA -> {
                        buktiKepemilikanUsahaUri = uploadDataToStorage(uri, nameImage, "bukti_kepemelikan_usaha")
                        tvBuktiKepemilikanUsaha.text = nameImage
                    }
                    KETERANGAN_PENGHASILAN -> {
                        keteranganPenghasilanUri = uploadDataToStorage(uri, nameImage, "keterangan_penghasilan")
                        tvKeteranganPenghasilan.text = nameImage
                    }
                    KETERANGAN_KEMATIAN -> {
                        keteranganKematianUri = uploadDataToStorage(uri, nameImage, "keterangan_kematian")
                        tvKeteranganKematian.text = nameImage
                    }
                    FOTO_ALMARHUM -> {
                        fotoAlmarhumUri = uploadDataToStorage(uri, nameImage, "foto_almarhum")
                        tvFotoAlmarhum.text = nameImage
                    }
                    BUKTI_KETERANGAN_PINDAH_DARI_TEMPAT_ASAL -> {
                        buktiKeteranganPindahDariTempatAsalUri = uploadDataToStorage(uri, nameImage, "keterangan_pindah_dari_tempat_asal")
                        tvBuktiKeteranganPindahDariTempatAsal.text = nameImage
                    }
                    BUKTI_KEPEMILIKAN_TEMPAT_TINGGAL -> {
                        buktiKepemilikanTempatTinggalUri = uploadDataToStorage(uri, nameImage, "bukti_kepemilikan_tempat_tinggal")
                        tvBuktiKepemilikanTempatTinggal.text = nameImage
                    }
                    PAS_FOTO -> {
                        pasFotoUri = uploadDataToStorage(uri, nameImage, "ktp_orang_tua")
                        tvPasFoto.text = nameImage
                    }
                }
            }
        }
    }

    @SuppressLint("Recycle")
    private fun uploadDataToStorage(fileUri: Uri?, fileFileName: String, nameAPI:String): MultipartBody.Part? {
        var filePart : MultipartBody.Part? = null
        fileUri?.let {
            val file = contentResolver.openInputStream(fileUri)?.readBytes()

            if (file != null) {
                filePart = convertFileToMultipartBody(file, fileFileName, nameAPI)
            }
        }
        return filePart
    }

    private fun convertFileToMultipartBody(file: ByteArray, pdfFileName: String, nameAPI:String): MultipartBody.Part{
        val requestFile = file.toRequestBody("application/pdf".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData(nameAPI, pdfFileName, requestFile)

        return filePart
    }

    private fun getNameFile(uri: Uri): String {
        val cursor = contentResolver.query(uri, null, null, null, null)
        val nameIndex = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor?.moveToFirst()
        val name = cursor?.getString(nameIndex!!)
        cursor?.close()
        return name!!
    }

    private fun convertStringToMultipartBody(data: String): RequestBody {
        return data.toRequestBody("multipart/form-data".toMediaTypeOrNull())
    }

    private fun checkFile(check: String): Boolean{
        when(check){
            Constant.KETERANGAN_NIKAH -> {
                binding.apply {
                    if (ktpUri == null) {
                        tvKtp.error = "File ini harus diisi"
                        return false
                    }
                    else if (kkUri == null) {
                        tvKk.error = "File ini harus diisi"
                        return false
                    }
                    else if (suratPengantarRtRwUri == null) {
                        tvSuratPengantarRtRw.error = "File ini harus diisi"
                        return false
                    }
                    else if (aktaKelahiranUri == null) {
                        tvAktaKelahiran.error = "File ini harus diisi"
                        return false
                    }
                    else if (pasFotoUri == null) {
                        tvPasFoto.error = "File ini harus diisi"
                        return false
                    } else{
                        return true
                    }
                }
            }
            Constant.KETERANGAN_LAHIR -> {
                binding.apply {
                    if (ktpOrangTuaUri == null) {
                        tvKtpOrangTua.error = "File ini harus diisi"
                        return false
                    }
                    else if (kkUri == null) {
                        tvKk.error = "File ini harus diisi"
                        return false
                    }
                    else if (keteranganLahirDariBidanUri == null) {
                        tvKeteranganLahirDariBidan.error = "File ini harus diisi"
                        return false
                    } else{
                        return true
                    }
                }
            }
            Constant.KETERANGAN_USAHA -> {
                binding.apply {
                    if (ktpUri == null) {
                        tvKtp.error = "File ini harus diisi"
                        return false
                    }
                    else if (kkUri == null) {
                        tvKk.error = "File ini harus diisi"
                        return false
                    }
                    else if (suratPengantarRtRwUri == null) {
                        tvSuratPengantarRtRw.error = "File ini harus diisi"
                        return false
                    }
                    else if (buktiKepemilikanUsahaUri == null) {
                        tvBuktiKepemilikanUsaha.error = "File ini harus diisi"
                        return false
                    }
                    else if (pasFotoUri == null) {
                        tvPasFoto.error = "File ini harus diisi"
                        return false
                    } else{
                        return true
                    }
                }
            }
            Constant.KETERANGAN_TIDAK_MAMPU -> {
                binding.apply {
                    if (ktpUri == null) {
                        tvKtp.error = "File ini harus diisi"
                        return false
                    }
                    else if (kkUri == null) {
                        tvKk.error = "File ini harus diisi"
                        return false
                    }
                    else if (suratPengantarRtRwUri == null) {
                        tvSuratPengantarRtRw.error = "File ini harus diisi"
                        return false
                    }
                    else if (keteranganPenghasilanUri == null) {
                        tvKeteranganPenghasilan.error = "File ini harus diisi"
                        return false
                    }
                    else if (pasFotoUri == null) {
                        tvPasFoto.error = "File ini harus diisi"
                        return false
                    } else{
                        return true
                    }
                }
            }
            Constant.KETERANGAN_AKTE_KEMATIAN -> {
                binding.apply {
                    if (ktpUri == null) {
                        tvKtp.error = "File ini harus diisi"
                        return false
                    }
                    else if (kkUri == null) {
                        tvKk.error = "File ini harus diisi"
                        return false
                    }
                    else if (suratPengantarRtRwUri == null) {
                        tvSuratPengantarRtRw.error = "File ini harus diisi"
                        return false
                    }
                    else if (keteranganKematianUri == null) {
                        tvKeteranganKematian.error = "File ini harus diisi"
                        return false
                    }
                    else if (fotoAlmarhumUri == null) {
                        tvFotoAlmarhum.error = "File ini harus diisi"
                        return false
                    } else{
                        return true
                    }
                }
            }
            Constant.KETERANGAN_PINDAH -> {
                binding.apply {
                    if (ktpUri == null) {
                        tvKtp.error = "File ini harus diisi"
                        return false
                    }
                    else if (kkUri == null) {
                        tvKk.error = "File ini harus diisi"
                        return false
                    }
                    else if (buktiKeteranganPindahDariTempatAsalUri == null) {
                        tvBuktiKeteranganPindahDariTempatAsal.error = "File ini harus diisi"
                        return false
                    }
                    else if (pasFotoUri == null) {
                        tvPasFoto.error = "File ini harus diisi"
                        return false
                    } else{
                        return true
                    }
                }
            }
            Constant.KETERANGAN_IZIN_KERAMAIAN -> {
                binding.apply {
                    if (ktpUri == null) {
                        tvKtp.error = "File ini harus diisi"
                        return false
                    }
                    else if (kkUri == null) {
                        tvKk.error = "File ini harus diisi"
                        return false
                    }
                    else if (suratPengantarRtRwUri == null) {
                        tvSuratPengantarRtRw.error = "File ini harus diisi"
                        return false
                    }
                    else if (tvRencanaKegiatan.text.toString().isEmpty()) {
                        tvRencanaKegiatan.error = "Isi Rencana Kegiatan Anda"
                        return false
                    } else{
                        return true
                    }
                }
            }
            Constant.KETERANGAN_DOMISILI -> {
                binding.apply {
                    if (ktpUri == null) {
                        tvKtp.error = "File ini harus diisi"
                        return false
                    }
                    else if (kkUri == null) {
                        tvKk.error = "File ini harus diisi"
                        return false
                    }
                    else if (suratPengantarRtRwUri == null) {
                        tvSuratPengantarRtRw.error = "File ini harus diisi"
                        return false
                    }
                    else if (buktiKepemilikanTempatTinggalUri == null) {
                        tvBuktiKepemilikanTempatTinggal.error = "File ini harus diisi"
                        return false
                    }
                    else if (pasFotoUri == null) {
                        tvPasFoto.error = "File ini harus diisi"
                        return false
                    } else{
                        return true
                    }
                }
            }
            else -> return false
        }
    }

    companion object {
        private const val KTP = 1
        private const val KTP_ORANG_TUA = 2
        private const val KK = 3
        private const val SURAT_PENGANTAR_RT_RW = 4
        private const val AKTE_KELAHIRAN = 5
        private const val KETERANGAN_LAHIR_DARI_BIDAN = 6
        private const val BUKTI_KEPEMILIKAN_USAHA = 7
        private const val KETERANGAN_PENGHASILAN = 8
        private const val KETERANGAN_KEMATIAN = 9
        private const val FOTO_ALMARHUM = 10
        private const val BUKTI_KETERANGAN_PINDAH_DARI_TEMPAT_ASAL = 11
        private const val BUKTI_KEPEMILIKAN_TEMPAT_TINGGAL = 12
        private const val PAS_FOTO = 13
    }
}