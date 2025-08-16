@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.speedyserve.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

data class Category(
    val name: String,
    val icon: ImageVector,
    var isSelected: Boolean = false
)

data class Restaurant(
    val name: String,
    val cuisine: String,
    val rating: Float,
    val deliveryTime: String,
    val deliveryFee: String,
    val imageRes: Int? = null
)

@Preview(showSystemUi = true)
@Composable
fun FoodDeliveryApp() {
    val isSystemInDarkTheme = androidx.compose.foundation.isSystemInDarkTheme()
    val colorScheme = if (isSystemInDarkTheme) {
        FoodDeliveryDarkColors
    } else {
        FoodDeliveryLightColors
    }

    val categories = listOf(
        Category("All", Icons.Default.LocalFireDepartment, true),
        Category("Hot Dog", Icons.Default.Fastfood),
        Category("Burger", Icons.Default.LunchDining),
        Category("Pizza", Icons.Default.LocalPizza)
    )

    val restaurants = listOf(
        Restaurant(
            name = "Rose Garden Restaurant",
            cuisine = "Burger • Chicken • Riche • Wings",
            rating = 4.7f,
            deliveryTime = "20 min",
            deliveryFee = "Free"
        ),
        Restaurant(
            name = "Green Bowl Kitchen",
            cuisine = "Healthy • Salads • Bowls • Vegan",
            rating = 4.5f,
            deliveryTime = "25 min",
            deliveryFee = "₹30"
        )
    )

    MaterialTheme(colorScheme = colorScheme) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            TopAppBar(
                title = {
                    Column (modifier = Modifier.padding(start = 5.dp)){
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "DELIVER TO",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 10.sp
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Halol Lab office",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.W400
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Dropdown",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { },
                        modifier = Modifier.padding(start = 10.dp)) {
                        Box(
                            modifier = Modifier.clip(CircleShape)
                                .size(50.dp)
                                .background(color = Color(0xFFECF0F4))
//                                .shadow(elevation = 2.dp, shape = CircleShape)
                                ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                modifier = Modifier.align(Alignment.Center)
                                    .padding(5.dp)
                            )
                        }

                    }
                },
                actions = {
                    Box(
                        modifier = Modifier.padding(end = 10.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "H",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )

            // Greeting
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(
                    text = "Hey Halal, ",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Text(
                    text = "Good Afternoon!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }


            // Search Bar
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = "",
                onValueChange = { },
                placeholder = {
                    Text(
                        text = "Search dishes, restaurants",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
            )

            // Categories Section
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "All Categories",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { }) {
                    Text(
                        text = "See All",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "See All",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(categories) { category ->
                    CategoryChip(category = category){
                        category.isSelected = true
                    }
                }
            }

            // Open Restaurants Section
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Open Restaurants",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { }) {
                    Text(
                        text = "See All",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "See All",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            restaurants.forEach { restaurant ->
                RestaurantCard(restaurant = restaurant)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun CategoryChip(category: Category,
                 onclick : ()-> Unit) {
    val backgroundColor = if (category.isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val contentColor = if (category.isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .clickable(true, onClick = onclick),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = category.name,
                tint = contentColor,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = category.name,
                color = contentColor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun RestaurantCard(restaurant: Restaurant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Restaurant Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Restaurant,
                    contentDescription = "Restaurant Image",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(48.dp)
                )
            }

            // Restaurant Details
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = restaurant.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = restaurant.cuisine,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
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
                            tint = Color(0xFFFF9800),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = restaurant.rating.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Delivery Fee
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeliveryDining,
                            contentDescription = "Delivery",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = restaurant.deliveryFee,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = if (restaurant.deliveryFee == "Free") {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }

                    // Delivery Time
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = "Time",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = restaurant.deliveryTime,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}