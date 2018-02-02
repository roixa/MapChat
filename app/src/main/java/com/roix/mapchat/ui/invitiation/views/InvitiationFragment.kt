package com.roix.mapchat.ui.invitiation.views

import com.roix.mapchat.R
import com.roix.mapchat.databinding.FragmentInvitiationBinding
import com.roix.mapchat.ui.common.fragments.BaseDatabindingFragment
import com.roix.mapchat.ui.invitiation.viewmodels.InvitiationViewModel
import com.roix.mapchat.ui.root.models.NavigationAction
import com.roix.mapchat.ui.root.models.NavigationState
import com.roix.mapchat.ui.root.viewmodels.RootViewModel

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class InvitiationFragment : BaseDatabindingFragment<InvitiationViewModel, FragmentInvitiationBinding>() {

    lateinit var rootViewModel: RootViewModel

    override fun getLayoutId(): Int = R.layout.fragment_invitiation

    override fun setupUi() {
        super.setupUi()
        retainInstance = true
        rootViewModel = bindViewModel(RootViewModel::class.java)
    }

    override fun setupBinding() {
        super.setupBinding()
        rootViewModel.toolbarAction.sub { navigationAction ->
            if (navigationAction == NavigationAction.ON_CLICKED_INVITE) {
                viewModel.enterToGroup(rootViewModel.activeGroup.value!!.ownerUUid).sub {
                    rootViewModel.gotoChatScreen(it)
                }
            }
        }
    }
}

