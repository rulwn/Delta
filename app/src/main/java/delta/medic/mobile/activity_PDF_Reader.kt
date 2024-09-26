package delta.medic.mobile

import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class activity_PDF_Reader : AppCompatActivity() {

    private var pdfRenderer: PdfRenderer? = null
    private var parcelFileDescriptor: ParcelFileDescriptor? = null
    private var currentPage: PdfRenderer.Page? = null
    private lateinit var imageViewPdf: ImageView

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_reader)

        // Ocultar la Action Bar
        supportActionBar?.hide()
        val btnRegresar = findViewById<ImageView>(R.id.btnRegresar6)
        btnRegresar.setOnClickListener {
            finish()
        }

        imageViewPdf = findViewById(R.id.imageViewPdf)

        //val PDF = intent.getStringExtra("pdfReceta")!!
        // URL del PDF
        val PDF = "https://www.orimi.com/pdf-test.pdf"
        val pdfUrl = PDF

        // Extraer el nombre del archivo desde la URL
        val fileName = pdfUrl.substring(pdfUrl.lastIndexOf("/") + 1)

        // Verificar si ya existe un archivo con ese nombre
        val file = File(getExternalFilesDir(null), fileName)

        if (file.exists()) {
            abrirPDF(file) // Si ya existe, lo abrimos
        } else {
            descargarPDF(pdfUrl, fileName) // Si no existe, lo descargamos
        }
    }

    private fun descargarPDF(pdfUrl: String, fileName: String) {
        val request = DownloadManager.Request(Uri.parse(pdfUrl))
            .setTitle("Descargando PDF")
            .setDescription("Descargando archivo PDF...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationInExternalFilesDir(this, null, fileName)

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

        Toast.makeText(this, "Descargando PDF...", Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun abrirPDF(file: File) {
        try {
            parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            pdfRenderer = PdfRenderer(parcelFileDescriptor!!)

            // Mostrar la primera página del PDF
            mostrarPagina(0)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al abrir el PDF", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun mostrarPagina(index: Int) {
        if (pdfRenderer!!.pageCount <= index) return

        // Cerrar la página actual si está abierta
        currentPage?.close()

        // Abrir la nueva página
        currentPage = pdfRenderer!!.openPage(index)

        // Crear un Bitmap para renderizar la página
        val bitmap = Bitmap.createBitmap(
            currentPage!!.width, currentPage!!.height,
            Bitmap.Config.ARGB_8888
        )

        // Renderizar la página en el Bitmap
        currentPage!!.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

        // Mostrar el Bitmap en un ImageView
        imageViewPdf.setImageBitmap(bitmap)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDestroy() {
        super.onDestroy()
        currentPage?.close()
        pdfRenderer?.close()
        parcelFileDescriptor?.close()
    }
}