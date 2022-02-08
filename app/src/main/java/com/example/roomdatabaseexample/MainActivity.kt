package com.example.roomdatabaseexample

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

@SuppressLint("StaticFieldLeak")    // 안드로이드는 Lint를 통해 성능상 문제가 있을 수 있는 코드를 관리해줌
class MainActivity : AppCompatActivity() {
    // Database 변수들
    lateinit var db : MemoDatabase  // lateinit : 나중에 초기화를 해줌
    var memoList = listOf<MemoEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // db 지정 및 싱글턴 객체 가져옴
        db = MemoDatabase.genInstance(this)!!

        button_add.setOnClickListener {
            var memo = MemoEntity(12, editText_memo.text.toString())
            InsertMomo(memo)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)


    }
    // 1. Insert Data
    fun InsertMomo(memo : MemoEntity){
        // MainThread vs WorkerThread(Background Thread)
        // MainThread : 모든 UI 관련된 일들 처리 (텍스트 색상 변경, 버튼을 클릭하면 카운트 + 등)
        // WorkerThread(Background Thread) : 모든 데이터 통신과 관련된 일들 처리 (api에서 데이터 받기, 룸 데이터베이스에서 읽고 쓰기 등)
        val insertTask = object : AsyncTask<Unit, Unit, Unit>(){    // 자바의 void와 비슷
            override fun doInBackground(vararg p0: Unit?) { // insertTask를 백그라운드에서 무슨일을 할지 아래 정의함
                db.memoDAO().insert(memo)   // 위에서 초기화한 db에서 memoDAO()를 불러온 다음 insert(memo) 해줌
            }
            // insert가 끝나고 나서 무엇을 할지 아래 작성
            override fun onPostExecute(result: Unit?) { // Execute한 다음 무엇을 할지 아래 작성
                super.onPostExecute(result)
                getAllMemos()   // memo 모두 불러옴
            }
        }
        insertTask.execute()
    }
    // 모든 메모들을 가져옴
    fun getAllMemos(){
        val getTask = object : AsyncTask<Unit, Unit, Unit>(){   // AsyncTask : 비동기적인 활동이나 백그라운드 활동을 도와주는 클래스
            override fun doInBackground(vararg p0: Unit?) {
                memoList = db.memoDAO().getAll()    // 비어있던 memoList에 새로운 memoList를 넣어줌
            }
            // memoList를 다 가져왔다면 아래 실행
            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                setRecyclerView(memoList)
            }
        }
        getTask.execute()
    }
    // 2. Get Data
    fun getData(){

    }
    // 3. Delete Data
    fun deleteData(){

    }
    // 4. Set RecyclerView, MemoAdapter를 연결해줌
    fun setRecyclerView(memoList : List<MemoEntity>){
        recyclerView.adapter = MemoAdapter(this, memoList)
    }

}