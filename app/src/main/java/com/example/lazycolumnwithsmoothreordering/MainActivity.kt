package com.example.lazycolumnwithsmoothreordering

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lazycolumnwithsmoothreordering.reorderable_column.ReorderableLazyColumn
import com.example.lazycolumnwithsmoothreordering.ui.theme.LazyColumnWithSmoothReorderingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyColumnWithSmoothReorderingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ReorderableLazyColumn().ReorderableColumn()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LazyColumnWithSmoothReorderingTheme {
        Greeting("Android")
    }
}