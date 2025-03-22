package com.kraos.querycalendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kraos.querycalendar.activity.CameraViewActivity
import com.kraos.querycalendar.activity.ComposeTestActivity
import com.kraos.querycalendar.activity.CoroutinesTestActivity
import com.kraos.querycalendar.activity.CountdownTimerTestActivity
import com.kraos.querycalendar.activity.DashBoardActivity
import com.kraos.querycalendar.activity.DatePickerTestActivity
import com.kraos.querycalendar.activity.DragViewTestActivity
import com.kraos.querycalendar.activity.IllegalStateTestActivity
import com.kraos.querycalendar.activity.ImageTextActivity
import com.kraos.querycalendar.activity.KotlinTestActivity
import com.kraos.querycalendar.activity.MaterialEditTextActivity
import com.kraos.querycalendar.activity.PieChartActivity
import com.kraos.querycalendar.activity.RichEditorTestActivity
import com.kraos.querycalendar.activity.ScalableImageViewActivity
import com.kraos.querycalendar.activity.ScheduleTestActivity
import com.kraos.querycalendar.activity.SwipeStackCardActivity
import com.kraos.querycalendar.activity.SwipeStrangerCardActivity
import com.kraos.querycalendar.activity.TagLayoutActivity
import com.kraos.querycalendar.activity.TestAnimateActivity
import com.kraos.querycalendar.activity.TestCalenderHistoryActivity
import com.kraos.querycalendar.activity.TestCustomViewActivity
import com.kraos.querycalendar.activity.TestDragViewActivity
import com.kraos.querycalendar.activity.TestKeyBoardActivity
import com.kraos.querycalendar.activity.TestPhotoPickerActivity
import com.kraos.querycalendar.activity.TestPopupActivity
import com.kraos.querycalendar.activity.TestProxyActivity
import com.kraos.querycalendar.activity.TestSelectActivity
import com.kraos.querycalendar.activity.TestTextColorFormatActivity
import com.kraos.querycalendar.activity.ZoomPagerActivity

class MainActivity : AppCompatActivity() {
    //数据源
    private val mList: ArrayList<String> = object : ArrayList<String>() {
        init {
            add("文字选择测试")
            add("日程导入测试")
            add("时间选择器测试")
            add("富文本编辑器测试")
            add("协程测试")
            add("ListView异步Notify测试")
            add("拖拽view测试")
            add("Kotlin语法测试")
            add("自定义view测试")
            add("仪表盘自定义View")
            add("饼图自定义View")
            add("自定义TextView")
            add("自定义CameraView")
            add("MaterialEditText练习")
            add("TagLayout练习")
            add("ScalableImageView练习")
            add("动态代理练习")
            add("限制范围的拖动View练习")
            add("组合动画练习")
            add("Compose练习")
            add("文本分色练习")
            add("仿探探卡片滑动交互")
            add("陌生人社交练习")
            add("软键盘弹出练习")
            add("Popup练习")
            add("ZoomPager练习")
            add("倒计时动画练习")
            add("图片选择器练习")
            add("仿QQ聊天记录日历练习")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val notificationManagerCompat = NotificationManagerCompat.from(this)
        Log.d(
            TAG, "onCreate: " + NotificationManagerCompat.from(
                applicationContext
            ).areNotificationsEnabled()
        )
        initListView()
    }

    private fun initListView() {
        val rv = findViewById<RecyclerView>(R.id.rv_test)
        rv.layoutManager = LinearLayoutManager(this)
        val adapter = RvAdapter(mList)
        rv.adapter = adapter
    }

    internal inner class RvAdapter(list: List<String?>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        //数据源
        private val mList: ArrayList<*>?

        init {
            mList = list as ArrayList<*>
            val string = list[0]
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val item = View.inflate(parent.context, R.layout.item_rv, null)
            return MyViewHolder(item)
        }

        override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            @SuppressLint("RecyclerView") position: Int
        ) {
            val viewHolder = holder as MyViewHolder
            val string = mList!![position] as String
            viewHolder.tv.text = string
            viewHolder.itemView.setOnClickListener {
                when (position) {
                    0 -> TestSelectActivity.bootActivity(this@MainActivity)
                    1 -> ScheduleTestActivity.bootActivity(this@MainActivity)
                    2 -> DatePickerTestActivity.bootActivity(this@MainActivity)
                    3 -> RichEditorTestActivity.bootActivity(this@MainActivity)
                    4 -> CoroutinesTestActivity().bootActivity(this@MainActivity)
                    5 -> IllegalStateTestActivity.bootActivity(this@MainActivity)
                    6 -> DragViewTestActivity.bootActivity(this@MainActivity)
                    7 -> KotlinTestActivity.bootActivity(this@MainActivity)
                    8 -> TestCustomViewActivity.bootActivity(this@MainActivity)
                    9 -> DashBoardActivity.bootActivity(this@MainActivity)
                    10 -> PieChartActivity.bootActivity(this@MainActivity)
                    11 -> ImageTextActivity().bootActivity(this@MainActivity)
                    12 -> CameraViewActivity().bootActivity(this@MainActivity)
                    13 -> MaterialEditTextActivity().bootActivity(this@MainActivity)
                    14 -> TagLayoutActivity().bootActivity(this@MainActivity)
                    15 -> ScalableImageViewActivity().bootActivity(this@MainActivity)
                    16 -> TestProxyActivity().bootActivity(this@MainActivity)
                    17 -> TestDragViewActivity().bootActivity(this@MainActivity)
                    18 -> TestAnimateActivity().bootActivity(this@MainActivity)
                    19 -> ComposeTestActivity().bootActivity(this@MainActivity)
                    20 -> TestTextColorFormatActivity().bootActivity(this@MainActivity)
                    21 -> SwipeStackCardActivity().bootActivity(this@MainActivity)
                    22 -> SwipeStrangerCardActivity().bootActivity(this@MainActivity)
                    23 -> TestKeyBoardActivity().bootActivity(this@MainActivity)
                    24 -> TestPopupActivity().bootActivity(this@MainActivity)
                    25 -> ZoomPagerActivity().bootActivity(this@MainActivity)
                    26 -> CountdownTimerTestActivity().bootActivity(this@MainActivity)
                    27 -> TestPhotoPickerActivity().bootActivity(this@MainActivity)
                    28 -> TestCalenderHistoryActivity().bootActivity(this@MainActivity)
                }
            }
        }

        override fun getItemCount(): Int {
            return mList?.size ?: 0
        }
    }

    internal inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv: TextView

        init {
            tv = itemView.findViewById(R.id.item_text)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
