package com.abcd.aksan_aplikasipelayanankantordesa.ui.fragment.user.proses

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.abcd.aksan_aplikasipelayanankantordesa.adapter.ProsesBerkasAdapter
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BerkasModel
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.FragmentProsesBerkasBinding
import com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.main.MainActivity
import com.abcd.aksan_aplikasipelayanankantordesa.utils.OnClickItem
import com.abcd.aksan_aplikasipelayanankantordesa.utils.SharedPreferencesLogin
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList

@AndroidEntryPoint
class ProsesBerkasFragment : Fragment() {
    private lateinit var binding: FragmentProsesBerkasBinding
    private val viewModel: ProsesBerkasViewModel by viewModels()
    private lateinit var adapter: ProsesBerkasAdapter
    private lateinit var sharedPreferences : SharedPreferencesLogin
    private var arrayBerkas: ArrayList<BerkasModel> = arrayListOf()

    private lateinit var activityContext: MainActivity
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContext = (activity as MainActivity)
        context = requireContext().applicationContext

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProsesBerkasBinding.inflate(layoutInflater)

        setSharedPreferences()
        setNavTopBar()
        fetchKeteranganDomisili(sharedPreferences.getIdUser())
        getKeteranganDomisili()

        return binding.root
    }

    private fun setSharedPreferences() {
        sharedPreferences = SharedPreferencesLogin(context)
    }

    @SuppressLint("SetTextI18n")
    private fun setNavTopBar(){
        binding.navTopBar.apply {
            ivNavDrawer.visibility = View.GONE
            ivBack.visibility = View.GONE

            tvTitle.text = "Proses Berkas"
        }
    }

    private fun fetchKeteranganDomisili(idUser: Int){
        viewModel.fetchProsesBerkas(idUser)
    }

    private fun getKeteranganDomisili(){
        viewModel.getProsesBerkas.observe(viewLifecycleOwner){result->
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
            Toast.makeText(context, "Tidak Ada Data", Toast.LENGTH_SHORT).show()
        }
        setStopShimmer()
    }

    private fun setFailureFetchKeteranganDomisili(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        setStopShimmer()
    }


    private fun setRecyclerViewSuratKeterangan(data: ArrayList<BerkasModel>) {
        adapter = ProsesBerkasAdapter(data, object: OnClickItem.ClickBerkas{
            override fun clickBerkas(berita: BerkasModel) {
                // Click data

            }
        })

        binding.apply {
            rvSuratKeterangan.adapter = adapter
            rvSuratKeterangan.layoutManager = GridLayoutManager(context, 2)
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