package pjwstk.s20124.prm_1

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import pjwstk.s20124.prm_1.data.UserExpense
import pjwstk.s20124.prm_1.viewModel.UserExpenseViewModel
import pjwstk.s20124.prm_1.viewModel.UserExpenseViewModelFactory
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*
import java.util.stream.Stream
import kotlin.collections.ArrayList


class MonthViewActivity : AppCompatActivity() {

    private lateinit var lineChart: LineChart
    private var  data: List<UserExpense> = emptyList()
    private var month: Month = LocalDate.now().month
    private val viewModel: UserExpenseViewModel by lazy {
        ViewModelProvider(this, UserExpenseViewModelFactory((application as UserExpenseApplication)))[UserExpenseViewModel::class.java]
    }
    private lateinit var df: DateFormat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month_view)

        viewModel.userExpense.observe(this) {
            data = it
            initDataSet()
            initChart()
            initDatePicker()
        }






    }

    private fun initChart(date: Date? = null){
        lineChart = findViewById(R.id.chart1)
        val lineDataSet = LineDataSet(lineChartDataSet(date), getString(R.string.chart_line))
        val iLineDataSets = ArrayList<ILineDataSet>()
        iLineDataSets.add(lineDataSet)
        val lineData = LineData(iLineDataSets)
        lineChart.data = lineData
        lineChart.invalidate()

        lineChart.setNoDataText(getString(R.string.chart_data_not_availble))

        val colors: MutableList<Int> = IntArray(lineDataSet.values.size).toMutableList()

        for ((index, entry) in lineDataSet.values.withIndex()) {
            if (index == 0)
                continue
            if (entry.y < 0)
                colors[index - 1] = getColor(R.color.outcome)
            else
                colors[index - 1] = getColor(R.color.income)
        }
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        lineDataSet.colors = colors
        lineDataSet.setCircleColor(Color.GREEN)
        lineDataSet.lineWidth = 5f
        lineDataSet.valueTextSize = 10f
        lineDataSet.valueTextColor = Color.BLACK
    }

    private fun initDatePicker() {
        val datePicker = findViewById<TextView>(R.id.input_date)

        datePicker.text = df.format(Date())
        datePicker.setOnClickListener { openDatePicker(datePicker) }
    }

    private fun openDatePicker(dataPicker: TextView) {

        val picker = MaterialDatePicker.Builder
            .datePicker()
            .setInputMode(INPUT_MODE_CALENDAR)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        picker.addOnPositiveButtonClickListener {
            val date = Date(Timestamp(it).time)
            month = Month.of(Timestamp(it).month)
            dataPicker.text = df.format(date)
            initChart(date)
        }

        picker.show(supportFragmentManager, null)


    }

    private fun initDataSet() {
        df = SimpleDateFormat(AppConstants.BALANCE_DATE_FORMAT, Locale.ENGLISH)

    }

    private fun lineChartDataSet(date: Date?): List<Entry> {
        if(data.isEmpty()){
            return emptyList()
        }
        val baseDate: LocalDate = if (date != null)
            date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        else
            LocalDate.now()

        val beginRange = Date.from(baseDate.withDayOfMonth(1).atStartOfDay().toInstant(
            ZoneOffset.UTC))
        val endRange = Date.from(baseDate.plusMonths(1).withDayOfMonth(1).atStartOfDay().toInstant(
            ZoneOffset.UTC))
        var ballance = 0F
        return data
            .filter { it.date.after(beginRange) && it.date.before(endRange) }
            .sortedBy { it.date }
            .map {
                val day = it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().dayOfMonth.toFloat()
                ballance += it.value.toFloat()
                Entry(day, ballance) }
            .toMutableList()
    }
}