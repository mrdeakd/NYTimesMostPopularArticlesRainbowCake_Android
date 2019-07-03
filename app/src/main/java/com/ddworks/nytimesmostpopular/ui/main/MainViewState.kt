package com.ddworks.nytimesmostpopular.ui.main

import com.ddworks.nytimesmostpopular.domain.DomainNews

sealed class MainViewState

object NoConnection : MainViewState()

object Loading : MainViewState()

data class NewsLoaded(val data: List<DomainNews> = emptyList()) : MainViewState()
