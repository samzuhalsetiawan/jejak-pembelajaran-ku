package com.example.plapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var listProgrammingLanguage: List<ProgrammingLanguage>

    private val navHostFragment by lazy { activity?.supportFragmentManager?.findFragmentById(R.id.fcvMainNavigation) as? NavHostFragment }
    private val navController by lazy { navHostFragment?.navController }
    private val menuHost: MenuHost by lazy { requireActivity() }
    private val rvProgrammingLanguageAdapter: RVProgrammingLanguageAdapter by lazy {
        RVProgrammingLanguageAdapter { _: View, programingLanguage: ProgrammingLanguage ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(programingLanguage)
            navController?.navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar?.show()
        }
        binding =
            FragmentHomeBinding.bind(inflater.inflate(R.layout.fragment_home, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        listProgrammingLanguage = ProgrammingLanguage.asListFromResArray(this.requireContext())
            .also { rvProgrammingLanguageAdapter.listProgrammingLanguage = it }

        binding.rvProgrammingLanguageList.apply {
            adapter = rvProgrammingLanguageAdapter
            layoutManager = LinearLayoutManager(this@HomeFragment.requireContext())
        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_about -> {
                navController?.navigate(HomeFragmentDirections.actionHomeFragmentToAboutFragment())
                true
            }
            else -> false
        }
    }
}