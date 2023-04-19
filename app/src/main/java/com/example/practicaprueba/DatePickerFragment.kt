import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.practicaprueba.R
import java.util.Calendar

class DatePickerFragment(val listener: (day: Int, month:Int, year: Int)-> Unit) : DialogFragment(), DatePickerDialog.OnDateSetListener{

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        listener(day, month, year)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        return DatePickerDialog(activity as Context, R.style.datePicker, this, year, month, day)
    }


}