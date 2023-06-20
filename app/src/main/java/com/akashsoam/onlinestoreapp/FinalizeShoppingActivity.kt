package com.akashsoam.onlinestoreapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import java.math.BigDecimal

class FinalizeShoppingActivity : AppCompatActivity() {
    lateinit var btnPaymentProcessing: Button
    var ttPrice: Long = 0
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
                ttPrice = response.toLong()
            },
            { error -> }
        )
        requestQ.add((stringRequest))

        val paypalConfig: PayPalConfiguration =
            PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(MyPayPal.clientId)
        val ppService = Intent(this@FinalizeShoppingActivity, PayPalService::class.java)
        ppService.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig)
        startService(ppService)

        btnPaymentProcessing.setOnClickListener {
            var ppProcessing: PayPalPayment = PayPalPayment(
                BigDecimal.valueOf(ttPrice),
                "USD",
                "Online Store Kotlin",
                PayPalPayment.PAYMENT_INTENT_SALE
            )
            var paypalPaymentIntent =
                Intent(this@FinalizeShoppingActivity, PaymentActivity::class.java)
            paypalPaymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig)
            paypalPaymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, ppProcessing)
            startActivityForResult(paypalPaymentIntent, 1000)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                var intent = Intent(this@FinalizeShoppingActivity, ThankyouActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this@FinalizeShoppingActivity,
                    "Sorry, something went wrong.Try again",
                    Toast.LENGTH_LONG
                ).show();
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this@FinalizeShoppingActivity, PayPalService::class.java));

    }
}