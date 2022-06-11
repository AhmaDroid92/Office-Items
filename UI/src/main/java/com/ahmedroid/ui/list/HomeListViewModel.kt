package com.ahmedroid.ui.list

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import entities.Result
import network.Resource
import repos.ItemsRepo
import utils.NetworkHelper
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel @Inject constructor(
    private val itemsRepo: ItemsRepo,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val mutableItemsList = MutableLiveData<ArrayList<Result>>()

    val itemsList: LiveData<ArrayList<Result>>
        get() = mutableItemsList

    fun loadItems() = liveData {

        if (mutableItemsList.value?.size ?: 0 == 0) {

            emit(Resource.Loading(show = true))

            emit(fetchItems())

            emit(Resource.Loading(show = false))
        }
    }

    fun refreshItems() = liveData {
        emit(Resource.Loading(show = true))

        removeCurrentItems()

        emit(fetchItems())

        emit(Resource.Loading(show = false))
    }

    private suspend fun fetchItems(): Resource<ArrayList<Result>> {
        return if (networkHelper.isNetworkConnected()) {
            val itemsResult = itemsRepo.getItems()
            if (itemsResult is Resource.Success<ArrayList<Result>>) {
                if (mutableItemsList.value == null) {
                    mutableItemsList.value = arrayListOf()
                }
                mutableItemsList.value?.addAll((itemsResult as Resource.Success).data?.toList() ?: listOf())
            }
            itemsResult
        } else {
            Resource.Error(0, "No Internet Connection")
        }
    }

    private fun removeCurrentItems() {
        mutableItemsList.value?.clear()
    }

    fun getItemAt(pos: Int): Result? = itemsList.value?.get(pos)

    fun itemsCount(): Int = itemsList.value?.size ?: 0

}
