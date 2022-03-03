package com.kraos.querycalendar.entity


/**
 * @Author Kraos 980737054@qq.com
 * @Date 2021/1/24 15:51
 * @Description :
 */

fun main() {

    /**
     * -------------------数据类用法----------------------------------
     */
    val cellphone1 = Cellphone("Samsung", 1299.99)
    val cellphone2 = Cellphone("Samsung", 1299.99)

    println(cellphone1)
    println(cellphone2)
    println("cellphone1 equals cellphone2 " + (cellphone1 == cellphone2))

    /**
     * -------------------单例类用法----------------------------------
     */
    Singleton.singletonTest();

    /**
     * -------------------集合用法----------------------------------
     */
    val arrayList = ArrayList<String>()
    arrayList.add("Apple")
    arrayList.add("Banana")

    //不可变list（该集合无法被添加、修改、删除）
    val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape")

    for(fruit in list){
        println(fruit)
    }

    //可变list
    val mutableList = mutableListOf("Apple", "Banana", "Orange", "Pear", "Grape")
    mutableList.add("Watermelon")
    for(fruit in mutableList){
        println(fruit)
    }

    //set
    val set = setOf("Apple", "Banana", "Orange", "Pear", "Grape")
    for(fruit in set){
        println(fruit)
    }

    //hashMap
    val hashMap = HashMap<String, Int>()
    hashMap.put("Apple",1)
    hashMap.put("Banana",2)
    hashMap.put("Orange",3)
    hashMap.put("Pear",4)
    hashMap.put("Grape",5)

    hashMap["Apple"] = 1
    hashMap["Banana"] = 1
    for(fruit in hashMap){
        println(fruit)
    }

    val map = mapOf("Apple" to 1, "Banana" to 2, "Orange" to 3, "Pear" to 4, "Grape" to 5)
    for(fruit in map){
        println("fruit is"+fruit.key+",number is "+fruit.value)
    }

    /**
     * -------------------函数式API用法----------------------------------
     */
    val list1 = listOf("Apple", "Banana", "Orange", "Pear", "Grape")
    val maxLengthFruit = list.maxBy { it.length }
    println("max length fruit is $maxLengthFruit")

    //map映射用法
    val map1 = list.map { it.toUpperCase() }
    for(fruit in map1){
        println(fruit)
    }

    //filter过滤
    val newList = list.filter { it.length < 5 }
            .map { it.toLowerCase() }
    for(fruit in newList){
        println(fruit)
    }

    //any是否至少一个元素满足条件;all全部满足
    val anyResult = list.any { it.length < 5 }
    val allResult = list.all { it.length < 5 }
    println("anyResult-->$anyResult,allResult-->$allResult")

    /**
     * -------------------判空辅助工具----------------------------------
     */
    fun doStudy(study: Study?){
        study?.doHomeWork()
        study?.readBooks()
    }

    fun doStudy2(study: Study?){
        study?.let {
            it.readBooks()
            it.doHomeWork()
        }
    }
}
