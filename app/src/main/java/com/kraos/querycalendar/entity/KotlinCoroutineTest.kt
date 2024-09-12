package com.kraos.querycalendar.entity

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlin.coroutines.EmptyCoroutineContext

/**
 * @author kraos
 * @date 2024/8/12
 * @desc Kotlin协程测试
 */
fun main1() = runBlocking {
    val scope = CoroutineScope(EmptyCoroutineContext)
    //打印当前线程
    println("main thread:${Thread.currentThread().name},time:${System.currentTimeMillis()}")
    launch {
        val childScope = scope.launch(scope.coroutineContext) {
//        launch(Dispatchers.IO) {
            try {
                println("coroutine thread:${Thread.currentThread().name},time:${System.currentTimeMillis()}")
                delay(3000L)
                println("coroutine run,time:${System.currentTimeMillis()}")
            } catch (e: CancellationException) {
                println("coroutine exception : exception:${e.message}")
            }
        }

        supervisorScope {

        }
    }

    println("main run")
    delay(500L)
    scope.cancel()
}
