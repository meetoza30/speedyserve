
import android.media.Rating
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Square
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.speedyserve.Backend.Authentication.Models.Dish
import com.example.speedyserve.Backend.Authentication.Models.OrderDish
import com.example.speedyserve.R
import com.example.speedyserve.frontend.MenuScreen.MenuScreenActions
import com.example.speedyserve.frontend.MenuScreen.MenuScreenViewModel
import com.example.speedyserve.frontend.cartScreen.CartScreenActions
import com.example.speedyserve.frontend.cartScreen.CartViewmodel


@Preview
@Composable
fun CartScreen(modifier: Modifier= Modifier) {
    val context=LocalContext.current
    val viewmodel: CartViewmodel = hiltViewModel()
    val dishlist = viewmodel.orderdishlist
    Log.d("cartcheck",dishlist.toString())
    Log.d("cartcheck2",viewmodel.orderdishlist.toString())
    Box(modifier = modifier.fillMaxSize()){
        LazyColumn(modifier= Modifier.fillMaxSize()) {
            items(dishlist) { dish ->
                OrderCard(dish,
                    viewModel = viewmodel)

            }
            item{
//                BillSummary()
            }
            item{
                TimeSlotSelector(viewmodel)
            }
            item{
                BillSummary(viewmodel)
            }
            item{
                Button(
                    onClick = {viewmodel.onEvent(CartScreenActions.placeOrder)
                              startPayment(context = context,viewmodel.calculate().toInt())},
                    modifier = Modifier.fillMaxWidth().height(50.dp).padding(start = 40.dp, end = 40.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(Color(0xFFFF9800), Color(0xFF995B00)),
                                center = androidx.compose.ui.geometry.Offset(0f, 0f),
                                radius = 400f
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Text(
                        text = "place Order",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFFFFF),
                        )
                    )
                }
            }
        }

    }
}
@Composable
fun TimeSlotSelector(viewmodel: CartViewmodel) {
    val timeSlots = listOf("3:00 AM - 4:00 AM", "4:00 AM - 5:00 PM", "5:00 PM - 6:00 PM")
    var selectedSlot by remember { mutableStateOf(timeSlots[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Select a Time Slot",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            timeSlots.forEach { slot ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedSlot = slot }
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = selectedSlot == slot,
                        onClick = { selectedSlot = slot
                        viewmodel.onEvent(CartScreenActions.TimeSlot(slot))}
                    )
                    Text(
                        text = slot,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

//@Composable
//fun CartScreenContent(dishes : List<OrderDish>,
//                      viewModel: CartViewmodel){
//    var scrollState = rememberScrollState()
//        LazyColumn(
//            modifier = Modifier.fillMaxSize(),
//            contentPadding = PaddingValues(horizontal = 16.dp,),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(dishes) { dish ->
//                OrderCard(dish,
//                    viewModel = viewModel)
//
//            }
//            item {
//                BillSummary()
//            }
//        }
//
//        Button(
//            onClick = {/* include the logic for timeslot*/},
//            modifier = Modifier.fillMaxWidth().height(50.dp).padding(start = 40.dp, end = 40.dp)
//                .background(
//                    brush = Brush.radialGradient(
//                        colors = listOf(Color(0xFFFF9800), Color(0xFF995B00)),
//                        center = androidx.compose.ui.geometry.Offset(0f, 0f),
//                        radius = 400f
//                    ),
//                    shape = RoundedCornerShape(12.dp)
//                ),
//            colors = ButtonDefaults.buttonColors(Color.Transparent),
//            elevation = ButtonDefaults.buttonElevation(0.dp)
//        ) {
//            Text(
//                text = "Select Time Slot",
//                style = TextStyle(
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFFFFFFFF),
//                )
//            )
//        }
//
//    }
//

@Composable
fun BillSummary(viewModel: CartViewmodel) {
    val totalPrice = viewModel.calculate()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text("To Pay", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        viewModel.orderdishlist.forEach { dish ->
            BillRow("${dish.quantity} ${dish.dishname}", Price = "₹${dish.price.toDoubleOrNull() ?: 0.0}")
        }
        BillRow("Total", Price = "₹$totalPrice", isBold = true)
    }
}

@Composable
fun BillRow(
    label: String,
    Price: String,
    isBold: Boolean = false,
    isGreen: Boolean = false,
    isClickable: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )

        Row {
            Text(
                text = Price,
                fontSize = 14.sp,
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
                color = when {
                    isGreen -> Color(0xFF4CAF50) // Green color for FREE
                    isClickable -> Color(0xFFFF5722) // Orange color for clickable "Add a tip"
                    else -> Color.Black
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTopBar(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .background(color = Color(0xFFECEFF1)),
    ) {
        CenterAlignedTopAppBar(modifier = Modifier.background(color = Color(0xFFECEFF1)),
            title = {
                Text(
                    "My Cart",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFECEFF1),
                titleContentColor = Color(0xFF000000)
            ),
            navigationIcon = {
                IconButton(onClick = { /* Handle notification click */ }) {
                    Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "back")
                }
            }
        )
    }
}

@Composable
fun OrderCard(item : OrderDish,
             viewModel: CartViewmodel,
             modifier: Modifier = Modifier) {
    val counter = remember{mutableStateOf(item.quantity)}
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
                Image(painterResource(R.drawable.dish),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp))
//                }else{
//                    Image(painter = painterResource(id = R.drawable.veg),
//                        contentDescription = "",
//                        modifier = Modifier.size(20.dp))
//                }

                Text(text = item.dishname,
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
                Card(modifier = Modifier.align(Alignment.CenterHorizontally)) {

                    Row {
                        IconButton(onClick = {if( counter.value>0) viewModel.onEvent(CartScreenActions.DecreaseQuantity(item))
                            if( counter.value>0) counter.value=counter.value-1
                        }) {
                            Text(text = "-")
                        }
                        Text(text = counter.value.toString(),
                            fontSize = 13.sp,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        IconButton(onClick = {viewModel.onEvent(CartScreenActions.IncreaseQuantity(item))
                        counter.value=counter.value+1}) {
                            Icon(imageVector = Icons.Default.Add, " ")
                        }
                    }
                }

            }
        }

    }
}

