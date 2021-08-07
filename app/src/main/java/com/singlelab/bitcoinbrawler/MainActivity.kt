package com.singlelab.bitcoinbrawler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.singlelab.bitcoinbrawler.data.Preference
import com.singlelab.bitcoinbrawler.databinding.ActivityMainBinding
import com.singlelab.bitcoinbrawler.model.Const
import com.singlelab.bitcoinbrawler.model.User
import com.singlelab.bitcoinbrawler.util.roundTo
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var preference: Preference

    val userLiveData = MutableLiveData<User>()

    val pricesLiveData = MutableLiveData<List<Float>>().apply {
        value = mutableListOf(10.0f, 12.0f, 12.0f, 12.0f, 13.0f)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        preference = Preference(getSharedPreferences(Const.PREF_DATA, MODE_PRIVATE))
        userLiveData.value = preference.getUserData()

        runTimerForUser()

        runTimerForStock()

        observeLiveData()
    }

    private fun observeLiveData() {
        userLiveData.observe(this, {
            with(binding) {
                balanceBtc.text = it.amountBtc.toString()
                balanceDollars.text = (it.amountDollar).roundTo(2).toString()
                velocity.text = "${it.getAllVelocity()}/sec"
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
        pricesLiveData.value?.let {
            val newPrice = generateNewPrice(it)
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
                list?.add(newPrice)
                pricesLiveData.value = list
            }
        }
    }

    private fun generateNewPrice(value: List<Float>): Float {
        val last = value.last()
        val newPrice = Random.nextDouble(last - Const.RANGE, last + Const.RANGE).toFloat()
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