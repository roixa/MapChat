package com.roix.mapchat.ui.chat.viewmodels

import com.roix.mapchat.buissness.chat.IChatInteractor
import com.roix.mapchat.toothpick.chat.ChatModule
import com.roix.mapchat.ui.common.viewmodels.BaseLifecycleViewModel
import javax.inject.Inject
import toothpick.config.Module

/**
 * Created by roix on 06.01.2018.
 */
class ChatViewModel : BaseLifecycleViewModel() {

    @Inject
    protected lateinit var interactor: IChatInteractor

    override fun getModule(): Module = ChatModule()
}
