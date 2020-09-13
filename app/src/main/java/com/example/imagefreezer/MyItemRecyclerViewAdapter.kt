package com.example.imagefreezer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.imagefreezer.dummy.DummyContent.DummyItem
import java.util.*


const val EXTRA_MESSAGE ="com.example.imagefreezer.PATH"

class MyItemRecyclerViewAdapter(val context: Context,
    private val values: LinkedList<String>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {


    companion object {

    }


    //    private lateinit var mInflater: LayoutInflater
    val mPathList: LinkedList<String>

    init {
//        mInflater= LayoutInflater.from(context)
        mPathList=values

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_choose_directory, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.idView.text = mPathList.get(position)


    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val idView: TextView = view.findViewById(R.id.item_Path)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            var mPosition = layoutPosition

            var element = mPathList.get(mPosition)


            val intent: Intent = Intent(context,MainActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE,element)
            }
//            intent.putExtra(EXTRA_MESSAGE,element)
            context.startActivity(intent)


        }

    }


}

