package com.example.belajarcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.belajarcompose.ui.theme.BelajarComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 9.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val painter = painterResource(id = R.drawable.ichi_valentine_card_after_training)
                val description = "Ichika KAWAIIIII"
                val title = "Ichika Valentine"
                MyCard(painter = painter, contentDescription = description, title = title)
                MyCard(painter = painter, contentDescription = description, title = title)
                MyCard(painter = painter, contentDescription = description, title = title)
            }
        }
    }
}

@Composable
fun MyCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .padding(vertical = 9.dp, horizontal = 18.dp)
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(0.8f)
                .height(200.dp),
            elevation = 20.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Box(
                modifier = modifier
            ) {
                Image(
                    painter = painter,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black),
                                startY = 300f
                            )
                        )
                )
                Text(
                    text = title,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 21.sp
                    ),
                    modifier = modifier
                        .padding(18.dp)
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}