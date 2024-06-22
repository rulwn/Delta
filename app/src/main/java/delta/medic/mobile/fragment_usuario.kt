package delta.medic.mobile

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_usuario.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_usuario : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_usuario, container, false)

        /******************************************************************************************
        * Values                                                                                  *
        ******************************************************************************************/
        //Image View
        val imgvSettings = root.findViewById<ImageView>(R.id.imgVSettingsPerfil)
        val imgvFoto = root.findViewById<ImageView>(R.id.imgvFotoPerfil)
        val imgvPersonalizar = root.findViewById<ImageView>(R.id.imgvPersonalizarPerfil)
        val imgvSeguro = root.findViewById<ImageView>(R.id.imgvSeguroPerfil)
        val imgvDoctoresFavoritos = root.findViewById<ImageView>(R.id.imgvDocFav)
        val imgvRecetas = root.findViewById<ImageView>(R.id.imgvRecetas)
        val imgvHistorialCitas = root.findViewById<ImageView>(R.id.imgvHistCitas)
        val imgvMisReseñas = root.findViewById<ImageView>(R.id.imgvMisReseñas)
        //Labels
        val lbNombre = root.findViewById<TextView>(R.id.lbNombrePerfil)
        val lbCorreo = root.findViewById<TextView>(R.id.lbCorreoPerfil)
        val lbPersonalizar = root.findViewById<TextView>(R.id.lbPersonalizarPerfil)
        val lbSeguro = root.findViewById<TextView>(R.id.lbSeguroPerfil)
        val lbPerfil = root.findViewById<TextView>(R.id.lbPerfil)

        lbPerfil.setText(Html.fromHtml(getResources().getString(R.string.lbPerfilSub)))

        /******************************************************************************************
        * On Clicks                                                                              *
        ******************************************************************************************/
        imgvSettings.setOnClickListener {
            val activitySettings = Intent(requireContext(), activity_configuracion::class.java)
            startActivity(activitySettings)
        }
        imgvPersonalizar.setOnClickListener{
            val activityEditarPerfil = Intent(requireContext(), activity_editarperfil::class.java)
            startActivity(activityEditarPerfil)
        }
        lbPersonalizar.setOnClickListener{
            val activityEditarPerfil = Intent(requireContext(), activity_editarperfil::class.java)
            startActivity(activityEditarPerfil)
        }
        imgvSeguro.setOnClickListener{
            //No sé hacia donde lleva
        }
        lbSeguro.setOnClickListener {
            //No sé hacia donde lleva
            val activityEditarPerfl = Intent(requireContext(), activity_vistadoctores::class.java)
            startActivity(activityEditarPerfl)
        }
        imgvDoctoresFavoritos.setOnClickListener {
            val activityDoctoresFavoritos = Intent(requireContext(), activity_doctoresfavoritos::class.java)
            startActivity(activityDoctoresFavoritos)
        }
        imgvRecetas.setOnClickListener {
            val activityRecetas = Intent(requireContext(), activity_misrecetas::class.java)
            startActivity(activityRecetas)
        }
        imgvHistorialCitas.setOnClickListener {
            val activityHistorialCitas = Intent(requireContext(), activity_historialdecitas::class.java)
            startActivity(activityHistorialCitas)
        }
        imgvMisReseñas.setOnClickListener {
            //No estan las reseñas
        }


        /******************************************************************************************
         * Funciones                                                                              *
         ******************************************************************************************/

        fun GetUserParameters(): List

        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_usuario.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_usuario().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}