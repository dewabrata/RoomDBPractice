package com.juaracoding.roomdbpractice

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juaracoding.roomdbpractice.adapter.ItemAdapter
import com.juaracoding.roomdbpractice.model.ItemModel
import com.juaracoding.roomdbpractice.viewmodel.ItemViewModel
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var txtNama : EditText
    lateinit var imgPhoto : ImageView
    lateinit var btnCapture : Button
    lateinit var btnSend : Button
    lateinit var fileSave :File
    lateinit var itemViewModel: ItemViewModel
    lateinit var lstItem : RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)




        initComponent()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

    fun initComponent(){
        txtNama = findViewById(R.id.txtNama)
        imgPhoto = findViewById(R.id.imgPhoto)
        btnCapture = findViewById(R.id.btnCamera)
        btnSend = findViewById(R.id.btnSave)

        lstItem = findViewById(R.id.lstData)

        lstItem.layoutManager = LinearLayoutManager(this)
        itemViewModel.allItem.observe(this){
           lstItem.adapter = ItemAdapter(it, {item ->showDialog(item)})
        }



        btnCapture.setOnClickListener{



            val intent = Intent (MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,88)


        }
        btnSend.setOnClickListener{

            val itemModel  = ItemModel(nama = txtNama.text.toString(), foto = fileSave.absolutePath)
            itemViewModel.insert(itemModel)

            Toast.makeText(this,"Data Berhasil disimpan",Toast.LENGTH_LONG).show()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 88 && resultCode == RESULT_OK){
            fileSave = createImageFile(data?.extras?.get("data") as Bitmap)
            Log.d("Url File", fileSave.absolutePath)
            imgPhoto.setImageBitmap(data?.extras?.get("data") as Bitmap)
        }

    }

    fun createImageFile(gambar : Bitmap): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(storageDir,"PHOTO_${timestamp}.jpg")
        val fos = FileOutputStream(file)
        gambar.compress(Bitmap.CompressFormat.PNG,100,fos)
        fos.flush()
        fos.close()



        return file
    }


    fun showDialog(item:ItemModel){
        val dialogShow = LayoutInflater.from(this).inflate(R.layout.dialog,null)
        val txtName = dialogShow.findViewById<EditText>(R.id.edit_text)
        val imgFoto = dialogShow.findViewById<ImageView>(R.id.image_view)

        txtName.setText(item.nama)
        imgFoto.setImageBitmap(BitmapFactory.decodeFile(item.foto))

        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setView(dialogShow)
            .setNeutralButton("Cancel",{dialogInterface,i ->
                dialogInterface.dismiss()
            })

            .setNegativeButton("Delete",{dialogInterface,i ->
                itemViewModel.delete(item.id)
                Toast.makeText(this,"Data Berhasil Dihapus",Toast.LENGTH_LONG).show()
            })


            .create()
        dialog.show()

    }
}