package com.example.remeberme.model

import android.media.Image
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import java.util.Date

enum class SocialMedia{
    Whatsapp, Telegram, X
}
public class ToDoContact(
//    val image: Image,
    val socialMedia: SocialMedia,
    @StringRes val name: Int,
    val lastContact: Int,
    val savedDate: Date
)

