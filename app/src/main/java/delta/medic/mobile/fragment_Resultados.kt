package delta.medic.mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class fragment_Resultados : Fragment() {

    private lateinit var txtResultadoBusqueda: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment__resultados, container, false)
        txtResultadoBusqueda = root.findViewById(R.id.txtResultadoBusqueda)

        arguments?.getString("query")?.let {
            txtResultadoBusqueda.text = it
        }

        return root
    }

}