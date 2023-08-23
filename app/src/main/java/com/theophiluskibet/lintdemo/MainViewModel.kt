package com.theophiluskibet.lintdemo

import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    fun getData() {
        Log.d("HOME", "This is home")
    }
}
