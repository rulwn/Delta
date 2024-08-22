package RecycleViewHelper

import Modelo.dataClassCentro
import Modelo.dataClassResena
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import delta.medic.mobile.R

class ViewHolderResenas(view: View) : RecyclerView.ViewHolder(view) {

    val imgProfile : ImageView = view.findViewById(R.id.imgProfile)
    val txtUserName : TextView = view.findViewById(R.id.txtUserName)
    val ratingBar : RatingBar = view.findViewById(R.id.ratingBar)
    val edtReview : TextView = view.findViewById(R.id.edtReview)

    fun render(Datos: dataClassResena){
        Glide.with(imgProfile.context).load(Datos.imgUsuario).into(imgProfile)
    }
}