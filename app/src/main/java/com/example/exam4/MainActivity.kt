package com.example.exam4

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exam4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db:WishDatabaseHelper
    private lateinit var wishesAdapter: WishAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db= WishDatabaseHelper(this)
        wishesAdapter= WishAdapter(db.getAllWishes(),this)

        binding.wishesRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.wishesRecyclerView.adapter=wishesAdapter

        binding.addButton.setOnClickListener {
            val intent =Intent(this,AddWishActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        wishesAdapter.refreshData(db.getAllWishes())
    }
}