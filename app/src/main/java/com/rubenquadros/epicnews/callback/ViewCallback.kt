package com.rubenquadros.epicnews.callback

import com.rubenquadros.epicnews.data.local.entity.NewsEntity

interface ViewCallback {
    fun onActionPerformed(action: String, data: NewsEntity?)
}