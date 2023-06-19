package com.akashsoam.onlinestoreapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class CartProductsActivity : AppCompatActivity() {
    lateinit var cartProductsListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_products)
        cartProductsListView = findViewById(R.id.cartProductsListView)

        val cartProductsUrl =
            "http://192.168.42.198/OnlineStoreApp/fetch_temporary_order.php?email=${Person.email}"
        var cartProductsList = ArrayList<String>()
        var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
        var jsonAR = JsonArrayRequest(
            Request.Method.GET,
            cartProductsUrl,
            null,
            { response ->
                for (joIndex in 0.until(response.length())) {//id, name, price, email, amount
                    cartProductsList.add(
                        "${
                            response.getJSONObject(joIndex).getInt("id")
                        }\n${
                            response.getJSONObject(joIndex).getString("name")
                        }\n${response.getJSONObject(joIndex).getInt("price")}\n" +
                                response.getJSONObject(joIndex).getString("email") +
                                "\n ${response.getJSONObject(joIndex).getInt("amount")}"
                    )
                }
                var cartProductsAdapter = ArrayAdapter<String>(
                    this@CartProductsActivity,
                    android.R.layout.simple_list_item_1, cartProductsList
                )

                cartProductsListView.adapter = cartProductsAdapter
            },
            { error -> }
        )
        requestQ.add(jsonAR)
    }
}