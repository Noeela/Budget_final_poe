package com.example.mygoodbudgetpart2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RewardFragment : Fragment() {

    private lateinit var recyclerViewBadges: RecyclerView
    private lateinit var badgeAdapter: BadgeAdapter
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reward, container, false)

        databaseHelper = DatabaseHelper(requireContext())
        sessionManager = SessionManager(requireContext())

        recyclerViewBadges = view.findViewById(R.id.recyclerViewBadges)

        // Sample badge data
        val badges = listOf(
            Badge(R.drawable.ic_badge_saver, "Saver", "Completed a spending goal"),
            Badge(R.drawable.ic_badge_first_budget, "First Budget", "Created your first budget"),
            Badge(R.drawable.ic_badge_consistent, "Consistent", "Maintained goals for 3 months"),
            Badge(R.drawable.ic_badge_master, "Budget Master", "Managed 5 budgets successfully")
        )

        badgeAdapter = BadgeAdapter(badges)
        recyclerViewBadges.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewBadges.adapter = badgeAdapter

        return view
    }
}