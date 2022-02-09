package com.example.weather.ui.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.data.models.Forecast
import com.example.weather.data.models.RemoteForecast
import com.example.weather.databinding.ItemCityBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.MainViewHolder>() {

    private val dataList: MutableList<Forecast> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun addList(list: List<Forecast>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val itemBinding: ItemCityBinding) : RecyclerView.ViewHolder(
        itemBinding.root
    ) {
        fun bindData(data: Forecast) {
            itemBinding.tvDate.text = formatDate(data.dt)
            itemBinding.tvTemp.text = formatTemperature(data.temp!!.day)
            itemBinding.tvPressure.text = formatPressure(data.pressure)
            itemBinding.tvHumidity.text = formatHumidity(data.humidity)
            itemBinding.tvDescription.text = "Description: ${data.weather!![0].description}"
        }

        private fun formatDate(timeStamp: Long): String {
            val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy")
            return "Date: ${dateFormat.format(Date())}"
        }

        private fun formatTemperature(temp: Float): String {
            return "Average temperature: ${(temp - 273.15F).toInt()}°C"
        }

        private fun formatPressure(pressure: Int): String {
            return "Pressure: $pressure"
        }

        private fun formatHumidity(value: Int): String {
            return "Humidity: ${value}%"
        }
    }
}