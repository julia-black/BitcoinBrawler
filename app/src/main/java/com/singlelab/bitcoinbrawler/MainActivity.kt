package com.singlelab.bitcoinbrawler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.singlelab.bitcoinbrawler.databinding.ActivityMainBinding
import com.singlelab.bitcoinbrawler.model.Const
import com.singlelab.bitcoinbrawler.model.User
import com.singlelab.bitcoinbrawler.util.roundTo
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    //todo пока что юзер живет в рамках сессии, далее будет сохраняться на устройстве
    val userLiveData = MutableLiveData<User>().apply {
        value = User(0, 0.0, 1)
    }

    val pricesLiveData = MutableLiveData<List<Double>>().apply {
        value = mutableListOf(10.0, 12.0, 12.0, 12.0, 13.0)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_stock, R.id.navigation_store
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        runTimerForUser()

        runTimerForStock()

        observeLiveData()
    }

    private fun observeLiveData() {
        userLiveData.observe(this, {
            with(binding) {
                balanceBtc.text = "${it.amountBtc} BTC"
                balanceDollars.text = "${(it.amountDollar).roundTo(2)} $"
                velocity.text = "${it.getAllVelocity()} BTC/sec"
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
        pricesLiveData.value?.let {
            val newPrice = generateNewPrice(it)
            val list = pricesLiveData.value?.toMutableList()
            if (it.size >= Const.COUNT_PRICES) {
                val newList = mutableListOf<Double>()
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
                list?.add(newPrice)
                pricesLiveData.value = list
            }
        }
    }

    private fun generateNewPrice(value: List<Double>): Double {
        val last = value.last()
        val newPrice = Random.nextDouble(last - Const.RANGE, last + Const.RANGE)
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