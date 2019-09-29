package com.rubenquadros.epicnews.data.remote.model

import com.google.gson.annotations.SerializedName

class Source {

    @SerializedName("id")
    var id: Any? = null
    @SerializedName("name")
    var name: String? = null

}
