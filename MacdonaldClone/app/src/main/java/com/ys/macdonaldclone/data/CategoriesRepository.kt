package com.ys.macdonaldclone.data

import com.ys.macdonaldclone.model.Category

object CategoriesRepository {
    fun getCategoriesData(): List<Category> {
        return listOf(
            Category(
                id = 1,
                name = "Burgers",
                image = "${IMAGE_PATH}category_burgers.png"
            ),
            Category(
                id = 2,
                name = "Fries",
                image = "${IMAGE_PATH}category_fries.png"
            ),
            Category(
                id = 3,
                name = "Beverages",
                image = "${IMAGE_PATH}category_beverages.png"
            ),
            Category(
                id = 4,
                name = "Combo Meals",
                image = "${IMAGE_PATH}category_combo_meals.png"
            ),
            Category(
                id = 5,
                name = "Happy Meals",
                image = "${IMAGE_PATH}category_happy_meals.png"
            ),
            Category(
                id = 6,
                name = "Desserts",
                image = "${IMAGE_PATH}category_desserts.png"
            )
        )
    }
}