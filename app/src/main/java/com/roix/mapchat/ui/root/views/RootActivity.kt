package com.roix.mapchat.ui.root.views

import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.roix.mapchat.R
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.databinding.ActivityRootBinding
import com.roix.mapchat.ui.common.activities.BaseSingleFragmentActivity
import com.roix.mapchat.ui.common.view.ToolbarType
import com.roix.mapchat.ui.group.views.GroupFragment
import com.roix.mapchat.ui.groups.views.GroupsFragment
import com.roix.mapchat.ui.invitiation.views.InvitiationFragment
import com.roix.mapchat.ui.new_group.views.NewGroupFragment
import com.roix.mapchat.ui.root.models.NavigationAction
import com.roix.mapchat.ui.root.models.NavigationState
import com.roix.mapchat.ui.root.viewmodels.RootViewModel
import com.roix.mapchat.ui.share.views.ShareFragment

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
        viewModel.navigation.sub { state ->

            when (state) {
                NavigationState.SHARE -> {
                    setFragment(ShareFragment::class.java)
                    viewModel.toolbarTitle.value = getString(R.string.title_toolbar_share)
                    clearToolbarItems()
                    addToolbarItem(R.drawable.ic_send_white, View.OnClickListener {
                        viewModel.toolbarAction.setValueNoHistory(NavigationAction.ON_CLICKED_PROCEED_SHARE)
                    })

                }
                NavigationState.CHAT -> {
                    setFragment(GroupFragment::class.java)

                    if (viewModel.activeGroup.value?.status == GroupItem.Status.OWNER) {
                        Log.d("boux","add toolbar item  ")
                        //TODO dont show toolbar item
                        clearToolbarItems()
                        addToolbarItem(R.drawable.ic_share_white, View.OnClickListener {
                            viewModel.toolbarAction.setValueNoHistory(NavigationAction.ON_CLICKED_SHARE)
                        })
                    }
                }

                NavigationState.INVITATION -> {
                    setFragment(InvitiationFragment::class.java)
                    viewModel.toolbarTitle.value = getString(R.string.toolbar_title_invintation)
                    clearToolbarItems()
                    addToolbarItem(R.drawable.ic_send_white, View.OnClickListener {
                        viewModel.toolbarAction.setValueNoHistory(NavigationAction.ON_CLICKED_INVITE)
                    })

                }
                NavigationState.NEW_GROUP -> {
                    setFragment(NewGroupFragment::class.java)
                    viewModel.toolbarTitle.value = getString(R.string.toolbar_title_new_group)
                    clearToolbarItems()
                    addToolbarItem(R.drawable.ic_send_white, View.OnClickListener {
                        viewModel.toolbarAction.setValueNoHistory(NavigationAction.ON_CLICKED_ADD_GROUP)
                    })
                }
                NavigationState.GROUP_LIST -> {
                    viewModel.toolbarTitle.value = getString(R.string.app_name)
                    setFragment(GroupsFragment::class.java)
                }
                NavigationState.FINISHED -> supportFinishAfterTransition()
            }
        }
        viewModel.toolbarAction.sub {
            when (it) {
                NavigationAction.ON_CLICKED_SHARE -> viewModel.navigation.value = NavigationState.SHARE
            }
        }
        viewModel.toolbarTitle.sub { s ->
            setToolbarWithTitle(s)
        }
    }

    private fun setToolbarWithTitle(title: String) {
        val type = ToolbarType.Builder(this).default().setTitle(title)
        setupToolbar(type.build())
    }

    override fun goBack() {
        viewModel.goBack()
    }

}


