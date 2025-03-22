package com.kraos.querycalendar.entity

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlin.coroutines.EmptyCoroutineContext

/**
 * @author kraos
 * @date 2024/8/16
 * @desc KotlinFlow测试
 */
fun main() {
    main1()
    main2()
}

fun main2() = runBlocking {
    val flow = flow<Int> {
        emit(1)
        delay(1000)
        emit(2)
        delay(1000)
        emit(3)
    }

    launch(Dispatchers.IO) {
        flow.collect {
            println("flow collect1:$it")
        }
    }

}
