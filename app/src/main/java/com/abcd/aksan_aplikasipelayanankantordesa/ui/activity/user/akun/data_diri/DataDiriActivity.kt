package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.akun.data_diri

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.abcd.aksan_aplikasipelayanankantordesa.R
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.UserModel
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ActivityDataDiriBinding
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.AlertDialogAkunBinding
import com.abcd.aksan_aplikasipelayanankantordesa.utils.KataAcak
import com.abcd.aksan_aplikasipelayanankantordesa.utils.LoadingAlertDialog
import com.abcd.aksan_aplikasipelayanankantordesa.utils.SharedPreferencesLogin
import com.abcd.aksan_aplikasipelayanankantordesa.utils.TanggalDanWaktu
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@AndroidEntryPoint
class DataDiriActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataDiriBinding
    private val viewModel: DataDiriViewModel by viewModels()
    private lateinit var sharedPreferences : SharedPreferencesLogin
    private var kataAcak = KataAcak()
    private var tanggalDanWaktu = TanggalDanWaktu()
    private var selectedDate: Date? = null
    private var tempUser: UserModel? = null
    private var tempDialogAkun: AlertDialog? = null

    @Inject lateinit var loading: LoadingAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataDiriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavTopBar()
        setButton()
        setSharedPreferences()
        getUpdateData()
    }

    private fun setButton() {
        binding.apply {
            btnUbahData.setOnClickListener{
                showDialogUpdateData()
            }
            navTopBar.ivBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun setNavTopBar() {
        binding.navTopBar.apply {
            tvTitle.text = "Data Diri"
            ivNavDrawer.visibility = View.GONE
            ivBack.visibility = View.VISIBLE
        }
    }

    private fun setSharedPreferences() {
        sharedPreferences = SharedPreferencesLogin(this@DataDiriActivity)
        setData()
    }

    private fun setData(){
        try {
            binding.apply {
                tvNama.text = sharedPreferences.getNama()
                tvAlamat.text = sharedPreferences.getAlamat()
                tvNomorHp.text = sharedPreferences.getNomorHp()
                tvNoKtp.text = sharedPreferences.getNoKtp()
                tvNoKK.text = sharedPreferences.getNoKK()
                tvTempatLahir.text = sharedPreferences.getTempatLahir()
                tvTanggalLahir.text = sharedPreferences.getTanggalLahir()
                tvJenisKelamin.text = sharedPreferences.getJenisKelamin()
                tvPassword.text = kataAcak.setPassword(sharedPreferences.getPassword().length)
            }
        } catch (ex: Exception){
            Toast.makeText(this@DataDiriActivity, ex.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDialogUpdateData(){
        val view = AlertDialogAkunBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(this@DataDiriActivity)
        alertDialog.setView(view.root)
            .setCancelable(false)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            etEditNama.setText(sharedPreferences.getNama())
            etEditAlamat.setText(sharedPreferences.getAlamat())
            etEditNomorHp.setText(sharedPreferences.getNomorHp())
            etEditNoKtp.setText(sharedPreferences.getNoKtp())
            etEditNoKK.setText(sharedPreferences.getNoKK())
            etEditTempatLahir.setText(sharedPreferences.getTempatLahir())
            etEditTanggalLahir.text = sharedPreferences.getTanggalLahir()
            etEditPassword.setText(sharedPreferences.getPassword())
            when(sharedPreferences.getJenisKelamin()){
                "Laki-laki"-> rbLakiLaki.isChecked = true
                "Perempuan"-> rbPerempuan.isChecked = true
            }

            etEditTanggalLahir.setOnClickListener {
                showDatePicker(view, sharedPreferences.getTanggalLahir())
            }

            var selectedGender = ""
            rgGender.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbLakiLaki -> selectedGender = "Laki-laki"
                    R.id.rbPerempuan -> selectedGender = "Perempuan"
                }
            }

            btnSimpan.setOnClickListener {
                tempDialogAkun = dialogInputan

                var cek = false
                if(etEditNama.toString().isEmpty()){
                    etEditNama.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditAlamat.toString().isEmpty()){
                    etEditAlamat.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditNomorHp.toString().isEmpty()){
                    etEditNomorHp.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditNoKtp.toString().isEmpty()){
                    etEditNoKtp.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditNoKK.toString().isEmpty()){
                    etEditNoKK.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditTempatLahir.toString().isEmpty()){
                    etEditTempatLahir.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditTanggalLahir.text.toString() == resources.getString(R.string.ket_klik_tanggal)){
                    etEditTanggalLahir.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditPassword.toString().isEmpty()){
                    etEditPassword.error = "Tidak Boleh Kosong"
                    cek = true
                }

                if(!cek){
                    val nama = etEditNama.text.toString()
                    val alamat = etEditAlamat.text.toString()
                    val nomorHp = etEditNomorHp.text.toString()
                    val noKtp = etEditNoKtp.text.toString()
                    val noKK = etEditNoKK.text.toString()
                    val tempatLahir = etEditTempatLahir.text.toString()
                    val tanggalLahir = etEditTanggalLahir.text.toString()
                    val jenisKelamin = selectedGender
                    val password = etEditPassword.text.toString()
                    val noKtpLama = sharedPreferences.getNoKtp()

                    tempUser = UserModel(
                        sharedPreferences.getIdUser(),
                        nama, alamat, nomorHp, noKtp, noKK, tempatLahir,
                        tanggalLahir, jenisKelamin, password
                    )
                    postUpdateData(
                        sharedPreferences.getIdUser(),
                        nama, alamat, nomorHp, noKtp, noKK, tempatLahir,
                        tanggalLahir, jenisKelamin, password, noKtpLama
                    )
                }

            }
            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
                tempDialogAkun = null
            }
        }
    }

    private fun postUpdateData(
        idUser: Int,
        nama: String,
        alamat: String,
        nomorHp: String,
        noKtp: String,
        noKK: String,
        tempatLahir: String,
        tanggalLahir: String,
        jenisKelamin: String,
        password: String,
        noKtpLama: String
    ) {
        viewModel.postUpdateDataDiri(
            idUser, nama, alamat, nomorHp, noKtp, noKK, tempatLahir,
            tanggalLahir, jenisKelamin, password, noKtpLama
        )
    }

    private fun getUpdateData(){
        viewModel.getResponseUpdateDataDiri.observe(this@DataDiriActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@DataDiriActivity)
                is UIState.Failure-> setFailureUpdateData(result.message)
                is UIState.Success-> setSuccessUpdateData(result.data)
            }
        }
    }

    private fun setSuccessUpdateData(data: ResponseModel) {
        if(data.status == "0"){
            Toast.makeText(this@DataDiriActivity, "Berhasil", Toast.LENGTH_SHORT).show()
            tempDialogAkun?.dismiss()
            tempDialogAkun = null
            setSharedPreferencesData(tempUser!!)
            tempUser = null
        } else{
            Toast.makeText(this@DataDiriActivity, data.message_response, Toast.LENGTH_SHORT).show()
        }

        loading.alertDialogCancel()
    }

    private fun setSharedPreferencesData(data: UserModel){
        sharedPreferences.setLogin(
            data.idUser!!, data.nama!!, data.alamat!!, data.nomorHp!!, data.no_ktp!!, data.no_kk!!, data.tempat_lahir!!, data.tanggal_lahir!!,
            data.jenis_kelamin!!, data.password!!, "user"
        )
        setData()
    }

    private fun setFailureUpdateData(message: String) {
        Toast.makeText(this@DataDiriActivity, message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun showDatePicker(view: AlertDialogAkunBinding, tanggal: String) {
        val listTanggal = tanggal.split("-")
        val vTahun = listTanggal[0].trim().toInt()
        val vBulan = listTanggal[1].trim().toInt()-1 // 0=jan, 1= feb, dst
        val vTanggal = listTanggal[2].trim().toInt()

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.set(vTahun, vBulan, vTanggal) // Tahun, Bulan, Tanggal

        val selectedDateInMillis = calendar.timeInMillis

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih Tanggal Lahir")
            .setSelection(selectedDateInMillis)
            .build()

//        val datePicker = MaterialDatePicker.Builder.datePicker()
//            .setTitleText("Pilih Tanggal Lahir")
//            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
//            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            selectedDate = Date(selection)
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            view.etEditTanggalLahir.text = dateFormat.format(selectedDate!!)
        }

        datePicker.show(supportFragmentManager, "DATE_PICKER")
    }

}