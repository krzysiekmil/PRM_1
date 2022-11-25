package pjwstk.s20124.prm_1

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pjwstk.s20124.prm_1.adapter.RecyclerViewAdapter
import pjwstk.s20124.prm_1.data.UserExpense
import pjwstk.s20124.prm_1.viewModel.UserExpenseViewModel
import pjwstk.s20124.prm_1.viewModel.UserExpenseViewModelFactory


class MainActivity : AppCompatActivity(), RecyclerViewAdapter.RowClickListener {
    private lateinit var button: FloatingActionButton
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView

    private val viewModel: UserExpenseViewModel by lazy {
        ViewModelProvider(
            this,
            UserExpenseViewModelFactory((application as UserExpenseApplication).repository)
        )[UserExpenseViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewAdapter = RecyclerViewAdapter(this@MainActivity)
            adapter = recyclerViewAdapter
        }

        viewModel.userExpense.observe(this) {
            title = getString(R.string.list) + it.size
            recyclerViewAdapter.setListData(ArrayList(it))
            recyclerViewAdapter.notifyDataSetChanged()
        }
        initAddButton()
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