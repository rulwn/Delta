package delta.medic.mobile.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import delta.medic.mobile.R
import delta.medic.mobile.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        tabLayout = root.findViewById(R.id.tab_layout_recordatorios)
        viewPager2 = root.findViewById(R.id.viewPagerRecordatorios)
        viewPager2.adapter = FragmentPageAdapterRecordatorios(this)
        TabLayoutMediator(tabLayout, viewPager2){ tab, position ->
            when(position){
                0 -> tab.text = "Tratamientos"
                1 -> tab.text = "Citas"
            }
        }.attach()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}