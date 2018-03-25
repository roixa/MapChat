package com.roix.mapchat.ui.root.views

import android.annotation.SuppressLint
import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.roix.mapchat.R
import com.roix.mapchat.data.models.GroupItem
import com.roix.mapchat.databinding.ActivityRootBinding
import com.roix.mapchat.ui.common.activities.BaseSingleFragmentActivity
import com.roix.mapchat.ui.common.view.ToolbarType
import com.roix.mapchat.ui.group.views.GroupFragment
import com.roix.mapchat.ui.groups.views.GroupsFragment
import com.roix.mapchat.ui.invitiation.viewmodels.InvitiationViewModel
import com.roix.mapchat.ui.invitiation.views.InvitiationFragment
import com.roix.mapchat.ui.new_group.viewmodels.NewGroupViewModel
import com.roix.mapchat.ui.new_group.views.NewGroupFragment
import com.roix.mapchat.ui.root.models.NavigationAction
import com.roix.mapchat.ui.root.models.Screen
import com.roix.mapchat.ui.root.models.ToolbarState
import com.roix.mapchat.ui.root.viewmodels.RootViewModel
import com.roix.mapchat.ui.share.viewmodels.ShareViewModel
import com.roix.mapchat.ui.share.views.ShareFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.AppNavigator
import ru.terrakok.cicerone.commands.Command


/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class RootActivity : BaseSingleFragmentActivity<RootViewModel, ActivityRootBinding>() {

    lateinit var shareViewModel: ShareViewModel
    lateinit var invitationViewModel: InvitiationViewModel
    lateinit var newGroupViewModel: NewGroupViewModel

    override fun getFragmentContainerId(): Int = R.id.container

    override fun getToolbar(): Toolbar? = binding.toolbar!!.tb

    override fun getLayoutId(): Int = R.layout.activity_root

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.hasExtra("group") == true) {
            val group = intent.getSerializableExtra("group") as GroupItem
            if (group != null) {
                viewModel.onOpenFromGroupNotification(group)
            }

        }
    }

    override fun setupUi() {
        super.setupUi()
        listenDynamicLinks()

        shareViewModel = bindViewModel(ShareViewModel::class.java)
        invitationViewModel = bindViewModel(InvitiationViewModel::class.java)
        newGroupViewModel = bindViewModel(NewGroupViewModel::class.java)

        viewModel.toolbarState.sub {
            when (it) {
                ToolbarState.ROOT -> {
                    setToolbarWithTitle(getString(R.string.app_name))
                }
                ToolbarState.NEW_GROUP -> {
                    setToolbarWithTitle(getString(R.string.toolbar_title_new_group))
                    clearToolbarItems()
                    addToolbarItem(R.drawable.ic_send_white, View.OnClickListener {
                        newGroupViewModel.createGroup().sub {
                            if (it != null) viewModel.gotoChatScreen(it, true)
                        }
                    })

                }
                ToolbarState.GROUP -> {
                    setToolbarWithTitle(viewModel.activeGroup.value?.name ?: "")
                    if (viewModel.activeGroup.value?.status == GroupItem.Status.OWNER) {
                        Log.d("boux", "add toolbar item  ")
                        //TODO dont show toolbar item
                        clearToolbarItems()
                        addToolbarItem(R.drawable.ic_share_white, View.OnClickListener {
                            viewModel.gotoShareScreen()
                        })
                    }
                }
                ToolbarState.SHARE -> {
                    setToolbarWithTitle(getString(R.string.title_toolbar_share))
                    clearToolbarItems()
                    addToolbarItem(R.drawable.ic_share_white, View.OnClickListener {
                        shareViewModel.shareClickEvent().subNoHistory { s ->
                            if (s != null) sendShareIntent(s)
                        }
                    })
                }
                ToolbarState.ENTER_GROUP -> {
                    setToolbarWithTitle(getString(R.string.toolbar_title_invintation))
                    clearToolbarItems()
                    addToolbarItem(R.drawable.ic_send_white, View.OnClickListener {
                        invitationViewModel.enterToGroup(viewModel.activeGroup.value!!.ownerUUid).sub {
                            viewModel.gotoChatScreen(it, true)
                        }
                    })

                }

            }
        }
    }

    private fun setToolbarWithTitle(title: String) {
        val type = ToolbarType(this, title = title)
        setupToolbar(type)
    }

    override fun getNavigator(): Navigator? = object : AppNavigator(this, R.id.container) {

        override fun createActivityIntent(context: Context?, screenKey: String?, data: Any?): Intent? {
            return null
        }

        @SuppressLint("ResourceType")
        override fun setupFragmentTransactionAnimation(command: Command?, currentFragment: Fragment?, nextFragment: Fragment?, fragmentTransaction: FragmentTransaction?) {
            fragmentTransaction?.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left)
        }

        override fun createFragment(screenKey: String, data: Any?): Fragment? {
            Log.d("boux", "navigation screen " + screenKey)
            when (screenKey) {
                Screen.SHARE -> return ShareFragment()
                Screen.GROUP -> return GroupFragment()
                Screen.INVITATION -> return InvitiationFragment()
                Screen.NEW_GROUP -> return NewGroupFragment()
                Screen.GROUP_LIST -> return GroupsFragment()
            }
            return null
        }
    }


    override fun goBack() {
        if (getCurrentFragment()?.goBack() != true) {
            viewModel.goBack()
        }
    }

    private fun listenDynamicLinks() {
        //TODO maybe inject this
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(intent)
                .addOnSuccessListener(this) { pendingDynamicLinkData ->
                    if (pendingDynamicLinkData != null) {
                        val deepLink = pendingDynamicLinkData.link
                        val uuid = deepLink.getQueryParameter("q")
                        Log.d("boux", "getDynamicLink: deepLink" + deepLink + " query " + uuid)
                        viewModel.onReceiveDeepLink(uuid.toLong())
                    }

                }
                .addOnFailureListener(this) { e -> Log.w("boux", "getDynamicLink:onFailure", e) }

    }

    private fun sendShareIntent(link: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, link)
        sendIntent.type = "text/plain"
        startActivity(sendIntent)

    }


}


