package com.example.a3tabtest.ui.dashboard

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a3tabtest.R

class RecyclerAdapter(val items: MutableList<RecyclerModel>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    // View Holder를 생성하고 View를 붙여주는 역할의 메서드
    interface onItemClickListener {
        fun onItemClick(position: Int)
    }
    // onItemClickListener 선언
    private lateinit var itemClickListener: onItemClickListener
    // OnItemClickListener 등록 메서드
    fun setItemClickListener(itemClickListener: onItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview, parent, false)
        return ViewHolder(v)
    }

    // 생성된 View Holder에 데이터를 바인딩 해주는 메서드
    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
        holder.bindItems(items[position])
    }

    // 데이터의 개수를 반환하는 메서드
    override fun getItemCount(): Int {
        return items.count()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bindItems(items: RecyclerModel) {
            val imageArea = itemView.findViewById<ImageView>(R.id.imageArea)
            val titleArea = itemView.findViewById<TextView>(R.id.titleArea)
            val contentArea = itemView.findViewById<TextView>(R.id.contentArea)


            when {
                items.imageUri != null -> {
                    Glide.with(itemView.context)
                        .load(items.imageUri)
                        .into(imageArea)
                }

                items.image != null -> {
                    imageArea.setImageResource(items.image)
                }

                else -> {
                    imageArea.setImageResource(R.drawable.pika) // 기본 이미지 설정
                }
            }
            titleArea.text = items.title
            contentArea.text = items.content
        }

    }
}