package com.example.studysquad.post

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.studysquad.R
import com.example.studysquad.loginsignup.GradientButton
import dagger.hilt.android.AndroidEntryPoint


@Composable
fun PostScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val context = LocalContext.current
        val viewmodel: ImagePickerViewModel = viewModel()

        val galleryLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                viewmodel.selectedImageUri.value = uri
            }
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                modifier = Modifier.padding(top = 13.dp, start = 10.dp),
                imageVector = Icons.Filled.Close,
                tint = colorResource(id = R.color.lightblue),
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(Alignment.Center)
                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                text = "Create Post ",
                color = colorResource(id = R.color.lightblue),
                textAlign = TextAlign.Center
            )
            val customIcon =
                painterResource(id = R.drawable.camera) // Replace with your PNG file resource ID
            Icon(
                painter = customIcon,
                contentDescription = "Gallery Icon",
                tint = Color.Unspecified, // Optional tint color
                modifier = Modifier
                    .clickable {
                        galleryLauncher.launch("image/*")
                    }
                    .align(Alignment.CenterVertically)
                    .padding(end = 20.dp)
                    .size(35.dp)
            )
            PostButton(
                text = "Post", textColor = Color.White, gradient = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.first_color),
                        colorResource(id = R.color.second_color)
                    )
                )
            ) {

            }

        }
        Box(
            modifier = Modifier
                .height(400.dp)
                .background(Color.White)
                .padding(top = 10.dp, start = 4.dp, end = 4.dp)
                .fillMaxWidth()
                .border(width = 1.5.dp, color = colorResource(id = R.color.lightblue))
        ) {
            var text by remember { mutableStateOf("") }
            CustomTextField(
                value = text,
                label = "What Do You Want To Talk About",
                onValueChange = { newText ->
                    text = newText
                }
            )
        }

        Column(modifier = Modifier) {
            viewmodel.selectedImageUri.value?.let { uri->
                val painter = rememberImagePainter(uri)
                Image(painter = painter,
                    contentDescription = null,
                    modifier=Modifier.fillMaxSize())
            }

        }

    }

}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    var isLabelVisible by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .height(400.dp)
            .background(colorResource(id = R.color.off_white))
            .padding(top = 10.dp, start = 4.dp, end = 4.dp)
            .fillMaxWidth()
    ) {
        // Label
        if (isLabelVisible && value.isEmpty()) {
            Text(
                text = label,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                color = Color.Black
            )
        }

        // Editable text field
        BasicTextField(
            modifier = Modifier
                .padding(start = 6.dp)
                .fillMaxWidth()
                .padding(top = 24.dp), // Adjust top padding to align with the label
            value = value,
            onValueChange = {
                onValueChange(it)
                isLabelVisible = it.isEmpty() // Hide label when text is not empty
            },
            textStyle = TextStyle(color = Color.Black)
        )


    }
}


@Composable
@Preview(showBackground = true)
fun PostScreenPreview() {
    PostScreen()
}