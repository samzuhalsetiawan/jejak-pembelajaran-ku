package com.example.plapp

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.plapp.databinding.FragmentDetailBinding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.palette.graphics.Palette
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var programmingLanguage: ProgrammingLanguage
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val navHostFragment by lazy { activity?.supportFragmentManager?.findFragmentById(R.id.fcvMainNavigation) as? NavHostFragment }
    private val navController by lazy { navHostFragment?.navController }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportActionBar?.hide()
        }
        binding =
            FragmentDetailBinding.bind(inflater.inflate(R.layout.fragment_detail, container, false))
        programmingLanguage = args.programmingLanguage
        binding.toolbar.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        appBarConfiguration = AppBarConfiguration.Builder(R.id.homeFragment).build()
        navController?.let {
            NavigationUI.setupWithNavController(
                binding.toolbar,
                it,
                appBarConfiguration
            )
        }
        binding.toolbar.setNavigationOnClickListener { navController?.navigateUp() }
        binding.toolbar.title = programmingLanguage.name
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            Glide
                .with(this@DetailFragment)
                .load(programmingLanguage.logo)
                .centerInside()
                .into(ivProgrammingLanguageLogo)
            tvProgrammingLanguageDescription.text = programmingLanguage.fullDescription
            tvCreatedAt.text = programmingLanguage.createdAt
            tvInventor.text = programmingLanguage.inventor
            tvExtFileName.text = programmingLanguage.fileExtension
        }
        lifecycleScope.launch(Dispatchers.Default) {
            val bitmap = BitmapFactory.decodeResource(resources, programmingLanguage.logo)
            val palette = Palette.from(bitmap).maximumColorCount(8).generate()
            val vibrant = palette.vibrantSwatch
            val rgb = vibrant?.rgb
            val titleTextColor = vibrant?.titleTextColor
            withContext(Dispatchers.Main) {
                rgb?.let {
                    binding.collapsingToolbar.setBackgroundColor(it)
                    binding.collapsingToolbar.setContentScrimColor(it)
                    binding.collapsingToolbar.setStatusBarScrimColor(it)
                    binding.coordinatorLayout.setBackgroundColor(it)
                }
                titleTextColor?.let { binding.toolbar.setTitleTextColor(it) }
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_about -> {
                navController?.navigate(DetailFragmentDirections.actionDetailFragmentToAboutFragment())
                true
            }
            else -> false
        }
    }
}