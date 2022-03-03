package com.kraos.querycalendar.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kraos.querycalendar.databinding.ActivityCoroutinesTestBinding
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
class CoroutinesTestActivity : AppCompatActivity() ,CoroutineScope by MainScope(){

    private lateinit var binding: ActivityCoroutinesTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutinesTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        loadDataAndShow()
//        var tv = findViewById<TextView>(R.id.tv_coroutines)
//        var job = GlobalScope.launch(Dispatchers.IO){
//            var content = fetchData()
//            Log.d("Coroutine",content)
//            //切换线程
//            withContext(Dispatchers.Main){
//                tv.setText(content)
//            }
//        }
//        该方法会阻塞线程
//        var job = runBlocking(Dispatchers.Main){
//            var content = fetchData()
//            Log.d("Coroutine",content)
//        }
//        tv.setText(job.join())
    }

    /**
     *
     * suspend 方法能够使协程执行暂停，等执行完毕后在返回结果，同时不会阻塞线程。
     */
    suspend fun fetchData():String{
        delay(2000)
        return "content"
    }

    companion object{
        fun bootActivity(context: Context){
            var intent = Intent(context,CoroutinesTestActivity::class.java)
            context.startActivity(intent)
        }
    }

    fun loadDataAndShow(){
        GlobalScope.launch(Dispatchers.IO){
            var result = fetchData()
            withContext(Dispatchers.Main){
                binding.tvCoroutines.text = result
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
