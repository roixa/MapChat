package com.roix.mapchat.buissness.chat

import com.roix.mapchat.buissness.common.IBaseListInteractor

import com.roix.mapchat.data.models.MessageItem
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
interface IChatInteractor : IBaseListInteractor<MessageItem> {
    fun postMessage(ownerUuid: Long, message: String, author: String, unixTimeStamp: Long): Completable

    fun getMessages(ownerUuid: Long): Flowable<List<MessageItem>>
}
