package com.ahmedroid.ui.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.NavigationUI
import com.ahmedroid.ui.R
import com.ahmedroid.ui.databinding.FragmentDetailsBinding
import com.ahmedroid.ui.list.HomeListViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null

    private val binding get() = _binding!!

    private val detailsViewModel: HomeListViewModel by navGraphViewModels(R.id.main_navigation_graph_xml)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews() {
        val item = detailsViewModel.getItemAt(arguments?.getInt("pos") ?: 0)

        binding.toolbar.title = item?.getNameWithPrice()

        Glide.with(requireContext())
            .load(item?.imageUrls?.get(0) ?: Uri.EMPTY)
            .placeholder(R.drawable.loading)
            .into(binding.headerImageView)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigateUp()
        }

        NavigationUI.setupWithNavController(binding.toolbar, findNavController())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
