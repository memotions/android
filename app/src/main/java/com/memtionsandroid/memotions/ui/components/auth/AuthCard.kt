package com.memtionsandroid.memotions.ui.components.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.memtionsandroid.memotions.R
import com.memtionsandroid.memotions.ui.theme.Poppins

@Composable
fun AuthCard(
    title: String,
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    inRegister: Boolean = false,
    nameValue: String?,
    emailValue: String,
    passwordValue: String,
    onNameChange: ((String) -> Unit)?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFEF5D9),
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.width(338.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = Poppins,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF292828)
                ),
                modifier = Modifier.width(148.dp)
            )
            Spacer(modifier = Modifier.height(25.dp))

            if (inRegister && nameValue != null && onNameChange != null) {
                OutlinedTextField(
                    value = nameValue,
                    onValueChange = {
                        if (!it.contains("\n"))
                            onNameChange(it)
                    },
                    placeholder = {
                        Text(
                            "Nama",
                            style = TextStyle(fontSize = 12.sp, color = Color(0xFF7B7B7B))
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_person),
                            contentDescription = "Person Icon"
                        )
                    },
                    modifier = Modifier.size(303.dp, 48.dp),
                    shape = RoundedCornerShape(8.dp),
                    textStyle = TextStyle(fontSize = 12.sp, color = Color(0xFF292828)),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFF7D7A78).copy(alpha = 0.5f),
                        focusedContainerColor = Color(0xFFFFFFFF).copy(alpha = 0.3f),
                        unfocusedContainerColor = Color(0xFFFFFFFF).copy(alpha = 0.3f),
                        unfocusedLeadingIconColor = Color(0xFF7B7B7B),
                        focusedLeadingIconColor = Color(0xFF7B7B7B),
                        focusedBorderColor = Color(0xFF292828),
                        cursorColor = Color(0xFF292828)
                    ),
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = emailValue,
                onValueChange = {
                    if (!it.contains("\n"))
                        onEmailChange(it)
                },
                placeholder = {
                    Text(
                        "Email",
                        style = TextStyle(fontSize = 12.sp, color = Color(0xFF7B7B7B))
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = "Email Icon"
                    )
                },
                modifier = Modifier.size(303.dp, 48.dp),
                shape = RoundedCornerShape(8.dp),
                textStyle = TextStyle(fontSize = 12.sp, color = Color(0xFF292828)),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFF7D7A78).copy(alpha = 0.5f),
                    focusedContainerColor = Color(0xFFFFFFFF).copy(alpha = 0.3f),
                    unfocusedContainerColor = Color(0xFFFFFFFF).copy(alpha = 0.3f),
                    unfocusedLeadingIconColor = Color(0xFF7B7B7B),
                    focusedLeadingIconColor = Color(0xFF7B7B7B),
                    focusedBorderColor = Color(0xFF292828),
                    cursorColor = Color(0xFF292828)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )

            Spacer(modifier = Modifier.height(14.dp))
            var passwordVisible by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = passwordValue,
                onValueChange = {
                    if (!it.contains("\n"))
                        onPasswordChange(it)
                },
                placeholder = {
                    Text(
                        "Kata Sandi",
                        style = TextStyle(fontSize = 12.sp, color = Color(0xFF7B7B7B))
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = "Password Icon"
                    )
                },
                modifier = Modifier.size(303.dp, 48.dp),
                shape = RoundedCornerShape(8.dp),
                textStyle = TextStyle(fontSize = 12.sp, color = Color(0xFF292828)),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFF7D7A78).copy(alpha = 0.5f),
                    focusedContainerColor = Color(0xFFFFFFFF).copy(alpha = 0.3f),
                    unfocusedContainerColor = Color(0xFFFFFFFF).copy(alpha = 0.3f),
                    unfocusedLeadingIconColor = Color(0xFF7B7B7B),
                    focusedLeadingIconColor = Color(0xFF7B7B7B),
                    focusedBorderColor = Color(0xFF292828),
                    cursorColor = Color(0xFF292828),
                    unfocusedTrailingIconColor = Color(0xFF7B7B7B),
                    focusedTrailingIconColor = Color(0xFF7B7B7B),
                ),
                trailingIcon = {
                    val icon = if (passwordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.width(303.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = { if (inRegister) onLogin() else onRegister() },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.size(109.dp, 34.dp)
                ) {
                    Text(
                        text = if (inRegister) "Masuk" else "Daftar",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF292828)
                        ),
                    )
                }

                Button(
                    onClick = { if (inRegister) onRegister() else onLogin() },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.size(109.dp, 34.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF292828)
                    )
                ) {
                    Text(
                        text = if (inRegister) "Daftar" else "Masuk",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFFFFF)
                        ),
                    )
                }
            }

        }
    }
}