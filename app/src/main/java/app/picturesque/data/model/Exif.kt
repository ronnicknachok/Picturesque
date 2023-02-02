package app.picturesque.data.model

data class Exif(
    val clean: Clean,
    val label: String,
    val raw: Raw,
    val tag: String,
    val tagspace: String,
    val tagspaceid: Int
)