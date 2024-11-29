package com.example.courotinrtask1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleCoroutineExample()
        }
    }
}

@Composable
fun SimpleCoroutineExample() {
    var message by remember { mutableStateOf("Appuyez sur le bouton pour démarrer") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, modifier = Modifier.padding(bottom = 16.dp))

        Button(onClick = {
            coroutineScope.launch {
                try {
                    message = "Chargement en cours..."

                    simulateLongRunningTask()

                    message = "Données chargées avec succès !"
                } catch (e: CancellationException) {
                    message = "Opération annulée !"
                } catch (e: Exception) {
                    message = "Une erreur s'est produite : ${e.message}"
                }
            }
        }) {
            Text("Démarrer")
        }
    }
}

suspend fun simulateLongRunningTask() {
    withContext(Dispatchers.IO) {
        delay(3000)
        if ((1..10).random() > 7) {
            throw Exception("Erreur simulée pendant la tâche.")
        }
    }
}
