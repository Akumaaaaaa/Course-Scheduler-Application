package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textview.MaterialTextView

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel: AddCourseViewModel
    private var selectedTimePickerTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]

        findViewById<Button>(R.id.btn_save).setOnClickListener {
            saveCourse()
        }

        findViewById<ImageButton>(R.id.ib_start_time).setOnClickListener {
            showTimePickerDialog(START_TIME_PICKER_TAG)
        }

        findViewById<ImageButton>(R.id.ib_end_time).setOnClickListener {
            showTimePickerDialog(END_TIME_PICKER_TAG)
        }
    }

    private fun saveCourse() {
        val courseName = findViewById<EditText>(R.id.et_course_name).text.toString()
        val day = findViewById<Spinner>(R.id.spinner_day).selectedItemPosition
        val startTime = findViewById<TextView>(R.id.tv_start_time).text.toString()
        val endTime = findViewById<TextView>(R.id.tv_end_time).text.toString()
        val lecturer = findViewById<EditText>(R.id.ed_lecturer).text.toString()
        val note = findViewById<EditText>(R.id.ed_note).text.toString()

        viewModel.insertCourse(courseName, day, startTime, endTime, lecturer, note)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showTimePickerDialog(tag: String) {
        selectedTimePickerTag = tag
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, tag)
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        if (tag == selectedTimePickerTag) {
            val time = String.format("%02d:%02d", hour, minute)
            when (tag) {
                START_TIME_PICKER_TAG -> findViewById<MaterialTextView>(R.id.tv_start_time).text = time
                END_TIME_PICKER_TAG -> findViewById<MaterialTextView>(R.id.tv_end_time).text = time
            }
        }
    }

    companion object {
        private const val START_TIME_PICKER_TAG = "start_time_picker"
        private const val END_TIME_PICKER_TAG = "end_time_picker"
    }
}