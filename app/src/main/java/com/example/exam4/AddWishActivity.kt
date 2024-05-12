package com.example.exam4

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.exam4.databinding.ActivityAddWishBinding

class AddWishActivity : AppCompatActivity() {

     private lateinit var binding: ActivityAddWishBinding
     private lateinit var db:WishDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddWishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db=WishDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val tile=binding.titleEditText.text.toString()
            val content =binding.contentEditText.text.toString()
            val wish=Wish(0,tile,content)
            db.insertWish(wish)
            finish()
            Toast.makeText(this,"Wish saved in the list",Toast.LENGTH_SHORT).show()
        }

    }
}