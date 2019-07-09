package com.ddworks.nytimesmostpopular.ui.main

import com.ddworks.nytimesmostpopular.domain.DomainNews

sealed class MainViewState

object NoConnection : MainViewState()

object Loading : MainViewState()

object DetailPageLoaded : MainViewState()

data class NewsLoaded(val dataList: List<DomainNews> = emptyList()) : MainViewState()

data class NewsSearching(val dataList: List<DomainNews> = emptyList(), val searchString: String) : MainViewState()

data class NewsDetailedLoaded(val newsId : Int) : MainViewState()
