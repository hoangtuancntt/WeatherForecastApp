package com.example.weather.ui.components

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.data.models.Forecast
import com.example.weather.data.models.RemoteForecast
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.ui.BaseActivity
import com.example.weather.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val mainViewModel: WeatherViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Weather Forecast"
        binding.rvWeather.layoutManager = LinearLayoutManager(this)
        binding.rvWeather.setHasFixedSize(true)
        binding.rvWeather.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.tvGetResult.setOnClickListener { v ->
            val key = binding.etSearch.text.toString()
            handleSearch(key)
        }
    }

    override fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun observeViewModel() {
        observe(mainViewModel.weatherListLiveData, ::showSearchResult)
        observe(mainViewModel.loadingLiveData, ::handleLoading)
        observe(mainViewModel.noSearchFound, ::handleErrorResult)
        observeToast(mainViewModel.showToast)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Toast.LENGTH_LONG)
    }

    private fun showSearchResult(list: List<Forecast>?) {
        if (!list.isNullOrEmpty()) {
            weatherAdapter = WeatherAdapter()
            weatherAdapter.addList(list)
            binding.rvWeather.adapter = weatherAdapter
            showDataView(show = true)
        } else {
            showDataView(show = false)
        }
    }

    private fun handleLoading(state: Boolean) {
        if (state) showLoadingView()
    }

    private fun handleSearch(query: String) {
        mainViewModel.getForecastByLocation(query)
    }

    private fun handleErrorResult(unit: Unit) {
        binding.pbLoading.toGone()
    }

    private fun showDataView(show: Boolean) {
        binding.tvNoData.visibility = if (show) GONE else VISIBLE
        binding.rvWeather.visibility = if (show) VISIBLE else GONE
        binding.pbLoading.toGone()
        binding.etSearch.hideKeyboard()
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rvWeather.toGone()
    }
}