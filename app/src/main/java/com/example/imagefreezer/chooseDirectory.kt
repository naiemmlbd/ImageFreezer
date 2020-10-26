package com.example.imagefreezer

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileFilter
import java.util.*

const val EXTRA_MESSAGE = "com.example.imagefreezer.PATH"


class chooseDirectory : AppCompatActivity() {


    private lateinit var mAdapter: MyItemRecyclerViewAdapter
    private lateinit var mRecyclerView: RecyclerView

    private var fileList = LinkedList<String>()

    private var stackOfDirectory: Stack<LinkedList<String>> = Stack()

    public var mPathList: LinkedList<String> = LinkedList<String>()
    private val path = Environment.getExternalStorageDirectory().absolutePath + "/"
//      private val path = Environment.ge

//    var root: String = this.getExternalFilesDir(EN)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_directory)

//        mPathList=listAllPaths(path)




        if(isStoragePermissionGranted()) {

            initAdapterWithRootDirectoryData()


        }
        //checkPersmissionStorage()
    }



    private fun initAdapterWithRootDirectoryData() {
        getPaths(path)


        fileList = stackOfDirectory.peek()

        Log.d("File List: ", fileList.toString())
        mRecyclerView = findViewById(R.id.listRecycle)
        mAdapter = MyItemRecyclerViewAdapter(
            this,
            fileList,
            object : MyItemRecyclerViewAdapter.ItemClickListener {
                override fun onItemClick(path: String) {
                    var flag = true
                    val file = File(path)
                    val files: Array<File> = File(path).listFiles()
                    for (file in files) {
                        if ((file.isDirectory) == false) {
                            flag = false
                            break
                        } else flag = true
                    }

                    if (file.isDirectory && flag) {
                        getPaths(path)

                        var fileListLocal = stackOfDirectory.peek()
                        mAdapter.pathList = fileListLocal
                        mAdapter.notifyDataSetChanged()

                    } else {

                        val intent: Intent =
                            Intent(this@chooseDirectory, MainActivity::class.java)
                        intent.putExtra(EXTRA_MESSAGE, path)
                        startActivity(intent)


                    }

                }

            })
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
    }


    fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Log.v("", "Permission is granted")
                true
            } else {
                Log.v("", "Permission is revoked")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted")
            true
        }
    }


//    private fun checkPersmissionStorage() {
//        if (ContextCompat.checkSelfPermission(this,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE) !==
//            PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                ActivityCompat.requestPermissions(this,
//                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
//            } else {
//                ActivityCompat.requestPermissions(this,
//                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
//            }
//        }
//
//    }


    private fun getPaths(directoryPath: String){


        var fileListLocal = LinkedList<String>()

        // List all the items within the folder.
        val root = File(directoryPath)
        val files: Array<File> = root.listFiles()
        for (file in files) {

            // Add the directories containing images or sub-directories
            if (file.isDirectory
                && file.listFiles(DirFileFilter()).isNotEmpty()
            ) {
                fileListLocal.add(file.absolutePath)
            } else {
//                val image: Bitmap = ImageHelper.decodeSampledBitmapfromPath(
//                    file.absolutePath,
//                    50,
//                    50
//                )
                fileListLocal.add(file.absolutePath)
            }
        }
        stackOfDirectory.push(fileListLocal)

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(
                "Req",
                "Permission: " + permissions[0] + "was " + grantResults[0]
            )
            //resume tasks needing this permission
            initAdapterWithRootDirectoryData()
        }
    }





}



