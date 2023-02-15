package ru.ivos.quantron_movies.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.ivos.quantron_movies.data.models.favorite.Favorite

@Dao
interface AppDao {

    @Query("SELECT * FROM favorites ORDER BY id DESC")
    suspend fun getFavorites(): List<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteFavorite(id: Int)
}