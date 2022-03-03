package com.kraos.querycalendar.entity

/**
 * @Author Kraos 980737054@qq.com
 * @Date 2021/1/18 22:34
 * @Description :
 */
class Student(val sno: String, val grade: Int, name: String, age: Int) : Person(name, age) ,Study{
    constructor(name: String, age: Int) : this("", 0, name, age) {
    }

    constructor():this("",0){

    }

    override fun readBooks() {
        TODO("Not yet implemented")
    }

}