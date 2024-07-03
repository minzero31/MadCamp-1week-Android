package com.example.a3tabtest.ui.dashboard

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityRecyclerViewBinding.inflate(inflater, container, false)

        initRecycler()
        loadSavedData()
        updateEmptyView() // 아이템 로드 후 빈 화면 업데이트
        return binding.root
    }

    private fun initRecycler() {
        itemList = mutableListOf()

        adapter = RecyclerAdapter(itemList)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        adapter.setItemClickListener(object : RecyclerAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                showImagePopup(itemList[position].imageUri)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_dashboard_settings -> {
                showAddImageDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showImagePopup(imageUri: Uri?) {
        if (imageUri == null) {
            return
        }
        val popupView = layoutInflater.inflate(R.layout.popup_image, null)
        val popupImageView = popupView.findViewById<ImageView>(R.id.popupImageView)
        Glide.with(this)
            .load(imageUri)
            .into(popupImageView)

        val popupWindow = PopupWindow(popupView, 900, 900)
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

        popupWindow.showAtLocation(binding.root, android.view.Gravity.CENTER, 0, 0)
    }

    private fun showAddImageDialog() {
        val dialog = AddImageDialog()
        dialog.setTargetFragment(this, 0)
        dialog.show(parentFragmentManager, "AddImageDialog")
    }

    override fun onImageAdded(uri: Uri, medicinename: String, takenday: String, isChecked: Boolean) {
        itemList.add(RecyclerModel(null, uri, medicinename, takenday, false))
        adapter.notifyItemInserted(itemList.size - 1)
        updateEmptyView()

        val sharedPref = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val itemIndex = itemList.size - 1

        editor.putString("uri_$itemIndex", uri.toString())
        editor.putString("medicinename_$itemIndex", medicinename)
        editor.putString("takenday_$itemIndex", takenday)
        editor.putBoolean("isChecked_$itemIndex", isChecked)
        editor.apply()
    }

    fun loadSavedData() {
        val sharedPref = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        itemList.clear()

        for (i in 0 until sharedPref.all.size / 4) {
            val uri = sharedPref.getString("uri_$i", null)?.let { Uri.parse(it) }
            val medicinename = sharedPref.getString("medicinename_$i", "")
            val takenday = sharedPref.getString("takenday_$i", "")
            val isChecked = sharedPref.getBoolean("isChecked_$i", false)

            if (uri != null && medicinename != null && takenday != null) {
                itemList.add(RecyclerModel(null, uri, medicinename, takenday, isChecked))
            }
        }

        adapter.notifyDataSetChanged()
        updateEmptyView()
    }

    private fun updateEmptyView() {
        if (itemList.isEmpty()) {
            binding.recyclerview.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
        } else {
            binding.recyclerview.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
        }
    }
}
