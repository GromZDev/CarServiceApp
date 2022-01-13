package carService.app.ui.splash_screen

import android.animation.Animator
import android.annotation.SuppressLint
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import carService.app.R
import carService.app.base.BaseFragment
import carService.app.databinding.ActivitySplashScreenStartBinding
import carService.app.utils.SharedPreferencesHelper
import carService.app.utils.hideToolbarAndBottomNav
import carService.app.utils.navigate
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment(
    override val layoutId: Int = R.layout.activity_splash_screen_start
) : BaseFragment<ActivitySplashScreenStartBinding>() {

    private val vm by viewModel<SplashScreenViewModel>()
    private val prefs by inject<SharedPreferencesHelper>()

    override fun initViews() {
        super.initViews()
        hideToolbarAndBottomNav()
    }

    override fun initViewModel() {
        super.initViewModel()
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
                    doInScope {
                        vm.userData.collect {
                            if (it == null)  {
                                prefs.isAuthed = false
                                prefs.isFirstOpen = true
                                prefs.isRegistrationStep1 = false
                                prefs.isRegistrationStep2 = false
                                prefs.isRegistrationStep3 = false
                                prefs.isRegistrationStep4 = false
                                openLogin()
                            }
                            else {
                                vm.isLoggedIn.collect { isLoggedIn ->
                                    if (isLoggedIn) {
                                        stepRegistration()
                                    } else {
                                        openLogin()
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationRepeat(p0: Animator?) {}
            })
    }

    private fun showComponents() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(requireContext(), R.layout.activity_splash_screen)
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

    fun openLogin() {
        navigate(R.id.loginFragment)
    }

    override fun onResume() {
        super.onResume()
        vm.checkAuth()
        vm.loadUser()
    }

    fun stepRegistration() {
        when {
            !prefs.isRegistrationStep1 -> navigate(R.id.registrationStep2Fragment)
            !prefs.isRegistrationStep2 -> navigate(R.id.registrationStep3Fragment)
            !prefs.isRegistrationStep3 -> navigate(R.id.registrationStep4LocationFragment)
            !prefs.isRegistrationStep4 -> navigate(R.id.registrationStep5RoleFragment)
            else -> navigate(R.id.mainUserFragment)
        }
    }
}