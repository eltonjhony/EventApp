package com.eltonjhony.eventapp.data.local

import androidx.room.*
import com.eltonjhony.eventapp.data.local.entities.EventEntity
import com.eltonjhony.eventapp.data.local.entities.EventsWithImages
import com.eltonjhony.eventapp.data.local.entities.ImageEntity

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: EventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: ImageEntity)

    @Delete
    fun delete(event: EventEntity)

    @Transaction
    @Query("SELECT * FROM event")
    fun getAll(): List<EventsWithImages>

    @Transaction
    @Query("SELECT COUNT(id) FROM event")
    fun getRowCount(): Int

}