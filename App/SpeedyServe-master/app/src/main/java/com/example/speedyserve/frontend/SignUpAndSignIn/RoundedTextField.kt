package com.example.speedyserve.frontend.SignUpAndSignIn


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.Alignment

@Composable
fun RoundedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolderText: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    isPasswordTextField: Boolean = false,
    isPasswordVisible: Boolean = false,
) {
    var internalPasswordVisible by remember { mutableStateOf(isPasswordVisible) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Color(0xFFE3F2FD), shape = RoundedCornerShape(12.dp)),
        singleLine = singleLine,
        enabled = enabled,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Placeholder text logic
                    if (value.isEmpty()) {
                        Text(
                            text = placeHolderText,
                            color = Color(0xFF607D8B),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                    // Editable text field
                    Box(
                        modifier = Modifier.weight(1f) // Ensure text takes remaining space
                    ) {
                        innerTextField()
                    }

                    // Trailing icon
                    if (isPasswordTextField) {
                        Icon(
                            imageVector = if (internalPasswordVisible) {
                                Icons.Default.Visibility
                            } else {
                                androidx.compose.material.icons.Icons.Default.VisibilityOff
                            },
                            contentDescription = "Toggle Password Visibility",
                            tint = Color.Gray,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable {
                                    internalPasswordVisible = !internalPasswordVisible
                                }
                        )
                    }
                }
            }
        }
    )
}