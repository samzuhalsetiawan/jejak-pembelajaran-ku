package com.example.latihannavcomp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.latihannavcomp.databinding.FragmentViewPagerBinding
import com.example.latihannavcomp.onboarding.FirstScreenBoarding
import com.example.latihannavcomp.onboarding.SecondScreenBoarding
import com.example.latihannavcomp.onboarding.ThirdScreenBoarding

class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding
    private lateinit var adapter: BoardingScreenViewPagerAdapter
    private lateinit var nextButtonListener: View.OnClickListener
    private lateinit var finnishButtonListener: View.OnClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        binding = FragmentViewPagerBinding.bind(view)
        nextButtonListener = View.OnClickListener {
            binding.vp2OnBoarding.currentItem += 1
        }
        finnishButtonListener = View.OnClickListener {
            saveSharedPref(ActivityPalingPertama.SHARED_PREF_NAME, Context.MODE_PRIVATE) { editor ->
                editor.putBoolean(ActivityPalingPertama.FINNISH_ONBOARDING_KEY, true)
            }
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            requireActivity().startActivity(intent)
        }
        val listFragment = listOf<Fragment>(
            FirstScreenBoarding(nextButtonListener),
            SecondScreenBoarding(nextButtonListener),
            ThirdScreenBoarding(finnishButtonListener)
        )
        val fragmentManager = requireActivity().supportFragmentManager
        adapter = BoardingScreenViewPagerAdapter(listFragment, fragmentManager, lifecycle)

        binding.vp2OnBoarding.adapter = adapter
        return binding.root
    }

    private fun saveSharedPref(sharePrefName: String, mode: Int, editor: (SharedPreferences.Editor) -> SharedPreferences.Editor) {
        val sharePrefEditor = requireActivity().getSharedPreferences(sharePrefName, mode).edit()
        editor(sharePrefEditor).apply()
    }
}