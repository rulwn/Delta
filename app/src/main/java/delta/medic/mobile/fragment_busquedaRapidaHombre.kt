package delta.medic.mobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import delta.medic.mobile.databinding.FragmentBusquedaRapidaHombreBinding
import delta.medic.mobile.ui.SpecialtiesBottomSheetFragment







class fragment_busquedaRapidaHombre : Fragment() {


    private  var _binding: FragmentBusquedaRapidaHombreBinding? = null
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
            // Implementa la lógica de navegación aquí si es necesario
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
            // Agrega más áreas del cuerpo según sea necesario
            else -> emptyList()
        }
        if (specialties.isNotEmpty()) {
            val bottomSheet = SpecialtiesBottomSheetFragment.newInstance(specialties)
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }
    }

    private fun isChestArea(x: Float, y: Float): Boolean {
        // Define las coordenadas específicas para el área del pecho
        return x in 100f..200f && y in 300f..400f
    }

    private fun isHeadArea(x: Float, y: Float): Boolean {
        // Define las coordenadas específicas para el área de la cabeza
        return x in 100f..200f && y in 100f..200f
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}