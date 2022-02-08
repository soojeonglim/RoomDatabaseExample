package com.example.roomdatabaseexample

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface MemoDAO {

    @Insert(onConflict = REPLACE)   // Insert를 할때 primarykey가 똑같으면 덮어씌움
    fun insert(memo : MemoEntity)

    // database안에있는 모든 memo를 불러옴
    @Query("SELECT * FROM memo")
    fun getAll() : List<MemoEntity>

    @Delete
    fun delete(memo : MemoEntity)

}