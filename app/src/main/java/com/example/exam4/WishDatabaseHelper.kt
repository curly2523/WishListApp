package com.example.exam4

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class WishDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME ="wishesapp.dp"
        private const val DATABASE_VERSION =1
        private const val TABLE_NAME="allwishes"
        private const val COLUMN_ID="id"
        private const val COLUMN_TITLE="title"
        private const val COLUMN_CONTENT ="content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery="CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITLE TEXT,$COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertWish(wish: Wish){
        val db=writableDatabase
        val values=ContentValues().apply {
            put(COLUMN_TITLE,wish.title)
            put(COLUMN_CONTENT,wish.content)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getAllWishes():List<Wish>{
        val wishList= mutableListOf<Wish>()
        val db=readableDatabase
        val query="SELECT * FROM $TABLE_NAME"
        val cursor=db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val wish=Wish(id,title,content)
            wishList.add(wish)
        }
        cursor.close()
        db.close()
        return wishList
    }

    fun updateWish(wish:Wish){
        val db=writableDatabase
        val values=ContentValues().apply {
            put(COLUMN_TITLE,wish.title)
            put(COLUMN_CONTENT,wish.content)
        }
        val whereClause="$COLUMN_ID=?"
        val whereArgs= arrayOf(wish.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }

    fun getWishByID(wishId:Int):Wish{
        val db =readableDatabase
        val query="SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID=$wishId"
        val cursor=db.rawQuery(query,null)
        cursor.moveToFirst()

        val id=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Wish(id,title,content)
    }

    fun deleteWish(wishId: Int){
        val db=writableDatabase
        val whereClause="$COLUMN_ID=?"
        val whereArgs= arrayOf(wishId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }

}