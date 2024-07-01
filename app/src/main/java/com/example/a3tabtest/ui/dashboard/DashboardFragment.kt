package com.example.a3tabtest.ui.dashboard

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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
        loadSavedData()
        return binding.root
    }

    private fun initRecycler() {
        itemList = mutableListOf(
        )

        adapter = RecyclerAdapter(itemList)
        // Adapter 설정
        binding.recyclerview.adapter = adapter
        // layoutManager 설정
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        adapter.setItemClickListener(object : RecyclerAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                showImagePopup(itemList[position].imageUri)
            }
        })
    }

    private fun showImagePopup(imageUri: Uri?) {
        if (imageUri == null){
            return
        }
        val popupView = layoutInflater.inflate(R.layout.popup_image, null)
        val popupImageView = popupView.findViewById<ImageView>(R.id.popupImageView)
        Glide.with(this)
            .load(imageUri)
            .into(popupImageView)

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

        // SharedPreferences 객체 가져오기
        val sharedPref =requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        // 고유한 키를 생성하기 위해 itemList 크기를 사용합니다.
        val itemIndex = itemList.size - 1

        // 데이터를 SharedPreferences에 저장
        editor.putString("uri_$itemIndex", uri.toString())
        editor.putString("medicinename_$itemIndex", medicinename)
        editor.putString("takenday_$itemIndex", takenday)
        editor.apply()
    }

    fun loadSavedData() {
        val sharedPref = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        itemList.clear()

        for (i in 0 until sharedPref.all.size / 3) { // 각 항목당 3개의 데이터 (uri, medicinename, takenday)
            val uri = sharedPref.getString("uri_$i", null)?.let { Uri.parse(it) }
            val medicinename = sharedPref.getString("medicinename_$i", "")
            val takenday = sharedPref.getString("takenday_$i", "")

            if (uri != null && medicinename != null && takenday != null) {
                itemList.add(RecyclerModel(null, uri, medicinename, takenday))
            }
        }

        adapter.notifyDataSetChanged()
    }
}