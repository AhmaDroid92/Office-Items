package entities

import com.google.gson.annotations.SerializedName

data class PaginationWrapper(
    @SerializedName(value = "key")
    val pageNumber: Int? = null
)
