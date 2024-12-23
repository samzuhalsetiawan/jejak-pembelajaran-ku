package com.samzuhalsetiawan.sekaide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.samzuhalsetiawan.sekaide.editor.ScoreEditor
import com.samzuhalsetiawan.sekaide.ui.theme.SekaIDETheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SekaIDETheme {
                Scaffold { scaffoldPadding ->
                    Box(
                        modifier = Modifier
                            .padding(scaffoldPadding)
                            .fillMaxSize()
                    ) {

                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ScoreEditor()
                        }

                        Row(
                            modifier = Modifier.align(Alignment.TopCenter),
                        ) {
                            Button(
                                onClick = {

                                }
                            ) {
                                Text(text = "Play")
                            }
                            Button(
                                onClick = {

                                }
                            ) {
                                Text(text = "Stop")
                            }
                        }
                    }
                }
            }
        }
    }
}
