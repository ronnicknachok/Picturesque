package app.picturesque.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import app.picturesque.data.model.PhotoItem

@Database(
    entities = [PhotoItem::class],
    version = 1,
    exportSchema = false
)
abstract class PicturesqueDatabase : RoomDatabase() {
    abstract val imageDao: ImageDao

    companion object {
        const val DATABASE_NAME = "image_db"
    }
}
