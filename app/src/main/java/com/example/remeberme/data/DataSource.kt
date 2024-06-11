package com.example.remeberme.data

import com.example.remeberme.R
import com.example.remeberme.model.SocialMedia
import com.example.remeberme.model.ToDoContact
import java.util.Date

class DataSource {
    fun loadToDoContact(): List<ToDoContact> {
        return listOf<ToDoContact>(
           ToDoContact(
               socialMedia = SocialMedia.Whatsapp,
               name = R.string.name1,
               lastContact = R.integer.lc1,
               savedDate = Date()
           ),
            ToDoContact(
                socialMedia = SocialMedia.Telegram,
                name = R.string.name2,
                lastContact = R.integer.lc2,
                savedDate = Date()
            ),
        )
    }
}