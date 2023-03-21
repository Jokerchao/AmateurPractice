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
    /**
     * 普通攻击组合动画
     */
    fun getNormalHitAnimationSet(
        vNormalText: View,
        vGift: View,
        vMonster: View,
        duration: Long
    ): AnimatorSet {
        val anim1 = ObjectAnimator.ofPropertyValuesHolder(
            vNormalText,
            getNormalTextAlpha(),
            getNormalTextTranslate(),
            getNormalTextScaleX(),
            getNormalTextScaleY()
        ).apply {
            this.duration = duration
        }

        val anim2 = ObjectAnimator.ofPropertyValuesHolder(
            vGift,
            getGiftTranslate(),
            getGiftAlpha()
        ).apply {
            this.duration = duration
        }

        val anim3 = ObjectAnimator.ofPropertyValuesHolder(
            vMonster,
            getMonsterNormalScaleX(),
            getMonsterNormalScaleY()
        ).apply {
            this.duration = duration
        }
        return AnimatorSet().apply {
            play(anim1)
                .with(anim2)
                .with(anim3)
        }
    }

    /**
     * 暴击组合动画
     */
    fun getCirtMalHitAnimationSet(
        vCrit: View,
        vGift: View,
        vMonster: View,
        vCritBg: View,
        duration: Long
    ): AnimatorSet {
        val anim1 = ObjectAnimator.ofPropertyValuesHolder(
            vCrit,
            getCritTextScaleY(),
            getCritTextScaleX(),
            getCritTextAlpha(),
            getCritTextTranslate()
        ).apply {
            this.duration = duration
        }
        val anim2 = ObjectAnimator.ofPropertyValuesHolder(
            vGift,
            getGiftTranslate(),
            getGiftAlpha()
        ).apply {
            this.duration = duration
        }
        val anim3 = ObjectAnimator.ofPropertyValuesHolder(
            vMonster,
            getMonsterCritScaleX(),
            getMonsterCritScaleY()
        ).apply {
            this.duration = duration
        }

        val anim4 = ObjectAnimator.ofPropertyValuesHolder(
            vCritBg,
            getCritBgScaleY(),
            getCritBgScaleX(),
            getCritBgAlpha(),
            getCritBgTranslate()
        ).apply {
            this.duration = duration
        }
        return AnimatorSet().apply {
            play(anim1)
                .with(anim2)
                .with(anim3)
                .with(anim4)
        }
    }

    /**
     * 小boss攻击动画
     */
    fun getSmallBossHitAnimationSet(
        vHit: View,
        duration: Long
    ): AnimatorSet {
        val anim1 = ObjectAnimator.ofPropertyValuesHolder(
            vHit,
            getSmallBossTextAlpha(),
            getSmallBossTextTranslate(),
            getSmallBossTextScaleX(),
            getSmallBossTextScaleY()
        ).apply {
            this.duration = duration
        }
        return AnimatorSet().apply {
            play(anim1)
        }
    }


    /**** 普通攻击文字动画 ****/
    fun getNormalTextAlpha(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0.33f, 0.3f)
        val kf1 = Keyframe.ofFloat(0.5f, 1f)
        val kf2 = Keyframe.ofFloat(0.66f, 1f)
        val kf3 = Keyframe.ofFloat(1f, 0f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("alpha", kf0, kf1, kf2, kf3)
        /*val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }*/
        return pvhRotation
    }

    fun getNormalTextTranslate(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0f, 0f)
        val kf1 = Keyframe.ofFloat(0.66f, 0f)
        val kf2 = Keyframe.ofFloat(1f, -100f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("translationY", kf0, kf1, kf2)
        /*val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }*/
        return pvhRotation
    }

    fun getNormalTextScaleX(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0.33f, 0.6f)
        val kf1 = Keyframe.ofFloat(0.5f, 1f)
        val kf2 = Keyframe.ofFloat(0.66f, 1f)
        val kf3 = Keyframe.ofFloat(1f, 0.6f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleX", kf0, kf1, kf2, kf3)
        /*val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }*/
        return pvhRotation
    }

    fun getNormalTextScaleY(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0.33f, 0.6f)
        val kf1 = Keyframe.ofFloat(0.5f, 1f)
        val kf2 = Keyframe.ofFloat(0.66f, 1f)
        val kf3 = Keyframe.ofFloat(1f, 0.6f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleY", kf0, kf1, kf2, kf3)
        /* val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
             this.duration = duration
         }*/
        return pvhRotation
    }

    /**** 普通攻击文字动画 ****/

    /**** 暴击文字动画 ****/

    fun getCritTextAlpha(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0.33f, 0.3f)
        val kf1 = Keyframe.ofFloat(0.5f, 1f)
        val kf2 = Keyframe.ofFloat(0.66f, 1f)
        val kf3 = Keyframe.ofFloat(1f, 0f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("alpha", kf0, kf1, kf2, kf3)
        /* val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
             this.duration = duration
         }*/
        return pvhRotation
    }

    fun getCritTextTranslate(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0f, 0f)
        val kf1 = Keyframe.ofFloat(0.66f, 0f)
        val kf2 = Keyframe.ofFloat(1f, -100f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("translationY", kf0, kf1, kf2)
        /*val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }*/
        return pvhRotation
    }

    fun getCritTextScaleX(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0.33f, 0.6f)
        val kf1 = Keyframe.ofFloat(0.5f, 1.3f)
        val kf2 = Keyframe.ofFloat(0.66f, 1.3f)
        val kf3 = Keyframe.ofFloat(1f, 0.6f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleX", kf0, kf1, kf2, kf3)
        /* val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
             this.duration = duration
         }*/
        return pvhRotation
    }

    fun getCritTextScaleY(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0.33f, 0.6f)
        val kf1 = Keyframe.ofFloat(0.5f, 1.3f)
        val kf2 = Keyframe.ofFloat(0.66f, 1.3f)
        val kf3 = Keyframe.ofFloat(1f, 0.6f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleY", kf0, kf1, kf2, kf3)
        /* val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
             this.duration = duration
         }*/
        return pvhRotation
    }


    /**** 礼物图飞出动画 ****/
    fun getGiftTranslate(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0f, 0f)
        val kf1 = Keyframe.ofFloat(0.33f, -430f)
        val kf2 = Keyframe.ofFloat(1f, -430f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("translationY", kf0, kf1, kf2)
        /*val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }*/
        return pvhRotation
    }

    fun getGiftAlpha(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0f, 1f)
        val kf1 = Keyframe.ofFloat(0.33f, 0f)
        val kf2 = Keyframe.ofFloat(1f, 0f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("alpha", kf0, kf1, kf2)
        /*val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }*/
        return pvhRotation
    }
    /**** 礼物图飞出动画 ****/

    /**** 怪物普通攻击动画 ****/
    fun getMonsterNormalScaleX(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0f, 1f)
        val kf1 = Keyframe.ofFloat(0.33f, 1f)
        val kf2 = Keyframe.ofFloat(0.43f, 0.97f)
        val kf3 = Keyframe.ofFloat(0.6f, 1.03f)
        val kf4 = Keyframe.ofFloat(0.66f, 1f)
        val kf5 = Keyframe.ofFloat(0.1f, 1f)

        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleX", kf0, kf1, kf2, kf3, kf4, kf5)
        /* val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
             this.duration = duration
         }*/

        return pvhRotation
    }

    fun getMonsterNormalScaleY(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0f, 1f)
        val kf1 = Keyframe.ofFloat(0.33f, 1f)
        val kf2 = Keyframe.ofFloat(0.43f, 0.97f)
        val kf3 = Keyframe.ofFloat(0.6f, 1.03f)
        val kf4 = Keyframe.ofFloat(0.66f, 1f)
        val kf5 = Keyframe.ofFloat(0.1f, 1f)

        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleY", kf0, kf1, kf2, kf3, kf4, kf5)
        /*val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }*/

        return pvhRotation
    }
    /**** 怪物普通攻击动画 ****/


    /**** 怪物暴击动画 ****/
    fun getMonsterCritScaleX(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0f, 1f)
        val kf1 = Keyframe.ofFloat(0.33f, 1f)
        val kf2 = Keyframe.ofFloat(0.43f, 0.9f)
        val kf3 = Keyframe.ofFloat(0.6f, 1.1f)
        val kf4 = Keyframe.ofFloat(0.66f, 1f)
        val kf5 = Keyframe.ofFloat(0.1f, 1f)

        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleX", kf0, kf1, kf2, kf3, kf4, kf5)
        /*val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }*/
        return pvhRotation
    }

    fun getMonsterCritScaleY(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0f, 1f)
        val kf1 = Keyframe.ofFloat(0.33f, 1f)
        val kf2 = Keyframe.ofFloat(0.43f, 0.9f)
        val kf3 = Keyframe.ofFloat(0.6f, 1.1f)
        val kf4 = Keyframe.ofFloat(0.66f, 1f)
        val kf5 = Keyframe.ofFloat(0.1f, 1f)

        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleY", kf0, kf1, kf2, kf3, kf4, kf5)
        /*val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }*/
        return pvhRotation
    }
    /**** 怪物暴击动画 ****/

    /**** 暴击背景动画 ****/
    fun getCritBgAlpha(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0.33f, 0.3f)
        val kf1 = Keyframe.ofFloat(0.5f, 1f)
        val kf2 = Keyframe.ofFloat(0.66f, 1f)
        val kf3 = Keyframe.ofFloat(1f, 0f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("alpha", kf0, kf1, kf2, kf3)
        /*val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }*/
        return pvhRotation
    }

    fun getCritBgTranslate(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0f, 0f)
        val kf1 = Keyframe.ofFloat(0.66f, 0f)
        val kf2 = Keyframe.ofFloat(1f, -100f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("translationY", kf0, kf1, kf2)
        /* val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
             this.duration = duration
         }*/
        return pvhRotation
    }

    fun getCritBgScaleX(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0.33f, 0.6f)
        val kf1 = Keyframe.ofFloat(0.5f, 2.0f)
        val kf2 = Keyframe.ofFloat(0.66f, 1.4f)
        val kf3 = Keyframe.ofFloat(1f, 0.6f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleX", kf0, kf1, kf2, kf3)
        /*val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }*/
        return pvhRotation
    }

    fun getCritBgScaleY(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0.33f, 0.6f)
        val kf1 = Keyframe.ofFloat(0.5f, 2.0f)
        val kf2 = Keyframe.ofFloat(0.66f, 1.4f)
        val kf3 = Keyframe.ofFloat(1f, 0.6f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleY", kf0, kf1, kf2, kf3)
        /*val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }*/
        return pvhRotation
    }
    /**** 暴击背景动画 ****/


    /**** 攻击小boss文字动画 ****/
    fun getSmallBossTextAlpha(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0f, 0.3f)
        val kf1 = Keyframe.ofFloat(0.2f, 1f)
        val kf2 = Keyframe.ofFloat(0.4f, 1f)
        val kf3 = Keyframe.ofFloat(0.6f, 0f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("alpha", kf0, kf1, kf2, kf3)
       /* val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }*/
        return pvhRotation
    }

    fun getSmallBossTextTranslate(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0f, 0f)
        val kf1 = Keyframe.ofFloat(0.4f, 0f)
        val kf2 = Keyframe.ofFloat(0.6f, -30f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("translationY", kf0, kf1, kf2)
       /* val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }*/
        return pvhRotation
    }

    fun getSmallBossTextScaleX(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0f, 0.6f)
        val kf1 = Keyframe.ofFloat(0.2f, 1.0f)
        val kf2 = Keyframe.ofFloat(0.4f, 1.0f)
        val kf3 = Keyframe.ofFloat(0.6f, 0.6f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleX", kf0, kf1, kf2, kf3)
        /*val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }*/
        return pvhRotation
    }

    fun getSmallBossTextScaleY(): PropertyValuesHolder {
        val kf0 = Keyframe.ofFloat(0f, 0.6f)
        val kf1 = Keyframe.ofFloat(0.2f, 1.0f)
        val kf2 = Keyframe.ofFloat(0.4f, 1.0f)
        val kf3 = Keyframe.ofFloat(0.6f, 0.6f)
        val pvhRotation = PropertyValuesHolder.ofKeyframe("scaleY", kf0, kf1, kf2, kf3)
        /*val anim = ObjectAnimator.ofPropertyValuesHolder(view, pvhRotation).apply {
            this.duration = duration
        }*/
        return pvhRotation
    }


    /**** 攻击小boss文字动画 ****/
}