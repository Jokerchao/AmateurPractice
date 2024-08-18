package com.kraos.querycalendar.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kraos.querycalendar.databinding.ActivityCoroutinesTestBinding
import kotlinx.coroutines.*

//@ExperimentalCoroutinesApi
class CoroutinesTestActivity : BaseActivity(), CoroutineScope by MainScope() {

    private lateinit var binding: ActivityCoroutinesTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutinesTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnStart.setOnClickListener {
            testCoroutinesStartMany()
        }
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
     * 启动多个协程测试
     */
    private fun testCoroutinesStartMany() {
        GlobalScope.launch {
//        GlobalScope.launch{
            for (index in 0..30) {
                launch {
                    delay(100)
                    Log.d(TAG, "launch执行$index")
                }
            }
        }

    }

    /**
     * 同步方式协程启动方式测试
     */
    private fun testCoroutinesSynchronize() {
        GlobalScope.launch(Dispatchers.Main) {
            Log.d(TAG, "启动runBlocking")
            val blocking = runBlocking {
                Log.d(TAG, "runBlocking执行")
            }
            Log.d(TAG, "blocking 返回: $blocking")

            Log.d(TAG, "启动launch")
            val job = launch(Dispatchers.IO) {
                Log.d(TAG, "launch执行")
            }
            Log.d(TAG, "launch 返回: $job")

            Log.d(TAG, "启动async")
            val deferred = async(Dispatchers.IO) {
                Log.d(TAG, "async执行")
            }
            Log.d(TAG, "async 返回: $deferred")
        }

    }

    /**
     * 三种协程启动方式测试
     */
    private fun testCoroutinesStart() {
        Log.d(TAG, "启动runBlocking")
        val blocking = runBlocking {
            Log.d(TAG, "runBlocking执行")
        }
        Log.d(TAG, "blocking 返回: $blocking")

        Log.d(TAG, "启动launch")
        val job = GlobalScope.launch(Dispatchers.IO) {
            Log.d(TAG, "launch执行")
        }
        Log.d(TAG, "launch 返回: $job")

        Log.d(TAG, "启动async")
        val deferred = GlobalScope.async(Dispatchers.IO) {
            Log.d(TAG, "async执行")
        }
        Log.d(TAG, "async 返回: $deferred")
    }

    /**
     *
     * suspend 方法能够使协程执行暂停，等执行完毕后在返回结果，同时不会阻塞线程。
     */
    private suspend fun fetchData(): String {
        delay(2000)
        return "content"
    }

    companion object {

        private const val TAG = "CoroutinesTestActivity"

//        fun bootActivity(context: Context) {
//            val intent = Intent(context, CoroutinesTestActivity::class.java)
//            context.startActivity(intent)
//        }
    }

    fun loadDataAndShow() {
        GlobalScope.launch(Dispatchers.IO) {
            var result = fetchData()
            withContext(Dispatchers.Main) {
                binding.tvCoroutines.text = result
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
