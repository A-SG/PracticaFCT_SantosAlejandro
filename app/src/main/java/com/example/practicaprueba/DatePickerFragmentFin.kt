package com.example.practicaprueba

import DatePickerFragment
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragmentFin (val listener: (day: Int, month:Int, year: Int)-> Unit) : DialogFragment(), DatePickerDialog.OnDateSetListener{


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        listener(day, month + 1, year)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)


        val picker = DatePickerDialog(activity as Context, R.style.datePicker, this, year, month, day)
        return  picker
    }


}