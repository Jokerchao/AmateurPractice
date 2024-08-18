package com.kraos.querycalendar.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext


/**
 * @Author: Kraos
 * @datetime: 2024/8/18
 * @desc: Flow的创建练习
 */
fun main() = runBlocking<Unit> {
    //创建一个产生数据的Sequence
    val sequence = sequence<Int> {
        yield(1)
        yield(2)
        yield(3)
        yield(4)
    }

    sequence.forEach { it ->
        println("sequence :$it")
    }

    val scope = CoroutineScope(EmptyCoroutineContext)

    scope.launch {
        weatherFlow.collect{
            println("weatherFlow:$it")
        }
    }

    //创建一个产生数据的Flow
    val flow = createFlow()

    //监听Flow
//    flow.collect {
//        println("flow:$it")
//    }

    delay(10000)


}

private fun createFlow(): Flow<Int> {
    val flow = flow<Int> {
        while (true) {
            emit(1)
            delay(1000)
            emit(2)
            delay(2000)
            emit(3)
        }
    }
    return flow
}

val weatherFlow = flow {
    while(true){
        emit(getWeather())
        delay(1000)
    }
}

suspend fun getWeather() = withContext(Dispatchers.IO){
    "SUN"
}
