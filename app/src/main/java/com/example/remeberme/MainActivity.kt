package com.example.remeberme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remeberme.data.DataSource
import com.example.remeberme.model.ToDoContact
import com.example.remeberme.ui.theme.RemeberMeTheme
import java.text.SimpleDateFormat

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

@Composable
fun RememberMeApp(
    modifier: Modifier = Modifier,
){
   HomePage()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePage(modifier: Modifier = Modifier){
    val scrollState = rememberScrollableState { delta -> delta }
    CompositionLocalProvider (
        LocalOverscrollConfiguration provides null
    ){
        LazyColumn (
            modifier = Modifier
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
                ContactCard()
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(id = R.string.to_do_contact),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(16.dp))
                ToDoContact()
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(id = R.string.long_time_no_see),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(16.dp))
                ToDoContact()
            }

        }
    }
}

@Composable
fun ContactCard(modifier: Modifier = Modifier){
    val contactsAmount = 4;
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
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ){
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
                        Color(0xff0466C8)
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
                    onClick = ({
                    }),
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
fun ToDoContact(modifier: Modifier = Modifier){
    ToDoContactList(toDoContactList = DataSource().loadToDoContact())
}

@Composable
fun ToDoContactList(
    modifier: Modifier = Modifier,
    toDoContactList: List<ToDoContact>){
    for (todo in toDoContactList){
        Column {
            toDoContactCard(
                toDoContact = todo,
                modifier = Modifier.padding(8.dp)
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
fun toDoContactCard(modifier: Modifier = Modifier, toDoContact: ToDoContact){
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
                Icon(painter = painterResource(
                    id = R.drawable.dots_vertical),
                    contentDescription = "dots vertical"
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = "Last Contacted: ${LocalContext.current.getString(toDoContact.lastContact)} Days")
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


