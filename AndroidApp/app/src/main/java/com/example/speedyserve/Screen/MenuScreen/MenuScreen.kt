@file:OptIn(ExperimentalMaterial3Api::class)

package com.speedyserve.ui.screens

import android.graphics.Paint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedyserve.Screen.MenuScreen.MenuScreenVM
import com.example.speedyserve.Screen.MenuScreen.dishWithQuantity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    canteenId : String,
    viewmodel : MenuScreenVM,
    onBackClick: () -> Unit = {},
    onAddToCart: () -> Unit = {}
) {
    val canteen by viewmodel.canteen.collectAsState()
    val dishes by viewmodel.menu.collectAsState()
    val cartOrder by viewmodel.cartOrder.collectAsState()
    val isLoading by viewmodel.isLoading.collectAsState()
    val context  = LocalContext.current
    LaunchedEffect(Unit) {
        viewmodel.fetchDishes (canteenId = canteenId){
            Toast.makeText(context,it, Toast.LENGTH_SHORT).show()
        }
    }
    var selectedCategory by remember { mutableStateOf("Burger") }

    val categories = listOf("Burger", "Sandwich", "Pizza", "Sandwich")


//    val burgerItems = listOf(
//        FoodItem(
//            id = "1",
//            name = "Burger Ferguson",
//            description = "Spicy Restaurant",
//            price = "$40"
//        ),
//        FoodItem(
//            id = "2",
//            name = "Rockin' Burgers",
//            description = "Cafeschoolcup",
//            price = "$40"
//        )
//    )

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//    )
    Scaffold(
        topBar =
            {
                TopAppBar(
                    title = {
                        Text(
                            text = canteen.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(10.dp)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick,
                            modifier = Modifier.padding(start = 10.dp)) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(40.dp)
                                    .background(color = Color(0xFFECF0F4))
        //                                .shadow(elevation = 2.dp, shape = CircleShape)
                            ){
                                Icon(
                                    imageVector = Icons.Default.ArrowBackIosNew,
                                    contentDescription = "Back",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }

                        }
                    },
                    actions = {
                        IconButton(onClick = { /* More options */ },
                            modifier = Modifier.padding(end=10.dp)) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(40.dp)
                                    .background(color = Color(0xFFECF0F4))
        //                                .shadow(elevation = 2.dp, shape = CircleShape)
                            ){
                                Icon(
                                    imageVector = Icons.Default.MoreHoriz,
                                    contentDescription = "More options",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }

                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            },

        floatingActionButton = {
            if(cartOrder.isNotEmpty()){
                FloatingActionButton(onClick = {viewmodel.saveCartOrderToRepo()
               onAddToCart()}) {
                    Icon(imageVector = Icons.Default.ShoppingCart, "add to cart")
                }
            }
        }

    ) {padding->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Restaurant Header Image
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                        .height(180.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color(0xFF2C3E50))
                ) {
                    // Placeholder for restaurant image - in real app use AsyncImage
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Restaurant Image",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // Restaurant Info
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = canteen.name,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Maurcenas sed elem eget risus vastib blandit sit amet non magna. Integer posiaere erat a ante venenatis dapibus posuere velit attibeal.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Rating and Info Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Rating
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = Color(0xFFFFA726),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "4.7",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium
                                ),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        // Delivery time
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.AccessTime,
                                contentDescription = "Time",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "20 min",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }

            // Category Filter
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(categories) { category ->
                        FilterChip(
                            onClick = { selectedCategory = category },
                            label = {
                                Text(
                                    text = category,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 10.dp)
                                )
                            },
                            selected = selectedCategory == category,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.background,
                                labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            border = BorderStroke(0.5.dp, Color.LightGray),
                            shape = RoundedCornerShape(20.dp),
                        )
                    }
                }
            }

            // Section Title
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$selectedCategory (${dishes.size})",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            if(isLoading){
                item{
                    CircularProgressIndicator(modifier = Modifier
                        .fillMaxWidth()
//                        align(Alignment.CenterHorizontally)
                        .padding(160.dp)
                    )
                }

            }else{
                // Food Items Grid (2 columns)
                items(dishes.chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowItems.forEach { item ->
                            FoodItemCard(
                                item = item,
                                onClickAdd = {viewmodel.onClickAdd(item)},
                                onClickReduce = {viewmodel.onClickMinus(item){
                                    Toast.makeText(context,it, Toast.LENGTH_SHORT).show()
                                }
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Fill remaining space if odd number of items
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

            }


            // Bottom padding for FAB
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun FoodItemCard(
    item: dishWithQuantity,
    onClickAdd : ()-> Unit,
    onClickReduce : ()->Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Food Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                // Placeholder for food image - in real app use AsyncImage
                Text(
                    text = "🍔",
                    fontSize = 32.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Food Name
            Text(
                text = item.dish.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Description
            Text(
                text = item.dish.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Price and Add Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.dish.price,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary
                )

                ScaleAnimationQuantityButton(
                    item,
                    onClickAdd,
                    onClickReduce
                )

//                if(item.quantity>0){
//                    AddMinusButtonAfterClick(onClickAdd,
//                        onClickReduce,
//                        item.quantity,
//                        modifier= Modifier.padding(2.dp))
//
//                }else{
//                    FilledIconButton(
//                        onClick = onClickAdd,
//                        modifier = Modifier.size(32.dp),
//                        colors = IconButtonDefaults.filledIconButtonColors(
//                            containerColor = MaterialTheme.colorScheme.primary
//                        )
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.Add,
//                            contentDescription = "add dish",
//                            tint = MaterialTheme.colorScheme.onPrimary,
//                            modifier = Modifier.size(16.dp)
//                        )
//                    }
//                }


            }
        }
    }
}


@Composable
fun AddMinusButtonAfterClick(onClickAdd: () -> Unit,
                             onClickReduce: () -> Unit,
                             quantity : Int,
                             modifier: Modifier = Modifier) {
    Card (colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(20.dp),
        modifier =modifier){
        Row (modifier= Modifier.padding(horizontal = 4.dp, vertical = 3.dp)){
            IconButton(onClick = onClickReduce ,
                colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(25.dp)
                    .align(alignment = Alignment.CenterVertically)) {
                Icon(imageVector = Icons.Default.HorizontalRule,"subtract button", tint =  Color.White,
                    modifier = Modifier.padding(5.dp))
            }
            VerticalDivider(modifier = Modifier.padding(2.dp))
            Box (modifier = Modifier
                .clip(CircleShape)
                .size(25.dp)
                .background(color = MaterialTheme.colorScheme.background)
                .align(Alignment.CenterVertically)){
                Text(text = quantity.toString(),
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center))
            }
            VerticalDivider(modifier = Modifier.padding(2.dp))
            IconButton(onClick = onClickAdd,
                colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(25.dp)
                    .align(alignment = Alignment.CenterVertically)) {
                Icon(imageVector = Icons.Default.Add,"add button",tint =  Color.White,
                    modifier = Modifier.padding(5.dp))
            }
        }
    }
}


@Composable
fun ScaleAnimationQuantityButton(
    item: dishWithQuantity,
    onClickAdd: () -> Unit,
    onClickReduce: () -> Unit
) {
    AnimatedContent(
        targetState = item.quantity > 0,
        transitionSpec = {
            scaleIn(
                initialScale = 0.8f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeIn(
                animationSpec = tween(300)
            ) togetherWith scaleOut(
                targetScale = 0.8f,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeOut(
                animationSpec = tween(300)
            )
        },
        label = "scale_animation"
    ) { hasQuantity ->
        if (hasQuantity) {
            AddMinusButtonAfterClick(
                onClickAdd = onClickAdd,
                onClickReduce = onClickReduce,
                quantity = item.quantity,
                modifier = Modifier.padding(2.dp)
            )
        } else {
            FilledIconButton(
                onClick = onClickAdd,
                modifier = Modifier.size(32.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add dish",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}


//
//@Preview(showBackground = true)
//@Composable
//fun RestaurantScreenPreview() {
//    SpeedyServeTheme {
//        RestaurantScreen()
//    }
//}
//
//@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun RestaurantScreenDarkPreview() {
//    SpeedyServeTheme(darkTheme = true) {
//        RestaurantScreen()
//    }
//}