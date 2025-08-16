package com.example.speedyserve.Screen

import com.example.speedyserve.ui.theme.SpeedyServeTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*


data class PastOrder(
    val orderId: String,
    val canteenName: String,
    val orderDate: Date,
    val totalAmount: Int,
    val itemCount: Int,
    val topItems: List<String>, // First 2-3 items for preview
    val status: String = "Completed"
)

enum class FilterType {
    ALL, TODAY, WEEK, MONTH
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PastOrdersScreen() {
    val orders = remember { getSamplePastOrders() }
    var selectedFilter by remember { mutableStateOf(FilterType.ALL) }
    var selectedCanteen by remember { mutableStateOf("All Canteens") }
    var showFilterSheet by remember { mutableStateOf(false) }
    var showReorderDialog by remember { mutableStateOf<PastOrder?>(null) }

    val canteens = remember {
        listOf("All Canteens") + orders.map { it.canteenName }.distinct()
    }

    // Filter orders based on selected filters
    val filteredOrders = remember(orders, selectedFilter, selectedCanteen) {
        var filtered = orders

        // Filter by date
        val calendar = Calendar.getInstance()
        filtered = when (selectedFilter) {
            FilterType.TODAY -> {
                val today = calendar.time
                filtered.filter { order ->
                    val orderCal = Calendar.getInstance().apply { time = order.orderDate }
                    val todayCal = Calendar.getInstance().apply { time = today }
                    orderCal.get(Calendar.YEAR) == todayCal.get(Calendar.YEAR) &&
                            orderCal.get(Calendar.DAY_OF_YEAR) == todayCal.get(Calendar.DAY_OF_YEAR)
                }
            }
            FilterType.WEEK -> {
                calendar.add(Calendar.DAY_OF_YEAR, -7)
                val weekAgo = calendar.time
                filtered.filter { it.orderDate.after(weekAgo) }
            }
            FilterType.MONTH -> {
                calendar.add(Calendar.DAY_OF_YEAR, -30)
                val monthAgo = calendar.time
                filtered.filter { it.orderDate.after(monthAgo) }
            }
            FilterType.ALL -> filtered
        }

        // Filter by canteen
        if (selectedCanteen != "All Canteens") {
            filtered = filtered.filter { it.canteenName == selectedCanteen }
        }

        filtered.sortedByDescending { it.orderDate }
    }

    // Filter Bottom Sheet
    if (showFilterSheet) {
        ModalBottomSheet(
            onDismissRequest = { showFilterSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Filter Orders",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Date Filter
                Text(
                    text = "Filter by Date",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                FilterType.values().forEach { filter ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedFilter = filter }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedFilter == filter,
                            onClick = { selectedFilter = filter }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = when (filter) {
                                FilterType.ALL -> "All Orders"
                                FilterType.TODAY -> "Today"
                                FilterType.WEEK -> "Last 7 Days"
                                FilterType.MONTH -> "Last 30 Days"
                            },
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Canteen Filter
                Text(
                    text = "Filter by Canteen",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                canteens.forEach { canteen ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedCanteen = canteen }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedCanteen == canteen,
                            onClick = { selectedCanteen = canteen }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = canteen,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { showFilterSheet = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Apply Filters")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Reorder Confirmation Dialog
    showReorderDialog?.let { order ->
        AlertDialog(
            onDismissRequest = { showReorderDialog = null },
            title = {
                Text(
                    text = "Reorder Items",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "Would you like to reorder the following items from ${order.canteenName}?",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    order.topItems.forEach { item ->
                        Text(
                            text = "• $item",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (order.itemCount > order.topItems.size) {
                        Text(
                            text = "• and ${order.itemCount - order.topItems.size} more items",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showReorderDialog = null
                        // Navigate to cart with reordered items
                    }
                ) {
                    Text("Add to Cart")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showReorderDialog = null }
                ) {
                    Text("Cancel")
                }
            }
        )
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
                    text = "Past Orders",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                // Filter Button
                IconButton(
                    onClick = { showFilterSheet = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filter orders"
                    )
                }
            }
        }

        // Active Filters Display
        if (selectedFilter != FilterType.ALL || selectedCanteen != "All Canteens") {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (selectedFilter != FilterType.ALL) {
                    item {
                        FilterChip(
                            selected = true,
                            onClick = { selectedFilter = FilterType.ALL },
                            label = {
                                Text(
                                    text = when (selectedFilter) {
                                        FilterType.TODAY -> "Today"
                                        FilterType.WEEK -> "Last 7 Days"
                                        FilterType.MONTH -> "Last 30 Days"
                                        else -> "All"
                                    }
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Remove filter",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        )
                    }
                }

                if (selectedCanteen != "All Canteens") {
                    item {
                        FilterChip(
                            selected = true,
                            onClick = { selectedCanteen = "All Canteens" },
                            label = {
                                Text(
                                    text = selectedCanteen,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Remove filter",
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        )
                    }
                }
            }
        }

        // Orders List
        if (filteredOrders.isEmpty()) {
            // Empty State
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Receipt,
                        contentDescription = "No orders",
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No orders found",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Try adjusting your filters or place your first order!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Orders Count
                item {
                    Text(
                        text = "${filteredOrders.size} orders found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(filteredOrders) { order ->
                    PastOrderCard(
                        order = order,
                        onReorder = { showReorderDialog = order },
                        onOrderClick = { /* Navigate to order details */ }
                    )
                }
            }
        }
    }
}

@Composable
fun PastOrderCard(
    order: PastOrder,
    onReorder: () -> Unit,
    onOrderClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOrderClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Order Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = order.canteenName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Order #${order.orderId}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = dateFormat.format(order.orderDate),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "₹${order.totalAmount}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = order.status,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Items Preview
            Text(
                text = "Items: ${order.topItems.joinToString(", ")}${if (order.itemCount > order.topItems.size) " and ${order.itemCount - order.topItems.size} more" else ""}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = onOrderClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Receipt,
                        contentDescription = "View details",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("View Details")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onReorder,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reorder",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Reorder")
                }
            }
        }
    }
}

fun getSamplePastOrders(): List<PastOrder> {
    val calendar = Calendar.getInstance()

    return listOf(
        PastOrder(
            orderId = "SP123456",
            canteenName = "Central Canteen",
            orderDate = calendar.time,
            totalAmount = 540,
            itemCount = 4,
            topItems = listOf("Butter Chicken", "Masala Dosa", "Mango Lassi")
        ),
        PastOrder(
            orderId = "SP123455",
            canteenName = "Food Court",
            orderDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }.time,
            totalAmount = 320,
            itemCount = 3,
            topItems = listOf("Veg Biryani", "Raita", "Gulab Jamun")
        ),
        PastOrder(
            orderId = "SP123454",
            canteenName = "Central Canteen",
            orderDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -2) }.time,
            totalAmount = 180,
            itemCount = 2,
            topItems = listOf("Samosa", "Chai")
        ),
        PastOrder(
            orderId = "SP123453",
            canteenName = "North Campus Canteen",
            orderDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -5) }.time,
            totalAmount = 420,
            itemCount = 5,
            topItems = listOf("Paneer Tikka", "Naan", "Dal Makhani")
        ),
        PastOrder(
            orderId = "SP123452",
            canteenName = "Food Court",
            orderDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -8) }.time,
            totalAmount = 280,
            itemCount = 3,
            topItems = listOf("Chicken Curry", "Rice", "Pickle")
        ),
        PastOrder(
            orderId = "SP123451",
            canteenName = "Central Canteen",
            orderDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -15) }.time,
            totalAmount = 150,
            itemCount = 2,
            topItems = listOf("Dosa", "Coffee")
        ),
        PastOrder(
            orderId = "SP123450",
            canteenName = "North Campus Canteen",
            orderDate = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -20) }.time,
            totalAmount = 380,
            itemCount = 4,
            topItems = listOf("Mutton Curry", "Roti", "Salad")
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PastOrdersScreenPreview() {
    SpeedyServeTheme{
         PastOrdersScreen()
     }
}