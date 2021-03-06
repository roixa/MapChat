package com.roix.mapchat.ui.chat.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.roix.mapchat.buissness.common.IBaseListInteractor
import com.roix.mapchat.buissness.chat.ChatInteractor
import com.roix.mapchat.toothpick.chat.ChatModule
import com.roix.mapchat.data.models.MessageItem
import com.roix.mapchat.ui.common.viewmodels.BaseListViewModel
import com.roix.mapchat.ui.common.viewmodels.BasePaginationListViewModel
import javax.inject.Inject
import toothpick.config.Module

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
class ChatViewModel : BaseListViewModel<MessageItem>() {

    @Inject lateinit var interactor: ChatInteractor

    val message = MutableLiveData<String>()

    var ownerUuid: Long? = null
    var author: String? = null

    override fun getInteractor(): IBaseListInteractor<MessageItem> = ChatInteractor()
    override fun getModule(): Module = ChatModule()

    fun onReceiveData(ownerUuid: Long, author: String) {
        this.ownerUuid = ownerUuid
        this.author = author
        interactor.getMessages(ownerUuid).toObservable().sub { list ->
            Log.d("boux", "list size " + list)
            items.clear()
            items.addAll(list)
            Log.d("boux", "list size " + items.size)

        }
    }

    fun onPostMessageClicked() {
        if (ownerUuid != null && author != null) {
            interactor.postMessage(ownerUuid!!, message.value!!, author!!, System.currentTimeMillis()).sub {
                message.value = ""
            }
        }
    }


}
