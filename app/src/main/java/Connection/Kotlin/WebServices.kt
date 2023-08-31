package Connection.Kotlin

import android.content.Context
import android.util.Log
import kotlinx.coroutines.*
import org.ksoap2.SoapEnvelope
import org.ksoap2.SoapFault12
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import org.xmlpull.v1.XmlPullParserException
import java.lang.Exception
import java.net.ConnectException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

public class WebServices : CoroutineScope {
    private var job : Job = Job();
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var URL: String? = null
    private var SOAP_ACTION         //Información del WebService
            : String? = null
    private var REQUEST             //Parámetro(s) del WebService
            : SoapObject? = null
    private var RESULT              //Resultado del WebService
            : Any? = null
    private var idResult            //Condición del WebService (Ok, KO, NO)
            = 0
    private var CONTEXT: Context? = null
    private var CALLBACK: OnResult? = null
    private var PROCESS_ID = 0

    constructor() {}
    constructor(namespace: String?, url: String?, methodName: String?,
                soapAction: String?, context: Context?, callback: OnResult?, processId: Int) {
        CONTEXT = context
        CALLBACK = callback
        PROCESS_ID = processId
        URL = url
        SOAP_ACTION = soapAction
        REQUEST = SoapObject(namespace, methodName)
    }

    fun addParameter(key: String?, value: Any?) {
        REQUEST!!.addProperty(key, value)
    }

    private fun onPreExecute() {

    }

    fun execute() = launch {
        onPreExecute()
        withContext(Dispatchers.IO){
            doInBackground()
        }
        onPostExecute()
    }

    private fun doInBackground() {
        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER12)
        envelope.dotNet = true
        envelope.isAddAdornments = false
        envelope.setOutputSoapObject(REQUEST)
        val transportSE = HttpTransportSE(URL, 15000)
        transportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
        try {
            transportSE.call(SOAP_ACTION, envelope)
            RESULT = envelope.response
            idResult = RESULT_OK
        } catch (e: Exception) {
            idResult = if (e is ConnectException || e is UnknownHostException) {
                RESULT_OFFLINE
            } //Sin conexión
            else if (e is XmlPullParserException) {
                RESULT_ERROR
            } //Servicio inexistente
            else if (e is SoapFault12) {
                RESULT_ERROR
            } //Método inexistente
            else {
                RESULT_ERROR
            }
            RESULT = e.message
            Log.e("WebServiceError", (if (e.message != null) e.message else "")!!)
        }
    }

    fun cancel() {
        idResult = RESULT_CANCELED
        RESULT = null

        job.cancel()
    }

    private fun onPostExecute() {
        CALLBACK!!.processFinish(RESULT, PROCESS_ID, idResult)
    }

    companion object {
        const val RESULT_ERROR = -1
        const val RESULT_OFFLINE = 0
        const val RESULT_OK = 1
        const val RESULT_CANCELED = -2
        @JvmStatic
        fun isNull(o: Any?): Boolean {
            return o == null || o.toString() == "null"
        }
    }

    interface OnResult {
        fun processFinish(result: Any?, processId: Int, idResult: Int)
    }
}