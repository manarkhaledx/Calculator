package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() {
    private var lastNum: Double = 0.0
    private var currentOperation: Operation? = null
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addCallBacks()
    }

    private fun addCallBacks() {
        binding.dotBtn.setOnClickListener {
            onClickNumber(it)
        }
        binding.reminderBtn.setOnClickListener {
            prepareOperation(Operation.Rem)
        }

        binding.cBtn.setOnClickListener {
            clearInput()
        }
        binding.plusBtn.setOnClickListener {
            prepareOperation(Operation.Plus)
        }
        binding.minusBtn.setOnClickListener {
            prepareOperation(Operation.Minus)
        }
        binding.multiBtn.setOnClickListener {
            prepareOperation(Operation.Multi)
        }
        binding.dividedBtn.setOnClickListener {
            prepareOperation(Operation.Div)
        }
        binding.equalBtn.setOnClickListener {
            val result = doCurrentOperation()
            binding.resultTv.text = result.toString()
        }
        binding.backspceBtn.setOnClickListener {
            removeLastDigit()
        }
    }


    private fun removeLastDigit() {
        val inputText = binding.resultTv.text.toString()
        if (inputText.isNotEmpty()) {
            val newInput = inputText.substring(0, inputText.length - 1)
            binding.resultTv.text = newInput
        }
    }

    private fun doCurrentOperation(): Any {
        val inputText = binding.resultTv.text.toString()

        if (inputText.isEmpty()) {
            return formatOutput(lastNum)
        }


            val secNum = inputText.toDouble()

        if (currentOperation == Operation.Div && secNum == 0.0) {
            Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
            clearInput()
            currentOperation = null;
            return formatOutput(0.0)

        }else if (currentOperation == Operation.Rem && secNum == 0.0) {
            Toast.makeText(
                this,
                "Can't calculate rem with a divisor of zero",
                Toast.LENGTH_SHORT
            ).show()
            clearInput()
            currentOperation = null;
            return formatOutput(0.0)
        }



            val result = when (currentOperation) {
                Operation.Plus -> lastNum + secNum
                Operation.Minus -> lastNum - secNum
                Operation.Multi -> lastNum * secNum
                Operation.Div -> lastNum / secNum
                Operation.Rem -> lastNum % secNum
                else -> secNum
            }
            currentOperation = null
            return formatOutput(result)

    }

    private fun formatOutput(value: Double): Any {
        val threshold = 1e10
        return if (value.absoluteValue >= threshold) {
            String.format("%.2e", value)
        } else {
            if (value.isInt() || value == 0.0) value.toInt() else value
        }
    }


    private fun Double.isInt(): Boolean {
        return this == this.toInt().toDouble()
    }

    private fun prepareOperation(operation: Operation) {
        val inputText = binding.resultTv.text.toString()

        if (inputText.isEmpty()) {
            Toast.makeText(this, "Please enter a number first", Toast.LENGTH_SHORT).show()
            return
        }

        lastNum = inputText.toDouble()
        clearInput()
        currentOperation = operation
    }



    private fun clearInput() {
        binding.resultTv.text = ""
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
            val newResultNo = if (oldDigit == "0" && !containsDot) newDigit else oldDigit + newDigit
            binding.resultTv.text = newResultNo
        }
    }
}
