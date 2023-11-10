package com.example.littlelemon

import android.media.ImageReader
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

interface Destination {
    val route:String
    val icon:ImageVector
    val title:String
}
interface nav {
    val route:String
}
object Home:Destination{
    override val route="Home"
    override val icon= Icons.Filled.Home
    override val title= "Home"
}
object Search:Destination{
    override val route="Search"
    override val icon=Icons.Filled.Search
    override val title="Search"
}
object DishDetails:nav{
    override val route="DishDetails"
    const val argDishId = "dishId"
}
object Cart:nav{
    override val route="Cart"
    const val argDishId = "dishId"
}
object Login:nav{
    override val route="Login"
}
object Register:nav{
    override val route="Register"
}