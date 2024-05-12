package com.example.exam4

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class WishAdapter (private var wishes:List<Wish>,context: Context):
    RecyclerView.Adapter<WishAdapter.WishesViewHolder>() {

        private val db:WishDatabaseHelper= WishDatabaseHelper(context)

        class WishesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val titleTextView: TextView =itemView.findViewById(R.id.titleWishView)
            val contentTextView: TextView =itemView.findViewById(R.id.contentTextView)
            val updateButton:ImageView=itemView.findViewById(R.id.updateButton)
            val deleteButton:ImageView=itemView.findViewById(R.id.deleteButton)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishesViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.wish_item,parent,false)
        return WishesViewHolder(view)
    }

    override fun getItemCount(): Int=wishes.size

    override fun onBindViewHolder(holder: WishesViewHolder, position: Int) {
        val wish=wishes[position]
        holder.titleTextView.text=wish.title
        holder.contentTextView

        holder.updateButton.setOnClickListener{
            val intent=Intent(holder.itemView.context,UpdateWishActivity::class.java).apply{
                putExtra("wish_id",wish.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deleteWish(wish.id)
            refreshData(db.getAllWishes())
            Toast.makeText(holder.itemView.context,"Wish deleted",Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newWishes:List<Wish>){
        wishes=newWishes
        notifyDataSetChanged()
    }
}