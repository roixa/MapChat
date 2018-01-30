package com.roix.mapchat.buissness.chat

import com.roix.mapchat.data.repositories.firebase.IFirebaseRepository
import com.roix.mapchat.data.repositories.firebase.FirebaseRepository
import javax.inject.Inject
import com.roix.mapchat.data.models.MessageItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class ChatInteractor : IChatInteractor {

    @Inject constructor()

    @Inject lateinit var firebaseRepository: FirebaseRepository

    override fun loadItems(page: Long): Single<List<MessageItem>> = Single.create { e -> e.onSuccess(emptyList()) }

    override fun postMessage(message: String, author: String): Completable= Completable.create{e ->

    }

    override fun getMesages(): Flowable<List<MessageItem>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
