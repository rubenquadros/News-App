package com.rubenquadros.epicnews.data.remote.model

import com.google.gson.annotations.SerializedName

class TopNewsResponse {

    @SerializedName("articles")
    var articles: List<Article>? = null
    @SerializedName("status")
    var status: String? = null
    @SerializedName("totalResults")
    var totalResults: Long? = null

}
