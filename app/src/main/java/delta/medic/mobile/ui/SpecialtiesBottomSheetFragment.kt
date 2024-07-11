package delta.medic.mobile.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import delta.medic.mobile.R


class SpecialtiesBottomSheetFragment : BottomSheetDialogFragment() {

    private var specialties: List<String> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_especialiadidadessheet, container, false)
        val specialtiesTextView = view.findViewById<TextView>(R.id.specialtiesTextView)
        specialtiesTextView.text = specialties.joinToString("\n")
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(specialties: List<String>): SpecialtiesBottomSheetFragment {
            val fragment = SpecialtiesBottomSheetFragment()
            val args = Bundle()
            args.putStringArrayList("specialties", ArrayList(specialties))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            specialties = it.getStringArrayList("specialties") ?: listOf()
        }
    }
}
