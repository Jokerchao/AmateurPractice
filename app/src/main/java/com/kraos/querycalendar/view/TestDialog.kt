package com.kraos.querycalendar.view

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.kraos.querycalendar.R
import com.kraos.querycalendar.databinding.FragmentDiaglogTestBinding

/**
 * @author kraos
 * @date 2023/3/15
 */
class TestDialog : DialogFragment(){

    private lateinit var binding: FragmentDiaglogTestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_diaglog_test,
            container,
            false
        )
        binding.root.isClickable = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clContainer.animate()
            .scaleX(0.1f)
            .scaleY(0.1f)
            .apply {
                translationX(getScreenWidth() / 2f)
                    .translationY(getScreenHeight() / 2f)
            }
            .setDuration(1000L)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setStartDelay(800L).withEndAction { dismissAllowingStateLoss() }.start()

    }


    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        val params = window?.attributes
        params?.run {
            dimAmount = 0.0f
            flags = flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        }
        window?.attributes = params
        dialog?.setCanceledOnTouchOutside(false)
    }

/*    override fun getTheme(): Int {
        return R.style.DialogFullScreen
    }*/

    fun getScreenWidth(): Int {
        val context: Context = this.requireContext() ?: return 1080
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val point = Point()
        display.getSize(point)
        return point.x
    }

    fun getScreenHeight(): Int {
        val context: Context = this.requireContext() ?: return 1080
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val point = Point()
        display.getSize(point)
        return point.y
    }
}