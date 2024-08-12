package delta.medic.mobile

import Modelo.EmailSender
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.FirebaseException
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.UUID
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.storage.ktx.storage

class activity_register3 : AppCompatActivity() {

    private lateinit var btnAgregarFoto: ImageView
    lateinit var miPath: String
    val codigo_opcion_galeria = 102
    val STORAGE_REQUEST_CODE = 1
    val uuid = UUID.randomUUID().toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register3)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        btnAgregarFoto = findViewById(R.id.btnAgregarFoto)
        val btnOmitir = findViewById<TextView>(R.id.btnOmitirFoto)
        btnAgregarFoto.setOnClickListener {
            checkStoragePermission()
        }
        val txtTienesUnaCuenta = findViewById<TextView>(R.id.txtTienesUnaCuenta3)
        txtTienesUnaCuenta.setOnClickListener {
            val intent = Intent(this, activity_login::class.java)
            startActivity(intent)
        }
        val btnSiguiente = findViewById<Button>(R.id.btnSiguiente3)
        btnSiguiente.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                println("Correo enviado ${activity_register1.email}")
                EmailSender().enviarCorreo(activity_register1.email, "Codigo de verificacion delta", "${activity_register1.codigoautenticacion}")
            }

            val intent = Intent(this, activity_register4::class.java)
            startActivity(intent)


        }
        btnOmitir.setOnClickListener {
            activity_register1.imgUsuario = "no hay imagen"


            CoroutineScope(Dispatchers.Main).launch {
                println("Correo enviado ${activity_register1.email}")
                EmailSender().enviarCorreo(activity_register1.email, "Codigo de verificacion delta", "${activity_register1.codigoautenticacion}")
            }
            val intent = Intent(this, activity_register4::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                codigo_opcion_galeria -> {
                    val imageUri: Uri? = data?.data
                    imageUri?.let {
                        val imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                        subirImagenFirebase(imageBitmap) { url ->
                            miPath = url
                            btnAgregarFoto.setImageURI(it)
                            activity_register1.imgUsuario = uuid
                        }
                    }
                }
            }
        }
    }
    private fun subirImagenFirebase(bitmap: Bitmap, onSucces: (String) -> Unit){
        val storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("images/${uuid}.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)

        uploadTask.addOnFailureListener{
            Toast.makeText(this@activity_register3, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener{ taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                onSucces(uri.toString())
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            STORAGE_REQUEST_CODE -> {
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent,codigo_opcion_galeria)
                } else {
                    Toast.makeText(this , "No se pudo acceder a la galería", Toast.LENGTH_SHORT)
                        .show()
                }
            } else -> {

            }
        }
    }
    private fun checkStoragePermission(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            pedirPermisoAlmacenamiento()
        } else {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,codigo_opcion_galeria)
        }
    }
    private fun pedirPermisoAlmacenamiento(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)){

        }else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),STORAGE_REQUEST_CODE)
        }
    }
}