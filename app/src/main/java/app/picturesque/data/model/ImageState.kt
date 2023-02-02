package app.picturesque.data.model

data class ImageState(
    val image: PhotoItem? = null,
    val images: List<PhotoItem>? = null,
    val error: String? = null,
    val loading: Boolean = false,
    val metadata: PhotoMetadata? = null
)