package com.example.littlelemon

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Bottompanel(navController: NavHostController) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        IconButton(onClick = {navController.navigate(Home.route)}, modifier = Modifier.padding(end = 50.dp)
        ) {
            Icon(imageVector = Icons.Default.Home, contentDescription ="Home", modifier = Modifier.size(24.dp))
        }
        IconButton(onClick = {navController.navigate(Search.route) }, modifier = Modifier.padding(start = 50.dp)) {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "Fav", modifier = Modifier.size(24.dp))
        }
    }
}
