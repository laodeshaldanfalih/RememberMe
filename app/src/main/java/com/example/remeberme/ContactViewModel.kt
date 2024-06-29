package com.example.remeberme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.remeberme.data.Contact
import com.example.remeberme.data.ContactDao
import kotlinx.coroutines.launch

class ContactViewModel(private val contactDao: ContactDao) : ViewModel() {

    /* Insert new item to db */
    fun addNewContact(contactName: String, contactType: String) {
        val newContact = getNewContactEntry(contactName, contactType)
        insertContact(newContact)
    }

    fun isEntryValid(contactName: String, contactType: String): Boolean {
        if (contactName.isBlank() || contactType.isBlank()) {
            return false
        }
        return true
    }

    /* Launching a new coroutine to insert an item in a non-blocking way */
    private fun insertContact(contact: Contact) {
        viewModelScope.launch {
            contactDao.insert(contact)
        }
    }

    private fun getNewContactEntry(contactName: String, contactType: String): Contact {
        return Contact(
            contactName = contactName,
            contactType = contactType
        )
    }

}
class ContactViewModelFactory(private val contactDao: ContactDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(contactDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}