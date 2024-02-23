package com.example.studysquad.loginsignup

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studysquad.Navigations.Route
import com.example.studysquad.R
import com.example.studysquad.di.NavViewModel
import com.example.studysquad.ui.theme.StudySquadTheme

@Composable
fun Signup(authViewModel: AuthViewModel,navController: NavController,navViewModel: NavViewModel) {
    val emailstate = remember { mutableStateOf("") }
    val passwordstate = remember { mutableStateOf("") }
    val confirmstate = remember { mutableStateOf("") }
    val navigateToSignUp = navViewModel.navigateToSignUp.observeAsState()
    val navigatetopost=navViewModel.navigateToCreateProfile.observeAsState()
    val customColors = OutlinedTextFieldDefaults.colors(
        unfocusedTextColor = colorResource(id = R.color.black),
        focusedTextColor = colorResource(id = R.color.black),
        cursorColor = colorResource(id = R.color.lightblue), // Change the cursor color
        focusedBorderColor = Color.Gray, // Change the color when the field is focused
        unfocusedBorderColor = colorResource(id = R.color.lightblue), // Change the color when the field is not focused , // Change the text color
    )
    val context = LocalContext.current
LaunchedEffect(navigatetopost.value){
    if(navigatetopost.value==true){
        navController.navigate(Route.CreatePost.route){

        }
        navViewModel.onNavigateToCreateProfileComplete()
    }
}

    LaunchedEffect(navigateToSignUp.value) {
        if (navigateToSignUp.value == true) {
            // Navigate to the signup screen using the NavController
            navController.navigate(Route.LoginScreen.route) {
                // Optionally, you can specify additional navigation options here
            }
            navViewModel.onNavigateToSignUpComplete()
        }
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
    ) {
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
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextFieldWithIcon(
            value = confirmstate.value,
            onValueChange = { confirmstate.value = it },
            label = "Confirm Your Password",
            leadingIcon = Icons.Default.Lock,
            customColors = customColors,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(14.dp))
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            GradientButton(
                text = "Create Account", textColor = Color.White,
                onClick = {
                    val email = emailstate.value
                    val password = passwordstate.value
                    val conpass = confirmstate.value
                    if (email.isEmpty() || password.isEmpty() || conpass.isEmpty()) {
                        Toast.makeText(context, "Enter Details", Toast.LENGTH_SHORT).show()
                    } else {
                        authViewModel.signup(
                            emailstate.value,
                            passwordstate.value,
                            confirmstate.value
                        ) { isSuccess ->
                            if (isSuccess) {
                                Toast.makeText(
                                    context,
                                    "Account Created Sucess",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navViewModel.navigateToCreateProfile()

                            } else {
                                Toast.makeText(
                                    context,
                                    "Account Created Failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                },
                gradient = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.first_color),
                        colorResource(id = R.color.second_color)
                    )

                )
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an Account?", modifier = Modifier,
                fontFamily = FontFamily.Default
            )

            Text(
                text = "Login", modifier = Modifier.clickable {
                    navViewModel.navigateToSignUp()
                },
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold, // Set the text to be bold
                color = colorResource(id = R.color.lightblue)
            )

        }


    }

}

@Composable
 fun OutlinedTextFieldWithIcon(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    customColors: TextFieldColors,
    visualTransformation: VisualTransformation? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
        label = { Text(text = label, color = Color.Black) },
        leadingIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = colorResource(id = R.color.lightblue)
                )
            }
        },
        visualTransformation = visualTransformation ?: VisualTransformation.None,
        colors = customColors
    )
}


@Composable
fun Logo() {
    Box(
        modifier = Modifier
            .width(400.dp)
            .height(350.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.loginsignup),
            contentDescription = null
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SignupPreview() {
    StudySquadTheme {

    }

}