package com.singlelab.bitcoinbrawler.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.singlelab.bitcoinbrawler.model.User

class HomeViewModel : ViewModel() {

    val user = MutableLiveData<User>().apply {
        value = User(0, 0, 1)
    }

    fun updateUser() {
        user.value = user.value?.incBtc()
    }
}