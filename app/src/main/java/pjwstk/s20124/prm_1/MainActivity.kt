package pjwstk.s20124.prm_1

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pjwstk.s20124.prm_1.adapter.RecyclerViewAdapter
import pjwstk.s20124.prm_1.adapter.RowClickListener
import pjwstk.s20124.prm_1.data.UserExpense
import pjwstk.s20124.prm_1.providers.DataProvider
import pjwstk.s20124.prm_1.viewModel.UserExpenseViewModel
import pjwstk.s20124.prm_1.viewModel.UserExpenseViewModelFactory
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*


class MainActivity : AppCompatActivity(), RowClickListener {
    private lateinit var button: FloatingActionButton
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataProvider: DataProvider

    private val viewModel: UserExpenseViewModel by lazy {
        ViewModelProvider(this, UserExpenseViewModelFactory((application as UserExpenseApplication)))[UserExpenseViewModel::class.java]
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewAdapter = RecyclerViewAdapter(this@MainActivity)
            adapter = recyclerViewAdapter
        }

        val beginRange = Date.from(LocalDate.now().withDayOfMonth(1).atStartOfDay().toInstant(
            ZoneOffset.UTC))
        val endRange = Date.from(LocalDate.now().plusMonths(1).withDayOfMonth(1).atStartOfDay().toInstant(
            ZoneOffset.UTC))

        viewModel.userExpense.observe(this) { it ->
            val monthExpense: List<UserExpense> = it.filter { userExpense ->
                userExpense.date.after(beginRange) && userExpense.date.before(endRange)
            }
            findViewById<TextView>(R.id.month_ballance).text = getString(R.string.list_monthValue) + monthExpense.sumOf { it.value }
            findViewById<TextView>(R.id.month_date).text = SimpleDateFormat(AppConstants.BALANCE_DATE_FORMAT).format(Date())
            findViewById<ImageView>(R.id.month_Icon).setColorFilter(getColor(androidx.appcompat.R.color.primary_dark_material_dark))
            recyclerViewAdapter.setListData(ArrayList(it))
            recyclerViewAdapter.notifyDataSetChanged()
        }
        initAddButton()
//        initDataProvider()



    }

    private fun initDataProvider() {
        dataProvider = DataProvider()
//        dataProvider.viewModel = viewModel
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        recreate()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_monthButton -> {
                startActivity(Intent(this, MonthViewActivity::class.java))
                return true
            }
            R.id.menu_shareButton -> {
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initAddButton() {
        button = findViewById(R.id.addNewEntryButton)

        button.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onLongClickListener(userExpense: UserExpense) {
        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.dialog_remove_text) + userExpense.id)
            .setTitle(getString(R.string.dialog_remove_title))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { _: DialogInterface?, _: Int -> viewModel.delete(userExpense) }
            .setNegativeButton(getString(R.string.No)) { dialog: DialogInterface, _: Int -> dialog.cancel() }
            .create().show()
    }

    override fun onItemClickListener(userExpense: UserExpense) {
        val intent = Intent(this, FormActivity::class.java)
        intent.putExtra(AppConstants.ID, userExpense.id)
        intent.putExtra(AppConstants.DATA, userExpense)

        startActivity(intent)
    }
}