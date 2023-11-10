package com.example.littlelemon

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.LittleLemonColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerPanel(scaffoldState: ScaffoldState,scope:CoroutineScope,navController: NavHostController){
   /* val databaseReference = FirebaseDatabase.getInstance("https://food-delivery-app-355a6-default-rtdb.asia-southeast1.firebasedatabase.app").reference.child("user")
    val userReference = databaseReference.child(getUsername())
    var user by rememberSaveable() {
        mutableStateOf("")
    }
    userReference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val data=dataSnapshot.getValue(UserFirebaseModel::class.java)
            if (data != null) {
                user=data.full_name
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e("Firebase", "Database operation cancelled: ${error.message}")
        }
    })*/
    Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Close Icon")
        Text(text = "user", modifier = Modifier.padding(start = 10.dp))
    }
    Divider(
        color = LittleLemonColor.yellow,
        thickness = 1.dp
    )
    ClickableText(text ="Account" , onClick = {})
    ClickableText("Logout", onClick = {
        FirebaseAuth.getInstance().signOut()
        navController.navigate(Login.route){
        popUpTo(0){inclusive = true}
        launchSingleTop = true
        restoreState = true
    }
        })
    IconButton(onClick = {scope?.launch{scaffoldState?.drawerState?.close()}}) {
        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Close Icon")
    }
}
@Composable
fun ClickableText(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
    ) {
        Text(text = text, modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp))
    }
}
