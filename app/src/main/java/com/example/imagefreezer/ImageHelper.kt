package com.example.imagefreezer

import android.graphics.Bitmap
import android.graphics.BitmapFactory

//to decrease the size of image
class ImageHelper {


    companion object{
        fun calculateInSampleSize(options:BitmapFactory.Options,reqWidth:Int,reqHeight:Int):Int{
            val height:Int=options.outHeight
            val width =options.outWidth
            var inSampleSize =1

            if (height>reqHeight||width>reqWidth){
                val halfHeight:Int=height/2
                val halfWidth:Int=width/2

                while ((halfHeight/inSampleSize) >= reqHeight && (halfWidth/inSampleSize) >= reqWidth){
                    inSampleSize *=2
                }
            }

            return inSampleSize

        }

        fun decodeSampledBitmapfromPath(pathName:String,reqHeight: Int,reqWidth: Int):Bitmap{
            val options:BitmapFactory.Options=  BitmapFactory.Options()
            options.inJustDecodeBounds=true
            BitmapFactory.decodeFile(pathName,options)
            options.inSampleSize= calculateInSampleSize(options,reqWidth,reqHeight)

            options.inJustDecodeBounds=false

            return BitmapFactory.decodeFile(pathName,options)


        }
    }

}