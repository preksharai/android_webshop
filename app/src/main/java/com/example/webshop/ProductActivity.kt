package com.example.webshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        var list=ArrayList<ProductModel>()

        var category:String?=intent.getStringExtra("category")
        var url="http://192.171.0.25/webshop/getproducts.php?category=$category"
        var rq:RequestQueue= Volley.newRequestQueue(this)
        var jar=JsonArrayRequest(Request.Method.GET,url,null, Response.Listener { response->

            for(x in 0..response.length()-1)
            {
                //object of class ProductModel is created cz list m project model ke object hi jaate hn
                list.add(ProductModel(response.getJSONObject(x).getInt("id"),
                response.getJSONObject(x).getString("name"),
                response.getJSONObject(x).getInt("price"),
                response.getJSONObject(x).getString("image")))

                //creating object of adapter
                var adp=ProductAdapter(this,list)

                //attaching this adapter to the recycler view, firstly creating the layout
                RV_products.layoutManager=LinearLayoutManager(this)
                RV_products.adapter=adp

            }

        },Response.ErrorListener { error ->
            Toast.makeText(this,error.message,Toast.LENGTH_SHORT).show()

        })
        rq.add(jar)
    }
}