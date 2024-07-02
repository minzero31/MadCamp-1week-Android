package com.example.a3tabtest.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a3tabtest.Contact
import com.example.a3tabtest.ContactAdapter
import com.example.a3tabtest.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class HomeFragment : Fragment(), AddContactDialog.AddContactListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var contactList: MutableList<Contact>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = root.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        contactList = loadContactsFromJson().toMutableList()
        contactAdapter = ContactAdapter(contactList, this)
        recyclerView.adapter = contactAdapter

        root.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            showAddContactDialog()
        }

        return root
    }

    private fun loadContactsFromJson(): List<Contact> {
        val file = File(requireContext().filesDir, "contacts.json")
        if (!file.exists()) {
            resources.openRawResource(R.raw.contacts).use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }
        val reader = file.inputStream().reader()
        return Json.decodeFromString(reader.readText())
    }

    private fun showAddContactDialog() {
        val dialog = AddContactDialog()
        dialog.setTargetFragment(this, 0)
        dialog.show(parentFragmentManager, "AddContactDialog")
    }

    override fun onContactAdded(contact: Contact) {
        Log.d("HomeFragment", "onContactAdded called with: $contact")
        contactList.add(contact)
        contactAdapter.notifyItemInserted(contactList.size - 1)
        saveContactsToJson()
    }


    private fun saveContactsToJson() {
        try {
            val jsonString = Json.encodeToString(contactList)
            val file = File(requireContext().filesDir, "contacts.json")
            file.writeText(jsonString)
            Log.d("HomeFragment", "Contacts saved successfully: $jsonString")
        } catch (e: Exception) {
            Log.e("HomeFragment", "Failed to save contacts", e)
        }
    }
}
