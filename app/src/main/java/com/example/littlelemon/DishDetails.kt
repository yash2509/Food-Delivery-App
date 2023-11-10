package com.example.littlelemon

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun DishDetails(id: Int, navController: NavHostController) {
    val dish = requireNotNull(DishRepository.getDish(id))
    val counter = remember { mutableStateOf(1) }
    Column(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
    ) {
        Image(
            painter = painterResource(id = dish.imageResource),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = dish.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h1
        )
        Text(
            text = dish.description,
            style = MaterialTheme.typography.body1
        )
        Counter(counter.value, onCounterValueChanged = { value ->
            counter.value = value
        })
        Button(onClick = {
            val totalPrice = dish.price * counter.value
            val newDish = DishFirebaseModel(dish.id, dish.name, totalPrice)
            val databaseReference = FirebaseDatabase.getInstance("https://food-delivery-app-355a6-default-rtdb.asia-southeast1.firebasedatabase.app").reference.child("dishes")

            // Check if the dish already exists in the database
            val dishReference = databaseReference.child(dish.id.toString())
            dishReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Dish already exists, update the price
                        val existingDish = dataSnapshot.getValue(DishFirebaseModel::class.java)
                        existingDish?.let {
                            val newPrice = it.price + newDish.price
                            dishReference.child("price").setValue(newPrice)
                        }
                    } else {
                        // Dish does not exist, add it to the database
                        dishReference.setValue(newDish)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error: ${error.message}")
                }
            })
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Add for ₹${String.format("%.2f", dish.price * counter.value.toDouble())}", textAlign = TextAlign.Center)
        }

        Button(onClick = { navController.navigate(Home.route) }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Add another item")
        }
        Button(onClick = { navController.navigate(Cart.route)}, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Go to Cart")
        }
    }
}
data class DishFirebaseModel(
    val id: Int,
    val name: String, val price: Double){
    // Add a default no-argument constructor
    constructor() : this(0, "", 0.0)
}

@Composable
fun Counter(counter: Int, onCounterValueChanged: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        TextButton(
            onClick = {
                if (counter >= 2) {
                    onCounterValueChanged(counter - 1)
                }
            }
        ) {
            Text(
                text = "-",
                style = MaterialTheme.typography.h2,
                fontSize = 26.sp
            )
        }
        Text(
            text = counter.toString(),
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(16.dp),
            fontSize = 26.sp
        )
        TextButton(
            onClick = {
                onCounterValueChanged(counter + 1)
            }
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.h2,
                fontSize = 26.sp
            )
        }
    }
}

