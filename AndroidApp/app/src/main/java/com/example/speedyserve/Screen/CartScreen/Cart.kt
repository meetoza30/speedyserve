@file:OptIn(ExperimentalMaterial3Api::class)

package com.speedyserve.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedyserve.ui.theme.SpeedyServeTheme


data class CartItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val size: String,
    var quantity: Int,
    val imageRes: Int? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBackClick: () -> Unit = {},
    onPlaceOrder: () -> Unit = {},
    onEditAddress: () -> Unit = {},
    onViewBreakdown: () -> Unit = {},
    onRemoveItem: (CartItem) -> Unit = {},
    onUpdateQuantity: (CartItem, Int) -> Unit = {
            _, _ ->
    }
) {
    var cartItems by remember {
        mutableStateOf(
            listOf(
                CartItem(
                    id = "1",
                    name = "Pizza Calzone European",
                    description = "European",
                    price = 64.0,
                    size = "14\"",
                    quantity = 2
                ),
                CartItem(
                    id = "2",
                    name = "Pizza Calzone European",
                    description = "European",
                    price = 32.0,
                    size = "14\"",
                    quantity = 1
                )
                ,
                CartItem(
                    id = "2",
                    name = "Pizza Calzone European",
                    description = "European",
                    price = 32.0,
                    size = "14\"",
                    quantity = 1
                ),
                CartItem(
                    id = "2",
                    name = "Pizza Calzone European",
                    description = "European",
                    price = 32.0,
                    size = "14\"",
                    quantity = 1
                ),
                CartItem(
                    id = "2",
                    name = "Pizza Calzone European",
                    description = "European",
                    price = 32.0,
                    size = "14\"",
                    quantity = 1
                ),
                CartItem(
                    id = "2",
                    name = "Pizza Calzone European",
                    description = "European",
                    price = 32.0,
                    size = "14\"",
                    quantity = 1
                )
            )
        )
    }

    val total = cartItems.sumOf { it.price * it.quantity }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Cart",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick,
                    modifier = Modifier.padding(start = 10.dp)) {
                    Box(
                        modifier = Modifier.clip(CircleShape)
                            .size(50.dp)
                            .background(color = Color(0xFFECF0F4))
//                                .shadow(elevation = 2.dp, shape = CircleShape)
                    ){
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                }
            },
            actions = {
                TextButton(onClick = { /* Clear cart or done action */ }) {
                    Text(
                        text = "DONE",
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )

        // Cart Items
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(cartItems) { item ->
                CartItemCard(
                    item = item,
                    onRemove = { onRemoveItem(item) },
                    onQuantityChange = { newQuantity ->
                        onUpdateQuantity(item, newQuantity)
                        cartItems = cartItems.map { cartItem ->
                            if (cartItem.id == item.id) {
                                cartItem.copy(quantity = newQuantity)
                            } else cartItem
                        }
                    }
                )
            }
        }

        // Bottom Section with Address and Total
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // Delivery Address Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onEditAddress() },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "DELIVERY ADDRESS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                letterSpacing = 1.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "2118 Thornridge Cir, Syracuse",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    TextButton(onClick = onEditAddress) {
                        Text(
                            text = "EDIT",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Total Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onViewBreakdown() },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "TOTAL:",
                            style = MaterialTheme.typography.labelSmall.copy(
                                letterSpacing = 1.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${total.toInt()}",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Breakdown",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "View breakdown",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Place Order Button
                Button(
                    onClick = onPlaceOrder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "PLACE ORDER",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItem,
    onRemove: () -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Pizza Image
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🍕",
                    fontSize = 28.sp
                )
            }

            // Item Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$${item.price.toInt()}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Size and Quantity Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.size,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Quantity Controls
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconButton(
                            onClick = {
                                if (item.quantity > 1) {
                                    onQuantityChange(item.quantity - 1)
                                }
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(
                                modifier = Modifier.clip(CircleShape)
                                    .size(50.dp)
                                    .background(color = Color(0xFFECF0F4))
//                                .shadow(elevation = 2.dp, shape = CircleShape)
                            ){
                                Icon(
                                    imageVector = Icons.Outlined.Remove,
                                    contentDescription = "Decrease quantity",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                        .align(Alignment.Center)
                                )
                            }

                        }

                        Text(
                            text = item.quantity.toString(),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color =  MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(24.dp)
                        )

                        IconButton(
                            onClick = { onQuantityChange(item.quantity + 1) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(
                                modifier = Modifier.clip(CircleShape)
                                    .size(50.dp)
                                    .background(color = Color(0xFFECF0F4))
//                                .shadow(elevation = 2.dp, shape = CircleShape)
                            ){
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Increase quantity",
                                    tint =  MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                        .align(Alignment.Center)
                                )
                            }

                        }
                    }
                }
            }

            // Remove Button
            IconButton(onRemove,
                modifier = Modifier.size(32.dp)) {
                Box(
                    modifier = Modifier.clip(CircleShape)
                        .size(40.dp)
                        .background(color = MaterialTheme.colorScheme.onErrorContainer)
//                                .shadow(elevation = 2.dp, shape = CircleShape)
                ){
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Remove item",
                        tint = MaterialTheme.colorScheme.onError,
                        modifier = Modifier.padding(5.dp)
                            .align(Alignment.Center)
                    )
                }
            }

        }
    }
}



@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    SpeedyServeTheme {
        CartScreen()
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CartScreenDarkPreview() {
    SpeedyServeTheme(darkTheme = true) {
        CartScreen()
    }
}