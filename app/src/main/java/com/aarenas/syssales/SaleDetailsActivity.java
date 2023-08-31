package com.aarenas.syssales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.List;

import Connection.WebMethods;
import Connection.WebServices;
import Data.Objects.Customer;
import Data.Objects.Sale;
import Data.Objects.SaleDetail;
import Data.Objects.User;
import Design.CustomersAdapter;
import Design.SaleDetailsAdapter;

public class SaleDetailsActivity extends AppCompatActivity implements WebServices.OnResult {

    //Controls:
    GridView gvData_SaleDetailsActivity;

    //Parameters:
    Bundle parameters;
    User objUser;
    Sale objSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_details);

        parameters = getIntent().getExtras();
        LoadParameters(parameters);

        gvData_SaleDetailsActivity = findViewById(R.id.gvData_SaleDetailsActivity);

        WebMethods objWebMethods = new WebMethods(this, this);
        objWebMethods.getSaleDetailsBySale(objSale.getId());
    }

    @Override
    public void processFinish(WebServices.Result result, int processId) {
        try
        {
            if (result.getResultCode().getId() == WebServices.Result.RESULT_OK)
            {
                if (!WebServices.isNull(result.getResult()))
                {
                    if (processId == WebMethods.TYPE_LIST_SALE_DETAILS)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<SaleDetail> list = SaleDetail.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            SaleDetailsAdapter<SaleDetail> adapter = new SaleDetailsAdapter<>(getApplicationContext(), list);
                            gvData_SaleDetailsActivity.setAdapter(adapter);
                        }
                    }
                }
            }
            else if (result.getResultCode().getId() == WebServices.Result.RESULT_OFFLINE)
            {
                throw new Exception(getString(R.string.message_without_connection));
            }
            else if (result.getResultCode().getId() == WebServices.Result.RESULT_ERROR)
            {
                throw new Exception(result.getResult() != null ? result.getResult().toString() : getString(R.string.message_web_services_error));
            }
            else
            {
                throw new Exception(getString(R.string.message_web_services_error));
            }
        }
        catch (Exception e)
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String error_message = preferences.getBoolean("depuration", false) ? e.getMessage() : getString(R.string.message_web_services_error);

            Toast.makeText(getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
            Log.e("WS_SaleDetailsAct", String.format("%s (processId: %s)", error_message, processId));
        }
    }

    private void LoadParameters(Bundle data)
    {
        if (data != null)
        {
            if (data.get("user") != null)
            {
                this.objUser = data.getParcelable("user");
            }
            if (data.get("sale") != null)
            {
                this.objSale = data.getParcelable("sale");
            }
        }
    }
}