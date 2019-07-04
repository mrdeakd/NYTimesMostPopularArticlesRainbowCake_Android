package com.ddworks.nytimesmostpopular.ui.details

import com.ddworks.nytimesmostpopular.domain.DomainNews

sealed class DetailsViewState

object NoConnection : DetailsViewState()

object Loading : DetailsViewState()

data class NewsLoaded(val data: DomainNews) : DetailsViewState()