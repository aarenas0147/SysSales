package Connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Alan Arenas on 07/06/2020.
 */
public class WebConfig {

    private String server, hostname, application, namespace;
    private int port;

    public String getServer() {
        return server;
    }

    private void setServer(String server) {
        this.server = server;
    }

    public String getHostname() {
        return hostname;
    }

    private void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    private void setPort(int port) {
        this.port = port;
    }

    public String getApplication() {
        return application;
    }

    private void setApplication(String application) {
        this.application = application;
    }

    public String getNamespace() {
        return namespace;
    }

    private void setNamespace(String namespace) {
        this.namespace = namespace;
    }


    public WebConfig() {
        setHostname("");
        setPort(80);
        setServer("");
        setApplication("");
        setNamespace("");
    }

    public WebConfig(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        setHostname(preferences.getString("hostname", ""));
        setPort(preferences.getInt("port", 80));
        setApplication(preferences.getString("application", ""));
        setServer(getHostname() + (getPort() != 80 ? ":" + getPort() : "") + (!getApplication().isEmpty() ? "/" + getApplication() : ""));
        setNamespace("http://aarenas.pe/");
    }
}
