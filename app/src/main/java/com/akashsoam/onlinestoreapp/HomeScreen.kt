package com.akashsoam.onlinestoreapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class HomeScreen : AppCompatActivity() {
    lateinit var brandsListView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        brandsListView = findViewById(R.id.brandsListView)

        val brandsUrl = "http://192.168.42.198/OnlineStoreApp/fetch_brands.php"
        val brandsList = ArrayList<String>()

        val requestQ: RequestQueue = Volley.newRequestQueue(this@HomeScreen)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, brandsUrl, null,
            { response ->
                for (jsonObject in 0.until(response.length())) {
                    Log.i("response",""+ jsonObject)
                    brandsList.add(response.getJSONObject(jsonObject).getString("brand").toString())

                }
                val brandsListAdapter =
                    ArrayAdapter(this@HomeScreen, R.layout.brand_item_text_view, brandsList)
                brandsListView.adapter = brandsListAdapter
            },
            { error ->
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()

            }
        )
        requestQ.add(jsonArrayRequest)


        brandsListView.setOnItemClickListener { parent, view, position, id ->
            val tappedBrand = brandsList[position]
            val intent = Intent(this@HomeScreen, FetchEProductsActivity::class.java)
            intent.putExtra("BRAND", tappedBrand)
            startActivity(intent)

        }
    }
}