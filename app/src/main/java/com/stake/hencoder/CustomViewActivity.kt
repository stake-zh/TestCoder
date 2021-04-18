package com.stake.hencoder

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stake.hencoder.databinding.ActivityCustomViewBinding
import com.stake.hencoder.view.ProvinceEvaluator

class CustomViewActivity : AppCompatActivity() {

    private lateinit var activityCustomViewBinding: ActivityCustomViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        activityCustomViewBinding = ActivityCustomViewBinding.inflate(layoutInflater)
        setContentView(activityCustomViewBinding.root)

        // ObjectAnimator.ofFloat(activityMainBinding.root, "radius", 150.dp).apply {
        //     startDelay = 1000
        // }.also {
        //     it.start()
        // }

        // ObjectAnimator.ofObject(activityMainBinding.root, "point", object : TypeEvaluator<PointF> {
        //     override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
        //         return PointF(
        //             startValue.x + (endValue.x - startValue.x) * fraction,
        //             startValue.y + (endValue.y - startValue.y) * fraction
        //         )
        //     }
        // }, PointF(300.dp, 300.dp)).apply {
        //     startDelay = 1000
        // }.also {
        //     it.start()
        // }

         ObjectAnimator.ofObject(
             activityCustomViewBinding.root,
             "province",
             ProvinceEvaluator(),
             "澳门特别行政区"
         )
             .apply {
                 startDelay = 1000
                 duration = 5000
             }.also {
                 it.start()
             }
    }
}