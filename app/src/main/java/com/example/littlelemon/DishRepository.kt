package com.example.littlelemon

import androidx.annotation.DrawableRes

object DishRepository {
    var dishes = listOf(
        Dish(
            1,
            "Greek Salad",
            "The famous greek salad of crispy lettuce, peppers, olives, our Chicago.",
            12.99,
            "Main Course",
            R.drawable.greeksalad
        ),
        Dish(
            2,
            "Lemon Desert",
            "Traditional homemade Italian Lemon Ricotta Cake.",
            8.99,
            "Dessert",
            R.drawable.greeksalad
        ),
        Dish(
            3,
            "Bruschetta",
            "Our Bruschetta is made from grilled bread that has been smeared with garlic and seasoned with salt and olive oil.",
            4.99,
            "Breakfast",
            R.drawable.greeksalad
        ),
        Dish(
            4,
            "Grilled Fish",
            "Fish marinated in fresh orange and lemon juice. Grilled with orange and lemon wedges.",
            19.99,
            "Lunch",
            R.drawable.greeksalad
        ),
        Dish(
            5,
            "Pasta",
            "Penne with fried aubergines, cherry tomatoes, tomato sauce, fresh chilli, garlic, basil & salted ricotta cheese.",
            8.99,
            "Breakfast",
            R.drawable.greeksalad
        ),
        Dish(
            6,
            "Lasagne",
            "Oven-baked layers of pasta stuffed with Bolognese sauce, béchamel sauce, ham, Parmesan & mozzarella cheese .",
            7.99,
            "Main Course",
            R.drawable.greeksalad
        )
    )

    fun getDish(id: Int) = dishes.firstOrNull { it.id == id }
}

data class Dish(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    @DrawableRes val imageResource: Int
)
