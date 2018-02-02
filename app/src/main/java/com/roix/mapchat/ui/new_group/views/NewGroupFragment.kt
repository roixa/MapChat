package com.roix.mapchat.ui.new_group.views

import android.support.v7.widget.Toolbar
import com.roix.mapchat.R
import com.roix.mapchat.databinding.FragmentNewGroupBinding
import com.roix.mapchat.ui.common.fragments.BaseDatabindingFragment
import com.roix.mapchat.ui.new_group.viewmodels.NewGroupViewModel
import com.roix.mapchat.ui.root.models.NavigationAction
import com.roix.mapchat.ui.root.models.NavigationState
import com.roix.mapchat.ui.root.viewmodels.RootViewModel

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class NewGroupFragment : BaseDatabindingFragment<NewGroupViewModel, FragmentNewGroupBinding>() {

    private lateinit var rootViewModel: RootViewModel

    override fun getLayoutId(): Int = R.layout.fragment_new_group

    override fun setupUi() {
        super.setupUi()
        retainInstance = true
        rootViewModel = bindViewModel(RootViewModel::class.java)
        rootViewModel.toolbarAction.sub { action ->
            if (action == NavigationAction.ON_CLICKED_ADD_GROUP) {
                viewModel.createGroup().sub {
                    rootViewModel.gotoChatScreen(it)
                }
            }
        }
    }

}

