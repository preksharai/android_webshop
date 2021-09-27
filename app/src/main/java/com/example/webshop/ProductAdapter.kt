package com.example.webshop

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_singleitemlook.view.*

//RecyclerView.ViewHolder is bana bnaya
//still we create Product Holder
class ProductAdapter(val context:Context,var list:ArrayList<ProductModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    //usually, hum btate hn ki viewholder hmari class ke under hogi , pr yha custom viewholder bnare hn
    class ProductHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        //here bind function is needed to initialise the views
        //itemView is reference of the single item look file
        fun bind(name:String,price:Int,image:String,productId:Int)
        {
            itemView.tv_name_prod.text=name
            itemView.tv_price_prod.text="Rs:${price.toString()}"
            Picasso.with(itemView.context).load("http://192.168.1.6/images/"+image).into(itemView.prod_image)
            itemView.addToCart.setOnClickListener {
                UserInfo.productId=productId

                //creating object of class quantitiy fragment
                var obj=QuantityFragment()
                var manager=(itemView.context as Activity).fragmentManager
                obj.show(manager,"quantity")
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        //creating reference of single item look
        var v:View=LayoutInflater.from(context).inflate(R.layout.product_singleitemlook,parent,false)
        return ProductHolder(v)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        //telling that our holder is ProductHolder, iske ander jao or call kro bind function ko
        (holder as ProductHolder).bind(list[position].name,list[position].price,list[position].image,list[position].id)
    }
}