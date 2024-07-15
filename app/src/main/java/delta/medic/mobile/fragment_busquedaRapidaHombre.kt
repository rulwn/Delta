package delta.medic.mobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import delta.medic.mobile.databinding.FragmentBusquedaRapidaHombreBinding
import delta.medic.mobile.ui.SpecialtiesBottomSheetFragment
import androidx.navigation.fragment.findNavController


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

        binding.btnChangeGender.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_busquedaRapidaHombre_to_fragment_busquedaRapidaMujer)
        }

        binding.bodyImageView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val x = event.x
                val y = event.y
                handleBodyPartClick(x, y)
            }
            true
        }
    }

    private fun handleBodyPartClick(x: Float, y: Float) {
        val specialties = when {
            isChestArea(x, y) -> listOf("Cardiología", "Torax")
            isHeadArea(x, y) -> listOf("Neurología", "Otorrinolaringología")

            else -> emptyList()
        }
        if (specialties.isNotEmpty()) {
            val bottomSheet = SpecialtiesBottomSheetFragment.newInstance(specialties)
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }
    }

    private fun isChestArea(x: Float, y: Float): Boolean {
      return x in 550f..750f && y in 600f..800f
    }

    private fun isHeadArea(x: Float, y: Float): Boolean {
     return x in 550f..750f && y in 100f..300f
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}