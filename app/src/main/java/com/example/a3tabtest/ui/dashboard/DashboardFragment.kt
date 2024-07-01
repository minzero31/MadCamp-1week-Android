package com.example.a3tabtest.ui.dashboard

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a3tabtest.R
import com.example.a3tabtest.databinding.ActivityRecyclerViewBinding
import com.example.a3tabtest.AddImageDialog

class DashboardFragment : Fragment(), AddImageDialog.AddImageListener {

    private lateinit var binding: ActivityRecyclerViewBinding
    private lateinit var itemList: MutableList<RecyclerModel>
    private lateinit var adapter: RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityRecyclerViewBinding.inflate(inflater, container, false)

        initRecycler()
        binding.buttonUpload.setOnClickListener {
            showAddImageDialog()
        }
        return binding.root
    }

    private fun initRecycler() {
        itemList = mutableListOf(
            RecyclerModel(R.drawable.item_lv_01, null,"마리떼 프랑소와 저버", "CLASSIC LOGO CAP beige"),
            RecyclerModel(R.drawable.item_lv_02, null,"마리떼 프랑소와 저버", "CLASSIC LOGO WOOL ECO BAG blue"),
            RecyclerModel(R.drawable.item_lv_03, null,"마리떼 프랑소와 저버", "COLOR BLOCK SATIN SCRUNCHIE navy"),
            RecyclerModel(R.drawable.item_lv_04, null,"마리떼 프랑소와 저버", "CLASSIC LOGO BACKPACK light blue"),
            RecyclerModel(R.drawable.item_lv_05, null,"마리떼 프랑소와 저버", "CLASSIC LOGO COLOR BEANIE navy"),
            RecyclerModel(R.drawable.background, null,"원브릴리언트", "Ivan-OB166-Black"),
            RecyclerModel(R.drawable.item_lv_07, null,"시엔느", "Washing Lettering Ball Cap (Navy)"),
            RecyclerModel(R.drawable.item_lv_08, null,"마뗑킴", "ACCORDION WALLET IN WHITE"),
            RecyclerModel(R.drawable.item_lv_09, null,"마리떼 프랑소와 저버", "CIRCLE LOGO SATIN HAIRBAND ivorye"),
            RecyclerModel(R.drawable.item_lv_10, null,"리엔느와르", "Dot Toggle Pearl Necklace (2color)"),
            RecyclerModel(R.drawable.pika, null,"리엔느와르", "Dot Toggle Pearl Necklace (2color)"),
            RecyclerModel(R.drawable.item_lv_06, null,"리엔느와르", "Dot Toggle Pearl Necklace (2color)"),
            RecyclerModel(R.drawable.item_lv_08, null,"마뗑2", "ACCORDION WALLET IN WHITE"),
            RecyclerModel(R.drawable.item_lv_09, null,"마리떼 프2랑소와 저버", "CIRCLE LOGO SATIN HAIRBAND ivorye")
        )

        adapter = RecyclerAdapter(itemList)
        // Adapter 설정
        binding.recyclerview.adapter = adapter
        // layoutManager 설정
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        adapter.setItemClickListener(object : RecyclerAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                showImagePopup(itemList[position].image)
            }
        })
    }

    private fun showImagePopup(image: Int?) {
        if (image == null){
            return
        }
        val popupView = layoutInflater.inflate(R.layout.popup_image, null)
        val popupImageView = popupView.findViewById<ImageView>(R.id.popupImageView)
        popupImageView.setImageResource(image)

        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

        // 팝업 창을 부모 뷰 중앙에 표시
        popupWindow.showAtLocation(binding.root, android.view.Gravity.CENTER, 0, 0)
    }

    private fun showAddImageDialog() {
        val dialog = AddImageDialog()
        dialog.setTargetFragment(this, 0) // 이 줄은 parentFragment에 연결하기 위해 필요함
        dialog.show(parentFragmentManager, "AddImageDialog")
    }

    override fun onImageAdded(uri: Uri, medicinename: String, takenday: String) {
        // Handle the image URI here

        // 예시로 새로운 항목을 추가하는 방법을 보여줍니다.
        itemList.add(RecyclerModel(null, uri, medicinename, takenday))
        adapter.notifyItemInserted(itemList.size - 1)
    }
}