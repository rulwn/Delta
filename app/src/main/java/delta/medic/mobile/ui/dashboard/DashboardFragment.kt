package delta.medic.mobile.ui.dashboard

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.ContextCompat
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
    private lateinit var txtControl: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        tabLayout = root.findViewById(R.id.tab_layout_recordatorios)
        viewPager2 = root.findViewById(R.id.viewPagerRecordatorios)
        txtControl = root.findViewById(R.id.lbControl)
        txtControl.setText(Html.fromHtml(getResources().getString(R.string.lbControl)))
        viewPager2.adapter = FragmentPageAdapterRecordatorios(childFragmentManager, lifecycle)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null){
                    viewPager2.currentItem = tab.position
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
        /*TabLayoutMediator(tabLayout, viewPager2){ tab, position ->
            when(position){
                0 -> tab.text = "Tratamientos"
                1 -> tab.text = "Citas"
            }
        }.attach()*/
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val position = it.position
                    when (position) {
                        0 -> {
                            tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.Azul1))
                            txtControl.setTextColor(ContextCompat.getColor(requireContext(), R.color.Azul1))
                        }
                        1 -> {
                            tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.Turquesa1))
                            txtControl.setTextColor(ContextCompat.getColor(requireContext(), R.color.Turquesa1))
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}