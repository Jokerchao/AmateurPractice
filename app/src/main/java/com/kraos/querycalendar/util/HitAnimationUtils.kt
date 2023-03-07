package com.kraos.querycalendar.util

import android.animation.AnimatorSet
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View

/**
 * @author kraos
 * @date 2023/3/3
 */
object HitAnimationUtils {

    fun getCirtMalHitAnimationSet(
        vCrit: View,
        vGift: View,
        vMonster: View,
        vCritBg: View,
        duration: Long
    ): AnimatorSet {
        return AnimatorSet().apply {
            play(getCritTextScaleY(vCrit, duration))
                .with(getCritTextScaleX(vCrit, duration))
                .with(getCritTextAlpha(vCrit, duration))
                .with(getCritTextTranslate(vCrit, duration))

                .with(getGiftTranslate(vGift, duration))
                .with(getGiftAlpha(vGift, duration))

                .with(getMonsterCritScaleX(vMonster, duration))
                .with(getMonsterCritScaleY(vMonster, duration))

                .with(getCritBgScaleY(vCritBg, duration))
                .with(getCritBgScaleX(vCritBg, duration))
                .with(getCritBgAlpha(vCritBg, duration))
                .with(getCritBgTranslate(vCritBg, duration))
        }
    }

    /**** 普通攻击文字动画 ****/
    fun getNormalTextAlpha(view: View): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0.33f, 0.3f)
        val kf1 = Keyframe.ofFloat(0.5f, 1f)
        val kf2 = Keyframe.ofFloat(0.66f, 1f)
        val kf3 = Keyframe.ofFloat(1f, 0f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("alpha", kf0, kf1, kf2, kf3)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            duration = 1000
        }
        return anim
    }

    fun getNormalTextTranslate(view: View): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0f, 0f)
        val kf1 = Keyframe.ofFloat(0.66f, 0f)
        val kf2 = Keyframe.ofFloat(1f, -100f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("translationY", kf0, kf1, kf2)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            duration = 1000
        }
        return anim
    }

    fun getNormalTextScaleX(view: View): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0.33f, 0.6f)
        val kf1 = Keyframe.ofFloat(0.5f, 1f)
        val kf2 = Keyframe.ofFloat(0.66f, 1f)
        val kf3 = Keyframe.ofFloat(1f, 0.6f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleX", kf0, kf1, kf2, kf3)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            duration = 1000
        }
        return anim
    }

    fun getNormalTextScaleY(view: View): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0.33f, 0.6f)
        val kf1 = Keyframe.ofFloat(0.5f, 1f)
        val kf2 = Keyframe.ofFloat(0.66f, 1f)
        val kf3 = Keyframe.ofFloat(1f, 0.6f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleY", kf0, kf1, kf2, kf3)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            duration = 1000
        }
        return anim
    }

    /**** 普通攻击文字动画 ****/

    /**** 暴击文字动画 ****/

    fun getCritTextAlpha(view: View, duration: Long): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0.33f, 0.3f)
        val kf1 = Keyframe.ofFloat(0.5f, 1f)
        val kf2 = Keyframe.ofFloat(0.66f, 1f)
        val kf3 = Keyframe.ofFloat(1f, 0f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("alpha", kf0, kf1, kf2, kf3)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }
        return anim
    }

    fun getCritTextTranslate(view: View, duration: Long): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0f, 0f)
        val kf1 = Keyframe.ofFloat(0.66f, 0f)
        val kf2 = Keyframe.ofFloat(1f, -100f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("translationY", kf0, kf1, kf2)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }
        return anim
    }

    fun getCritTextScaleX(view: View, duration: Long): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0.33f, 0.6f)
        val kf1 = Keyframe.ofFloat(0.5f, 1.3f)
        val kf2 = Keyframe.ofFloat(0.66f, 1.3f)
        val kf3 = Keyframe.ofFloat(1f, 0.6f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleX", kf0, kf1, kf2, kf3)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }
        return anim
    }

    fun getCritTextScaleY(view: View, duration: Long): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0.33f, 0.6f)
        val kf1 = Keyframe.ofFloat(0.5f, 1.3f)
        val kf2 = Keyframe.ofFloat(0.66f, 1.3f)
        val kf3 = Keyframe.ofFloat(1f, 0.6f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleY", kf0, kf1, kf2, kf3)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }
        return anim
    }


    /**** 礼物图飞出动画 ****/
    fun getGiftTranslate(view: View, duration: Long): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0f, 0f)
        val kf1 = Keyframe.ofFloat(0.33f, -430f)
        val kf2 = Keyframe.ofFloat(1f, -430f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("translationY", kf0, kf1, kf2)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }
        return anim
    }

    fun getGiftAlpha(view: View, duration: Long): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0f, 1f)
        val kf1 = Keyframe.ofFloat(0.33f, 0f)
        val kf2 = Keyframe.ofFloat(1f, 0f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("alpha", kf0, kf1, kf2)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }
        return anim
    }
    /**** 礼物图飞出动画 ****/

    /**** 怪物普通攻击动画 ****/
    fun getMonsterNormalScaleX(view: View): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0f, 1f)
        val kf1 = Keyframe.ofFloat(0.33f, 1f)
        val kf2 = Keyframe.ofFloat(0.43f, 0.97f)
        val kf3 = Keyframe.ofFloat(0.6f, 1.03f)
        val kf4 = Keyframe.ofFloat(0.66f, 1f)
        val kf5 = Keyframe.ofFloat(0.1f, 1f)

        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleX", kf0, kf1, kf2, kf3, kf4, kf5)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            duration = 1000
        }

        return anim
    }

    fun getMonsterNormalScaleY(view: View): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0f, 1f)
        val kf1 = Keyframe.ofFloat(0.33f, 1f)
        val kf2 = Keyframe.ofFloat(0.43f, 0.97f)
        val kf3 = Keyframe.ofFloat(0.6f, 1.03f)
        val kf4 = Keyframe.ofFloat(0.66f, 1f)
        val kf5 = Keyframe.ofFloat(0.1f, 1f)

        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleY", kf0, kf1, kf2, kf3, kf4, kf5)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            duration = 1000
        }

        return anim
    }
    /**** 怪物普通攻击动画 ****/


    /**** 怪物暴击动画 ****/
    fun getMonsterCritScaleX(view: View, duration: Long): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0f, 1f)
        val kf1 = Keyframe.ofFloat(0.33f, 1f)
        val kf2 = Keyframe.ofFloat(0.43f, 0.9f)
        val kf3 = Keyframe.ofFloat(0.6f, 1.1f)
        val kf4 = Keyframe.ofFloat(0.66f, 1f)
        val kf5 = Keyframe.ofFloat(0.1f, 1f)

        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleX", kf0, kf1, kf2, kf3, kf4, kf5)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }
        return anim
    }

    fun getMonsterCritScaleY(view: View, duration: Long): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0f, 1f)
        val kf1 = Keyframe.ofFloat(0.33f, 1f)
        val kf2 = Keyframe.ofFloat(0.43f, 0.9f)
        val kf3 = Keyframe.ofFloat(0.6f, 1.1f)
        val kf4 = Keyframe.ofFloat(0.66f, 1f)
        val kf5 = Keyframe.ofFloat(0.1f, 1f)

        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleY", kf0, kf1, kf2, kf3, kf4, kf5)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }
        return anim
    }
    /**** 怪物暴击动画 ****/

    /**** 暴击背景动画 ****/
    fun getCritBgAlpha(view: View, duration: Long): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0.33f, 0.3f)
        val kf1 = Keyframe.ofFloat(0.5f, 1f)
        val kf2 = Keyframe.ofFloat(0.66f, 1f)
        val kf3 = Keyframe.ofFloat(1f, 0f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("alpha", kf0, kf1, kf2, kf3)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }
        return anim
    }

    fun getCritBgTranslate(view: View, duration: Long): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0f, 0f)
        val kf1 = Keyframe.ofFloat(0.66f, 0f)
        val kf2 = Keyframe.ofFloat(1f, -100f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("translationY", kf0, kf1, kf2)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }
        return anim
    }

    fun getCritBgScaleX(view: View, duration: Long): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0.33f, 0.6f)
        val kf1 = Keyframe.ofFloat(0.5f, 2.0f)
        val kf2 = Keyframe.ofFloat(0.66f, 1.4f)
        val kf3 = Keyframe.ofFloat(1f, 0.6f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleX", kf0, kf1, kf2, kf3)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }
        return anim
    }

    fun getCritBgScaleY(view: View, duration: Long): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0.33f, 0.6f)
        val kf1 = Keyframe.ofFloat(0.5f, 2.0f)
        val kf2 = Keyframe.ofFloat(0.66f, 1.4f)
        val kf3 = Keyframe.ofFloat(1f, 0.6f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleY", kf0, kf1, kf2, kf3)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }
        return anim
    }


}