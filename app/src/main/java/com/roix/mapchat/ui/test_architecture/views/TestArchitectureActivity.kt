package com.roix.mapchat.ui.test_architecture.views

import android.support.v7.widget.Toolbar
import com.roix.mapchat.R
import com.roix.mapchat.databinding.ActivityTestArchitectureBinding
import com.roix.mapchat.ui.common.activities.BaseDatabindingActivity
import com.roix.mapchat.ui.test_architecture.viewmodels.TestArchitectureViewModel

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class TestArchitectureActivity : BaseDatabindingActivity<TestArchitectureViewModel, ActivityTestArchitectureBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_test_architecture

    override fun setupBinding() {
        super.setupBinding()
        binding.buttonTest.setOnClickListener{ view ->
            var count =viewModel.count.value
            viewModel.count.value= count!!.plus(1)
        }

    }
}

