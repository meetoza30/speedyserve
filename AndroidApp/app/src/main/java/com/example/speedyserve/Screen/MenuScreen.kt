@file:OptIn(ExperimentalMaterial3Api::class)

package com.speedyserve.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Custom Color Scheme
private val FoodDeliveryLightColors = lightColorScheme(
    primary = Color(0xFFFF6B35), // Vibrant Orange
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFE8E0), // Light Orange
    onPrimaryContainer = Color(0xFF8B2500), // Dark Orange

    secondary = Color(0xFFFFA726), // Amber
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFFFF3C4), // Light Amber
    onSecondaryContainer = Color(0xFF663C00), // Dark Amber

    tertiary = Color(0xFF4CAF50), // Green for success states
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFE8F5E8),
    onTertiaryContainer = Color(0xFF1B5E20),

    error = Color(0xFFE53935),
    onError = Color.White,
    errorContainer = Color(0xFFFFEBEE),
    onErrorContainer = Color(0xFFB71C1C),

    background = Color(0xFFFFFBFF), // Slightly warm white
    onBackground = Color(0xFF1F1B16), // Dark brown

    surface = Color.White,
    onSurface = Color(0xFF1F1B16),
    surfaceVariant = Color(0xFFF5F1EB), // Warm light gray
    onSurfaceVariant = Color(0xFF524639), // Warm dark gray

    outline = Color(0xFF857468), // Warm gray outline
    outlineVariant = Color(0xFFD7C2B8), // Light warm outline

    inverseSurface = Color(0xFF352F2A), // Dark warm surface
    inverseOnSurface = Color(0xFFF9EFE6), // Light warm text
    inversePrimary = Color(0xFFFFB599), // Light orange for dark theme
)

private val FoodDeliveryDarkColors = darkColorScheme(
    primary = Color(0xFFFFB599), // Light Orange for dark
    onPrimary = Color(0xFF552100), // Dark Orange
    primaryContainer = Color(0xFF7A3200), // Medium Orange
    onPrimaryContainer = Color(0xFFFFE8E0),

    secondary = Color(0xFFFFD54F), // Light Amber for dark
    onSecondary = Color(0xFF3D2F00), // Dark Amber
    secondaryContainer = Color(0xFF5A4600),
    onSecondaryContainer = Color(0xFFFFF3C4),

    tertiary = Color(0xFF81C784), // Light Green
    onTertiary = Color(0xFF003D06),
    tertiaryContainer = Color(0xFF1B5E20),
    onTertiaryContainer = Color(0xFFE8F5E8),

    error = Color(0xFFEF5350),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFEBEE),

    background = Color(0xFF1F1B16), // Dark warm background
    onBackground = Color(0xFFF9EFE6), // Light warm text

    surface = Color(0xFF1F1B16),
    onSurface = Color(0xFFF9EFE6),
    surfaceVariant = Color(0xFF524639), // Dark warm gray
    onSurfaceVariant = Color(0xFFD7C2B8), // Light warm gray

    outline = Color(0xFF9F8D80), // Medium warm gray
    outlineVariant = Color(0xFF524639),

    inverseSurface = Color(0xFFF9EFE6),
    inverseOnSurface = Color(0xFF352F2A),
    inversePrimary = Color(0xFFFF6B35),
)

data class FoodItem(
    val id: String,
    val name: String,
    val description: String,
    val price: String,
    val imageRes: Int? = null // In real app, use URL
)

data class Category(
    val name: String,
    val isSelected: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreen(
    onBackClick: () -> Unit = {},
    onItemClick: (FoodItem) -> Unit = {},
    onAddToCart: (FoodItem) -> Unit = {}
) {
    var selectedCategory by remember { mutableStateOf("Burger") }

    val categories = listOf("Burger", "Sandwich", "Pizza", "Sandwich")

    val burgerItems = listOf(
        FoodItem(
            id = "1",
            name = "Burger Ferguson",
            description = "Spicy Restaurant",
            price = "$40"
        ),
        FoodItem(
            id = "2",
            name = "Rockin' Burgers",
            description = "Cafeschoolcup",
            price = "$40"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Restaurant View",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(10.dp)
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick,
                    modifier = Modifier.padding(start = 10.dp)) {
                    Box(
                        modifier = Modifier.clip(CircleShape)
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
                        modifier = Modifier.clip(CircleShape)
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

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
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
                        text = "Spicy Restaurant",
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

                        // Free delivery
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Money,
                                contentDescription = "Free",
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Free",
                                style = MaterialTheme.typography.bodyMedium,
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
                        text = "$selectedCategory (${burgerItems.size})",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            // Food Items Grid (2 columns)
            items(burgerItems.chunked(2)) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowItems.forEach { item ->
                        FoodItemCard(
                            item = item,
                            onClick = { onItemClick(item) },
                            onAddToCart = { onAddToCart(item) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Fill remaining space if odd number of items
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
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
    item: FoodItem,
    onClick: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
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
                text = item.name,
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
                text = item.description,
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
                    text = item.price,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary
                )

                FilledIconButton(
                    onClick = onAddToCart,
                    modifier = Modifier.size(32.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add to cart",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SpeedyServeTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) FoodDeliveryDarkColors else FoodDeliveryLightColors

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun RestaurantScreenPreview() {
    SpeedyServeTheme {
        RestaurantScreen()
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RestaurantScreenDarkPreview() {
    SpeedyServeTheme(darkTheme = true) {
        RestaurantScreen()
    }
}