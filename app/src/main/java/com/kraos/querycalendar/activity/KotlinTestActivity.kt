package com.kraos.querycalendar.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kraos.querycalendar.R
import com.kraos.querycalendar.databinding.ActivityKotlinTestBinding
import com.kraos.querycalendar.entity.Cellphone
import com.kraos.querycalendar.entity.Student

/**
 * @Author Kraos 980737054@qq.com
 * @Date 2021/1/18 22:24
 * @Description : Kotlin语法测试页面
 */
class KotlinTestActivity : AppCompatActivity() {

    private lateinit var binding:ActivityKotlinTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKotlinTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val student1 = Student()
        val student2 = Student("Jack",19)
        val student3 = Student("a123",5,"Jack",19)

        val cellphone1 = Cellphone("Samsung",1299.99)

        Thread{
            println("Thread is Running")
        }.start()

        binding.buttonIntent1.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.baidu.com")
            startActivity(intent)
        }
    }

    companion object{
        fun bootActivity(context: Context){
            val intent = Intent(context,KotlinTestActivity::class.java)
            context.startActivity(intent)
        }
    }
}