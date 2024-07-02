package com.example.a3tabtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a3tabtest.ui.home.ViewContactDialog

class ContactAdapter(private val contactList: List<Contact>, private val fragment: androidx.fragment.app.Fragment) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val phoneNumberTextView: TextView = itemView.findViewById(R.id.phoneNumberTextView)

        fun bind(contact: Contact, fragment: androidx.fragment.app.Fragment) {
            nameTextView.text = contact.name
            phoneNumberTextView.text = contact.phoneNumber
            itemView.setOnClickListener {
                val dialog = ViewContactDialog.newInstance(contact.name, contact.phoneNumber, contact.address)
                dialog.show(fragment.childFragmentManager, "ViewContactDialog")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentContact = contactList[position]
        holder.bind(currentContact, fragment)
    }

    override fun getItemCount() = contactList.size
}
