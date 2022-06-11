package repos

import entities.Result
import network.ItemsService
import network.Resource
import javax.inject.Inject

class ItemsRepo @Inject constructor(
    private val itemsService: ItemsService
) {

    suspend fun getItems(): Resource<ArrayList<Result>> {
        val response = itemsService.getItemsWithPage()

        return if (response.isSuccessful) {
            Resource.Success(response.body()?.results)
        } else {
            Resource.Error(response.raw().code, response.message())
        }
    }
}
