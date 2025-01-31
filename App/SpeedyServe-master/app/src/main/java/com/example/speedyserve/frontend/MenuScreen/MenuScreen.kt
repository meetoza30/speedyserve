import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Square
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.speedyserve.Backend.Authentication.Models.Dish
import com.example.speedyserve.Backend.Authentication.Models.OrderDish
import com.example.speedyserve.R
import com.example.speedyserve.frontend.MenuScreen.MenuScreenActions
import com.example.speedyserve.frontend.MenuScreen.MenuScreenViewModel
import com.example.speedyserve.frontend.Navigation.Screens
import kotlinx.coroutines.flow.toList

@Composable
fun MenuScreen(navController: NavHostController,
               modifier: Modifier = Modifier) {
    val viewmodel: MenuScreenViewModel = hiltViewModel()
    val list=viewmodel.menu.collectAsState()
    Log.d("checkTest",viewmodel.ordereddishList.toString())

        Box (modifier= Modifier){
            LazyColumn (modifier = Modifier.fillMaxSize()
                .align(Alignment.TopCenter)){
                item{
                    Topbar("Menu",{},modifier.windowInsetsPadding(WindowInsets(0.dp)))
                }
                item{
                    BodyPartMenuScreen()
                }
                if(list.value.isNotEmpty()){
                    items(list.value){
                            item->
                        MenuCard(item,
                            viewmodel)
                    }
                }

            }
            Log.d("clicked",viewmodel.ordereddishList.isNotEmpty().toString())
            if(viewmodel.isDishListEmpty.value==false){
                Log.d("clicked","fab clicked")
                FloatingActionButton(
                    onClick = {navController.navigate("${Screens.CARTSCREEN}")},
                    modifier= Modifier.align(Alignment.BottomEnd)
                        .padding(horizontal = 30.dp, vertical = 20.dp)

                ) {
                    Text(text = "Go to Cart",
                        modifier= Modifier.padding(10.dp))
                }
            }

    }




}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topbar(title : String,onback : ()->Unit,modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Row {
                Text(text = title,
                    modifier=Modifier.align(Alignment.CenterVertically)
                        .weight(0.4f))
                Spacer(modifier = Modifier.weight(0.2f))
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.FavoriteBorder,
                        modifier = Modifier.weight(0.2f),
                        contentDescription ="Favourite" )

                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = ""
                        ,modifier = Modifier.weight(0.2f))
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Share, contentDescription ="ShareButton" ,
                        modifier = Modifier.weight(0.2f))
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onback) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription ="back button" )

            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp)
    )
}

@Composable
private fun BodyPartMenuScreen(modifier: Modifier = Modifier) {
    Column (modifier = Modifier.fillMaxWidth()){
        Text(text = "VIIT Canteen",
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(5.dp))
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center){
            Icon(imageVector = Icons.Default.Star,
                contentDescription ="",
                modifier=Modifier.size(16.dp))
            Text(text = "Pure veg ")
            Spacer(modifier = Modifier.padding(3.dp))
            Icon(imageVector = Icons.Default.Star,
                contentDescription ="",
                modifier=Modifier.size(16.dp))
            Text(text = "Pure veg ")
            Spacer(modifier = Modifier.padding(3.dp))
            Icon(imageVector = Icons.Default.Star,
                contentDescription ="",
                modifier=Modifier.size(16.dp))
            Text(text = "Pure veg ")
        }


    }
}


@Composable
fun MenuCard(item : Dish,
             viewModel: MenuScreenViewModel,
             modifier: Modifier = Modifier) {
    Card (shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.elevatedCardElevation(5.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier = modifier
            .padding(15.dp)
            .fillMaxWidth()){
        Row {
            Column (modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 8.dp)){
//                if(dishItem.Nonveg) {
                    Image(painterResource(R.drawable.veg),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp))
//                }else{
//                    Image(painter = painterResource(id = R.drawable.veg),
//                        contentDescription = "",
//                        modifier = Modifier.size(20.dp))
//                }

                Text(text = item.name,
                    fontSize = 20.sp,
//                    fontFamily = FontFamily(Font(R.font.cabin_semibold)),
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                Row (modifier = Modifier.padding(bottom = 5.dp)){
                    for(i in 0..4){
                        Image(imageVector = Icons.Outlined.Star,
                            contentDescription = "",
                            modifier=Modifier.size(10.dp))
                    }
                }
                Text(text = item.price,
                    modifier = Modifier.padding(bottom = 5.dp))
                Text(text = item.description,
//                    fontFamily = FontFamily(Font(R.font.cabin_med)),
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
            Column (
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp)) {
                Spacer(modifier = Modifier.padding(12.dp))
                Card (modifier=Modifier.padding(horizontal = 30.dp)
                    ,
//                            colors = CardDefaults.cardColors(Color.White)
                ){
                    Image(painter =painterResource(R.drawable.dish),
//                        rememberAsyncImagePainter(model = dishItem.img_link),
                        contentDescription ="",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(height = 95.dp, width = 100.dp)
                    )
                }
                OutlinedButton(
                    onClick = {
                        viewModel.onEvent(MenuScreenActions.addDish(item))
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Add",
                    )
                }
            }
        }

    }
}