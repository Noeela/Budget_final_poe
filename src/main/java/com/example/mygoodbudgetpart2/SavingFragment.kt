package com.example.mygoodbudgetpart2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext

class SavingFragment : Fragment() {

    private lateinit var editGoalName: EditText
    private lateinit var editTargetAmount: EditText
    private lateinit var editSavedAmount: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var textProgress: TextView
    private lateinit var buttonSave: Button
    private lateinit var buttonViewGoals: Button
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saving, container, false)

        databaseHelper = DatabaseHelper(requireContext())
        sessionManager = SessionManager(requireContext())

        // Initialize views
        editGoalName = view.findViewById(R.id.editGoalName)
        editTargetAmount = view.findViewById(R.id.editTargetAmount)
        editSavedAmount = view.findViewById(R.id.editSavedAmount)
        progressBar = view.findViewById(R.id.progressBar)
        textProgress = view.findViewById(R.id.textProgress)
        buttonSave = view.findViewById(R.id.buttonSave)
        buttonViewGoals = view.findViewById(R.id.buttonViewGoals)

        buttonSave.setOnClickListener {
            saveGoal()
        }

        buttonViewGoals.setOnClickListener {
            Toast.makeText(requireContext(), "View Goals clicked (functionality to be added)", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun saveGoal() {
        val goalName = editGoalName.text.toString().trim()
        val targetStr = editTargetAmount.text.toString().trim()
        val savedStr = editSavedAmount.text.toString().trim()

        if (goalName.isEmpty() || targetStr.isEmpty() || savedStr.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val target = targetStr.toDoubleOrNull()
        val saved = savedStr.toDoubleOrNull()

        if (target == null || saved == null) {
            Toast.makeText(requireContext(), "Invalid amount", Toast.LENGTH_SHORT).show()
            return
        }

        if (saved > target) {
            Toast.makeText(requireContext(), "Saved amount cannot be more than target", Toast.LENGTH_SHORT).show()
            return
        }

        val progress = ((saved / target) * 100).toInt()
        progressBar.progress = progress
        textProgress.text = "Progress: $progress%"

        // TODO: Save to database
        Toast.makeText(requireContext(), "Goal saved: $goalName", Toast.LENGTH_SHORT).show()
    }
}









