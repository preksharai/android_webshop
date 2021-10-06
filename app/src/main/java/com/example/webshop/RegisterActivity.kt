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
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

            btn_register.setOnClickListener {
                if(et_emailRegister.text.toString().isEmpty() || et_passwordRegister.text.toString().isEmpty()
                    || et_name.text.toString().isEmpty() || et_mobile.text.toString().isEmpty()
                    || et_address.text.toString().isEmpty())
                {
                    Toast.makeText(this,"Field can't be empty.",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    var url="http://192.171.0.25/webshop/adduser.php?name="+et_name.text+
                            "&email="+et_emailRegister.text+
                            "&mobile="+et_mobile.text+
                            "&address="+et_address.text+
                            "&password="+et_passwordRegister.text

                    var rq:RequestQueue=Volley.newRequestQueue(this)
                    var sr=StringRequest(Request.Method.GET,url,Response.Listener { response ->

                        if(response.equals("0"))
                            Toast.makeText(this,"Email aready exists!",Toast.LENGTH_SHORT).show()
                        else {
                            Toast.makeText(
                                this,
                                "Registration done successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            UserInfo.email=et_emailRegister.text.toString()
                            val intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    },Response.ErrorListener { error ->
                        Toast.makeText(this,error.message,Toast.LENGTH_SHORT).show()

                    })
                    rq.add(sr)
                }
            }
            tv_login.setOnClickListener {
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()

        }

    }
}