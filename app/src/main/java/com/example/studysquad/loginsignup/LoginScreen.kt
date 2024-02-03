package com.example.studysquad.loginsignup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studysquad.R


@Composable
fun Login(authViewModel: AuthViewModel,navController: NavController){
    val emailstate = remember { mutableStateOf("") }
    val passwordstate = remember { mutableStateOf("") }
    val customColors = OutlinedTextFieldDefaults.colors(
        unfocusedTextColor = colorResource(id = R.color.black),
        focusedTextColor = colorResource(id = R.color.black),
        cursorColor = colorResource(id = R.color.lightblue), // Change the cursor color
        focusedBorderColor = Color.Gray, // Change the color when the field is focused
        unfocusedBorderColor = colorResource(id = R.color.lightblue), // Change the color when the field is not focused , // Change the text color
    )
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),) {
        Logo()
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Study Squads",
            color = colorResource(id = R.color.lightblue),
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            fontFamily = FontFamily.Cursive
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextFieldWithIcon(
            value = emailstate.value,
            onValueChange = { emailstate.value = it },
            label = "Enter Your Email",
            leadingIcon = Icons.Default.Email,
            customColors = customColors,
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextFieldWithIcon(
            value = passwordstate.value,
            onValueChange = { passwordstate.value = it },
            label = "Enter Your Password",
            leadingIcon = Icons.Default.Lock,
            customColors = customColors,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            GradientButton(
                text = "Login", textColor = Color.White,
                onClick = { authViewModel.login(emailstate.value,passwordstate.value)  },
                gradient = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.first_color),
                        colorResource(id = R.color.second_color)
                    )

                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't Have An Account ?", modifier = Modifier,
                fontFamily = FontFamily.Default
            )

            Text(
                text = "Create One", modifier = Modifier,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold, // Set the text to be bold
                color = colorResource(id = R.color.lightblue)
            )

        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview(){
}
