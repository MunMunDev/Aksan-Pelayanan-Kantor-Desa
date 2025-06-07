package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.berkas_tersimpan

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.aksan_aplikasipelayanankantordesa.R
import com.abcd.aksan_aplikasipelayanankantordesa.adapter.BerkasAdapter
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BerkasModel
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.ActivityBerkasTersimpanBinding
import com.abcd.aksan_aplikasipelayanankantordesa.utils.Constant
import com.abcd.aksan_aplikasipelayanankantordesa.utils.OnClickItem
import com.abcd.aksan_aplikasipelayanankantordesa.utils.SharedPreferencesLogin
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class BerkasTersimpanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBerkasTersimpanBinding
    private val viewModel : BerkasTersimpanViewModel by viewModels()
    private lateinit var sharedPreferences : SharedPreferencesLogin
    private var dataPreviousActivity : String = ""
    private var constant = Constant
    private lateinit var adapter: BerkasAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerkasTersimpanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSharedPreferences()
        fetchDataPreviousActivity()
        getAllKeterangan()
    }

    private fun getAllKeterangan() {
        getKeteranganNikah()
        getKeteranganLahir()
        getKeteranganUsaha()
        getKeteranganTidakMampu()
        getKeteranganAkteKematian()
        getKeteranganPindah()
        getKeteranganIzinKeramaian()
        getKeteranganDomisili()
    }

    private fun setNavTopBar(keterangan: String){
        binding.navTopBar.apply {
            ivNavDrawer.visibility = View.GONE
            ivBack.visibility = View.VISIBLE

            tvTitle.text = keterangan
        }
    }

    private fun setSharedPreferences() {
        sharedPreferences = SharedPreferencesLogin(this@BerkasTersimpanActivity)
    }

    private fun fetchDataPreviousActivity() {
        if(intent != null){
            dataPreviousActivity = intent.getStringExtra("keterangan")!!

            val keterangan = dataPreviousActivity.split(" ").joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
            setNavTopBar(keterangan)
            fetchData(dataPreviousActivity)
        }
    }

    private fun fetchData(data: String){
        when(data){
            constant.KETERANGAN_NIKAH-> fetchKeteranganNikah(sharedPreferences.getIdUser().toString())
            constant.KETERANGAN_LAHIR-> fetchKeteranganLahir(sharedPreferences.getIdUser().toString())
            constant.KETERANGAN_USAHA-> fetchKeteranganUsaha(sharedPreferences.getIdUser().toString())
            constant.KETERANGAN_TIDAK_MAMPU-> fetchKeteranganTidakMampu(sharedPreferences.getIdUser().toString())
            constant.KETERANGAN_AKTE_KEMATIAN-> fetchKeteranganAkteKematian(sharedPreferences.getIdUser().toString())
            constant.KETERANGAN_PINDAH-> fetchKeteranganPindah(sharedPreferences.getIdUser().toString())
            constant.KETERANGAN_IZIN_KERAMAIAN-> fetchKeteranganIzinKeramaian(sharedPreferences.getIdUser().toString())
            constant.KETERANGAN_DOMISILI-> fetchKeteranganDomisili(sharedPreferences.getIdUser().toString())
        }
    }

    private fun fetchKeteranganNikah(idUser: String){
        viewModel.fetchKeteranganNikah(idUser)
    }

    private fun getKeteranganNikah(){
        viewModel.getKeteranganNikah.observe(this@BerkasTersimpanActivity){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Failure-> setFailureFetchKeteranganNikah(result.message)
                is UIState.Success-> setSuccessFetchKeteranganNikah(result.data)
            }
        }
    }

    private fun setSuccessFetchKeteranganNikah(data: ArrayList<BerkasModel>) {
        if(data.isNotEmpty()){
            setRecyclerViewSuratKeterangan(data)
        }
        else{
            Toast.makeText(this@BerkasTersimpanActivity, "Tidak Ada Data", Toast.LENGTH_SHORT).show()
        }
        setStopShimmer()
    }

    private fun setFailureFetchKeteranganNikah(message: String) {
        Toast.makeText(this@BerkasTersimpanActivity, message, Toast.LENGTH_SHORT).show()
        setStopShimmer()
    }

    private fun fetchKeteranganLahir(idUser: String){
        viewModel.fetchKeteranganLahir(idUser)
    }

    private fun getKeteranganLahir(){
        viewModel.getKeteranganLahir.observe(this@BerkasTersimpanActivity){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Failure-> setFailureFetchKeteranganLahir(result.message)
                is UIState.Success-> setSuccessFetchKeteranganLahir(result.data)
            }
        }
    }

    private fun setSuccessFetchKeteranganLahir(data: ArrayList<BerkasModel>) {
        if(data.isNotEmpty()){
            setRecyclerViewSuratKeterangan(data)
        }
        else{
            Toast.makeText(this@BerkasTersimpanActivity, "Tidak Ada Data", Toast.LENGTH_SHORT).show()
        }
        setStopShimmer()
    }

    private fun setFailureFetchKeteranganLahir(message: String) {
        Toast.makeText(this@BerkasTersimpanActivity, message, Toast.LENGTH_SHORT).show()
        setStopShimmer()
    }

    private fun fetchKeteranganUsaha(idUser: String){
        viewModel.fetchKeteranganUsaha(idUser)
    }

    private fun getKeteranganUsaha(){
        viewModel.getKeteranganUsaha.observe(this@BerkasTersimpanActivity){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Failure-> setFailureFetchKeteranganUsaha(result.message)
                is UIState.Success-> setSuccessFetchKeteranganUsaha(result.data)
            }
        }
    }

    private fun setSuccessFetchKeteranganUsaha(data: ArrayList<BerkasModel>) {
        if(data.isNotEmpty()){
            setRecyclerViewSuratKeterangan(data)
        }
        else{
            Toast.makeText(this@BerkasTersimpanActivity, "Tidak Ada Data", Toast.LENGTH_SHORT).show()
        }
        setStopShimmer()
    }

    private fun setFailureFetchKeteranganUsaha(message: String) {
        Toast.makeText(this@BerkasTersimpanActivity, message, Toast.LENGTH_SHORT).show()
        setStopShimmer()
    }

    private fun fetchKeteranganTidakMampu(idUser: String){
        viewModel.fetchKeteranganTidakMampu(idUser)
    }

    private fun getKeteranganTidakMampu(){
        viewModel.getKeteranganTidakMampu.observe(this@BerkasTersimpanActivity){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Failure-> setFailureFetchKeteranganTidakMampu(result.message)
                is UIState.Success-> setSuccessFetchKeteranganTidakMampu(result.data)
            }
        }
    }

    private fun setSuccessFetchKeteranganTidakMampu(data: ArrayList<BerkasModel>) {
        if(data.isNotEmpty()){
            setRecyclerViewSuratKeterangan(data)
        }
        else{
            Toast.makeText(this@BerkasTersimpanActivity, "Tidak Ada Data", Toast.LENGTH_SHORT).show()
        }
        setStopShimmer()
    }

    private fun setFailureFetchKeteranganTidakMampu(message: String) {
        Toast.makeText(this@BerkasTersimpanActivity, message, Toast.LENGTH_SHORT).show()
        setStopShimmer()
    }

    private fun fetchKeteranganAkteKematian(idUser: String){
        viewModel.fetchKeteranganAkteKematian(idUser)
    }

    private fun getKeteranganAkteKematian(){
        viewModel.getKeteranganAkteKematian.observe(this@BerkasTersimpanActivity){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Failure-> setFailureFetchKeteranganAkteKematian(result.message)
                is UIState.Success-> setSuccessFetchKeteranganAkteKematian(result.data)
            }
        }
    }

    private fun setSuccessFetchKeteranganAkteKematian(data: ArrayList<BerkasModel>) {
        if(data.isNotEmpty()){
            setRecyclerViewSuratKeterangan(data)
        }
        else{
            Toast.makeText(this@BerkasTersimpanActivity, "Tidak Ada Data", Toast.LENGTH_SHORT).show()
        }
        setStopShimmer()
    }

    private fun setFailureFetchKeteranganAkteKematian(message: String) {
        Toast.makeText(this@BerkasTersimpanActivity, message, Toast.LENGTH_SHORT).show()
        setStopShimmer()
    }

    private fun fetchKeteranganPindah(idUser: String){
        viewModel.fetchKeteranganPindah(idUser)
    }

    private fun getKeteranganPindah(){
        viewModel.getKeteranganPindah.observe(this@BerkasTersimpanActivity){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Failure-> setFailureFetchKeteranganPindah(result.message)
                is UIState.Success-> setSuccessFetchKeteranganPindah(result.data)
            }
        }
    }

    private fun setSuccessFetchKeteranganPindah(data: ArrayList<BerkasModel>) {
        if(data.isNotEmpty()){
            setRecyclerViewSuratKeterangan(data)
        }
        else{
            Toast.makeText(this@BerkasTersimpanActivity, "Tidak Ada Data", Toast.LENGTH_SHORT).show()
        }
        setStopShimmer()
    }

    private fun setFailureFetchKeteranganPindah(message: String) {
        Toast.makeText(this@BerkasTersimpanActivity, message, Toast.LENGTH_SHORT).show()
        setStopShimmer()
    }

    private fun fetchKeteranganIzinKeramaian(idUser: String){
        viewModel.fetchKeteranganIzinKeramaian(idUser)
    }

    private fun getKeteranganIzinKeramaian(){
        viewModel.getKeteranganIzinKeramaian.observe(this@BerkasTersimpanActivity){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Failure-> setFailureFetchKeteranganIzinKeramaian(result.message)
                is UIState.Success-> setSuccessFetchKeteranganIzinKeramaian(result.data)
            }
        }
    }

    private fun setSuccessFetchKeteranganIzinKeramaian(data: ArrayList<BerkasModel>) {
        if(data.isNotEmpty()){
            setRecyclerViewSuratKeterangan(data)
        }
        else{
            Toast.makeText(this@BerkasTersimpanActivity, "Tidak Ada Data", Toast.LENGTH_SHORT).show()
        }
        setStopShimmer()
    }

    private fun setFailureFetchKeteranganIzinKeramaian(message: String) {
        Toast.makeText(this@BerkasTersimpanActivity, message, Toast.LENGTH_SHORT).show()
        setStopShimmer()
    }

    private fun fetchKeteranganDomisili(idUser: String){
        viewModel.fetchKeteranganDomisili(idUser)
    }

    private fun getKeteranganDomisili(){
        viewModel.getKeteranganDomisili.observe(this@BerkasTersimpanActivity){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Failure-> setFailureFetchKeteranganDomisili(result.message)
                is UIState.Success-> setSuccessFetchKeteranganDomisili(result.data)
            }
        }
    }

    private fun setSuccessFetchKeteranganDomisili(data: ArrayList<BerkasModel>) {
        if(data.isNotEmpty()){
            setRecyclerViewSuratKeterangan(data)
        }
        else{
            Toast.makeText(this@BerkasTersimpanActivity, "Tidak Ada Data", Toast.LENGTH_SHORT).show()
        }
        setStopShimmer()
    }

    private fun setFailureFetchKeteranganDomisili(message: String) {
        Toast.makeText(this@BerkasTersimpanActivity, message, Toast.LENGTH_SHORT).show()
        setStopShimmer()
    }


    private fun setRecyclerViewSuratKeterangan(data: ArrayList<BerkasModel>) {
        adapter = BerkasAdapter(data, object: OnClickItem.ClickBerkas{
            override fun clickBerkas(berita: BerkasModel) {
                // Click data

            }
        })

        binding.apply {
            rvSuratKeterangan.adapter = adapter
            rvSuratKeterangan.layoutManager = GridLayoutManager(this@BerkasTersimpanActivity, 2)
        }
    }

    private fun setStartShimmer(){
        binding.apply {
            smSuratKeterangan.startShimmer()
            smSuratKeterangan.visibility = View.VISIBLE
            rvSuratKeterangan.visibility = View.GONE
        }
    }
    private fun setStopShimmer(){
        binding.apply {
            smSuratKeterangan.stopShimmer()
            smSuratKeterangan.visibility = View.GONE
            rvSuratKeterangan.visibility = View.VISIBLE
        }
    }

}