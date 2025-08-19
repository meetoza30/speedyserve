package com.example.speedyserve.Screen

import com.example.speedyserve.ui.theme.SpeedyServeTheme


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class UserProfile(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val profileImageUrl: String? = null,
    val isVerified: Boolean = false,
    val memberSince: String,
    val totalOrders: Int,
    val favoriteCanteen: String
)

data class ProfileMenuItem(
    val title: String,
    val subtitle: String? = null,
    val icon: ImageVector,
    val showToggle: Boolean = false,
    val isToggled: Boolean = false,
    val showBadge: Boolean = false,
    val badgeText: String? = null,
    val isDestructive: Boolean = false,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    var userProfile by remember { mutableStateOf(getSampleUserProfile()) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showEditProfileDialog by remember { mutableStateOf(false) }

    // Logout Confirmation Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text(
                    text = "Logout",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to logout from SpeedyServe?",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        // Handle logout logic
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Logout")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    // Edit Profile Dialog
    if (showEditProfileDialog) {
        var editName by remember { mutableStateOf(userProfile.name) }
        var editEmail by remember { mutableStateOf(userProfile.email) }
        var editPhone by remember { mutableStateOf(userProfile.phoneNumber) }

        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            title = {
                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = editEmail,
                        onValueChange = { editEmail = it },
                        label = { Text("Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = editPhone,
                        onValueChange = { editPhone = it },
                        label = { Text("Phone Number") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        userProfile = userProfile.copy(
                            name = editName,
                            email = editEmail,
                            phoneNumber = editPhone
                        )
                        showEditProfileDialog = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showEditProfileDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    val profileMenuItems = listOf(
        ProfileMenuItem(
            title = "Edit Profile",
            subtitle = "Update your personal information",
            icon = Icons.Default.Edit,
            onClick = { showEditProfileDialog = true }
        ),
        ProfileMenuItem(
            title = "Notifications",
            subtitle = "Manage your notification preferences",
            icon = Icons.Default.Notifications,
            showToggle = true,
            isToggled = notificationsEnabled,
            onClick = { notificationsEnabled = !notificationsEnabled }
        ),
        ProfileMenuItem(
            title = "Order History",
            subtitle = "View your past orders",
            icon = Icons.Default.History,
            onClick = { /* Navigate to order history */ }
        ),
        ProfileMenuItem(
            title = "Favorite Canteens",
            subtitle = "Manage your favorite places",
            icon = Icons.Default.Favorite,
            onClick = { /* Navigate to favorites */ }
        ),
        ProfileMenuItem(
            title = "Payment Methods",
            subtitle = "Manage your payment options",
            icon = Icons.Default.CreditCard,
            onClick = { /* Navigate to payment methods */ }
        ),
        ProfileMenuItem(
            title = "Help & Support",
            subtitle = "Get help or contact support",
            icon = Icons.Default.Help,
            onClick = { /* Navigate to help */ }
        ),
        ProfileMenuItem(
            title = "Privacy Policy",
            subtitle = "Read our privacy policy",
            icon = Icons.Default.Policy,
            onClick = { /* Navigate to privacy policy */ }
        ),
        ProfileMenuItem(
            title = "Terms of Service",
            subtitle = "Read our terms of service",
            icon = Icons.Default.Description,
            onClick = { /* Navigate to terms */ }
        ),
        ProfileMenuItem(
            title = "Rate App",
            subtitle = "Rate SpeedyServe on Play Store",
            icon = Icons.Default.Star,
            onClick = { /* Open Play Store rating */ }
        ),
        ProfileMenuItem(
            title = "About",
            subtitle = "App version and information",
            icon = Icons.Default.Info,
            showBadge = true,
            badgeText = "v1.0.0",
            onClick = { /* Show about dialog */ }
        ),
        ProfileMenuItem(
            title = "Logout",
            subtitle = "Sign out of your account",
            icon = Icons.Default.Logout,
            isDestructive = true,
            onClick = { showLogoutDialog = true }
        )
    )

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
                    text = "Profile",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Profile Header Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Profile Picture
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                )
                                .border(
                                    width = 4.dp,
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = CircleShape
                                )
                                .clickable { /* Handle profile picture change */ },
                            contentAlignment = Alignment.Center
                        ) {
                            if (userProfile.profileImageUrl != null) {
                                // TODO: Load image from URL
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Profile picture",
                                    modifier = Modifier.size(50.dp),
                                    tint = Color.White
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Profile picture",
                                    modifier = Modifier.size(50.dp),
                                    tint = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Name and Verification
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = userProfile.name,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )

                            if (userProfile.isVerified) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "Verified",
                                    modifier = Modifier.size(20.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Email
                        Text(
                            text = userProfile.email,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Phone Number
                        Text(
                            text = userProfile.phoneNumber,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Stats Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ProfileStatItem(
                                title = "Orders",
                                value = userProfile.totalOrders.toString()
                            )

                            ProfileStatItem(
                                title = "Member Since",
                                value = userProfile.memberSince
                            )

                            ProfileStatItem(
                                title = "Favorite",
                                value = userProfile.favoriteCanteen
                            )
                        }
                    }
                }
            }

            // Menu Items
            items(profileMenuItems.size) { index ->
                val item = profileMenuItems[index]

                // Add section divider before logout
                if (item.isDestructive && index > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                ProfileMenuItemCard(
                    menuItem = item,
                    onToggleChange = { isToggled ->
                        if (item.title == "Notifications") {
                            notificationsEnabled = isToggled
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileStatItem(
    title: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ProfileMenuItemCard(
    menuItem: ProfileMenuItem,
    onToggleChange: (Boolean) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (!menuItem.showToggle) {
                    menuItem.onClick()
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = if (menuItem.isDestructive) {
                            MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
                        } else {
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = menuItem.icon,
                    contentDescription = menuItem.title,
                    modifier = Modifier.size(20.dp),
                    tint = if (menuItem.isDestructive) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = menuItem.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = if (menuItem.isDestructive) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )

                menuItem.subtitle?.let { subtitle ->
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Toggle Switch
            if (menuItem.showToggle) {
                Switch(
                    checked = menuItem.isToggled,
                    onCheckedChange = { isChecked ->
                        onToggleChange(isChecked)
                        menuItem.onClick()
                    }
                )
            }

            // Badge
            if (menuItem.showBadge && menuItem.badgeText != null) {
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = menuItem.badgeText,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Arrow Icon (for non-toggle items)
            if (!menuItem.showToggle && !menuItem.showBadge) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Navigate",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

fun getSampleUserProfile(): UserProfile {
    return UserProfile(
        name = "Rajesh Kumar",
        email = "rajesh.kumar@example.com",
        phoneNumber = "+91 98765 43210",
        profileImageUrl = null,
        isVerified = true,
        memberSince = "2023",
        totalOrders = 47,
        favoriteCanteen = "Central Canteen"
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    SpeedyServeTheme(){
         ProfileScreen()
     }
}