package com.eltonjhony.eventapp.data.local

import com.eltonjhony.eventapp.domain.Genre

object GenreFilterLocalPreferences {
    private val genres: MutableList<Genre> = mutableListOf()

    fun addFilter(genre: Genre?) =
        genre?.let { genres.add(it) }

    fun removeFilter(genre: Genre?) =
        genre?.let { genres.remove(it) }

    fun find(genre: Genre?) =
        genre?.let { genres.contains(it) } ?: false

    fun getAsFilter(): String = genres.joinToString(",") { it.id }

}