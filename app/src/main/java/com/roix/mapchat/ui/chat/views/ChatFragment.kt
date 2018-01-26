package com.roix.mapchat.ui.chat.views

import android.support.v7.widget.Toolbar
import com.roix.mapchat.R
import com.roix.mapchat.databinding.FragmentChatBinding
import com.roix.mapchat.ui.common.fragments.BaseDatabindingFragment
import com.roix.mapchat.ui.chat.viewmodels.ChatViewModel

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class ChatFragment : BaseDatabindingFragment<ChatViewModel, FragmentChatBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_chat

}

