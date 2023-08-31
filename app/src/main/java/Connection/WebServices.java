package Connection;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.SoapFault12;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

/**
 * Created by Alan Arenas on 20/04/2022.
 */
public class WebServices extends AsyncTask<Void, Integer, Void> {

    private String URL;
    private String SOAP_ACTION;     //Información del WebService
    private SoapObject REQUEST;     //Parámetro(s) del WebService

    private Context CONTEXT;
    private OnResult CALLBACK;
    private int PROCESS_ID;

    private Result RESULT;

    ProgressDialog progressDialog;

    public WebServices(String namespace, String url, String methodName,
                       String soapAction, Context context, OnResult callback, int processId)
    {
        this.CONTEXT = context;
        this.CALLBACK = callback;
        this.PROCESS_ID = processId;

        this.URL = url;
        this.SOAP_ACTION = soapAction;

        this.REQUEST = new SoapObject(namespace, methodName);

        this.RESULT = new Result();
        this.RESULT.setResultCode(new ResultCode());
    }

    public void addParameter(String key, Object value)
    {
        this.REQUEST.addProperty(key, value);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(this.CONTEXT);
        progressDialog.setMessage("Cargando...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        //envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(this.REQUEST);

        HttpTransportSE transportSE = new HttpTransportSE(this.URL, 15000);
        transportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        //transportSE.debug = true;

        try
        {
            transportSE.call(this.SOAP_ACTION, envelope);
            //transportSE.getServiceConnection().disconnect();
            this.RESULT.setResult(envelope.getResponse());
            this.RESULT.getResultCode().setId(Result.RESULT_OK);
        }
        catch (Exception ex)
        {
            this.RESULT.getResultCode().setErrorResult(new ErrorResult());
            this.RESULT.getResultCode().getErrorResult().setInnerException(ex);

            if (ex instanceof ConnectException ||
                    ex instanceof UnknownHostException)
            {
                this.RESULT.getResultCode().setId(Result.RESULT_OFFLINE);      //Sin conexión
                this.RESULT.getResultCode().getErrorResult().setId(ErrorResult.ERROR_CONNECTION);
            }
            else if (ex instanceof XmlPullParserException)
            {
                this.RESULT.getResultCode().setId(Result.RESULT_ERROR);        //Servicio inexistente
                this.RESULT.getResultCode().getErrorResult().setId(ErrorResult.ERROR_NON_EXISTENT_SERVICE);
            }
            else if (ex instanceof SoapFault12)
            {
                this.RESULT.getResultCode().setId(Result.RESULT_ERROR);        //Método inexistente
                this.RESULT.getResultCode().getErrorResult().setId(ErrorResult.ERROR_NON_EXISTENT_METHOD);
            }
            else
            {
                this.RESULT.getResultCode().setId(Result.RESULT_ERROR);        //Error desconocido
                this.RESULT.getResultCode().getErrorResult().setId(ErrorResult.ERROR_UNKNOWN);
            }

            Log.e("WebServiceError", (ex.getMessage() != null ? ex.getMessage() : "Error en los servicios web"));
        }

        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        this.RESULT.setResult(null);
        this.RESULT.getResultCode().setId(Result.RESULT_CANCELED);
        //this.RESULT = "Operación cancelada por el usuario";

        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        progressDialog.dismiss();
        //this.CALLBACK.processFinish(this.RESULT, this.PROCESS_ID, this.idResult);
        this.CALLBACK.processFinish(this.RESULT, this.PROCESS_ID);
    }

    public interface OnResult {

        //void processFinish(Object result, int processId, int idResult);
        void processFinish(Result result, int processId);
    }


    public class Result {

        public final static int RESULT_ERROR = -1;
        public final static int RESULT_OFFLINE = 0;
        public final static int RESULT_OK = 1;
        public final static int RESULT_CANCELED = -2;
        protected Object Result;            //Resultado del WebService
        protected ResultCode ResultCode;    //Condición del WebService (Ok, KO, NO)

        public Object getResult() {
            return Result;
        }

        public void setResult(Object result) {
            Result = result;
        }

        public WebServices.ResultCode getResultCode() {
            return ResultCode;
        }

        public void setResultCode(WebServices.ResultCode resultCode) {
            ResultCode = resultCode;
        }
    }

    public class ResultCode {
        protected int Id;
        protected ErrorResult ErrorResult;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public WebServices.ErrorResult getErrorResult() {
            return ErrorResult;
        }

        public void setErrorResult(WebServices.ErrorResult errorResult) {
            ErrorResult = errorResult;
        }
    }

    public class ErrorResult {
        public final static int ERROR_CONNECTION = 1;
        public final static int ERROR_NON_EXISTENT_SERVICE = 2;
        public final static int ERROR_NON_EXISTENT_METHOD = 3;
        public final static int ERROR_UNKNOWN = 99;

        protected int Id;
        protected String Description;
        protected Exception InnerException;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public Exception getInnerException() {
            return InnerException;
        }

        public void setInnerException(Exception innerException) {
            InnerException = innerException;
        }
    }

    public static boolean isNull(Object o)
    {
        return (o == null || o.toString().equals("null"));
    }
}
