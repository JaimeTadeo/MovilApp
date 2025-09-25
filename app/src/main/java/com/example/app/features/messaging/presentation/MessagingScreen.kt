package com.example.app.features.messaging.presentation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MessagingScreen() {
    val messagingViewModel: MessagingViewModel = viewModel()
    var token by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Mensajería Firebase", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            loading = true
            error = ""
            token = ""
            messagingViewModel.fetchFirebaseToken()
        }) {
            Text("Obtener Token FCM")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (loading) {
            LaunchedEffect(Unit) {
                try {
                    token = messagingViewModel.getToken()
                } catch (e: Exception) {
                    error = e.message ?: "Error desconocido"
                } finally {
                    loading = false
                }
            }
            CircularProgressIndicator()
        }
        if (token.isNotEmpty()) {
            Text(text = "Token FCM:", style = MaterialTheme.typography.bodyMedium)
            Text(text = token, style = MaterialTheme.typography.bodySmall)
        }
        if (error.isNotEmpty()) {
            Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
        }
    }
}

