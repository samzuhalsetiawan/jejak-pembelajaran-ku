package com.example.githubuser.presentation.ui.followers_and_following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapters.UserListAdapter
import com.example.githubuser.data.models.User
import com.example.githubuser.databinding.FragmentFollowersAndFollowingListBinding
import com.example.githubuser.enums.FollowType
import com.example.githubuser.factories.FollowListViewModelFactory
import com.example.githubuser.interfaces.IUserCardClickEventHandler
import com.example.githubuser.presentation.ui.detail_user.DetailFragmentDirections
import com.example.githubuser.presentation.viewmodel.FollowListViewModel
import com.example.githubuser.presentation.viewmodel.MainViewModel
import com.example.githubuser.sealed_class.ViewModelState

class FollowersAndFollowingListFragment :
    Fragment(R.layout.fragment_followers_and_following_list),
    IUserCardClickEventHandler {

    companion object {
        private const val ARGS_KEY_FOLLOW_TYPE = "ARGS_KEY_FOLLOW_TYPE"
        private const val ARGS_KEY_USERNAME = "ARGS_KEY_USERNAME"

        fun newInstance(followType: FollowType, username: String): FollowersAndFollowingListFragment {
            return FollowersAndFollowingListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGS_KEY_FOLLOW_TYPE, followType.name)
                    putString(ARGS_KEY_USERNAME, username)
                }
            }
        }
    }

    private lateinit var binding: FragmentFollowersAndFollowingListBinding
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var username: String
    private lateinit var followType: FollowType
    private val mainViewModel: MainViewModel by activityViewModels()
    private val followListViewModel: FollowListViewModel by viewModels { FollowListViewModelFactory(username, followType, requireActivity().application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(ARGS_KEY_FOLLOW_TYPE)?.also { followType = FollowType.valueOf(it) }
        arguments?.getString(ARGS_KEY_USERNAME)?.also { username = it }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersAndFollowingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userListAdapter = UserListAdapter(this)
        binding.setupRecyclerView()
        followListViewModel.listOfUser.observe(viewLifecycleOwner) {
            when (it) {
                is ViewModelState.Loading -> showLoading()
                is ViewModelState.Success -> showData(it.data)
                is ViewModelState.Error -> showError(it.displayMessage)
            }
        }

    }

    private fun showLoading() {
        binding.showShimmer()
    }

    private fun showData(data: List<User>) {
        userListAdapter.listOfUser = data
        binding.closeShimmer(null)
    }

    private fun showError(errorMessage: String) = binding.closeShimmer(errorMessage)

    override fun onCardClickListener(card: CardView, user: User) {
        val action = DetailFragmentDirections.actionDetailFragmentToDetailFragment(user)
        requireParentFragment().findNavController().navigate(action)
    }

    override fun onFavoriteIconCheckedListener(favButton: CheckBox, user: User) {
        mainViewModel.changeFavoriteStatusOfUser(user, true)
        Toast.makeText(requireContext(), "${user.login} added to favorite ❤️", Toast.LENGTH_SHORT).show()
    }

    override fun onFavoriteIconUncheckedListener(favButton: CheckBox, user: User) {
        mainViewModel.changeFavoriteStatusOfUser(user, false)
        Toast.makeText(requireContext(), "${user.login} removed from favorite️", Toast.LENGTH_SHORT).show()
    }

    private fun FragmentFollowersAndFollowingListBinding.setupRecyclerView() {
        rvFollowersAndFollowingList.apply {
            adapter = userListAdapter
            layoutManager =
                LinearLayoutManager(this@FollowersAndFollowingListFragment.requireContext())
            setHasFixedSize(true)
        }
    }

    private fun FragmentFollowersAndFollowingListBinding.showShimmer() {
        shimmerFollowsList.visibility = View.VISIBLE
        flErrorPage.visibility = View.GONE
        rvFollowersAndFollowingList.visibility = View.GONE
    }

    private fun FragmentFollowersAndFollowingListBinding.closeShimmer(errorMessage: String?) {
        if (errorMessage == null) {
            flErrorPage.visibility = View.GONE
            rvFollowersAndFollowingList.visibility = View.VISIBLE
        } else {
            rvFollowersAndFollowingList.visibility = View.GONE
            includeErrorPage.tvUserNotFound.text = errorMessage
            flErrorPage.visibility = View.VISIBLE
        }
        shimmerFollowsList.visibility = View.GONE
    }
}