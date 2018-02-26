package com.roix.mapchat.data.repositories.dynamic_links

import android.net.Uri
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.roix.mapchat.toothpick.common.ApplicationScope
import io.reactivex.Single
import javax.inject.Inject


/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */
@ApplicationScope
class DynamicLinksRepository : IDynamicLinksRepository {

    val dynamicLink = FirebaseDynamicLinks.getInstance()

    @Inject constructor() {}

    override fun buildDynamicInviteLink(uuid: Long): Single<String> = Single.create { e ->
        val url = dynamicLink.createDynamicLink()
                .setLink(Uri.parse("https://mapchat.com/query?q="+uuid))
                .setDynamicLinkDomain("z9695.app.goo.gl")
                .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
                .buildDynamicLink().uri
        dynamicLink.createDynamicLink().setLongLink(url).buildShortDynamicLink()
                .addOnCompleteListener { task ->
                    e.onSuccess(task.result.shortLink.toString())
                }

    }

}
