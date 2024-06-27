package delta.medic.mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class fragment_Resultados : Fragment() {

    private lateinit var btnRegresar: ImageView
    private lateinit var rcvResultados: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment__resultados, container, false)

        btnRegresar = root.findViewById(R.id.btnRegresar)
        rcvResultados = root.findViewById(R.id.rcvResultados)

        btnRegresar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        rcvResultados.layoutManager = LinearLayoutManager(context)


        arguments?.getString("query")?.let {

        }

        return root
    }

}