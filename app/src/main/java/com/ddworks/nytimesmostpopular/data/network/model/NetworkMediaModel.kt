package com.ddworks.nytimesmostpopular.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkMediaModel(@SerializedName("media-metadata") val media_metadata: List<NetworkMediaMetaModel>)