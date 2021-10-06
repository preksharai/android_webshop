package com.example.webshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var list2=ArrayList<String>()
        var email=intent.getStringExtra("email")
        var rq:RequestQueue= Volley.newRequestQueue(this)
        var url2="http://192.171.0.25/webshop/getname.php?email=$email"
        var jor=JsonObjectRequest(Request.Method.GET,url2,null,Response.Listener { response ->
            tv_hello.text="Hello "+response.getString("name")

        },Response.ErrorListener { error ->
            Toast.makeText(this,error.message,Toast.LENGTH_SHORT).show()

        })
        rq.add(jor)

        var list=ArrayList<String>()
        var url="http://192.171.0.25/webshop/getcategory.php"
        var jar=JsonArrayRequest(Request.Method.GET,url,null,Response.Listener { response ->
            //here, reponse has json array
            for(x in 0..response.length()-1)
            {
                list.add(response.getJSONObject(x).getString("category"))
            }

            //adapter for list view
            var adp=ArrayAdapter(this,R.layout.listview_singleitem,list)
            category_listview.adapter=adp

        },Response.ErrorListener { error ->
            Toast.makeText(this,error.message, Toast.LENGTH_SHORT).show()

        })
        rq.add(jar)

        //use setOnItemClickListener for listview
        //position is the position of an item starting from 0, position has that index jispe user ne click kia
        category_listview.setOnItemClickListener { adapterView, view, position, id ->
            var category:String=list[position]
            var intent= Intent(this,ProductActivity::class.java)
            intent.putExtra("category",category)
            startActivity(intent)
        }

    }
}