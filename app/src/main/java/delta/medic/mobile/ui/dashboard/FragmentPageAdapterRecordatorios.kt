package delta.medic.mobile.ui.dashboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import delta.medic.mobile.fragment_controlCitas
import delta.medic.mobile.fragment_control_tratamientos

class FragmentPageAdapterRecordatorios(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle)  {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                fragment_control_tratamientos()
            }
            else -> {
                fragment_controlCitas()
            }
        }
    }

}