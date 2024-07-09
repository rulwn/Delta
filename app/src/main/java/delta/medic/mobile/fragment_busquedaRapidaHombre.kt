package delta.medic.mobile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import delta.medic.mobile.databinding.FragmentBusquedaRapidaHombreBinding
import delta.medic.mobile.ui.fragment_bottonSheetPierna




private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class fragment_busquedaRapidaHombre : Fragment() {


    private var _binding: FragmentBusquedaRapidaHombreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBusquedaRapidaHombreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegresar.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.openBottomSheetButton.setOnClickListener {
            val bottomSheetFragment = fragment_bottonSheetPierna.newInstance("param1", "param2")
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}