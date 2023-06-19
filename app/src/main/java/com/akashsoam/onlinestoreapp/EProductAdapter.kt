package com.akashsoam.onlinestoreapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class EProductAdapter(var context: Context, var arrayList: ArrayList<EProduct>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val productView: View =
            LayoutInflater.from(context).inflate(R.layout.e_product_row, parent, false)
        return ProductViewHolder(productView)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductViewHolder).initializeRowUIComponents(
            arrayList[position].id,
            arrayList[position].name,
            arrayList[position].price,
            arrayList[position].pictureName
        )
    }

    inner class ProductViewHolder(myView: View) : RecyclerView.ViewHolder(myView) {
        var imageProduct: ImageView = myView.findViewById(R.id.imgProduct)
        var txtId: TextView = myView.findViewById(R.id.txtId)
        var txtName: TextView = myView.findViewById(R.id.txtName)
        var txtPrice: TextView = myView.findViewById(R.id.txtPrice)

        fun initializeRowUIComponents(id: Int, name: String, price: Int, picName: String) {
            txtId.text = id.toString()
            txtName.text = name
            txtPrice.text = price.toString()
            var picUrl = "http://192.168.42.198/OnlineStoreApp/osimages/osimages/"
//            picUrl = picName.replace(" ", "%20")

            Picasso.get().load(picUrl + picName).into(imageProduct)

        }

    }
}