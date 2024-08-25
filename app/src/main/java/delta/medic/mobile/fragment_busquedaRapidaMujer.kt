package delta.medic.mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import delta.medic.mobile.databinding.FragmentBusquedaRapidaMujerBinding
import delta.medic.mobile.ui.SpecialtiesBottomSheetFragment


class fragment_busquedaRapidaMujer : Fragment() {
    private var _binding: FragmentBusquedaRapidaMujerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBusquedaRapidaMujerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegresar.setOnClickListener {
            findNavController().navigateUp()
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
        val (specialties, bodyArea) = when {
            isChestArea(x, y) -> listOf("Cardiología", "Torax", "Mastología") to "Pecho"
            isHeadArea(x, y) -> listOf("Neurología", "Otorrinolaringología") to "Cabeza"
            isAbdomenArea(x, y) -> listOf("Gastroenterología", "Ginecología", "Cirugía General") to "Abdomen"
            isHandArea(x, y) -> listOf("Ortopedia", "Cirugía de Mano") to "Manos"
            isLegArea(x, y) -> listOf("Ortopedia", "Traumatología", "Flebología") to "Piernas"
            else -> emptyList<String>() to ""
        }

        if (specialties.isNotEmpty()) {
            val bottomSheet = SpecialtiesBottomSheetFragment.newInstance(specialties, bodyArea)
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }
    }
    private fun isChestArea(x: Float, y: Float): Boolean {
        return x in 450f..850f && y in 400f..900f
    }

    private fun isHeadArea(x: Float, y: Float): Boolean {
        return x in 350f..850f && y in 50f..350f
    }

    private fun isAbdomenArea(x: Float, y: Float): Boolean {
         return x in 450f..850f && y in 900f..1200f
    }

    private fun isHandArea(x: Float, y: Float): Boolean {
      return (x in 100f..350f && y in 900f..1200f) || (x in 850f..1100f && y in 900f..1200f)
    }

    private fun isLegArea(x: Float, y: Float): Boolean {
       return x in 450f..850f && y in 1200f..1600f
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}