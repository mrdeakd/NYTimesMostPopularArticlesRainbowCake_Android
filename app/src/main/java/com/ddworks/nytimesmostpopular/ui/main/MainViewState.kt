package com.ddworks.nytimesmostpopular.ui.main

import com.ddworks.nytimesmostpopular.domain.DomainNews

sealed class MainViewState

object NoConnection : MainViewState()

object Loading : MainViewState()

object Loaded : MainViewState()

data class NewsLoaded(val dataList: List<DomainNews> = emptyList()) : MainViewState()

data class NewsDetailedLoaded(val newsId : Int) : MainViewState()
