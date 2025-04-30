sealed class Screen(val route: String) {
    object GetProduct : Screen("getProductScreen/{productId}/{productName}/{price}") {
        fun createRoute(productId: String, productName: String, price: String) =
            "getProductScreen/$productId/$productName/$price"


    }
}
