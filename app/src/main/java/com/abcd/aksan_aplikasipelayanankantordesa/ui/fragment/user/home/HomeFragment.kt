package com.abcd.aksan_aplikasipelayanankantordesa.ui.fragment.user.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.aksan_aplikasipelayanankantordesa.adapter.BeritaAdapter
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BeritaModel
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.FragmentHomeBinding
import com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.berita.BeritaActivity
import com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.layanan.LayananActivity
import com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.main.MainActivity
import com.abcd.aksan_aplikasipelayanankantordesa.utils.Constant
import com.abcd.aksan_aplikasipelayanankantordesa.utils.OnClickItem
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var beritaAdapter: BeritaAdapter

    private lateinit var activityContext: MainActivity
    private lateinit var context: Context
    private lateinit var contextLifecycleOwner: LifecycleOwner  // harus dipanggil pada onCreateView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContext = (activity as MainActivity)
        context = requireContext().applicationContext

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        contextLifecycleOwner = viewLifecycleOwner
        fetchBerita()
        getBerita()
        setButton()
        setSwipeRefreshLayout()

        return binding.root
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefresh.isRefreshing = false
                fetchBerita()
            }, 1500)
        }
        binding.swipeRefresh.setProgressViewOffset(true, 0, 280)
    }

    private fun setButton() {
        binding.apply {
            btnKeteranganNikah.setOnClickListener {
                val i = Intent(context, LayananActivity::class.java)
                i.putExtra("layanan", Constant.KETERANGAN_NIKAH)
                startActivity(i)
            }
            btnKeteranganLahir.setOnClickListener {
                val i = Intent(context, LayananActivity::class.java)
                i.putExtra("layanan", Constant.KETERANGAN_LAHIR)
                startActivity(i)
            }
            btnKeteranganUsaha.setOnClickListener {
                val i = Intent(context, LayananActivity::class.java)
                i.putExtra("layanan", Constant.KETERANGAN_USAHA)
                startActivity(i)
            }
            btnKeteranganTidakMampu.setOnClickListener {
                val i = Intent(context, LayananActivity::class.java)
                i.putExtra("layanan", Constant.KETERANGAN_TIDAK_MAMPU)
                startActivity(i)
            }
            btnKeteranganAkteKematian.setOnClickListener {
                val i = Intent(context, LayananActivity::class.java)
                i.putExtra("layanan", Constant.KETERANGAN_AKTE_KEMATIAN)
                startActivity(i)
            }
            btnKeteranganPindah.setOnClickListener {
                val i = Intent(context, LayananActivity::class.java)
                i.putExtra("layanan", Constant.KETERANGAN_PINDAH)
                startActivity(i)
            }
            btnKeteranganIzinKeramaian.setOnClickListener {
                val i = Intent(context, LayananActivity::class.java)
                i.putExtra("layanan", Constant.KETERANGAN_IZIN_KERAMAIAN)
                startActivity(i)
            }
            btnKeteranganDomisili.setOnClickListener {
                val i = Intent(context, LayananActivity::class.java)
                i.putExtra("layanan", Constant.KETERANGAN_DOMISILI)
                startActivity(i)
            }
            btnBerita.setOnClickListener {
                activityContext.clickBerita()
            }
        }
    }

    private fun fetchBerita() {
        viewModel.fetchBerita()
    }

    private fun getBerita() {
        viewModel.getBerita().observe(contextLifecycleOwner){ result->
            when(result){
                is UIState.Loading-> setStartShimmerProduk()
                is UIState.Failure-> setFailureFetchBerita(result.message)
                is UIState.Success-> setSuccessFetchBerita(result.data)
            }
        }
    }

    private fun setFailureFetchBerita(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        setStopShimmerProduk()
    }

    private fun setSuccessFetchBerita(data: List<BeritaModel>) {
        if(data.isNotEmpty()){
            setAdapter(data)
        }
        setStopShimmerProduk()
    }

    private fun setAdapter(data: List<BeritaModel>) {
        beritaAdapter = BeritaAdapter(data, object: OnClickItem.ClickBerita{
            override fun clickBerita(berita: BeritaModel) {
                val i = Intent(context, BeritaActivity::class.java)
                i.putExtra("berita", berita)
                startActivity(i)
            }
        }, true)

        binding.rvBerita.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = beritaAdapter
        }
    }

    private fun setStartShimmerProduk(){
        binding.apply {
            smBerita.startShimmer()
            smBerita.visibility = View.VISIBLE
            rvBerita.visibility = View.GONE
        }
    }

    private fun setStopShimmerProduk(){
        binding.apply {
            smBerita.stopShimmer()
            smBerita.visibility = View.GONE
            rvBerita.visibility = View.VISIBLE
        }
    }

}