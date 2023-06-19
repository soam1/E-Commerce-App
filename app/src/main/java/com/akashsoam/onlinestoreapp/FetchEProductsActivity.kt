package com.akashsoam.onlinestoreapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class FetchEProductsActivity : AppCompatActivity() {

    lateinit var recyclerViewProducts: RecyclerView
    lateinit var txtSelectedBrand: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_eproducts)
        recyclerViewProducts = findViewById(R.id.recyclerViewFetchProducts)
        txtSelectedBrand = findViewById(R.id.txtSelectedBrand)


        val selectedBrand = intent.getStringExtra("BRAND")
        txtSelectedBrand.text = selectedBrand

        var productsList = ArrayList<EProduct>()
        val productsUrl =
            "http://192.168.42.198/OnlineStoreApp/fetch_eproducts.php?brand=$selectedBrand"


        val requestQ = Volley.newRequestQueue(this@FetchEProductsActivity)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            productsUrl,
            null,
            { response ->
                for (productJsonObjectIndex in 0.until(response.length())) {
                    productsList.add(
                        EProduct(
                            response.getJSONObject(productJsonObjectIndex).getInt("id"),
                            response.getJSONObject(productJsonObjectIndex).getString("name"),
                            response.getJSONObject(productJsonObjectIndex).getInt("price"),
                            response.getJSONObject(productJsonObjectIndex).getString("picture")
                        )
                    )
//                    Log.i("SEE", response.getJSONObject(productJsonObjectIndex).toString())

                }
                val pAdapter = EProductAdapter(this@FetchEProductsActivity, productsList)
                recyclerViewProducts.layoutManager =
                    LinearLayoutManager(this@FetchEProductsActivity)
                recyclerViewProducts.adapter = pAdapter
            },
            { error ->
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()
            })
        requestQ.add(jsonArrayRequest)
    }
}