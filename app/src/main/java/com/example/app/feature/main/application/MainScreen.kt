package com.example.app.feature.main.application

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onNavigateToGithub: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToMovies: () -> Unit,
    onNavigateToDollar: () -> Unit, // Nuevo parámetro para la cotización boliviana
    onNavigateToMessaging: () -> Unit // Nuevo parámetro para la mensajería
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(36.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Funcionalidades",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Start
        )

        NavigationCard(
            title = "Perfil de GitHub",
            description = "los perfiles de github",
            icon = Icons.Default.Person,
            onClick = onNavigateToGithub
        )

        Spacer(modifier = Modifier.height(16.dp))

        NavigationCard(
            title = "Cotización Dólar Boliviano",
            description = "Cotización en tiempo real desde Firebase",
            icon = Icons.Default.Star, // Quitar AttachMoney, usar Star como placeholder
            onClick = onNavigateToDollar
        )

        Spacer(modifier = Modifier.height(16.dp))

        NavigationCard(
            title = "Películas Populares",
            description = "Descubre las películas más populares",
            icon = Icons.Default.PlayArrow,
            onClick = onNavigateToMovies
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para navegar a la pantalla de mensajería
        Button(
            onClick = { onNavigateToMessaging() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(Icons.Default.Email, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Mensajería Firebase")
        }
    }
}

@Composable
private fun NavigationCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Ir a $title",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
