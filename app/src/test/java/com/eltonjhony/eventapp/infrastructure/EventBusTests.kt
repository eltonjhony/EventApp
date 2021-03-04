package com.eltonjhony.eventapp.infrastructure

import com.eltonjhony.eventapp.infrastructure.events.EventBus
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EventBusTests {

    @Test
    fun `should receive only events fired after subscription`() {

        GlobalScope.launch {
            val values = mutableListOf<Int>()

            EventBus.send(1)
            EventBus.send(2)

            val job = launch {
                EventBus.on<Int>().collect {
                    values.add(it)
                }
            }

            EventBus.send(2)
            assert(values.size == 1)
            assert(values[0] == 3)

            job.cancel()
        }

    }

}