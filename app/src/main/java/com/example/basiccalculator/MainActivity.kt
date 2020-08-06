package com.example.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var lastNumeric = false
    private var lastDecimal = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        txt_result.append((view as Button).text)
        lastNumeric = true

        txt_result.text.contains("1")
    }

    fun onClear(view: View) {
        txt_result.text = ""
        lastNumeric = false
        lastDecimal = false
    }

    fun onDecimal(view: View) {
        if (lastNumeric && !lastDecimal) {
            txt_result.append(".")
            lastNumeric = false
            lastDecimal = true
        }
    }

    fun onEdit(view: View) {
        if (lastNumeric && !operatorSelected(txt_result.text.toString())) {
            txt_result.append((view as Button).text)
            lastNumeric = false
            lastDecimal = false
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var txtValue = txt_result.text.toString()
            var one: String
            var two: String
            var prefix = ""

            try {
                if (txtValue.startsWith("-")) {
                    prefix = "-"
                    txtValue = txtValue.substring(1)
                }
                when {
                    txtValue.contains("-") -> {
                        val splitValue = txtValue.split("-")

                         one = splitValue[0]
                         two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        txt_result.text = removeDecimal((one.toDouble() - two.toDouble()).toString())
                    }
                    txtValue.contains("x") -> {
                        val splitValue = txtValue.split("x")

                         one = splitValue[0]
                         two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        txt_result.text = removeDecimal((one.toDouble() * two.toDouble()).toString())
                    }
                    txtValue.contains("+") -> {
                        val splitValue = txtValue.split("+")

                         one = splitValue[0]
                         two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        txt_result.text = removeDecimal((one.toDouble() + two.toDouble()).toString())
                    }
                    txtValue.contains("/") -> {
                        val splitValue = txtValue.split("/")

                         one = splitValue[0]
                         two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        txt_result.text = removeDecimal((one.toDouble() / two.toDouble()).toString())
                    }
                }
            }
            catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun operatorSelected(value: String): Boolean {
        return if (value.startsWith("-"))
            false
        else
            value.contains("/") || value.contains("x") || value.contains("+") || value.contains("-")
    }

    private fun removeDecimal(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }
}