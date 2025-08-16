package com.example.speedyserve.Screen


import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedyserve.ui.theme.SpeedyServeTheme
import kotlinx.coroutines.delay

// SpeedyServe Color Scheme (from original file)

data class OrderItem(
    val id: Int,
    val name: String,
    val quantity: Int,
    val price: Int,
    val isVeg: Boolean,
    val category: String
)

enum class OrderStatus {
    ORDERED, PREPARING, READY
}

data class OrderDetails(
    val orderId: String,
    val canteenName: String,
    val items: List<OrderItem>,
    val totalAmount: Int,
    val estimatedReadyTime: String,
    val currentStatus: OrderStatus,
    val placedTime: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTrackingScreen() {
    val orderDetails = getSampleOrderDetails()
    var currentStatus by remember { mutableStateOf(orderDetails.currentStatus) }
    var timeRemaining by remember { mutableStateOf("12 min") }

    // Simulate order progress
    LaunchedEffect(Unit) {
        delay(5000) // After 5 seconds, move to preparing
        if (currentStatus == OrderStatus.ORDERED) {
            currentStatus = OrderStatus.PREPARING
        }
        delay(8000) // After 8 more seconds, move to ready
        if (currentStatus == OrderStatus.PREPARING) {
            currentStatus = OrderStatus.READY
            timeRemaining = "Ready!"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { /* Navigate back */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Track Your Order",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Order Header Card
            item {
                OrderHeaderCard(
                    orderId = orderDetails.orderId,
                    canteenName = orderDetails.canteenName,
                    placedTime = orderDetails.placedTime,
                    totalAmount = orderDetails.totalAmount
                )
            }

            // Live Status Tracking
            item {
                OrderStatusTracker(
                    currentStatus = currentStatus,
                    timeRemaining = timeRemaining
                )
            }

            // Estimated Time Card
            item {
                EstimatedTimeCard(
                    timeRemaining = timeRemaining,
                    currentStatus = currentStatus
                )
            }

            // Order Items
            item {
                Text(
                    text = "Order Items",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(orderDetails.items) { item ->
                OrderItemCard(item = item)
            }

            // Help Section
            item {
                Spacer(modifier = Modifier.height(16.dp))
                HelpSection()
            }
        }
    }
}

@Composable
fun OrderHeaderCard(
    orderId: String,
    canteenName: String,
    placedTime: String,
    totalAmount: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Order ID: $orderId",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = canteenName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "₹$totalAmount",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = placedTime,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun OrderStatusTracker(
    currentStatus: OrderStatus,
    timeRemaining: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Order Status",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ordered Status
                StatusStep(
                    title = "Ordered",
                    icon = Icons.Default.CheckCircle,
                    isActive = true,
                    isCompleted = currentStatus != OrderStatus.ORDERED
                )

                // Connecting Line 1
                StatusConnector(
                    isActive = currentStatus != OrderStatus.ORDERED
                )

                // Preparing Status
                StatusStep(
                    title = "Preparing",
                    icon = Icons.Default.Restaurant,
                    isActive = currentStatus == OrderStatus.PREPARING || currentStatus == OrderStatus.READY,
                    isCompleted = currentStatus == OrderStatus.READY
                )

                // Connecting Line 2
                StatusConnector(
                    isActive = currentStatus == OrderStatus.READY
                )

                // Ready Status
                StatusStep(
                    title = "Ready",
                    icon = Icons.Default.TaskAlt,
                    isActive = currentStatus == OrderStatus.READY,
                    isCompleted = currentStatus == OrderStatus.READY
                )
            }
        }
    }
}

@Composable
fun StatusStep(
    title: String,
    icon: ImageVector,
    isActive: Boolean,
    isCompleted: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Status Icon
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = when {
                        isCompleted -> MaterialTheme.colorScheme.secondary
                        isActive -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    },
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isActive || isCompleted) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Status Title
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isActive || isCompleted) FontWeight.Bold else FontWeight.Normal,
            color = if (isActive || isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun StatusConnector(isActive: Boolean) {
    Box(
        modifier = Modifier
            .height(2.dp)
            .width(40.dp)
            .background(
                color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(1.dp)
            )
    )
}

@Composable
fun EstimatedTimeCard(
    timeRemaining: String,
    currentStatus: OrderStatus
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (currentStatus == OrderStatus.READY)
                MaterialTheme.colorScheme.secondary
            else MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (currentStatus == OrderStatus.READY)
                    Icons.Default.TaskAlt
                else Icons.Default.Schedule,
                contentDescription = "Time",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = if (currentStatus == OrderStatus.READY)
                        "Your order is ready!"
                    else "Estimated ready time",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Text(
                    text = timeRemaining,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun OrderItemCard(item: OrderItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Food Image Placeholder
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (item.category) {
                        "Veg" -> "🥗"
                        "Non-Veg" -> "🍖"
                        "Drinks" -> "🥤"
                        else -> "🍽️"
                    },
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Item Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(
                                if (item.isVeg) MaterialTheme.colorScheme.secondary
                                else Color(0xFFE57373),
                                CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Qty: ${item.quantity}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "₹${item.price * item.quantity}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun HelpSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Need Help?",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = { /* Call canteen */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Call",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Call Canteen")
                }

                TextButton(
                    onClick = { /* Open support */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Support,
                        contentDescription = "Support",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Support")
                }
            }
        }
    }
}

fun getSampleOrderDetails(): OrderDetails {
    return OrderDetails(
        orderId = "SP123456",
        canteenName = "Central Canteen",
        items = listOf(
            OrderItem(1, "Butter Chicken", 2, 180, false, "Non-Veg"),
            OrderItem(2, "Masala Dosa", 1, 80, true, "Veg"),
            OrderItem(3, "Mango Lassi", 2, 60, true, "Drinks"),
            OrderItem(4, "Paneer Tikka", 1, 160, true, "Veg")
        ),
        totalAmount = 540,
        estimatedReadyTime = "15-20 min",
        currentStatus = OrderStatus.ORDERED,
        placedTime = "10:30 AM"
    )
}

@Preview(showBackground = true)
@Composable
fun OrderTrackingScreenPreview() {
    SpeedyServeTheme{
         OrderTrackingScreen()
     }
}