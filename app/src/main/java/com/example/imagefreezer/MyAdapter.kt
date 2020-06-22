package com.example.imagefreezer

import android.content.Context
import android.graphics.Bitmap
import android.service.voice.VoiceInteractionService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class MyAdapter: RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    private lateinit var gallerylist:ArrayList<Cell>
    private lateinit var context:Context


    constructor(_context: Context, _gallerylist:ArrayList<Cell> ){

        context=_context
        gallerylist=_gallerylist
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {

        var view:View=LayoutInflater.from(parent.context).inflate(R.layout.cell,parent,false)
        return MyAdapter.ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP)

        setImageFromPath(gallerylist.get(position).,holder.img)
    }

   class ViewHolder: RecyclerView.ViewHolder {

          var img:ImageView

        constructor(view:View) : super(view) {

            img=view.findViewById(R.id.img)
        }


    }

    override fun getItemCount(): Int {
        return gallerylist.size
    }

    private fun setImageFromPath(path:String,image:ImageView){

        val imgFile=File(path)
        if (imgFile.exists()){
            var myBitmap: Bitmap= ImageHelper.decodeSampledBitmapfromPath(imgFile.absolutePath,200,200)

            image.setImageBitmap(myBitmap)
        }
    }


}