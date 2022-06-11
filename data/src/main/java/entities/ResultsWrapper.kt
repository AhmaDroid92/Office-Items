package entities

import com.google.gson.annotations.SerializedName

data class ResultsWrapper(
    @SerializedName(value = "results")
    val results: ArrayList<Result>? = null,

    @SerializedName(value = "pagination")
    val paginationWrapper : PaginationWrapper? = null
)
