package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var lastNum :Double =0.0
    private var currentOperation:Operation?=null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater) //inflater : take xml and convert it into ui
        setContentView(binding.root)
//        initView()
        addCallBacks()
    }

    private fun addCallBacks() {
        binding.dotBtn.setOnClickListener {
            onClickNumber(it)
        }
        binding.cBtn.setOnClickListener{
            clearInput()
        }

        binding.plusBtn.setOnClickListener{
            prepareOperation(Operation.Plus)

        }

        binding.minusBtn.setOnClickListener{
            prepareOperation(Operation.Minus)

        }
        binding.multiBtn.setOnClickListener{
            prepareOperation(Operation.Multi)

        }
        binding.dividedBtn.setOnClickListener{
            prepareOperation(Operation.Div)

        }
        binding.equalBtn.setOnClickListener{
            val result=doCurrentOperation()
            binding.resultTv.text= result.toString()
        }
    }

    private fun doCurrentOperation(): Any {
        val secNum = binding.resultTv.text.toString().toDouble()
        val result = when (currentOperation) {
            Operation.Plus -> lastNum + secNum
            Operation.Minus -> lastNum - secNum
            Operation.Multi -> lastNum * secNum
            Operation.Div -> lastNum / secNum
            else -> secNum
        }
        return if (result.isInt() || result==0.0) result.toInt() else result
    }

    private fun Double.isInt(): Boolean {
        return this == this.toInt().toDouble()
    }


    private fun prepareOperation(operation: Operation){
        lastNum=binding.resultTv.text.toString().toDouble()
        clearInput()
        currentOperation=operation

    }
    private fun clearInput(){
        binding.resultTv.text=""
    }

    fun onClickNumber(v: View) {

        val newDigit = when (v) {
            binding.dotBtn -> "."
            else -> (v as Button).text.toString()
        }

        val oldDigit = binding.resultTv.text.toString()
        val containsDot = oldDigit.contains(".")

        if (newDigit == ".") {
            if (!containsDot) {
                val newResultNo = if (oldDigit.isEmpty()) "0$newDigit" else oldDigit + newDigit
                binding.resultTv.text = newResultNo
            }
        } else {
            // If the clicked button is a number button
            val newResultNo = if (oldDigit == "0" && !containsDot) newDigit else oldDigit + newDigit
            binding.resultTv.text = newResultNo
        }
    }

}







