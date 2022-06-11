package com.ahmedroid.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedroid.ui.R
import com.ahmedroid.ui.databinding.FragmentHomeListBinding
import com.google.android.material.snackbar.Snackbar
import com.paginate.Paginate
import dagger.hilt.android.AndroidEntryPoint
import entities.Result
import network.Resource

@AndroidEntryPoint
class HomeListFragment : Fragment(), Paginate.Callbacks {

    private var _binding: FragmentHomeListBinding? = null

    private val binding get() = _binding!!

    private val homeListViewModel: HomeListViewModel
            by hiltNavGraphViewModels(R.id.main_navigation_graph_xml)

    private var itemsAdapter: ItemsAdapter? = null

    private var isLoading: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeListBinding.inflate(inflater, container, false)
        val view = binding.root

        initViews()

        homeListViewModel.loadItems().observe(viewLifecycleOwner, ::handleItemsResource)

        return view
    }

    private fun initViews() {
        val linearLayoutManager = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically(): Boolean {
                return !binding.itemsRefreshLayout.isRefreshing
            }
        }

        binding.itemsRecyclerView.layoutManager = linearLayoutManager
        itemsAdapter = ItemsAdapter(homeListViewModel) { view, int ->
            if (!binding.itemsRefreshLayout.isRefreshing) {
                findNavController().navigate(R.id.action_homeListFragment_to_detailsFragment, bundleOf("pos" to int))
            }
        }

        binding.itemsRecyclerView.adapter = itemsAdapter

        Paginate.with(binding.itemsRecyclerView, this)
            .setLoadingTriggerThreshold(2)
            .addLoadingListItem(false)
            .build()

        binding.itemsRefreshLayout.setOnRefreshListener {
            homeListViewModel.refreshItems().observe(this@HomeListFragment.viewLifecycleOwner, ::handleItemsResource)
        }
    }

    private fun handleItemsResource(resource: Resource<ArrayList<Result>>) {
        when (resource) {
            is Resource.Loading -> {
                isLoading = resource.show
                showLoading(resource.show)
            }
            is Resource.Success<ArrayList<Result>> -> {
                showItems()
            }
            is Resource.Error -> {
                showError(resource.message)
            }
        }
    }

    private fun showLoading(isShowLoading: Boolean) {
        binding.itemsRefreshLayout.isRefreshing = isShowLoading
    }

    private fun showItems() {
        itemsAdapter?.notifyDataSetChanged()
    }

    private fun showError(message: String) {
        Snackbar.make(binding.itemsRefreshLayout, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onLoadMore() {
        // empty stub because the pagination is not used by the API
    }

    override fun isLoading(): Boolean = isLoading

    override fun hasLoadedAllItems(): Boolean = true

}
