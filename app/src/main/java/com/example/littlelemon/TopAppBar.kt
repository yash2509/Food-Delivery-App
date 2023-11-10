package com.example.littlelemon

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopAppBar(scaffoldState: ScaffoldState?=null,scope: CoroutineScope,navController: NavHostController){
    Row(horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {scope?.launch{scaffoldState?.drawerState?.open()}}
        ) {
           Icon(imageVector = Icons.Default.AccountCircle, contentDescription ="Menu Icon",
           modifier = Modifier.size(24.dp))
        }
        Image(painter = painterResource(id = R.drawable.im), contentDescription ="Little Lemon Logo",
        modifier = Modifier.padding(end = 30.dp, start = 30.dp, top = 10.dp, bottom = 10.dp))
        IconButton(onClick = { navController.navigate(Cart.route)}) {
            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart",
            modifier = Modifier.size(24.dp))
        }
    }
}
