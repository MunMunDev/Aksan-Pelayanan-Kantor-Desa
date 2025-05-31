package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.register

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.abcd.aksan_aplikasipelayanankantordesa.R
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ActivityRegisterBinding
import com.google.android.material.datepicker.MaterialDatePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private var selectedDate: Date? = null
    private var ktpUri: Uri? = null
    private var kkUri: Uri? = null
    private var photoUri: Uri? = null

    private var nameKtpUri = ""
    private var nameKkUri = ""
    private var namePhotoUri = ""

    private val getKtpContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            ktpUri = it
            nameKtpUri = getNameFile(ktpUri!!)
            binding.btnUploadKtp.text = nameKtpUri
        }
    }

    private val getKkContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            kkUri = it
            nameKkUri = getNameFile(kkUri!!)
            binding.btnUploadKk.text = nameKkUri
        }
    }

    private val getPhotoContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            photoUri = it
            namePhotoUri = getNameFile(photoUri!!)
            binding.btnUploadPhoto.text = namePhotoUri
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showFirstPage()
        setupViews()
        setupButton()
    }

    private fun setupViews() {
        binding.apply {
            // Setup gender radio buttons
            rgGender.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbLakiLaki -> selectedGender = "Laki-laki"
                    R.id.rbPerempuan -> selectedGender = "Perempuan"
                }
            }
        }
    }

    private fun setupButton() {
        binding.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }

            btnTanggalLahir.setOnClickListener {
                showDatePicker()
            }

            btnUploadKtp.setOnClickListener {
                getKtpContent.launch("application/pdf")
            }

            btnUploadKk.setOnClickListener {
                getKkContent.launch("application/pdf")
            }

            btnUploadPhoto.setOnClickListener {
                getPhotoContent.launch("image/*")
            }

            btnLanjutkan.setOnClickListener {
                if (validateFirstPage()) {
                    // Save first page data
                    saveFirstPageData()
                    // Show second page
                    showSecondPage()
                }
            }

            btnRegistrasi.setOnClickListener {
                if (validateSecondPage()) {
                    // Complete registration
                    completeRegistration()
                }
            }
        }
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih Tanggal Lahir")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            selectedDate = Date(selection)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.btnTanggalLahir.text = dateFormat.format(selectedDate!!)
        }

        datePicker.show(supportFragmentManager, "DATE_PICKER")
    }

    private fun validateFirstPage(): Boolean {
        binding.apply {
            if (etNama.text.toString().isEmpty()) {
                etNama.error = "Nama harus diisi"
                return false
            }
            if (etAlamat.text.toString().isEmpty()) {
                etAlamat.error = "Alamat harus diisi"
                return false
            }
            if (etNomorHp.text.toString().isEmpty()) {
                etNomorHp.error = "Nomor HP harus diisi"
                return false
            }
            if (etNomorKtp.text.toString().isEmpty()) {
                etNomorKtp.error = "Nomor KTP harus diisi"
                return false
            }
            if (etNomorKk.text.toString().isEmpty()) {
                etNomorKk.error = "Nomor KK harus diisi"
                return false
            }
            if (selectedDate == null) {
                Toast.makeText(this@RegisterActivity, "Pilih tanggal lahir", Toast.LENGTH_SHORT).show()
                return false
            }
            if (selectedGender == null) {
                Toast.makeText(this@RegisterActivity, "Pilih jenis kelamin", Toast.LENGTH_SHORT).show()
                return false
            }
            if (etPassword.text.toString().isEmpty()) {
                etPassword.error = "Password harus diisi"
                return false
            }
        }
        return true
    }

    private fun validateSecondPage(): Boolean {
        if (ktpUri == null) {
            Toast.makeText(this, "Upload KTP terlebih dahulu", Toast.LENGTH_SHORT).show()
            return false
        }
        if (kkUri == null) {
            Toast.makeText(this, "Upload KK terlebih dahulu", Toast.LENGTH_SHORT).show()
            return false
        }
        if (photoUri == null) {
            Toast.makeText(this, "Upload foto terlebih dahulu", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun saveFirstPageData() {
        // Save first page data to shared preferences or temporary storage
        // Implementation depends on your data storage strategy
    }

    private fun showFirstPage() {
        binding.apply {
            firstPageLayout.visibility = View.VISIBLE
            secondPageLayout.visibility = View.GONE
        }
    }

    private fun showSecondPage() {
        binding.apply {
            firstPageLayout.visibility = View.GONE
            secondPageLayout.visibility = View.VISIBLE
        }
    }

    private fun completeRegistration() {
        // Implement registration logic here
        // Upload files and save user data
        Log.d("RegisterTAG", "completeRegistration: $ktpUri")
        Log.d("RegisterTAG", "completeRegistration: $kkUri")
        Log.d("RegisterTAG", "completeRegistration: $photoUri")

        Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
//        finish()

        val post = convertStringToMultipartBody("register_user")
        var nama: RequestBody
        var alamat: RequestBody
        var nomorHp: RequestBody
        var noKtp: RequestBody
        var noKK: RequestBody
        var tempatLahir: RequestBody
        var tanggalLahir: RequestBody
        var jenisKelamin: RequestBody
        var password: RequestBody
        var ktp: MultipartBody.Part?
        var kk: MultipartBody.Part?
        var photo: MultipartBody.Part?
        binding.apply {
            nama = convertStringToMultipartBody(etNama.text.toString())
            alamat = convertStringToMultipartBody(etAlamat.text.toString())
            nomorHp = convertStringToMultipartBody(etNomorHp.text.toString())
            noKtp = convertStringToMultipartBody(etNomorKtp.text.toString())
            noKK = convertStringToMultipartBody(etNomorKk.text.toString())
            tempatLahir = convertStringToMultipartBody(etTempatLahir.text.toString())
            tanggalLahir = convertStringToMultipartBody(btnTanggalLahir.text.toString())
            jenisKelamin = convertStringToMultipartBody(selectedGender!!)
            password = convertStringToMultipartBody(etPassword.text.toString())

            ktp = uploadDataToStorage(ktpUri, nameKtpUri, "ktp")
            kk = uploadDataToStorage(kkUri, nameKkUri, "kk")
            photo = uploadDataToStorage(photoUri, namePhotoUri, "ktp")
        }
        postRegister(
            post, nama, alamat, nomorHp, noKtp, noKK,
            tempatLahir, tanggalLahir, jenisKelamin, password,
            ktp!!, kk!!, photo!!    // file
        )
    }

    private fun postRegister(
        post: RequestBody, nama: RequestBody, alamat: RequestBody, nomorHp: RequestBody,
        noKtp: RequestBody, noKK: RequestBody, tempatLahir: RequestBody,
        tanggalLahir: RequestBody, jenisKelamin: RequestBody, password: RequestBody,
        ktp: MultipartBody.Part, kk: MultipartBody.Part, fotoDiri: MultipartBody.Part
    ){
        viewModel.postRegister(
            post, nama, alamat, nomorHp, noKtp, noKK, tempatLahir,
            tanggalLahir, jenisKelamin, password, ktp, kk, fotoDiri
        )
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
        return RequestBody.create("multipart/form-data".toMediaTypeOrNull(), data)
    }

    companion object {
        private var selectedGender: String? = null
    }
}