package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.UserModel
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ActivityLoginBinding
import com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.register.RegisterActivity
import com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.main.MainActivity
import com.abcd.aksan_aplikasipelayanankantordesa.utils.LoadingAlertDialog
import com.abcd.aksan_aplikasipelayanankantordesa.utils.SharedPreferencesLogin
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    @Inject
    lateinit var loading: LoadingAlertDialog
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setSharedPreferencesLogin()
        getUser()
    }

    private fun setSharedPreferencesLogin() {
        sharedPreferencesLogin = SharedPreferencesLogin(this@LoginActivity)
    }

    private fun setupListeners() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (validateInput()) {
                    performLogin()
                }
            }

            tvRegister.setOnClickListener {
                navigateToRegister()
            }
        }
    }

    private fun validateInput(): Boolean {
        binding.apply {
            if (etNomorKtp.text.toString().isEmpty()) {
                etNomorKtp.error = "Nomor KTP harus diisi"
                return false
            }
            if (etPassword.text.toString().isEmpty()) {
                etPassword.error = "Password harus diisi"
                return false
            }
        }
        return true
    }

    private fun performLogin() {
        var noKtp: String
        var password: String
        binding.apply {
            noKtp = etNomorKtp.text.toString()
            password = etPassword.text.toString()
        }
        fetchUser(noKtp, password)
    }

    private fun fetchUser(noKtp: String, password: String){
        viewModel.fetchUser(noKtp, password)
    }

    private fun getUser(){
        viewModel.getUser().observe(this@LoginActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@LoginActivity)
                is UIState.Success-> setSuccessFetchUser(result.data)
                is UIState.Failure-> setFailureFetchUser(result.message)
            }
        }
    }

    private fun setSuccessFetchUser(data: UserModel) {
        if(checkFetchUser(data)){
            setDataSharedPreferences(data)
        } else{
            Toast.makeText(this@LoginActivity, "Data tidak ditemukan \n" +
                    "Pastikan No KTP dan Password Terdaftar ada", Toast.LENGTH_SHORT).show()
        }
        loading.alertDialogCancel()
    }

    private fun setFailureFetchUser(message: String) {
        Toast.makeText(this@LoginActivity, "Data tidak ditemukan \n" +
                "Pastikan No KTP dan Password Terdaftar ada", Toast.LENGTH_SHORT).show()
        Log.d("LoginTAG", "setFailureFetchUser: $message")
        loading.alertDialogCancel()
    }

    private fun checkFetchUser(data: UserModel): Boolean{
        return data.idUser != null
    }

    private fun setDataSharedPreferences(data: UserModel){
        try {
            data.apply {
                sharedPreferencesLogin.setLogin(
                    idUser!!, nama!!, alamat!!, nomorHp!!, no_ktp!!, no_kk!!, tempat_lahir!!,
                    tanggal_lahir!!, jenis_kelamin!!, password!!, sebagai!!
                )
                Toast.makeText(this@LoginActivity, "Berhasil", Toast.LENGTH_SHORT).show()
                moveToMainActivity(sebagai!!)
            }
        } catch (ex: Exception){
            Toast.makeText(this@LoginActivity, ex.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun moveToMainActivity(sebagai: String){
        if(sebagai == "user"){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        } else{
            Toast.makeText(this@LoginActivity, "Data tidak ditemukan \n" +
                    "Pastikan No KTP dan Password Terdaftar ada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}