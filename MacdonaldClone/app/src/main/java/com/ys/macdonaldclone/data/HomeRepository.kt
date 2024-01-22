package com.ys.macdonaldclone.data

import com.ys.macdonaldclone.model.Home
import com.ys.macdonaldclone.model.MenuItem
import com.ys.macdonaldclone.model.User

object HomeRepository {
    fun getHomeData(): Home {
        return Home(
            user = User(
                name = "Albert Lee"
            ),
            categories = CategoriesRepository.getCategoriesData(),
            popularMenuItems = listOf(
                MenuItem(
                    id = 4001,
                    name = "Big Mac Combo Meal",
                    description = "The one and only McDonald’s Big Mac Combo Meal. Big Mac Ingredients include a classic sesame hamburger bun, Big Mac Sauce, American cheese and sliced pickles. The Big Mac Combo Meal is served with our World Famous Fries and your choice of an icy Coca-Cola fountain drink.",
                    image = "${IMAGE_PATH}menu_item_big_mac_combo_meal.png",
                    price = getRandomPrice(),
                    categoryId = 4
                ),
                MenuItem(
                    id = 4002,
                    name = "Cheeseburger Combo Meal",
                    description = "Our Cheeseburger Combo Meal is a simple, satisfying classic McDonald’s 100% beef burger, served with our World Famous Fries and your choice of a Medium McDonald’s soda or soft drink.",
                    image = "${IMAGE_PATH}menu_item_cheeseburger_combo_meal.png",
                    price = getRandomPrice(),
                    categoryId = 4
                ),
                MenuItem(
                    id = 4004,
                    name = "Double Quarter Pounder with Cheese Meal",
                    description = "Get double the fresh beef flavor with a Double Quarter Pounder with Cheese made with fresh beef that’s cooked when you order. Served with our World Famous Fries and your choice of an icy soft drink.",
                    image = "${IMAGE_PATH}menu_item_double_quarter_pounder_with_cheese_meal.png",
                    price = getRandomPrice(),
                    categoryId = 4
                ),
                MenuItem(
                    id = 4005,
                    name = "10 piece Chicken McNuggets Meal",
                    description = "Enjoy 10 tender and delicious Chicken McNuggets made with all white meat chicken—plus our World Famous Fries and your choice of a Medium McDonald’s drink with the 10 Piece McNuggets for the ultimate McDonald’s Combo Meal.",
                    image = "${IMAGE_PATH}menu_item_ten_piece_chicken_mcnuggets_meal.png",
                    price = getRandomPrice(),
                    categoryId = 4
                ),
                MenuItem(
                    id = 5001,
                    name = "Hamburger Happy Meal",
                    description = "A juicy hamburger with kid-sized World Famous Fries and your choice of a Happy Meal side: Apple Slices or Yoplait GO-GURT Low Fat Strawberry Yogurt. Then pick a kids’ drink: 1% Low Fat Milk Jug, DASANI Water or Honest Kids Appley Ever After Organic Juice Drink.",
                    image = "${IMAGE_PATH}menu_item_hamburger_happy_meal.png",
                    price = getRandomPrice(),
                    categoryId = 5
                ),
                MenuItem(
                    id = 6002,
                    name = "Hot Fudge Sundae",
                    description = "Our classic hot fudge sundae is made with creamy vanilla soft serve and smothered in chocolaty hot fudge topping.",
                    image = "${IMAGE_PATH}menu_item_hot_fudge_sundae.png",
                    price = getRandomPrice(),
                    categoryId = 6
                ),
            ),
            recommendedMenuItems = listOf(
                MenuItem(
                    id = 4001,
                    name = "Big Mac Combo Meal",
                    description = "The one and only McDonald’s Big Mac Combo Meal. Big Mac Ingredients include a classic sesame hamburger bun, Big Mac Sauce, American cheese and sliced pickles. The Big Mac Combo Meal is served with our World Famous Fries and your choice of an icy Coca-Cola fountain drink.",
                    image = "${IMAGE_PATH}menu_item_big_mac_combo_meal.png",
                    price = getRandomPrice(),
                    categoryId = 4
                ),
                MenuItem(
                    id = 4004,
                    name = "Double Quarter Pounder with Cheese Meal",
                    description = "Get double the fresh beef flavor with a Double Quarter Pounder with Cheese made with fresh beef that’s cooked when you order. Served with our World Famous Fries and your choice of an icy soft drink.",
                    image = "${IMAGE_PATH}menu_item_double_quarter_pounder_with_cheese_meal.png",
                    price = getRandomPrice(),
                    categoryId = 4
                ),
                MenuItem(
                    id = 4006,
                    name = "Filet-O-Fish Meal",
                    description = "A classic fish sandwich value meal made with wild-caught Alaskan Pollock on a regular McDonald’s bun, served with our World Famous Fries and your choice of an icy soft drink. Enjoy a Filet-O-Fish Meal today.",
                    image = "${IMAGE_PATH}menu_item_filet_o_fish_meal.png",
                    price = getRandomPrice(),
                    categoryId = 4
                ),
                MenuItem(
                    id = 2002,
                    name = "Medium French Fries",
                    description = "Our World Famous Fries are made with premium potatoes such as the Russet Burbank and the Shepody. With 0g of trans fat per labeled serving, these epic fries are crispy and golden on the outside and fluffy on the inside.",
                    image = "${IMAGE_PATH}menu_item_medium_french_fries.png",
                    price = getRandomPrice(),
                    categoryId = 2
                ),
                MenuItem(
                    id = 3008,
                    name = "McCafé Hot Chocolate",
                    description = "Taste the delicious chocolaty flavor of a McCafé hot chocolate. Hot chocolate made with steamed whole or nonfat milk, rich chocolate syrup, and topped with whipped topping and chocolate drizzle.",
                    image = "${IMAGE_PATH}menu_item_mccafe_hot_chocolate.png",
                    price = getRandomPrice(),
                    categoryId = 3
                ),
            )
        )
    }
}