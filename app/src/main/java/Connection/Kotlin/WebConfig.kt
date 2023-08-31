package Connection.Kotlin

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

/**
 * Created by Alan Arenas on 23/0/2022.
 */
class WebConfig {
    var server: String? = null
        private set
    var hostname: String? = null
        private set
    var application: String? = null
        private set
    var namespace: String? = null
        private set
    var port = 0
        private set

    private fun setServer(server: String) {
        this.server = server
    }

    private fun setNamespace(namespace: String) {
        this.namespace = namespace
    }

    constructor() {
        hostname = ""
        port = 80
        setServer("")
        application = ""
        setNamespace("")
    }

    constructor(context: Context?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context!!)
        hostname = preferences.getString("hostname", "")
        port = preferences.getInt("port", 80)
        application = preferences.getString("application", "")
        setServer(hostname + (if (port != 80) ":$port" else "") + if (application!!.isNotEmpty()) "/$application" else "")
        setNamespace("http://aarenas.pe/")
    }
}