package com.example.tiptime3
import android.content.Context
import  androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tiptime3.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculate.setOnClickListener {
            calculateTip()
        }
        binding.costOfService.setOnKeyListener { view, keycode -> handleKeyEvent(view, keycode) }


    }
    private fun calculateTip(){
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        if(cost == null){
            binding.tipResult.txt = ""
            return
        }
        val selectId = binding.tipOptions.checkedRadioButtonId
        val roundUp = binding.setUpTrip.isChecked
        val tipPercentaje = when(selectId){
            R.id.option_twenty_percent -> 0.20
            R.id.option_fifteen_percent -> 0.18
            else -> 0.15
        }
        var tip = cost * tipPercentaje
        if(roundUp){
            tip = ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)

        binding.tipResult.text = getString(R.string.tip_amount,formattedTip)
    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            calculateTip()
            return true
        }
        return false
    }
}



