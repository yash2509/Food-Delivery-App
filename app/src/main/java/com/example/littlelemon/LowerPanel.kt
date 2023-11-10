package com.example.littlelemon

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.DishRepository
import com.example.littlelemon.ui.theme.LittleLemonColor

@Composable
fun LowerPanel(navController: NavHostController) {
    Column {
        WeeklySpecial()
        Menu(navController)
    }
}


@Composable
fun WeeklySpecial(){
    Card(Modifier.fillMaxWidth()) {
        Column() {
            Text(text = "Weekly Special",
                fontSize =26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
            Row() {
                category.forEach{
                    MenuCategory(category = it)
                }
            }
        }
    }
}


@Composable
fun MenuCategory(category: String){
    Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(Color.LightGray), shape = RoundedCornerShape(40),
        modifier = Modifier.padding(5.dp)) {
        Text(text = category)
    }
}
val category= listOf<String>(
    "Lunch",
    "Dessert",
    "Main Course"
)

@Composable
fun Menu(navController: NavHostController) {
    LazyColumn {
        items(DishRepository.dishes) { Dish ->
            MenuDish(Dish, navController)
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuDish(Dish: Dish, navController: NavHostController? = null) {
    var count by rememberSaveable(){
        mutableStateOf(0)
    }
    Card(onClick = { Log.d("AAA", "Click ${Dish.id}")
        navController?.navigate(DishDetails.route + "/${Dish.id}")}, modifier = Modifier.padding(10.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column {
                Text(
                    text = Dish.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.h2
                )
                Text(
                    text = Dish.description,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp)
                        .fillMaxWidth(.75f),
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "₹"+Dish.price.toString(), color = Color.Gray, fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body2
                )
            }
            Image(
                painter = painterResource(id = Dish.imageResource),
                contentDescription = "",
                modifier = Modifier.clip(RoundedCornerShape(10.dp))
            )
        }
    }
    Divider(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        color = LittleLemonColor.yellow,
        thickness = 1.dp
    )
}

