package com.roix.mapchat.buissness.chat

import com.roix.mapchat.data.models.MessageItem
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class ChatInteractor : IChatInteractor {

    @Inject constructor()

    @Inject lateinit var firebaseRepository: FirebaseRepository

    override fun loadItems(page: Long): Single<List<MessageItem>> = Single.never()

    override fun postMessage(ownerUuid: Long, message: String, author: String, unixTimeStamp: Long): Completable= firebaseRepository
            .postMessagesInGroupChat(ownerUuid,message,author,unixTimeStamp,null)

    override fun getMessages(ownerUuid: Long): Flowable<List<MessageItem>> = firebaseRepository.listenMessagesInGroupChat(ownerUuid).map {
        it.asReversed()
    }

}
