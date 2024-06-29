package com.example.a3tabtest.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.a3tabtest.R
import com.example.a3tabtest.databinding.ActivityRecyclerViewBinding

class DashboardFragment : Fragment() {
    lateinit var binding: ActivityRecyclerViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_recycler_view, container, false)

        initRecycler()
        return binding.root
    }

    private fun initRecycler() {
        val itemList = mutableListOf<RecyclerModel>()
        itemList.add(RecyclerModel(R.drawable.item_lv_01, "마리떼 프랑소와 저버", "CLASSIC LOGO CAP beige", "49,000원"))
        itemList.add(RecyclerModel(R.drawable.item_lv_02, "마리떼 프랑소와 저버", "CLASSIC LOGO WOOL ECO BAG blue", "49,000원"))
        itemList.add(RecyclerModel(R.drawable.item_lv_03, "마리떼 프랑소와 저버", "COLOR BLOCK SATIN SCRUNCHIE navy", "39,000원"))
        itemList.add(RecyclerModel(R.drawable.item_lv_04, "마리떼 프랑소와 저버", "CLASSIC LOGO BACKPACK light blue", "159,000원"))
        itemList.add(RecyclerModel(R.drawable.item_lv_05, "마리떼 프랑소와 저버", "CLASSIC LOGO COLOR BEANIE navy", "49,000원"))
        itemList.add(RecyclerModel(R.drawable.background, "원브릴리언트", "Ivan-OB166-Black", "112,000원"))
        itemList.add(RecyclerModel(R.drawable.item_lv_07, "시엔느", "Washing Lettering Ball Cap (Navy)", "49,000원"))
        itemList.add(RecyclerModel(R.drawable.item_lv_08, "마뗑킴", "ACCORDION WALLET IN WHITE", "88,000원"))
        itemList.add(RecyclerModel(R.drawable.item_lv_09, "마리떼 프랑소와 저버", "CIRCLE LOGO SATIN HAIRBAND ivorye", "29,000원"))
        itemList.add(RecyclerModel(R.drawable.item_lv_10, "리엔느와르","Dot Toggle Pearl Necklace (2color)", "57,000원"))
        itemList.add(RecyclerModel(R.drawable.pika, "리엔느와르","Dot Toggle Pearl Necklace (2color)", "57,000원"))
        itemList.add(RecyclerModel(R.drawable.item_lv_06, "리엔느와르","Dot Toggle Pearl Necklace (2color)", "57,000원"))
        itemList.add(RecyclerModel(R.drawable.item_lv_08, "마뗑2", "ACCORDION WALLET IN WHITE", "88,000원"))
        itemList.add(RecyclerModel(R.drawable.item_lv_09, "마리떼 프2랑소와 저버", "CIRCLE LOGO SATIN HAIRBAND ivorye", "29,000원"))
        itemList.add(RecyclerModel(R.drawable.item_lv_10, "리엔느와2르","Dot Toggle Pearl Necklace (2color)", "57,000원"))
        itemList.add(RecyclerModel(R.drawable.pika, "리엔느와2르","Dot Toggle Pearl Necklace (2color)", "57,000원"))
        itemList.add(RecyclerModel(R.drawable.item_lv_06, "리엔2느와르","Dot Toggle Pearl Necklace (2color)", "57,000원"))
        itemList.add(RecyclerModel(R.drawable.pika, "리엔느와2르","Dot Toggle Pearl Necklace (2color)", "57,000원"))
        val adapter = RecyclerAdapter(itemList)
        // Adapter 설정
        binding.recyclerview.adapter = adapter
        // layyoutManager 설정
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter.setItemClickListener(object: RecyclerAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                showImagePopup(itemList[position].image)
            }
        })
    }

    private fun showImagePopup(image: Int){
        val inflater = LayoutInflater.from(requireContext())
        val popupView = inflater.inflate(R.layout.popup_image, null)
        val popupImageView = popupView.findViewById<ImageView>(R.id.popupImageView)
        popupImageView.setImageResource(image)

        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

        // 팝업 창을 부모 뷰 중앙에 표시
        popupWindow.showAtLocation(binding.root, android.view.Gravity.CENTER, 0, 0)
    }
}