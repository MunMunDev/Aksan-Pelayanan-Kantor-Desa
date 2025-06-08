package com.abcd.aksan_aplikasipelayanankantordesa.ui.fragment.user.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.FragmentProfileBinding
import com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.login.LoginActivity
import com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.akun.data_diri.DataDiriActivity
import com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.berkas_tersimpan.BerkasTersimpanActivity
import com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.main.MainActivity
import com.abcd.aksan_aplikasipelayanankantordesa.utils.Constant
import com.abcd.aksan_aplikasipelayanankantordesa.utils.SharedPreferencesLogin

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var activityContext: MainActivity
    private lateinit var context: Context
    private var constant = Constant
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContext = (activity as MainActivity)
        context = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        setButton()
        setSharedPreferences()

        return binding.root
    }


    private fun setSharedPreferences() {
        sharedPreferencesLogin = SharedPreferencesLogin(context)

        setDataShared()
    }

    private fun setDataShared() {
        binding.apply {
            tvNama.text = sharedPreferencesLogin.getNama()
        }
    }

    private fun setButton() {
        setButtonPersonal()
        setButtonLogout()
        setButtonBerkasTersimpan()
    }

    private fun setButtonLogout() {
        binding.btnLogout.setOnClickListener {
            sharedPreferencesLogin.setLogin(
                0, "", "", "", "", "", "",
                "", "", "", ""
            )
            startActivity(Intent(context, LoginActivity::class.java))
            activityContext.finish()
        }
    }

    private fun setButtonPersonal() {
        binding.apply {
            btnDataDiri.setOnClickListener {
                startActivity(Intent(context, DataDiriActivity::class.java))
            }
        }
    }

    private fun setButtonBerkasTersimpan() {
        binding.apply {
            btnKeteranganNikah.setOnClickListener {
                moveToActivity(constant.KETERANGAN_NIKAH)
            }
            btnKeteranganLahir.setOnClickListener {
                moveToActivity(constant.KETERANGAN_LAHIR)
            }
            btnKeteranganUsaha.setOnClickListener {
                moveToActivity(constant.KETERANGAN_USAHA)
            }
            btnKeteranganTidakMampu.setOnClickListener {
                moveToActivity(constant.KETERANGAN_TIDAK_MAMPU)
            }
            btnKeteranganAkteKematian.setOnClickListener {
                moveToActivity(constant.KETERANGAN_AKTE_KEMATIAN)
            }
            btnKeteranganPindah.setOnClickListener {
                moveToActivity(constant.KETERANGAN_PINDAH)
            }
            btnKeteranganIzinKeramaian.setOnClickListener {
                moveToActivity(constant.KETERANGAN_IZIN_KERAMAIAN)
            }
            btnKeteranganDomisili.setOnClickListener {
                moveToActivity(constant.KETERANGAN_DOMISILI)
            }
        }
    }

    private fun moveToActivity(keterangan: String){
        val intent = Intent(context, BerkasTersimpanActivity::class.java)
        intent.putExtra("keterangan", keterangan)
        startActivity(intent)
    }
}