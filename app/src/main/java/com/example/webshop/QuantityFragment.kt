package com.example.webshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.app.DialogFragment
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_quantity.*

class QuantityFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v=inflater!!.inflate(R.layout.fragment_quantity,container,false)
        var et=v.findViewById<EditText>(R.id.et_qty)
        var btn=v.findViewById<Button>(R.id.btn_qty)

        btn.setOnClickListener {
            var rq: RequestQueue = Volley.newRequestQueue(activity)
            var url="http://192.168.1.6/webshop/add_temp_order.php?email="+UserInfo.email+
            "&productid="+UserInfo.productId+"&quantity="+et_qty.text.toString()
            var sr=StringRequest(Request.Method.GET,url,Response.Listener { response ->

                var intent= Intent(activity,CartActivity::class.java)
                startActivity(intent)
                activity.finish()

            },Response.ErrorListener { error ->

                Toast.makeText(activity,error.message,Toast.LENGTH_SHORT).show()

            })
            rq.add(sr)
        }

        return v
    }

    }