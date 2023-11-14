package com.example.littlelemon

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.littlelemon.ui.theme.LittleLemonColor
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
var t=0.0
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun C():Double {
    val context = LocalContext.current
    val databaseRef = FirebaseDatabase.getInstance("https://food-delivery-app-355a6-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("dishes")

    var users: List<DishFirebaseModel>? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userList = mutableListOf<DishFirebaseModel>()
                for (childSnapshot in dataSnapshot.children) {
                    val user = childSnapshot.getValue(DishFirebaseModel::class.java)
                    user?.let {
                        userList.add(it)
                    }
                }
                users = userList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error cases here
            }
        }

        databaseRef.addValueEventListener(valueEventListener)

        // Clean up the listener when the effect is removed

    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            Text(
                text = "Cart Total",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = LittleLemonColor.yellow
            )
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxHeight(0.7f)
                .verticalScroll(rememberScrollState())
        ) {
            users?.forEach { user ->
                t+=user.price
                val dish = requireNotNull(DishRepository.getDish(user.id))
                Card() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column {
                            Text(
                                text = user.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.h2
                            )
                            Text(
                                text = "₹" + user.price.toString(),
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.fillMaxWidth(.75f)
                            )
                            Button(onClick = {
                                val dishRef = FirebaseDatabase.getInstance("https://food-delivery-app-355a6-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("dishes").child(user.id.toString())
                                dishRef.removeValue().addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(context, "Dish deleted successfully!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Failed to delete dish.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }, colors = ButtonDefaults.buttonColors(LittleLemonColor.yellow)) {
                                Text(text = "Delete")
                            }
                        }
                        Image(
                            painter = painterResource(id = dish.imageResource),
                            contentDescription = "",
                            modifier = Modifier.clip(
                                RoundedCornerShape(10.dp)
                            )
                        )

                    }
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = LittleLemonColor.yellow,
                        thickness = 1.dp
                    )
                }
            }
        }

}
return t}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Cart(total:Double,onFilterClicked: (total:Double) -> Unit) {
    val scaffoldState1 = rememberBottomSheetScaffoldState()
    val scope1 = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    BottomSheetScaffold(
        scaffoldState = scaffoldState1,
        sheetBackgroundColor = Color.Black,
        sheetPeekHeight = 150.dp,
        sheetShape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp),
        drawerGesturesEnabled = true,
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(145.dp),
                contentAlignment = Alignment.TopCenter
            ) {

                Column {
                    Image(
                        painter = painterResource(id = R.drawable.round_horizontal_rule_24),
                        contentDescription = "Upper Panel Image",
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                scope1.launch {
                                    if (scaffoldState1.bottomSheetState.isCollapsed)
                                        scaffoldState1.bottomSheetState.animateTo(
                                            BottomSheetValue.Expanded,
                                            tween(1500)
                                        )
                                    else
                                        scaffoldState1.bottomSheetState.animateTo(
                                            BottomSheetValue.Collapsed,
                                            tween(1500)
                                        )
                                }
                            }
                            .fillMaxWidth()

                    )
                    Row(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        if(total!=0.0){
                        Row(Modifier.fillMaxWidth(0.52f)) {
                            Text(
                                text = "Grand Total",
                                color = Color.Cyan,
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    top = 20.dp,
                                    bottom = 20.dp
                                ),
                                fontSize = 35.sp
                            )
                        }
                        Text(
                            text = ":",
                            color = Color.Cyan,
                            modifier = Modifier.padding(start = 0.dp, top = 20.dp, bottom = 20.dp),
                            fontSize = 35.sp
                        )
                        Text(
                            text = String.format("%.2f",total + (18 / 100.0) * total),
                            color = Color.Cyan,
                            modifier = Modifier.padding(start = 10.dp, top = 20.dp, bottom = 20.dp),
                            fontSize = 35.sp
                        )
                    }
                        else{
                            Text(
                                text = "Empty Cart",
                                color = Color.Cyan,
                                modifier = Modifier.padding(
                                    start = 20.dp,
                                    top = 20.dp,
                                    bottom = 20.dp
                                ),fontSize = 35.sp)
                        }
                }
            }}
            if(total!=0.0){
            Row {
                Column(
                    Modifier.fillMaxWidth(0.52f),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        "Sub Total",
                        color = Color.Cyan,
                        modifier = Modifier.padding(start = 20.dp, bottom = 2.dp),
                        fontSize = 17.sp
                    )
                    Text(
                        "Discount",
                        color = Color.Cyan,
                        modifier = Modifier.padding(start = 20.dp, bottom = 2.dp),
                        fontSize = 17.sp
                    )
                    Text(
                        "Tax and Charges",
                        color = Color.Cyan,
                        modifier = Modifier.padding(start = 20.dp, bottom = 10.dp),
                        fontSize = 17.sp
                    )
                }
                Column {
                    Text(text = ":", color = Color.Cyan, fontSize = 17.sp)
                    Text(text = ":", color = Color.Cyan, fontSize = 17.sp)
                    Text(text = ":", color = Color.Cyan, fontSize = 17.sp)
                }
                Column {
                    Text(
                        text = total.toFloat().toString(),
                        color = Color.Cyan,
                        modifier = Modifier.padding(bottom = 2.dp, start = 18.dp),
                        fontSize = 17.sp
                    )
                    Text(
                        text = "0.0",
                        color = Color.Cyan,
                        modifier = Modifier.padding(bottom = 2.dp, start = 18.dp),
                        fontSize = 17.sp
                    )
                    Text(
                        text =  String.format("%.2f", (18 / 100.0) * total),
                        color = Color.Cyan,
                        modifier = Modifier.padding(bottom = 10.dp, start = 18.dp),
                        fontSize = 17.sp
                    )
                }

            }}

        }) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight()
                .clickable {
                    scope1.launch {
                        scaffoldState1.bottomSheetState.animateTo(
                            BottomSheetValue.Collapsed,
                            tween(1500)
                        )
                    }
                },contentAlignment = Alignment.TopCenter) {
            C()
            onFilterClicked(t)
            t = 0.0
        }
    }
}




