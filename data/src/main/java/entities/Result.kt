package entities

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName(value = "uid")
    val id: String? = null,

    @SerializedName(value = "name")
    val name: String? = null,

    @SerializedName(value = "price")
    val price: String? = null,

    @SerializedName(value = "created_at")
    val creationDate: String? = null,

    @SerializedName(value = "image_ids")
    val imageIds: ArrayList<String>? = null,

    @SerializedName(value = "image_urls")
    val imageUrls: ArrayList<String>? = null,

    @SerializedName(value = "image_urls_thumbnails")
    val thumbnailUrls: ArrayList<String>? = null,
) {
    fun getNameWithPrice() : String = "${this.name} - ${this.price}"
}
