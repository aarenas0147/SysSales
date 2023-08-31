package Connection.Kotlin

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.ksoap2.SoapEnvelope
import org.ksoap2.SoapFault12
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import org.xmlpull.v1.XmlPullParserException
import java.lang.Exception
import java.net.ConnectException
import java.net.UnknownHostException

class WebServices2(
    private val namespace: String?,
    private val url: String?,
    private val methodName: String?,
    private val soapAction: String?,
    private val context: Context?,
    private val callback: OnResult?,
    private val processId: Int
) {
    private val progressDialog: ProgressDialog? = null

    suspend fun executeAsync() {
        withContext(Dispatchers.Main) {
            val progressDialog = ProgressDialog(context)
            progressDialog.setMessage("Cargando...")
            progressDialog.isIndeterminate = true
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressDialog.setCancelable(false)
            progressDialog.show()

            val result = withContext(Dispatchers.IO) {
                try {
                    val REQUEST = SoapObject(namespace, methodName)
                    val envelope = SoapSerializationEnvelope(SoapEnvelope.VER12)
                    envelope.dotNet = true
                    envelope.isAddAdornments = false
                    envelope.setOutputSoapObject(REQUEST)
                    val transportSE = HttpTransportSE(url, 15000)
                    transportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
                    transportSE.call(soapAction, envelope)
                    val response = envelope.response
                    Triple(response, RESULT_OK, null)
                } catch (e: Exception) {
                    val idResult = when (e) {
                        is ConnectException, is UnknownHostException -> RESULT_OFFLINE
                        is XmlPullParserException, is SoapFault12 -> RESULT_ERROR
                        else -> RESULT_ERROR
                    }
                    Log.e("WebServiceError", e.message ?: "")
                    Triple(e.message, idResult, e)
                }
            }

            progressDialog.dismiss()
            callback?.processFinish(result.first, processId, result.second)
        }
    }

    interface OnResult {
        fun processFinish(result: Any?, processId: Int, idResult: Int)
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
}