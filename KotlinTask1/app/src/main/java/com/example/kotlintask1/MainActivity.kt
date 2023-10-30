package com.example.kotlintask1

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var sizeRow = 0
    var elements = mutableListOf<Int>()
    private lateinit var scrlv: TableLayout
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scrlv = (findViewById(R.id.scrollView2) as ScrollView).getChildAt(0) as TableLayout
        sizeRow = getRowSize()
        if (savedInstanceState != null) {
            elements = savedInstanceState.getIntegerArrayList("elem") as MutableList<Int>
            for(i in 0 until elements.count()){
                addElement(scrlv, elements[i])
            }
        }
        button = findViewById(R.id.button)
        button.setOnClickListener {
            elements.add(if (elements.isEmpty()) 0 else elements[elements.size - 1] + 1)
            addElement(scrlv, elements[elements.size - 1])
        }
    }

    private fun addElement(scrlv : TableLayout, num : Int){
        if((scrlv.getChildAt(scrlv.childCount - 1) as TableRow).childCount == sizeRow ){
            val tr = TableRow(this)
            scrlv.addView(tr)

        }
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.num_item, null)
        val textv : TextView = view.findViewById(R.id.numeric)

        if(sizeRow == 4) {
            val param = textv.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(150, 30, 120, 30)
            textv.layoutParams = param
        }
        textv.text = num.toString()
        textv.setBackgroundResource(if (num%2==0) R.drawable.red else R.drawable.blue)
        (scrlv.getChildAt(scrlv.childCount - 1) as TableRow).addView(view)
    }

    private fun getRowSize() : Int{
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            return 4
        }
        return 3
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntegerArrayList("elem", elements as ArrayList<Int>)
    }
}