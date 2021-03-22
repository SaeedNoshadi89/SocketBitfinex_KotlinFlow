package com.sn.socketbitfinex_kotlinflow.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sn.socketbitfinex_kotlinflow.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.apply {

            viewModel.apply {
                lifecycleScope.launchWhenStarted {
                    getTicker().collect { ticker ->
                        tvVolume.text = "volume: ${ticker.volume}"
                        tvLastPrice.text = "last price: ${ticker.lastPrice}"
                        tvLow.text = "low: ${ticker.low}"
                        tvHigh.text = "high: ${ticker.high}"
                        tvDailyChange.text = "change: ${ticker.dailyChange}"
                    }
                }

                lifecycleScope.launchWhenStarted {
                    getOrderBook().collect { orderBookList ->
                        orderBookBidList.adapter = OrderBookAdapter().apply {
                            asyncDiffer.submitList(getOrderBookBidList(orderBookList))
                        }
                        orderBookAskList.adapter = OrderBookAdapter().apply {
                            asyncDiffer.submitList(getOrderBookAskList(orderBookList))
                        }
                    }
                }
            }
        }
    }

}
