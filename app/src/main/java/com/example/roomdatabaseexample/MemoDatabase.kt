package com.example.roomdatabaseexample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(MemoEntity::class), version = 1)
abstract class MemoDatabase : RoomDatabase(){
    // MemoDAO를 반환하는 추상화 메소드
    abstract fun memoDAO() : MemoDAO

    // get instance
    companion object {
        // 싱글턴 패턴, 객체를 딱 한번만 생성
        var INSTANCE : MemoDatabase? = null

        fun genInstance(context: Context) : MemoDatabase? {
            if(INSTANCE == null){
                synchronized(MemoDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    MemoDatabase::class.java,"memo.db")
                        .fallbackToDestructiveMigration()   // 버전이 업그레이드 됐을때는 덮어씌움
                        .build()
                }
            }
            return INSTANCE
        }
    }

}