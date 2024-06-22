package delta.medic.mobile.ui.dashboard

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import delta.medic.mobile.fragment_controlCitas
import delta.medic.mobile.fragment_control_tratamientos

class FragmentPageAdapterRecordatorios(fa: DashboardFragment): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> fragment_control_tratamientos()
            1 -> fragment_controlCitas()
            else -> fragment_control_tratamientos()
        }
    }

}