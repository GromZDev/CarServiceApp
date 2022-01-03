package carService.app.ui.splash_screen

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import by.kirich1409.viewbindingdelegate.viewBinding
import carService.app.MainActivity
import carService.app.R
import carService.app.databinding.ActivitySplashScreenStartBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity(R.layout.activity_splash_screen_start) {

    private val binding: ActivitySplashScreenStartBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        animationLogo()

    }

    private fun animationLogo() {
        val image = binding.imageViewSplashScreen
        image.animate().rotationBy(720f)
            .setInterpolator(AnticipateOvershootInterpolator()).setDuration(4320)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                    showComponents()
                }

                override fun onAnimationEnd(p0: Animator?) {
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finish()
                }

                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationRepeat(p0: Animator?) {}
            })
    }

    private fun showComponents() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.activity_splash_screen)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.5f)
        transition.duration = 3400
        transition.startDelay = 400
        TransitionManager.beginDelayedTransition(
            binding.constraintContainer,
            transition
        )
        constraintSet.applyTo(binding.constraintContainer)
    }

}