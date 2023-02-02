package app.picturesque.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoItem(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val url: String,
    val title: String
)
