package com.example.imagefreezer

import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileFilter
import java.util.*


class chooseDirectory : AppCompatActivity() {


    private lateinit var mAdapter: MyItemRecyclerViewAdapter
    private lateinit var mRecyclerView: RecyclerView

    private val fileList = LinkedList<String>()


    public var mPathList: LinkedList<String> = LinkedList<String>()
    private val path = Environment.getExternalStorageDirectory().absolutePath + "/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_directory)

//        mPathList=listAllPaths(path)

        getPaths(path)


        mRecyclerView=findViewById(R.id.listRecycle)
        mAdapter=MyItemRecyclerViewAdapter(this,fileList)
        mRecyclerView.adapter=mAdapter
        mRecyclerView.layoutManager= LinearLayoutManager(this)



    }

    private fun getPaths(directoryPath: String){


        // List all the items within the folder.
        val files: Array<File> = File(directoryPath).listFiles(DirFileFilter())
        for (file in files) {

            // Add the directories containing images or sub-directories
            if (file.isDirectory
                && file.listFiles(DirFileFilter()).isNotEmpty()
            ) {
                fileList.add(file.absolutePath)
            } else {
//                val image: Bitmap = ImageHelper.decodeSampledBitmapfromPath(
//                    file.absolutePath,
//                    50,
//                    50
//                )
                fileList.add(file.absolutePath)
            }
        }

    }


    private class DirFileFilter : FileFilter {
        override fun accept(file: File): Boolean {
            if (file.isDirectory) {
                if(file.listFiles(DirFileFilter()).isNotEmpty())
                    return true

            } else if (isImageFile(file.absolutePath)) {
                return true
            }
            return false
        }


        private fun isImageFile(filePath: String): Boolean {
            return filePath.endsWith(".jpg") || filePath.endsWith(".png")
        }
    }




//    private fun listAllPaths(path: String): LinkedList<String> {
//
//        var allPath = LinkedList<String>()
//        val f: File = File(path)
//        val files: Array<File> = f.listFiles()
//        for (inFile in files) {
//            if (inFile.isDirectory) {
//                allPath.add(inFile.toString())
//            }
//        }
//
//        return allPath
//    }



}