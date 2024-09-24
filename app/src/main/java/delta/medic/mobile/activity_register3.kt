package delta.medic.mobile

import Modelo.EmailSender
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
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
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.android.material.imageview.ShapeableImageView
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
    private lateinit var imgFotoAgregada: ShapeableImageView
    lateinit var miPath: String
    val codigo_opcion_galeria = 102
    val STORAGE_REQUEST_CODE = 1
    val uuid = UUID.randomUUID().toString()
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register3)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSiguiente3 = findViewById<Button>(R.id.btnSiguiente3)
        val textoGrande = findViewById<TextView>(R.id.txtTextoGrande)
        val btnFlechaAtras = findViewById<ImageView>(R.id.imgFlechaAtrasRegistro2)



// qn sabe 5 (comentario agregado para poder hacer push)
        imgFotoAgregada = findViewById(R.id.imgFotoAgregada)
        imgFotoAgregada.visibility = View.GONE

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
        btnFlechaAtras.setOnClickListener {
            val intent = Intent(this, activity_register2::class.java)
            startActivity(intent)
        }

/*
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                btnSiguiente3.setBackgroundColor(ContextCompat.getColor(this, R.color.Azul1))
                txtTienesUnaCuenta.setTextColor(ContextCompat.getColor(this, R.color.black))
                textoGrande.setTextColor(ContextCompat.getColor(this, R.color.black))
                btnOmitir.setTextColor(ContextCompat.getColor(this, R.color.black))
            } // Night mode is not active, we're using the light theme.
            Configuration.UI_MODE_NIGHT_YES -> {
                btnSiguiente3.setBackgroundColor(ContextCompat.getColor(this, R.color.Turquesa2))
                txtTienesUnaCuenta.setTextColor(ContextCompat.getColor(this, R.color.white))
                textoGrande.setTextColor(ContextCompat.getColor(this, R.color.white))
                btnOmitir.setTextColor(ContextCompat.getColor(this, R.color.white))
            } // Night mode is active, we're using dark theme.
        }

 */

        btnOmitir.setOnClickListener {
            activity_register1.imgUsuario = "no hay imagen"


            CoroutineScope(Dispatchers.Main).launch {
                println("Correo enviado ${activity_register1.email}")
                EmailSender().enviarCorreo(activity_register1.email, "Codigo de verificacion delta", "${activity_register1.codigoautenticacion}")
            }
            val intent = Intent(this, activity_register4::class.java)
            startActivity(intent)
        }

        imgFotoAgregada.setOnClickListener {
            checkStoragePermission()
        }

    }

    private fun checkStoragePermission(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){
            pedirPermisoAlmacenamiento()
        } else {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,codigo_opcion_galeria)
        }
    }

    private fun pedirPermisoAlmacenamiento(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_MEDIA_IMAGES)){
            // Aquí podrías mostrar una explicación al usuario
            Toast.makeText(this, "Necesitamos acceso a la galería para seleccionar una imagen", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),STORAGE_REQUEST_CODE)
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
                    Toast.makeText(this , "No se pudo acceder a la galería", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                codigo_opcion_galeria -> {
                    val imageUri: Uri? = data?.data
                    imageUri?.let {
                        val imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                        subirImagenFirebase(imageBitmap) { url ->
                            miPath = url
                            Glide.with(this)
                                .load(it)
                                .transform(CircleCrop()) // Aplica la transformación de redondeo aquí
                                .into(imgFotoAgregada)
                            imgFotoAgregada.visibility = View.VISIBLE
                            activity_register1.imgUsuario = miPath
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
}