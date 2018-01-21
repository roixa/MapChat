package com.roix.mapchat.ui.root.views

import android.support.v7.widget.Toolbar
import com.roix.mapchat.R
import com.roix.mapchat.databinding.ActivityRootBinding
import com.roix.mapchat.ui.common.activities.BaseSingleFragmentActivity
import com.roix.mapchat.ui.common.view.ToolbarType
import com.roix.mapchat.ui.groups.views.GroupsFragment
import com.roix.mapchat.ui.new_group.views.NewGroupFragment
import com.roix.mapchat.ui.root.viewmodels.RootViewModel

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class RootActivity : BaseSingleFragmentActivity<RootViewModel, ActivityRootBinding>() {

    override fun getFragmentContainerId(): Int = R.id.container

    override fun getToolbar(): Toolbar? = binding.toolbar!!.tb

    override fun getLayoutId(): Int = R.layout.activity_root

    override fun setupUi() {
        super.setupUi()
        viewModel.navigation.sub {state ->
            when(state){
                RootViewModel.State.NEW_GROUP -> setFragment(NewGroupFragment::class.java)
                RootViewModel.State.GROUP_LIST -> setFragment(GroupsFragment::class.java)
                RootViewModel.State.FINISHED -> supportFinishAfterTransition()
            }
        }
    }

    override fun goBack() {
        viewModel.goBack()
    }

}


