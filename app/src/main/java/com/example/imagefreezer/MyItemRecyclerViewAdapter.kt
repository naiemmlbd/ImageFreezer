package com.example.imagefreezer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*



class MyItemRecyclerViewAdapter(
    val context: Context,
    var pathList: LinkedList<String>, private val listener: ItemClickListener
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {


    companion object {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_choose_directory, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val path = pathList.get(position)
        holder.idView.text = path
        holder.itemView.setOnClickListener{
            listener.onItemClick(path)
        }

    }

    override fun getItemCount(): Int = pathList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val idView: TextView = view.findViewById(R.id.item_Path)

        init {
            //view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

           /// var mPosition = layoutPosition

           // var element = mPathList.get(mPosition)


            //val intent: Intent = Intent(context, MainActivity::class.java).apply {
              //  putExtra(EXTRA_MESSAGE, element)
            //}
//            intent.putExtra(EXTRA_MESSAGE,element)
//            context.startActivity(intent)


        }

    }

    interface ItemClickListener {
        fun onItemClick(path: String)
    }

}


