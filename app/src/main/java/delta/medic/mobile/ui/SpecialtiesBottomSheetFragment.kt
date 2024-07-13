package delta.medic.mobile.ui
import RecycleViewHelper.AdaptadorEspecialidades
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import delta.medic.mobile.R
import delta.medic.mobile.activity_busqueda


class SpecialtiesBottomSheetFragment : BottomSheetDialogFragment() {

    private var specialties: List<String> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_especialiadidadessheet, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.specialtiesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = AdaptadorEspecialidades(specialties) { specialty ->
            onSpecialtyClicked(specialty)
        }
        return view
    }

    private fun onSpecialtyClicked(specialty: String) {
        val intent = Intent(context, activity_busqueda::class.java).apply {
            putExtra("query", specialty)
        }
        startActivity(intent)
        dismiss()
    }

    companion object {
        @JvmStatic
        fun newInstance(specialties: List<String>) =
            SpecialtiesBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList("specialties", ArrayList(specialties))
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            specialties = it.getStringArrayList("specialties") ?: listOf()
        }
    }
    
}
