package com.akashsoam.onlinestoreapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class FinalizeShoppingActivity : AppCompatActivity() {
    lateinit var btnPaymentProcessing: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalize_shopping)
        btnPaymentProcessing = findViewById(R.id.btnPaymentProcessing)

        var calculateTotalPriceUrl =
            "http://192.168.42.198/OnlineStoreApp/calculate_total_price.php?invoice_num=${
                intent.getStringExtra("LATEST_INVOICE_NUMBER")
            }"

        val requestQ = Volley.newRequestQueue(this@FinalizeShoppingActivity)
        val stringRequest = StringRequest(
            Request.Method.GET,
            calculateTotalPriceUrl,
            { response ->
                btnPaymentProcessing.text = "Pay $$response via PayPal Now!"
            },
            { error -> }
        )
        requestQ.add((stringRequest))
    }
}