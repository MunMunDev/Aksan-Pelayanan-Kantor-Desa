package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.layanan

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ActivityLayananBinding
import com.abcd.aksan_aplikasipelayanankantordesa.ui.fragment.user.layanan.keterangan_nikah.KeteranganNikahFragment
import com.abcd.aksan_aplikasipelayanankantordesa.utils.Constant

class LayananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLayananBinding
    private var fragmentTransaction : FragmentTransaction? = null
    private var layanan: String? = null

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
            Constant.KETERANGAN_NIKAH -> setFragment(KeteranganNikahFragment())
//            Constant.KETERANGAN_LAHIR -> setFragment()
//            Constant.KETERANGAN_USAHA -> setFragment()
//            Constant.KETERANGAN_TIDAK_MAMPU -> setFragment()
//            Constant.KETERANGAN_AKTE_KEMATIAN -> setFragment()
//            Constant.KETERANGAN_PINDAH -> setFragment()
//            Constant.KETERANGAN_IZIN_KERAMAIAN -> setFragment()
//            Constant.KETERANGAN_DOMISILI -> setFragment()
            else->{
                Toast.makeText(this@LayananActivity, "Error, Layanan Not Found", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction?.apply {
            replace(binding.flLayanan.id, fragment)
            commit()
        }
    }

}