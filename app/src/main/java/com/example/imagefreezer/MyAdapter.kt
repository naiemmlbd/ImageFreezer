package com.example.imagefreezer

import android.content.Context
import android.graphics.Bitmap
import android.service.voice.VoiceInteractionService
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.io.File


class MyAdapter(var context: Context ,private var _gallerylist: ArrayList<Cell>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

     


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {

        var view:View=LayoutInflater.from(parent.context).inflate(R.layout.cell,parent,false)
        return ViewHolder(view)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP)

        setImageFromPath(_gallerylist.get(position).path ,holder.img)
        holder.img.setOnClickListener{
            Toast.makeText(context,_gallerylist.get(position).title,Toast.LENGTH_SHORT).show()
        }

    }

   class ViewHolder: RecyclerView.ViewHolder {

          var img:ImageView

        constructor(view:View) : super(view) {

            img=view.findViewById(R.id.img)
        }


    }

    override fun getItemCount(): Int {
        Log.d("Adapet", _gallerylist.size.toString())
        return _gallerylist.size

    }

    private fun setImageFromPath(path:String,image:ImageView){

        val imgFile=File(path)
        if (imgFile.exists()){
            var myBitmap: Bitmap= ImageHelper.decodeSampledBitmapfromPath(imgFile.absolutePath,200,200)

            image.setImageBitmap(myBitmap)
        }
    }


}