package com.singlelab.bitcoinbrawler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.singlelab.bitcoinbrawler.data.Preference
import com.singlelab.bitcoinbrawler.data.Res
import com.singlelab.bitcoinbrawler.databinding.ActivityMainBinding
import com.singlelab.bitcoinbrawler.model.Const
import com.singlelab.bitcoinbrawler.model.User
import com.singlelab.bitcoinbrawler.util.roundTo
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var preference: Preference

    val userLiveData = MutableLiveData<User>()

    val pricesLiveData = MutableLiveData<List<Float>>()

    val isPositiveTrendLiveData = MutableLiveData<Boolean>().apply {
        this.value = true
    }

    private var ticks = 0

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Res.setContext(this)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        preference = Preference(getSharedPreferences(Const.PREF_DATA, MODE_PRIVATE))
        userLiveData.value = preference.getUserData()
        pricesLiveData.value = preference.getPrices()

        runTimerForUser()

        runTimerForStock()

        observeLiveData()
    }

    override fun onDestroy() {
        Res.setContext(null)
        super.onDestroy()
    }

    private fun observeLiveData() {
        userLiveData.observe(this, {
            with(binding) {
                balanceBtc.text = it.amountBtc.roundTo(3).toString()
                balanceDollars.text = (it.amountDollar).roundTo(2).toString()
                velocity.text = "${it.getAllVelocity().roundTo(3)}/sec"
                preference.updateUserData(it)
            }
        })
    }

    private fun runTimerForStock() {
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    updateStock()
                }
            }
        }
        val timer = Timer()
        timer.schedule(task, 0, 3000)
    }

    private fun updateStock() {
        ticks++
        pricesLiveData.value?.let {
            if (ticks == 10) {
                isPositiveTrendLiveData.value = generateTrend()
                ticks = 0
            }
            val newPrice = generateNewPrice(it, isPositiveTrendLiveData.value!!)
            val list = pricesLiveData.value?.toMutableList()
            if (it.size >= Const.COUNT_PRICES) {
                val newList = mutableListOf<Float>()
                list?.reverse()
                list?.forEachIndexed { index, item ->
                    if (index >= Const.COUNT_PRICES) {
                        return@forEachIndexed
                    } else {
                        newList.add(item)
                    }
                }
                newList.reverse()
                newList.add(newPrice)
                pricesLiveData.value = newList
            } else {
                list?.apply {
                    add(newPrice)
                    pricesLiveData.value = this
                }
            }
        }
        pricesLiveData.value?.let {
            preference.savePrices(it)
        }
    }

    private fun generateTrend() = Random.nextDouble() <= 0.7

    private fun generateNewPrice(value: List<Float>, isPositiveTrend: Boolean): Float {
        val last = value.last()
        val newPrice = if (isPositiveTrend) {
            Random.nextDouble(last - Const.RANGE_MIN_POSITIVE, last + Const.RANGE_MAX_POSITIVE)
                .toFloat()
        } else {
            Random.nextDouble(last - Const.RANGE_MIN_NEGATIVE, last + Const.RANGE_MAX_NEGATIVE)
                .toFloat()
        }
        return newPrice.roundTo(2)
    }

    private fun runTimerForUser() {
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    updateUser()
                }
            }
        }
        val timer = Timer()
        timer.schedule(task, 0, 1000)
    }

    private fun updateUser() {
        userLiveData.value = userLiveData.value?.incBtc()
    }
}