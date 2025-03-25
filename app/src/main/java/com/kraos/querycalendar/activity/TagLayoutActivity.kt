package com.kraos.querycalendar.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import com.kraos.querycalendar.databinding.ActivityTagLayoutBinding
import com.kraos.querycalendar.view.ProvinceView.ProvinceEvaluator

class TagLayoutActivity : BaseActivity() {
    private lateinit var binding: ActivityTagLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTagLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //1.放缩圆形动画
//        val objectAnimator = ObjectAnimator.ofFloat(binding.circleView, "radius", 100f.px, 150f.px)
//        objectAnimator.startDelay = 1000
//        objectAnimator.duration = 2000
//        objectAnimator.start()

        //2.点移动动画
//        val objectAnimator =
//            ObjectAnimator.ofObject(
//                binding.pointView,
//                "position",
//                PointEvaluator(),
//                PointF(200f.px, 200f.px)
//            )
//        objectAnimator.startDelay = 1000
//        objectAnimator.duration = 1500
//        objectAnimator.start()

        //3.Flip动画
//        val bottomFlipAnimator = ObjectAnimator.ofFloat(binding.cameraProView, "bottomFlip", 60f)
//        bottomFlipAnimator.startDelay = 1000
//        bottomFlipAnimator.duration = 1500
//        bottomFlipAnimator.start()
//        val rotateAnimator = ObjectAnimator.ofFloat(binding.cameraProView, "rotateAngle", 270f)
//        rotateAnimator.startDelay = 200
//        rotateAnimator.duration = 1500
//        val topFlipAnimator = ObjectAnimator.ofFloat(binding.cameraProView, "topFlip", -60f)
//        topFlipAnimator.startDelay = 200
//        topFlipAnimator.duration = 1500
//
//        val animationSet = AnimatorSet().apply {
//            playSequentially(bottomFlipAnimator, rotateAnimator, topFlipAnimator)
//        }
//        animationSet.start()

        //4.省份文字变化动画
        val objectAnimator =
            ObjectAnimator.ofObject(
                binding.provinceView,
                "showText",
                ProvinceEvaluator(),
                "北京市",
                "澳门特别行政区"
            )
        objectAnimator.duration = 10000
        objectAnimator.startDelay = 1000
        objectAnimator.start()


    }
}