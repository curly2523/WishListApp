package com.example.exam4

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.exam4.databinding.ActivityUpdateWishBinding


class UpdateWishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateWishBinding
    private lateinit var db:WishDatabaseHelper
    private var wishId:Int=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUpdateWishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db=WishDatabaseHelper(this)

        wishId=intent.getIntExtra("wish_id",-1)
        if (wishId==-1){
            finish()
            return
        }

        var wish=db.getWishByID(wishId)
        binding.updateTitleEditText.setText(wish.title)
        binding.updateContentEditText.setText(wish.content)

        binding.updateSaveButton.setOnClickListener {
            val newTitle=binding.updateTitleEditText.text.toString()
            val newContent=binding.updateContentEditText.text.toString()
            val updatedWish=Wish(wishId,newTitle,newContent)
            db.updateWish(updatedWish)
            finish()
            Toast.makeText(this,"Changes Saved",Toast.LENGTH_SHORT).show()
        }


    }
}