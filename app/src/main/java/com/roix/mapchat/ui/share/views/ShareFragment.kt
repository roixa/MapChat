package com.roix.mapchat.ui.share.views

import com.roix.mapchat.R
import com.roix.mapchat.databinding.FragmentShareBinding
import com.roix.mapchat.ui.common.fragments.BaseDatabindingFragment
import com.roix.mapchat.ui.share.viewmodels.ShareViewModel

/**
 * Created by roix template
 * https://github.com/roixa/RoixArchitectureTemplates
 */

class ShareFragment : BaseDatabindingFragment<ShareViewModel, FragmentShareBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_share

}

