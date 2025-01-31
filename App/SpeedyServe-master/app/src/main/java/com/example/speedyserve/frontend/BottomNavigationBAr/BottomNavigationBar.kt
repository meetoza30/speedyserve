package com.example.speedyserve.frontend.BottomNavigationBAr

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Dining
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Dining
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.speedyserve.frontend.Navigation.BottomBarViewModel
import com.example.speedyserve.frontend.Navigation.BottomNavigationEvents
import com.example.speedyserve.frontend.Navigation.Screens

data class DrawerItem(
    val string: String,
    val ScreenName : String,
    val selectedicon: ImageVector,
    val unselectedicon : ImageVector
)

@Composable
fun BottomNavigationBar(viewmodel: BottomBarViewModel,
                        navController: NavController,
                        modifier: Modifier = Modifier) {
    val onClick = viewmodel::OnBottomBarActions
    val currentScreen = viewmodel.currentScreen.value
    val navigationItem=listOf(
        DrawerItem(
            string = "Canteens",
            ScreenName = "${Screens.CANTEENSCREEN}",
            selectedicon = Icons.Filled.Dining,
            unselectedicon = Icons.Outlined.Dining,
        ),
        DrawerItem(
            string = "Cart",
            ScreenName = "${Screens.CARTSCREEN}",
            selectedicon = Icons.Filled.ShoppingCart,
            unselectedicon = Icons.Outlined.ShoppingCart
        ),
        DrawerItem(
            string = "Profile",
            ScreenName = "${Screens.PROFILESCREEN}",
            selectedicon = Icons.Filled.Person,
            unselectedicon = Icons.Outlined.Person
        ),

        )
    var selectedDrawerIndex by rememberSaveable {
        mutableStateOf(0)
    }
    NavigationBar(modifier) {
        navigationItem.forEachIndexed { index, it ->
            NavigationBarItem(
                selected = it.ScreenName==currentScreen,
                onClick = {
                    onClick(BottomNavigationEvents.Onclick(ScreenName = it.ScreenName,
                        navController = navController))
                },
                icon = {
                    if( it.ScreenName==currentScreen){
                        Icon(imageVector = it.selectedicon,
                            "")
                    }else Icon(imageVector = it.unselectedicon,
                        "")
                }
                , label = {
                    Text(text = it.string)
                }
            )
            Log.d("navtest1", (it.ScreenName==currentScreen).toString())
        }
    }

}