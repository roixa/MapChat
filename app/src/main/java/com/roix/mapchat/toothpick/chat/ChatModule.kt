package com.roix.mapchat.toothpick.chat

import com.roix.mapchat.buissness.chat.IChatInteractor
import com.roix.mapchat.buissness.chat.ChatInteractor
import toothpick.config.Module

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class ChatModule : Module() {
    init {
        bind(IChatInteractor::class.java).to(ChatInteractor::class.java).instancesInScope()
    }
}