package com.hemanth.winuhall.common

import okio.IOException

class NoConnectivityException : IOException() {
    override val message: String?
        get() = "No Internet Connection"
}