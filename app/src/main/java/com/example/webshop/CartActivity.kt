package com.example.webshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_quantity.*

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        var rq: RequestQueue = Volley.newRequestQueue(this)
        var url="http://192.168.1.6/webshop/get_temp_order.php?email="+UserInfo.email
        var list=ArrayList<String>()
        var jar= JsonArrayRequest(Request.Method.GET,url,null,Response.Listener { response ->
            //here, reponse has json array
            for(x in 0..response.length()-1)
            {
                //concatenating the things
                list.add(response.getJSONObject(x).getString("name")+"\n"+
                response.getJSONObject(x).getInt("price") +"\n"+
                response.getJSONObject(x).getInt("quantity"))
            }

            //adapter for list view
            var adp2= ArrayAdapter(this,R.layout.cart_single_item,list)
            cartListView.adapter=adp2

        },Response.ErrorListener { error ->
            Toast.makeText(this,error.message, Toast.LENGTH_SHORT).show()

        })
        rq.add(jar)
    }
    //this function has the reference of menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    //jis item pe click kia gya tha, ye function uska reference leke aata h
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.cancel_order)
        {
            //means remove all orders
            var rq: RequestQueue = Volley.newRequestQueue(this)
            var url="http://192.168.1.6/webshop/cancel_order.php?email="+UserInfo.email
            var sr=StringRequest(Request.Method.GET,url,Response.Listener { response ->

                val intent=Intent(this,HomeActivity::class.java)
                intent.putExtra("email",UserInfo.email)
                startActivity(intent)
                finish()

            },Response.ErrorListener { error ->
                Toast.makeText(this,error.message, Toast.LENGTH_SHORT).show()
            })
            rq.add(sr)

        }
        else if(item.itemId==R.id.confirm_order)
        {
            //del the details from temporary table and move then to the new table
            var rq: RequestQueue = Volley.newRequestQueue(this)
            val url="http://192.168.1.6/webshop/confirm_order.php?email="+UserInfo.email

            //jb koi output ni aata, toh StringRequest hi create hoti h
            var sr=StringRequest(Request.Method.GET,url,Response.Listener { response ->

                //bill no is the output in response
                var intent=Intent(this, TotalConfirm::class.java)
                intent.putExtra("bno",response)
                startActivity(intent)

            },Response.ErrorListener { error ->
                Toast.makeText(this,error.message, Toast.LENGTH_SHORT).show()
            })
            rq.add(sr)
        }
        else
        {
            val intent=Intent(this,HomeActivity::class.java)
            intent.putExtra("email",UserInfo.email)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


}