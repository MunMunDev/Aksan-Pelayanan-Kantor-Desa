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
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ActivityRegisterBinding
import com.abcd.aksan_aplikasipelayanankantordesa.utils.LoadingAlertDialog
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private var selectedDate: Date? = null
    private var loading = LoadingAlertDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupButton()
        getRegister()
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

            btnRegistrasi.setOnClickListener {
                // Complete registration
                if(validaeData()){
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

    private fun validaeData(): Boolean {
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

    private fun completeRegistration() {

        binding.apply {
            val nama = etNama.text.toString()
            val alamat = etAlamat.text.toString()
            val nomorHp = etNomorHp.text.toString()
            val noKtp = etNomorKtp.text.toString()
            val noKK = etNomorKk.text.toString()
            val tempatLahir = etTempatLahir.text.toString()
            val tanggalLahir = btnTanggalLahir.text.toString()
            val jenisKelamin = selectedGender!!
            val password = etPassword.text.toString()


            postRegister(
                nama, alamat, nomorHp, noKtp, noKK,
                tempatLahir, tanggalLahir, jenisKelamin, password,
            )
        }
    }

    private fun postRegister(
        nama: String, alamat: String, nomorHp: String,
        noKtp: String, noKK: String, tempatLahir: String,
        tanggalLahir: String, jenisKelamin: String, password: String,
    ){
        viewModel.postRegister(
            nama, alamat, nomorHp, noKtp, noKK, tempatLahir,
            tanggalLahir, jenisKelamin, password
        )
    }

    private fun getRegister(){
        viewModel.getRegister().observe(this@RegisterActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@RegisterActivity)
                is UIState.Success-> setSuccessRegister(result.data)
                is UIState.Failure-> setFailureRegister(result.message)
            }
        }
    }

    private fun setSuccessRegister(data: ResponseModel) {
        loading.alertDialogCancel()
        if(data.status=="0"){
            Toast.makeText(this@RegisterActivity, "Berhasil Registrasi", Toast.LENGTH_SHORT).show()
            finish()
        } else{
            Toast.makeText(this@RegisterActivity, data.message_response, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFailureRegister(message: String) {
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    companion object {
        private var selectedGender: String? = null
    }
}