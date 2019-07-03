package com.ddworks.nytimesmostpopular.ui.details

import co.zsmb.rainbowcake.withIOContext
import javax.inject.Inject

class DetailsPresenter @Inject constructor() {

    suspend fun getData(): String = withIOContext {
        ""
    }

}
