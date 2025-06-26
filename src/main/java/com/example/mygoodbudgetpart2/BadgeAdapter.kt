package com.example.mygoodbudgetpart2

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class BadgeAdapter( private val badgeList: List<Badge>
) : RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder>() {

    inner class BadgeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val badgeIcon: ImageView = itemView.findViewById(R.id.badgeIcon)
        val title: TextView = itemView.findViewById(R.id.textBadgeTitle)
        val description: TextView = itemView.findViewById(R.id.textBadgeDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_badge_item, parent, false)
        return BadgeViewHolder(view)
    }

    override fun onBindViewHolder(holder: BadgeViewHolder, position: Int) {
        val badge = badgeList[position]
        holder.badgeIcon.setImageResource(badge.iconResId)
        holder.title.text = badge.title
        holder.description.text = badge.description
    }

    override fun getItemCount(): Int = badgeList.size
}