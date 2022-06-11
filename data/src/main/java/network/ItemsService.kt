package network

import entities.ResultsWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ItemsService {
    @GET("default/dynamodb-writer")
    suspend fun getItemsWithPage(
        @Query("page") pageNumber: Int = 0,
    ): Response<ResultsWrapper>
}
