package com.example.roomdatabaseexample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_memo.view.*

class MemoAdapter(val context: Context, val list: List<MemoEntity>) : RecyclerView.Adapter<MemoAdapter.MemoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder { // item_memo를 만드는 곳
        // LayoutInflater : 위의 context를 받아와서레이아웃을 만들어줌
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_memo, parent,false)

        return MemoViewHolder(itemView) // MemoViewHolder를 onCreateViewHolder를 통해 만듦
    }
    // MemoViewHolder(itemView)로 생성된 객체가 아래로 넘어감
    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {  // item_memo 틀과 내용을 합쳐주는 곳곳
        // list = 1,2,3 / position이 0 이라면 list 1 과 아래내용을 바인드함
        val memo = list[position]

        holder.memo.text = memo.memo
        holder.root.setOnLongClickListener(object : View.OnLongClickListener{   // root(item_memo)를 클릭하면
            override fun onLongClick(v: View?) : Boolean { // OnLongClick을 했을때 아래가 실행됨
                return true
            }

        })

    }
    // 바인드 전에 MemoViewHolder에서
    // inner class : 클래스 안에 클래스를 만듦
    inner class MemoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val memo = itemView.textview_memo
        val root = itemView.root


    }
    override fun getItemCount(): Int {  // 리스트를 몇개나 만들었는지 카운트
        return list.size
    }
}