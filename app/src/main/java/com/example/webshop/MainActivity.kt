package com.example.webshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener {
            if(et_emaillogin.text.toString().isEmpty() || et_passwordlogin.text.toString().isEmpty())
            {
                Toast.makeText(this,"Field can't be empty.",Toast.LENGTH_SHORT).show()
            }
            else
            {
                var url="http://192.168.1.6/webshop/login.php?&email="+et_emaillogin.text+
                        "&password="+et_passwordlogin.text

                //to connect to the web service file
                var rq: RequestQueue = Volley.newRequestQueue(this)

                //as output of php file is string therefore, create object of json string request class
                //use (Request.Method.GET) when we give data in url
                //listener is for result 0 or 1
                var sr=StringRequest(Request.Method.GET,url, Response.Listener { response ->
                    //jb success aayga in response variable, means php file chl gyi, do  the rest here
                    if(response.equals("0"))
                        Toast.makeText(this,"Sorry, email or password is wrong.",Toast.LENGTH_SHORT).show()
                    else if(response.equals("1"))
                    {
                        Toast.makeText(this,"Login done",Toast.LENGTH_SHORT).show()
                        UserInfo.email=et_emaillogin.text.toString()
                        val intent=Intent(this,HomeActivity::class.java)
                        intent.putExtra("email",et_emaillogin.text.toString())
                        startActivity(intent)
                        finish()
                    }


                },Response.ErrorListener { error ->
                    //when error comes means php nhi chli, do the needful here
                    Toast.makeText(this,error.message,Toast.LENGTH_SHORT).show()

                })
                //to send string request in request queue
                rq.add(sr)
            }
        }
        tv_register.setOnClickListener {
            val intent= Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}