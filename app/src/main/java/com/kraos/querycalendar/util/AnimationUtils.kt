package com.kraos.querycalendar.util

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.view.View

/**
 * @author kraos
 * @date 2023/3/3
 */
object AnimationUtils {

    fun getAnimate(view: View): ObjectAnimator? {
        val fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f).apply {
            duration = 1000
        }
        return fadeAnim
    }

    //插值器动画
    fun getInterpolatorAnimate(view: View): ObjectAnimator? {
        val fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0.3f,1f).apply {
            startDelay = 330
            duration = 170
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
            start()
        }
        return fadeAnim
    }

    //指定关键帧的动画
    fun getKeyFrameAnimate(view: View): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0.33f, 0.3f)
        val kf1 = Keyframe.ofFloat(0.5f, 1f)
        val kf2 = Keyframe.ofFloat(0.66f, 1f)
        val kf3 = Keyframe.ofFloat(1f, 0f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("alpha", kf0, kf1, kf2, kf3)
        val fadeAnim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            duration = 1000
        }
        return fadeAnim
    }

}