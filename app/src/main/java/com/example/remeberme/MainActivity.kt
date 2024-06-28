package com.example.remeberme

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.remeberme.data.DataSource
import com.example.remeberme.model.SocialMedia
import com.example.remeberme.model.ToDoContact
import com.example.remeberme.ui.theme.RemeberMeTheme
import java.text.SimpleDateFormat

enum class Screen {
    Splash, Home, AddContact, EditContact, AllContact
}
data class ContactState(val contact: ToDoContact? = null)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RemeberMeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    RememberMeApp()
                }
            }
        }
    }
}

@Preview
@Composable
fun RememberMeApp(
    modifier: Modifier = Modifier,
){
    var currentScreen by remember { mutableStateOf(Screen.Splash) }
    var contactState by remember { mutableStateOf(ContactState()) }
    when(currentScreen) {
        Screen.Splash -> SplashPage(onGetStartedClick = { currentScreen = Screen.Home })
        Screen.Home -> HomePage(
            onAddContactClick = { currentScreen = Screen.AddContact },
            onViewAllContactClick = {currentScreen = Screen.AllContact},
            onEditContactClick = { contact ->
                contactState = ContactState(contact)
                currentScreen = Screen.EditContact
            },

            )
        Screen.AddContact -> AddContactPage(onSaveClick = { currentScreen = Screen.Home }, onBackClick = { currentScreen = Screen.Home })
        Screen.EditContact -> EditContactPage(
            contactState = contactState,
            onSaveClick = { currentScreen = Screen.Home },
            onDeleteClick = { currentScreen = Screen.Home },
            onBackClick = { currentScreen = Screen.Home }
        )
        Screen.AllContact -> AllContactPage(
            onEditContactClick = { contact ->
                contactState = ContactState(contact)
                currentScreen = Screen.EditContact
            },
            onBackClick = { currentScreen = Screen.Home }
        )

    }
//    SplashPage()
//    HomePage()
}

@Composable
fun SplashPage(onGetStartedClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF051923))
            .padding(horizontal = 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Image(
                painter = painterResource(id = R.drawable.splash_image),
                contentDescription = "splashImage",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(bottom = 48.dp)
                .align(Alignment.BottomCenter),
            onClick = onGetStartedClick, // Update this line
            colors = ButtonDefaults.buttonColors(Color(0xFFD90368))
        ) {
            Text(
                text = stringResource(id = R.string.get_started),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePage(onAddContactClick: () -> Unit, onViewAllContactClick: () -> Unit, onEditContactClick: (ToDoContact) -> Unit){
    val scrollState = rememberScrollableState { delta -> delta }
    CompositionLocalProvider (
        LocalOverscrollConfiguration provides null
    ){
        LazyColumn (
            modifier = Modifier
                .background(Color(0xFFFFFFFF))
                .fillMaxSize()
                .padding(top = 33.dp)
                .padding(horizontal = 16.dp)
                .scrollable(
                    state = scrollState,
                    orientation = androidx.compose.foundation.gestures.Orientation.Vertical,
                    enabled = false // Disable scroll effect
                )
        ){
            item {
                Text(
                    text = stringResource(id = R.string.homepage_title),
                    fontSize = 46.sp,
                    lineHeight = 40.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(32.dp))
                ContactCard(onAddContactClick = onAddContactClick, onViewAllContactClick = onViewAllContactClick)
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(id = R.string.to_do_contact),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(16.dp))
                ToDoContact(onEditContactClick = onEditContactClick)
                Spacer(modifier = Modifier.height(32.dp))
            }

        }
    }
}

@Composable
fun ContactCard(onAddContactClick: () -> Unit, onViewAllContactClick: () -> Unit){
    val contactsAmount = 4
    Card(
        colors = CardDefaults.cardColors(
            Color(0xffF9FAFB)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 137.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 12.dp,
                    vertical = 14.dp
                ),
            verticalArrangement = Arrangement.SpaceBetween,

            ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Text(
                        text = contactsAmount.toString(),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(id = R.string.contact),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0XFF666D7A)
                    )
                }
                Button(
                    onClick = onViewAllContactClick,
                    colors = ButtonDefaults.buttonColors(Color(0xffF9FAFB)),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),

                    ) {
                    Text(
                        text = "See All",
                        color = Color(0xff0466C8),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.arrow___right),
                        contentDescription = "arrow right",
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xff0466C8)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp)
            ){
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = onAddContactClick,
                    colors = ButtonDefaults.buttonColors(Color(0xFFD90368))
                ){
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.plus_circle),
                            contentDescription = "plus circle",
                            modifier = Modifier.size(26.dp),
                            Color.White
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Add New",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ToDoContact(onEditContactClick: (ToDoContact) -> Unit){
    ToDoContactList(toDoContactList = DataSource().loadToDoContact(), onEditContactClick = onEditContactClick)
}

