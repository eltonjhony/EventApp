package com.eltonjhony.eventapp.infrastructure.extensions

fun <E> MutableList<E>.pushAll(elements: List<E>) {
    clear()
    addAll(elements)
}