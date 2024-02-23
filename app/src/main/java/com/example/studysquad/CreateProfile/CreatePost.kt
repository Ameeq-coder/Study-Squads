package com.example.studysquad.CreateProfile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.studysquad.R
import com.example.studysquad.loginsignup.GradientButton
import com.example.studysquad.loginsignup.OutlinedTextFieldWithIcon
import com.example.studysquad.post.ImagePickerViewModel

@Composable
fun CreatePost() {

    val emailstate = remember { mutableStateOf("") }
    val viewmodel: ImagePickerViewModel = viewModel()

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            viewmodel.selectedImageUri.value = uri
        }
    val customColors = OutlinedTextFieldDefaults.colors(
        unfocusedTextColor = colorResource(id = R.color.black),
        focusedTextColor = colorResource(id = R.color.black),
        cursorColor = colorResource(id = R.color.lightblue), // Change the cursor color
        focusedBorderColor = Color.Gray, // Change the color when the field is focused
        unfocusedBorderColor = colorResource(id = R.color.lightblue), // Change the color when the field is not focused , // Change the text color
    )
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 140.dp),
            text = "Create Your Profile",
            color = colorResource(id = R.color.lightblue),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            )
        val selectedImageUri = viewmodel.selectedImageUri.value
        val painter = if (selectedImageUri != null) {
            rememberImagePainter(selectedImageUri)
        } else {
            painterResource(id = R.drawable.user)
        }
        Image(
            painter = painter,
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 20.dp)
                .size(84.dp)
                .clip(CircleShape)
                .border(2.dp, colorResource(id = R.color.lightblue), CircleShape),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(modifier = Modifier.padding(top = 10.dp),
            onClick = {
                //your onclick code
                galleryLauncher.launch("image/*")
            },
            border = BorderStroke(1.dp, colorResource(id = R.color.lightblue)),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
        ) {
            Text(text = "Select Image", color = Color.Black)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextFieldWithIcon(
            value = emailstate.value,
            onValueChange = { emailstate.value = it },
            label = "Enter Your Username",
            leadingIcon = Icons.Default.AccountCircle,
            customColors = customColors,
        )
        Spacer(modifier = Modifier.height(15.dp))
        GradientButton(text = "Signup", textColor = Color.White, gradient = Brush.verticalGradient(
            colors = listOf(
                colorResource(id = R.color.first_color),
                colorResource(id = R.color.second_color)
            )
        ) ) {

        }
    }

}

@Preview(showBackground = true)
@Composable
fun CreatePostPreview(){
    CreatePost()
}
