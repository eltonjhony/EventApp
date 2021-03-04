package com.eltonjhony.eventapp.infrastructure

import com.eltonjhony.eventapp.infrastructure.extensions.pushAll
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CollectionTests {

    @Test
    fun `push all elements to list`() {
        val targetList = mutableListOf(1, 2, 3)
        targetList.pushAll(listOf(6, 8))

        assert(targetList.size == 2)
        assert(targetList[0] == 6)
        assert(targetList[1] == 8)
    }

}