package com.example.mygoodbudgetpart2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.util.*

class BudgetFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager

    private lateinit var pieChart: PieChart
    private lateinit var badge: ImageView
    private lateinit var monthInput: EditText
    private lateinit var minGoalInput: EditText
    private lateinit var maxGoalInput: EditText
    private lateinit var saveGoalButton: Button
    private lateinit var btnContinue: Button
    private lateinit var levelText: TextView
    private lateinit var xpProgress: ProgressBar

    private var xp = 0
    private var level = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_budget, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())
        sessionManager = SessionManager(requireContext())

        // Initialize views
        monthInput = view.findViewById(R.id.monthInput)
        minGoalInput = view.findViewById(R.id.minGoalInput)
        maxGoalInput = view.findViewById(R.id.maxGoalInput)
        saveGoalButton = view.findViewById(R.id.saveGoalButton)
        pieChart = view.findViewById(R.id.pieChart)
        badge = view.findViewById(R.id.congratsBadge)
        levelText = view.findViewById(R.id.levelText)
        xpProgress = view.findViewById(R.id.xpProgress)
        btnContinue = view.findViewById<Button>(R.id.btnContinue)

        // Set current month
        val dateFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        val currentMonth = dateFormat.format(Date())
        monthInput.setText(currentMonth)

        loadBudgetGoal(currentMonth)

        saveGoalButton.setOnClickListener {
            saveBudgetGoal()
        }

        btnContinue.setOnClickListener {
            val intent = Intent(requireContext(), RewardFragment::class.java)
            startActivity(intent)
        }

    }


    private fun loadBudgetGoal(month: String) {
        val user = sessionManager.getUserDetails() ?: return

        val budgetGoal = databaseHelper.getBudgetGoal(month, user.id)

        if (budgetGoal != null) {
            minGoalInput.setText(budgetGoal.minGoal.toString())
            maxGoalInput.setText(budgetGoal.maxGoal.toString())
        }
    }

    private fun saveBudgetGoal() {
        val user = sessionManager.getUserDetails() ?: return

        val month = monthInput.text.toString().trim()
        val minGoalStr = minGoalInput.text.toString().trim()
        val maxGoalStr = maxGoalInput.text.toString().trim()

        if (month.isEmpty() || minGoalStr.isEmpty() || maxGoalStr.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val minGoal = minGoalStr.toDouble()
            val maxGoal = maxGoalStr.toDouble()

            if (minGoal > maxGoal) {
                Toast.makeText(
                    requireContext(),
                    "Min goal cannot be greater than max goal",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            xp += 10
            var leveledUp = false
            if (xp >= 100) {
                level++
                xp -= 100
                leveledUp = true
                Toast.makeText(
                    requireContext(),
                    "🎉 You leveled up to Level $level!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            levelText.text = "Level: $level (XP: $xp/100)"
            xpProgress.progress = xp

            val id = databaseHelper.setBudgetGoal(month, minGoal, maxGoal, user.id)

            if (id > 0) {
                Toast.makeText(
                    requireContext(),
                    "Budget goal saved successfully",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(requireContext(), "Failed to save budget goal", Toast.LENGTH_SHORT)
                    .show()
            }

            if (leveledUp) {
                pieChart.visibility = View.VISIBLE
                badge.visibility = View.VISIBLE
                drawPieChart(minGoal.toFloat(), maxGoal.toFloat())

                // Navigate to RewardActivity
                val intent = Intent(requireContext(), RewardFragment::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Invalid input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun drawPieChart(min: Float, max: Float) {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(min, "Min Goal"))
        entries.add(PieEntry(max, "Max Goal"))

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextSize = 14f

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.centerText = "Budget Goal"
        pieChart.setCenterTextSize(18f)
        pieChart.description.isEnabled = false
        pieChart.animateY(1000)
        pieChart.invalidate()
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                sessionManager.logout()
                startActivity(Intent(requireActivity(), MainActivity_login::class.java))
                requireActivity().finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

}




