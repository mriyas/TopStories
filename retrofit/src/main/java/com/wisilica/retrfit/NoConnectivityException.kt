package com.wisilica.retrfit

import java.io.IOException

class NoConnectivityException: IOException() {
    override val message: String?
        get() = "No Internet Connection"

}