package com.example.webshop

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import kotlinx.android.synthetic.main.activity_total_confirm.*
import java.math.BigDecimal

class TotalConfirm : AppCompatActivity() {

    var config:PayPalConfiguration?=null
    var amount:Double=0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_confirm)

        var bno=intent.getStringExtra("bno")

        Log.e("bno","$bno")

        var rq: RequestQueue = Volley.newRequestQueue(this)
        val url= "http://192.168.1.6/webshop/total_price.php?bill_no=$bno"

        //jb koi output ni aata, toh StringRequest hi create hoti h
        var sr= StringRequest(Request.Method.GET,url, Response.Listener { response ->

            totalprice.text="Your total price is : $response"
            amount=response.toDouble()

        }, Response.ErrorListener { error ->
            Toast.makeText(this,error.message, Toast.LENGTH_SHORT).show()
        })
        rq.add(sr)

        config=PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(UserInfo.clientId)
        var intent=Intent(this,PayPalService::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config)
        startService(intent)

        btn_pay.setOnClickListener {

            //convert the amount in BigDecimal
            var payment=PayPalPayment(BigDecimal.valueOf(amount),"USD","WebShop",PayPalPayment.PAYMENT_INTENT_SALE)
            var intent=Intent(this,PaymentActivity::class.java)
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config)
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment)

            //it is used so that the later activity can send response to the former one
            startActivityForResult(intent,123)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //jo request code hmne bheja tha 123, usse jo response aaya h vo btaya
        if(resultCode== Activity.RESULT_OK)
        {
            var intent=Intent(this,FinalActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //if activity closes, to shut the paypal
    override fun onDestroy() {
        stopService(Intent(this,PayPalService::class.java))
        super.onDestroy()
    }
}