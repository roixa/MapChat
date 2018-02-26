package com.roix.mapchat.ui.splash.views

import android.content.Intent
import com.roix.mapchat.R
import com.roix.mapchat.databinding.ActivitySplashBinding
import com.roix.mapchat.ui.common.activities.BaseDatabindingActivity
import com.roix.mapchat.ui.root.views.RootActivity
import com.roix.mapchat.ui.splash.viewmodels.SplashViewModel

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class SplashActivity : BaseDatabindingActivity<SplashViewModel, ActivitySplashBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun setupUi() {
        super.setupUi()
        viewModel.prepareApplication().sub {
            startActivity(Intent(this,RootActivity::class.java))
            finish()
        }
    }

}

