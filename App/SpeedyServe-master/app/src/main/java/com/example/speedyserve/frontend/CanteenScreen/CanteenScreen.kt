package com.example.speedyserve.frontend.CanteenScreen

import android.util.Log
import android.view.Surface
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.speedyserve.Backend.Authentication.Models.Canteen
import com.example.speedyserve.R
import kotlinx.coroutines.flow.toList

@Composable
fun HomeScreen(onCanteenClick : (_id : String)-> Unit,
               modifier: Modifier = Modifier) {
    val viewmodel : CanteenScreenViewModel = hiltViewModel()
    val list by viewmodel.canteens.collectAsState()
    Log.d("listcheck1",list.toString())
    LazyColumn {
        item{
            TopComponentMainScreen(modifier = Modifier.padding(vertical = 30.dp, horizontal = 10.dp))
    }
        if(list.isNotEmpty()){
            items(list) { item ->
                CanteenCard(
                    item = item,
                    name = item.name,
                    openingTime = item.openingTime,
                    closingTime = item.closingTime,
                    isOpen = true,
                    onclick = onCanteenClick
                )
            }
        }
    }
}


@Composable
fun TopComponentMainScreen(
    modifier: Modifier = Modifier) {
    Card (colors = CardDefaults.cardColors(Color.Transparent),
        modifier = modifier){
        Box (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 8.dp, bottom = 0.dp) // padding for the box
        ){
            TextComponent(modifier = Modifier.align(Alignment.TopStart))
            AvatarComponent(modifier= Modifier.align(Alignment.TopEnd))

        }
        SearchBarComponent(modifier= Modifier
            .padding(start = 12.dp, end = 12.dp, top = 0.dp, bottom = 15.dp))
    }
}

@Composable
fun TextComponent(modifier: Modifier = Modifier) {
    Column(modifier= modifier
        .fillMaxWidth(0.6f)) {
        Text(text = "Welcome yash",
            style = MaterialTheme.typography.titleSmall)
        Text(text = "Ready to Eat!!",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun AvatarComponent(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .clip(CircleShape)
    ) {
        Image(painter = painterResource(R.drawable.frog),
            "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(80.dp)
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(modifier: Modifier = Modifier) {
   SearchBar(inputField = {
       SearchBarDefaults.InputField(
           query = "",
           onQueryChange = {},
           onSearch = {},
           onExpandedChange = {},
           expanded = false,
           trailingIcon = { Icon(imageVector = Icons.Default.Search, "") }
       )
   },
       expanded = false,
       onExpandedChange = {}) { }

}

@Composable
fun CanteenCard(
    item : Canteen,
    name: String,
    openingTime: String?,
    closingTime: String?,
    onclick: (_id : String)-> Unit,
    isOpen: Boolean=true
) {

    Card(
        modifier = Modifier
            .clickable{onclick(item._id)}
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)) // Light gray background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left Side: Canteen Name and Timing
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "opening time: $openingTime",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Gray
                    )
                )
                Text(
                    text = "closing time: $closingTime",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Gray
                    )
                )
            }

            // Right Side: Status
            Box(
                modifier = Modifier
                    .background(
                        color = if (isOpen) Color(0xFF4CAF50) else Color(0xFFFF5722), // Green for open, Orange for closed
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isOpen) "open" else "closed",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}
