package com.abcd.aksan_aplikasipelayanankantordesa.utils

import android.content.Context

class SharedPreferencesLogin(val context: Context) {
    val keyIdUser = "keyIdUser"
    val keyNama = "keyNama"
    val keyAlamat = "keyAlamat"
    val keyNomorHp = "keyNomorHp"
    val keyNoKtp = "keyNoKtp"
    val keyNoKK = "keyNoKK"
    val keyTempatLahir = "keyTempatLahir"
    val keyTanggalLahir = "keyTanggalLahir"
    val keyJenisKelamin = "keyJenisKelamin"
    val keyPassword = "keyPassword"
    val keySebagai = "keySebagai"
    val keyLogo = "keyLogo"
    val keyDeskripsi = "keyDeskripsi"

    var sharedPref = context.getSharedPreferences("sharedpreference_login", Context.MODE_PRIVATE)
    var editPref = sharedPref.edit()

    fun setLogin(
        id_user:Int, nama:String, alamat:String, nomorHp:String, noKtp:String,
        noKK:String, tempatLahir:String, tanggalLahir:String, jenisKelamin:String,
        password:String, sebagai:String
    ){
        editPref.apply{
            putInt(keyIdUser, id_user)
            putString(keyNama, nama)
            putString(keyNomorHp, nomorHp)
            putString(keyAlamat, alamat)
            putString(keyNoKtp, noKtp)
            putString(keyNoKK, noKK)
            putString(keyTempatLahir, tempatLahir)
            putString(keyTanggalLahir, tanggalLahir)
            putString(keyJenisKelamin, jenisKelamin)
            putString(keyPassword, password)
            putString(keySebagai, sebagai)

            apply()
        }
    }

    fun getIdUser(): Int{
        return sharedPref.getInt(keyIdUser, 0)
    }
    fun getNama():String{
        return sharedPref.getString(keyNama, "").toString()
    }
    fun getAlamat():String{
        return sharedPref.getString(keyAlamat, "").toString()
    }
    fun getNomorHp():String{
        return sharedPref.getString(keyNomorHp, "").toString()
    }
    fun getNoKtp():String{
        return sharedPref.getString(keyNoKtp, "").toString()
    }
    fun getNoKK(): String{
        return sharedPref.getString(keyNoKK, "").toString()
    }
    fun getTempatLahir():String{
        return sharedPref.getString(keyTempatLahir, "").toString()
    }
    fun getTanggalLahir(): String{
        return sharedPref.getString(keyTanggalLahir, "").toString()
    }
    fun getJenisKelamin():String{
        return sharedPref.getString(keyJenisKelamin, "").toString()
    }
    fun getPassword(): String{
        return sharedPref.getString(keyPassword, "").toString()
    }
    fun getSebagai(): String{
        return sharedPref.getString(keySebagai, "").toString()
    }
}