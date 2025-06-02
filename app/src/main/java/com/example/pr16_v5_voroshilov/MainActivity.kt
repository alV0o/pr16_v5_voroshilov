package com.example.pr16_v5_voroshilov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.content.SharedPreferences
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    lateinit var spin: Spinner
    lateinit var adapter: ArrayAdapter<String>
    lateinit var firstValue: EditText
    lateinit var secondValue: EditText
    lateinit var thirdValue: EditText
    lateinit var perimeter: TextView
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val spin = findViewById<Spinner>(R.id.figures)
        firstValue = findViewById(R.id.firstValue)
        secondValue = findViewById(R.id.secondValue)
        thirdValue = findViewById(R.id.thirdValue)
        pref = getPreferences(MODE_PRIVATE)
        ArrayAdapter.createFromResource(
            this,
            R.array.figures_geometry,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spin.adapter = adapter
        }

        spin.setSelection(pref.getInt("position", 0))

        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val figureId = pref.edit()
                when (position){
                    0 -> {
                        firstValue.hint = "Введите сторону А"
                        secondValue.hint = "Введите сторону B"
                        thirdValue.hint = "Введите сторону C"
                        secondValue.visibility = View.VISIBLE
                        thirdValue.visibility = View.VISIBLE
                    }
                    1 -> {
                        firstValue.hint = "Введите сторону А"
                        secondValue.hint = "Введите сторону B"
                        secondValue.visibility = View.VISIBLE
                        thirdValue.visibility = View.GONE
                    }
                    2 -> {
                        firstValue.hint = "Введите радиус"
                        secondValue.visibility = View.GONE
                        thirdValue.visibility = View.GONE
                    }
                }

                figureId.putInt("position", position)
                figureId.apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }

        }
    }

    fun Calc(view: View) {
        perimeter = findViewById(R.id.perimeter)
        when {
            (secondValue.isVisible && thirdValue.isVisible) -> {
                if (firstValue.text.isNotEmpty() && secondValue.text.isNotEmpty() && thirdValue.text.isNotEmpty()) {
                    perimeter.text = "Периметр треугольника = ${firstValue.text.toString().toInt() + secondValue.text.toString().toInt() + thirdValue.text.toString().toInt()}"
                }
                else {
                    perimeter.text = "Заполните все поля!"
                }
            }
            (secondValue.isVisible && thirdValue.isGone) -> {
                if (firstValue.text.isNotEmpty() && secondValue.text.isNotEmpty()) {
                    perimeter.text = "Периметр прямоугольника = ${2 * (firstValue.text.toString().toInt() + secondValue.text.toString().toInt())}"
                }
                else {
                    perimeter.text = "Заполните все поля!"
                }
            }
            (secondValue.isGone && thirdValue.isGone) -> {
                if (firstValue.text.isNotEmpty()) {
                    perimeter.text = "Длина окружности = ${String.format("%.2f", 2 * firstValue.text.toString().toInt() * Math.PI)}"
                }
                else {
                    perimeter.text = "Заполните все поля!"
                }
            }
        }
    }
}