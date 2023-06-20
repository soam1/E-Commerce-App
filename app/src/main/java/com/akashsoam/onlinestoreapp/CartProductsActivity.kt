package com.akashsoam.onlinestoreapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.continueShoppingItems) {
            val intent = Intent(this@CartProductsActivity, HomeScreen::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.verifyOrderItem) {
            val verifyOrderUrl =
                "http://192.168.42.198/OnlineStoreApp/verify_order.php?email=${Person.email}"
            val requestQ = Volley.newRequestQueue(this@CartProductsActivity)
            val stringRequest = StringRequest(
                Request.Method.GET,
                verifyOrderUrl,
                { response ->
                    val intent =
                        Intent(this@CartProductsActivity, FinalizeShoppingActivity::class.java)
                    Toast.makeText(this@CartProductsActivity, response, Toast.LENGTH_LONG).show();
                    intent.putExtra("LATEST_INVOICE_NUMBER", response)
                    startActivity(intent)
                },
                { error -> }
            )
            requestQ.add((stringRequest))

        } else if (item.itemId == R.id.declineOrderItem) {
            val deleteUrl =
                "http://192.168.42.198/OnlineStoreApp/delete_order.php?email=${Person.email}"
            val requestQ = Volley.newRequestQueue(this@CartProductsActivity)
            val stringRequest = StringRequest(
                Request.Method.GET,
                deleteUrl,
                { response ->
                    val intent = Intent(this@CartProductsActivity, HomeScreen::class.java)
                    Toast.makeText(this@CartProductsActivity, response, Toast.LENGTH_SHORT).show();
                    startActivity(intent)
                },
                { error -> }
            )
            requestQ.add((stringRequest))

        }
        return super.onOptionsItemSelected(item)
    }
}