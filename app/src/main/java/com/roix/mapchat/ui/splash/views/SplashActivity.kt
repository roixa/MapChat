package com.roix.mapchat.ui.splash.views

import android.app.Fragment
import android.content.Context
import android.content.Intent
import com.roix.mapchat.R
import com.roix.mapchat.databinding.ActivitySplashBinding
import com.roix.mapchat.ui.common.activities.BaseDatabindingActivity
import com.roix.mapchat.ui.root.models.Screen
import com.roix.mapchat.ui.root.views.RootActivity
import com.roix.mapchat.ui.splash.viewmodels.SplashViewModel
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.AppNavigator

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class SplashActivity : BaseDatabindingActivity<SplashViewModel, ActivitySplashBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun setupUi() {
        super.setupUi()
        viewModel.prepareApplication().sub {
            viewModel.router.newRootScreen(Screen.ROOT)
        }
    }

    override fun getNavigator(): Navigator? = object : AppNavigator(this, getLayoutId()) {
        override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent? {
            if (Screen.ROOT == screenKey) {
                return Intent(this@SplashActivity, RootActivity::class.java)
            }
            return null
        }

        override fun createFragment(screenKey: String?, data: Any?): Fragment? = null
    }

}

