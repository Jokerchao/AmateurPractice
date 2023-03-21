package com.kraos.querycalendar.activity

import android.animation.AnimatorSet
import android.os.Bundle
import androidx.core.animation.doOnEnd
import androidx.databinding.DataBindingUtil
import com.kraos.querycalendar.R
import com.kraos.querycalendar.databinding.ActivityTestAnimateBinding
import com.kraos.querycalendar.util.HitAnimationUtils
import com.kraos.querycalendar.view.TestDialog

/**
 * @author kraos
 * @date 2023/3/3
 */
class TestAnimateActivity : BaseActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityTestAnimateBinding>(
            this,
            R.layout.activity_test_animate
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_animate)
        val animatorSets = mutableListOf<AnimatorSet>()

        repeat(5){
            animatorSets.add(
                binding.run {
                    HitAnimationUtils.getCirtMalHitAnimationSet(
                        tvCrit,
                        ivGift,
                        ivMonster,
                        ivCritBg,
                        1000
                    )
                }
            )
        }

        val animatorSet = binding.run {
             HitAnimationUtils.getCirtMalHitAnimationSet(
                 tvCrit,
                 ivGift,
                 ivMonster,
                 ivCritBg,
                 1000
             )
        }


        binding.btnAttack.setOnClickListener {
            if (animatorSet.isRunning) {
                animatorSet.currentPlayTime = 500
            }
            animatorSet.start()
        }

        binding.btnShowDilog.setOnClickListener {
            TestDialog().show(supportFragmentManager, "test")
        }

    }

}