package Connection

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import org.ksoap2.SoapEnvelope
import org.ksoap2.SoapFault12
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import org.xmlpull.v1.XmlPullParserException
import java.lang.Exception
import java.net.ConnectException
import java.net.UnknownHostException

class WebServices2 : AsyncTask<Void?, Int?, Void?> {
    private var URL: String? = null
    private var SOAP_ACTION //Información del WebService
            : String? = null
    private var REQUEST //Parámetro(s) del WebService
            : SoapObject? = null
    private var RESULT //Resultado del WebService
            : Any? = null
    private var idResult //Condición del WebService (Ok, KO, NO)
            = 0
    private var CONTEXT: Context? = null
    private var CALLBACK: WebServices.OnResult? = null
    private var PROCESS_ID = 0
    var progressDialog: ProgressDialog? = null

    constructor() {}
    constructor(namespace: String?, url: String?, methodName: String?,
                soapAction: String?, context: Context?, callback: WebServices.OnResult?, processId: Int) {
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

    override fun onPreExecute() {
        super.onPreExecute()
        progressDialog = ProgressDialog(CONTEXT)
        progressDialog!!.setMessage("Cargando...")
        progressDialog!!.isIndeterminate = true
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    override fun doInBackground(vararg voids: Void?): Void? {
        val envelope = SoapSerializationEnvelope(SoapEnvelope.VER12)
        envelope.dotNet = true
        //envelope.implicitTypes = true;
        envelope.isAddAdornments = false
        envelope.setOutputSoapObject(REQUEST)
        val transportSE = HttpTransportSE(URL, 15000)
        transportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
        //transportSE.debug = true;
        try {
            transportSE.call(SOAP_ACTION, envelope)
            //transportSE.getServiceConnection().disconnect();
            RESULT = envelope.response
            idResult = RESULT_OK
        } catch (e: Exception) {
            if (e is ConnectException
                    || e is UnknownHostException) {
                idResult = RESULT_OFFLINE
            } //Sin conexión
            else if (e is XmlPullParserException) {
                idResult = RESULT_ERROR
            } //Servicio inexistente
            else if (e is SoapFault12) {
                idResult = RESULT_ERROR
            } //Método inexistente
            else {
                idResult = RESULT_ERROR
            }
            RESULT = e.message
            Log.e("WebServiceError", (if (e.message != null) e.message else "")!!)
        }
        return null
    }

    override fun onCancelled() {
        super.onCancelled()
        idResult = RESULT_CANCELED
        //this.RESULT = "Operación cancelada por el usuario";
        RESULT = null
        progressDialog!!.dismiss()
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        progressDialog!!.progress = values[0]!!
    }

    override fun onPostExecute(aVoid: Void?) {
        super.onPostExecute(aVoid)
        progressDialog!!.dismiss()
        CALLBACK!!.processFinish(RESULT, PROCESS_ID, idResult)
    }

    /*interface OnResult {
        fun processFinish(result: Any?, processId: Int, idResult: Int)
    }*/

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
}