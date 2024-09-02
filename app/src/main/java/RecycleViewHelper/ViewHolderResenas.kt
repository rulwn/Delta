package RecycleViewHelper

import Modelo.dataClassResena
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import delta.medic.mobile.R

class ViewHolderResenas(view: View) : RecyclerView.ViewHolder(view) {

    val imgProfile : ImageView = view.findViewById(R.id.imgProfileReview)
    val txtUserName : TextView = view.findViewById(R.id.txtUserReview)
    val ratingBar : RatingBar = view.findViewById(R.id.ratingBar)
    val txtReview : TextView = view.findViewById(R.id.txtReview)
    val btnBorrar : ImageView = view.findViewById(R.id.btnBorrar)

    fun render(Datos: dataClassResena){
        Glide.with(imgProfile.context).
        load(Datos.imgUsuario).
        into(imgProfile)
    }
}