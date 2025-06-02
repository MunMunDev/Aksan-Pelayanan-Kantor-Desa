package com.abcd.aksan_aplikasipelayanankantordesa.ui.fragment.user.berita

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.aksan_aplikasipelayanankantordesa.R
import com.abcd.aksan_aplikasipelayanankantordesa.adapter.BeritaAdapter
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BeritaModel
import com.abcd.aksan_aplikasipelayanankantordesa.databinding.FragmentBeritaBinding
import com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.main.MainActivity
import com.abcd.aksan_aplikasipelayanankantordesa.utils.OnClickItem
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeritaFragment : Fragment() {
    private lateinit var binding: FragmentBeritaBinding
    private val viewModel: BeritaViewModel by viewModels()
    private lateinit var beritaAdapter: BeritaAdapter
    private var listBerita: List<BeritaModel> = listOf()

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
        binding = FragmentBeritaBinding.inflate(layoutInflater)
        contextLifecycleOwner = viewLifecycleOwner

        fetchBerita()
        getBerita()
        setSwipeRefreshLayout()
        setSearchBerita()

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

    private fun setSearchBerita() {
        binding.navTopBar.etSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                beritaAdapter.searchData(s.toString())
            }

        })
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
            listBerita = data
            setAdapter(data)
        }
        setStopShimmerProduk()
    }

    private fun setAdapter(data: List<BeritaModel>) {
        beritaAdapter = BeritaAdapter(data, object: OnClickItem.ClickBerita{
            override fun clickBerita(berita: BeritaModel) {
//                val i = Intent(context, BeritaActivity::class.java)
//                startActivity(i)
            }
        }, false)

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