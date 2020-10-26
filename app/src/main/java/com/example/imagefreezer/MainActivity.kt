package com.example.imagefreezer

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.lang.System.out
import java.nio.file.Path
import java.util.jar.Manifest

@Suppress("DEPRECATION")
lateinit var messagePath: String
class MainActivity : AppCompatActivity() {

      lateinit var allFilesPath: List<Cell>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val message = intent.getStringExtra(EXTRA_MESSAGE)

        messagePath=message

//        val intent: Intent= getIntent()
//        val message: String=intent.getStringExtra(EXTRA_M)





        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){

            requestPermissions(arrayOf( android.Manifest.permission.READ_EXTERNAL_STORAGE),1000);

        }else{
            showImages()

        }

    }

    private fun showImages() {

        var path = messagePath

        allFilesPath=listAllFiles(path)
        Log.d("path", allFilesPath.toString())
        var recycleView: RecyclerView = findViewById(R.id.gallery)
        recycleView.setHasFixedSize(true)

        //list with 3 columns
        var layoutManager:RecyclerView.LayoutManager= GridLayoutManager(applicationContext,3)
        recycleView.layoutManager=layoutManager

        var cells= prepareData()
        var adapter =MyAdapter(applicationContext,cells)
        Log.d("Adapter Size", adapter.toString())
        recycleView.adapter=adapter




    }

    private fun prepareData():ArrayList<Cell>{

        var allImages= ArrayList<Cell>()
        for (c: Cell in allFilesPath){
            val cell = Cell()
            cell.title=c.title
            cell.path=c.path

            allImages.add(cell)


        }
        Log.d("IM", allImages.toString())

        return allImages
    }

    //Load Files from the folder
    private fun listAllFiles(pathName: String):List<Cell>{
        var allFiles= ArrayList<Cell>()
        val file = File(pathName)
        var files =file.listFiles()
        //Log.d("Files", files.toString())
        if (files != null){
            for (f in files){
                val cell = Cell()
                cell.title=f.name
                cell.path=f.absolutePath
                allFiles.add(cell)
            }


        }

        Log.d("all files", allFiles.toString())
        return allFiles

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode==1000){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                showImages()
            }else{
                Toast.makeText(this,"Denied",Toast.LENGTH_SHORT).show()
                finish()
            }

        }

    }
}