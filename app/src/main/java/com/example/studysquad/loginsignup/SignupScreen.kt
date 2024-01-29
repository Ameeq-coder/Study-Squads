package com.example.studysquad.loginsignup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studysquad.R
import com.example.studysquad.ui.theme.StudySquadTheme

@Composable
fun Signup() {
    val emailstate = remember { mutableStateOf("") }
    val passwordstate = remember { mutableStateOf("") }
    val confirmstate = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
    ) {
        val customColors = OutlinedTextFieldDefaults.colors(
            cursorColor = colorResource(id = R.color.lightblue), // Change the cursor color
            focusedBorderColor = Color.Gray, // Change the color when the field is focused
            unfocusedBorderColor = colorResource(id = R.color.lightblue), // Change the color when the field is not focused , // Change the text color
        )
        Box(modifier = Modifier, contentAlignment = Alignment.TopCenter) {
            Image(painter = painterResource(id = R.drawable.loginsignup), contentDescription = null)
        }
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Study Squads",
            color = colorResource(id = R.color.lightblue),
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            fontFamily = FontFamily.Cursive
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp)
            .align(Alignment.CenterHorizontally),
            value = emailstate.value,
            colors = customColors,
            onValueChange = { emailstate.value = it },
            label = { Text(text = "Enter Email") },
            leadingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Icon",
                        tint = colorResource(id = R.color.lightblue)
                    )
                }
            })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp)
            .align(Alignment.CenterHorizontally),
            value = emailstate.value,
            colors = customColors,
            onValueChange = { passwordstate.value = it },
            label = { Text(text = "Enter Your Password") },
            leadingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Email Icon",
                        tint = colorResource(id = R.color.lightblue)
                    )
                }
            })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp)
            .align(Alignment.CenterHorizontally),
            value = emailstate.value,
            colors = customColors,
            onValueChange = { confirmstate.value = it },
            label = { Text(text = "Confirm Your Password") },
            leadingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Email Icon",
                        tint = colorResource(id = R.color.lightblue)
                    )
                }
            })
        Spacer(modifier = Modifier.height(14.dp))
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            GradientButton(
                text = "Login", textColor = Color.White, gradient = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.first_color),
                        colorResource(id = R.color.second_color)
                    )
                )
            ) {

            }
        }
        Spacer(modifier = Modifier.height(15.dp))
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
fun SignupPreview() {
    StudySquadTheme {
        Signup() // Pass the required parameters to Signup function
    }

}