@Composable
fun ToDoContactList(
    toDoContactList: List<ToDoContact>,  onEditContactClick: (ToDoContact) -> Unit){
    for (todo in toDoContactList){
        Column {
            ToDoContactCard(
                toDoContact = todo,
                modifier = Modifier.padding(8.dp),
                onEditContactClick = onEditContactClick
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
//    LazyColumn(modifier = modifier) {
//        items(toDoContactList) { todocontact ->
//            toDoContactCard(
//                toDoContact = todocontact,
//                modifier = Modifier.padding(8.dp)
//            )
//            Spacer(modifier = Modifier.height(12.dp))
//        }
//    }
}

@Composable
fun ToDoContactCard(modifier: Modifier = Modifier, toDoContact: ToDoContact, onEditContactClick: (ToDoContact) -> Unit){
    val currentDate = toDoContact.savedDate
    val formatter = SimpleDateFormat("dd/MM/yy")
    val formattedDate = formatter.format(currentDate)

    Card(
        colors = CardDefaults.cardColors(
            Color(0xffF9FAFB)
//            Color.Red
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 137.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 12.dp,
                    vertical = 12.dp
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.whatsapp),
                            contentDescription = "social media icon",
                            Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = toDoContact.socialMedia.toString(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        text = LocalContext.current.getString(toDoContact.name,
                        ),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically,  horizontalArrangement = Arrangement.End, modifier = Modifier.padding(0.dp)) {
                    Button(
                        modifier = Modifier
                            .wrapContentSize(),
                        onClick = ({onEditContactClick(toDoContact)
                        }),
                        colors = ButtonDefaults.buttonColors(Color(0xffF9FAFB))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_icon),
                            contentDescription = "arrow right",
                            modifier = Modifier.size(12.dp),
                            Color(0xFF71717A)
                        )
                    }
                }

            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = "Saved: $formattedDate")
            }
            Box(
            ){
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = ({
                    }),
                    colors = ButtonDefaults.buttonColors(Color(0xffF9E3ED))
                ){
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Text(
                            text = "Done",
                            color = Color(0xffD90368),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun getSocialMediaIcon(socialMedia: SocialMedia): Painter {
    return when (socialMedia) {
        SocialMedia.Whatsapp -> painterResource(id = R.drawable.whatsapp)
        SocialMedia.Telegram -> painterResource(id = R.drawable.telegram)
        SocialMedia.X -> painterResource(id = R.drawable.x)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactPage(modifier: Modifier = Modifier, onSaveClick: () -> Unit, onBackClick: () -> Unit) {
    var contactName by remember { mutableStateOf("") }
    val context = LocalContext.current
    var selectedSocialMedia by remember { mutableStateOf<SocialMedia?>(null) }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 16.dp) // Padding vertikal tambahan jika diperlukan
            ) {
                Button(
                    onClick = onBackClick,
                    colors = ButtonDefaults.buttonColors(Color.White),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = "arrow right",
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFF1B1B1B)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp)) // Spacer untuk memberikan jarak horizontal
                Text(
                    text = "Add New Contact",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically) // Align text vertically with button
                )
            }
            Column {
                Text(
                    text = "Contact Name",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = contactName,
                    onValueChange = { contactName = it },
                    label = { Text("Insert Contact Name") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .heightIn(min = 56.dp),
                    textStyle = TextStyle(fontSize = 16.sp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                Text(
                    text = "Social Media",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Box {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {
                        OutlinedTextField(
                            value = selectedSocialMedia?.name ?: "Select Social Media",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Select social media") },
                            leadingIcon = {
                                selectedSocialMedia?.let {
                                    Icon(
                                        painter = getSocialMediaIcon(it),
                                        contentDescription = it.name
                                    )
                                }
                            },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                                .heightIn(min = 56.dp)
                                .clickable { expanded = !expanded } // Handle click event to toggle dropdown
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            SocialMedia.values().forEach { item ->
                                DropdownMenuItem(
                                    leadingIcon = {
                                        Icon(
                                            painter = getSocialMediaIcon(item),
                                            contentDescription = item.name,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    },
                                    text = { Text(text = item.name) },
                                    onClick = {
                                        selectedSocialMedia = item
                                        expanded = false
                                        Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                // Save new contact data here
                onSaveClick()
            },
            colors = ButtonDefaults.buttonColors(Color(0xFFD90368)),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Save Contact",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}




@Composable
fun EditContactPage(
    contactState: ContactState,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var contactName by remember { mutableStateOf("") }
    var socialMedia by remember { mutableStateOf("") }


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 16.dp) // Padding vertikal tambahan jika diperlukan
            ) {
                Button(
                    onClick = onBackClick,
                    colors = ButtonDefaults.buttonColors(Color.White),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = "arrow right",
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFF1B1B1B)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp)) // Spacer untuk memberikan jarak horizontal
                Text(
                    text = "Add New Contact",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically) // Align text vertically with button
                )
            }
            Column {
                Text(
                    text = "Contact Name",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = contactName,
                    onValueChange = { contactName = it },
                    label = { Text("Insert Contact Name") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .heightIn(min = 56.dp),
                    textStyle = TextStyle(fontSize = 16.sp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                Text(
                    text = "Social Media",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = socialMedia,
                    onValueChange = { socialMedia = it },
                    label = { Text("Select Social Media") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .heightIn(min = 56.dp),
                    textStyle = TextStyle(fontSize = 16.sp)
                )
            }
        }

        Button(
            onClick = {
                // Hapus kontak di sini
                onDeleteClick()
            },
            colors = ButtonDefaults.buttonColors(
                Color(0xFFFFFFFF), // Ganti warna tombol menjadi putih
                contentColor = Color(0xFFD90368) // Ganti warna konten tombol menjadi warna sebelumnya
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Delete Contact",
                color = Color(0xFFD90368), // Ganti warna teks menjadi warna sebelumnya
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Button(
            onClick = {
                // Simpan data kontak baru di sini
                onSaveClick()
            },
            colors = ButtonDefaults.buttonColors(Color(0xFFD90368)),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp)
        ) {
            Text(
                text = "Save Changes",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

    }
}

@Composable
fun AllContactPage(onEditContactClick: (ToDoContact) -> Unit, onBackClick: () -> Unit,) {
    val scrollState = rememberScrollableState { delta -> delta }
    val contactsAmount = 4;
    val toDoContactList = remember { DataSource().loadToDoContact() }
    LazyColumn (
        modifier = Modifier
            .background(Color(0xFFFFFFFF))
            .fillMaxSize()
            .padding(top = 33.dp)
            .padding(horizontal = 16.dp)
            .scrollable(
                state = scrollState,
                orientation = androidx.compose.foundation.gestures.Orientation.Vertical,
                enabled = false // Disable scroll effect
            )
    ){
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onBackClick,
                    colors = ButtonDefaults.buttonColors(Color.White),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = "arrow right",
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFF1B1B1B)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = contactsAmount.toString(),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(id = R.string.contact),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0XFF666D7A)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            AllContacts(onEditContactClick = onEditContactClick)
        }

    }
}

@Composable
fun AllContacts(modifier: Modifier = Modifier, onEditContactClick: (ToDoContact) -> Unit) {
    val toDoContactList = DataSource().loadToDoContact() // Ganti dengan metode untuk load semua kontak
    AllContactList(toDoContactList = toDoContactList, onEditContactClick = onEditContactClick)
}


@Composable
fun AllContactList(
    modifier: Modifier = Modifier,
    toDoContactList: List<ToDoContact>,  onEditContactClick: (ToDoContact) -> Unit){
    for (todo in toDoContactList){
        Column {
            AllContactCard(
                toDoContact = todo,
                modifier = Modifier.padding(8.dp),
                onEditContactClick = onEditContactClick
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
@Composable
fun AllContactCard(modifier: Modifier = Modifier, toDoContact: ToDoContact, onEditContactClick: (ToDoContact) -> Unit) {

    val currentDate = toDoContact.savedDate
    val formatter = SimpleDateFormat("dd/MM/yy")
    val formattedDate = formatter.format(currentDate)
    Card(
        colors = CardDefaults.cardColors(
            Color(0xffF9FAFB)
//            Color.Red
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 137.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 12.dp,
                    vertical = 12.dp
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.whatsapp),
                            contentDescription = "social media icon",
                            Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = toDoContact.socialMedia.toString(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        text = LocalContext.current.getString(toDoContact.name,
                        ),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically,  horizontalArrangement = Arrangement.End, modifier = Modifier.padding(0.dp)) {
                    Button(
                        modifier = Modifier
                            .wrapContentSize(),
                        onClick = ({onEditContactClick(toDoContact)
                        }),
                        colors = ButtonDefaults.buttonColors(Color(0xffF9FAFB))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_icon),
                            contentDescription = "arrow right",
                            modifier = Modifier.size(12.dp),
                            Color(0xFF71717A)
                        )
                    }
                }

            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = "Saved: $formattedDate")
            }
            Box(
            ){
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = ({
                    }),
                    colors = ButtonDefaults.buttonColors(Color(0xffF9E3ED))
                ){
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Text(
                            text = "Add Into To-Do",
                            color = Color(0xffD90368),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}