package com.aarenas.syssales;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import Connection.WebMethods;
import Connection.WebServices;
import Data.Objects.Company;
import Data.Objects.Configuration;
import Data.Objects.User;

public class MainActivity extends AppCompatActivity implements WebServices.OnResult {

    //Controls:
    private ImageView imgBackground_Main;
    private TextView tvCompanyName_Main, tvUsername_Main, tvOutlet_Main;
    private Button btnNewSale_Main, btnSalesBySeller_Main, btnAccountsReceivable_Main,
            btnCollectionSheet_Main,
            btnCustomers_Main, btnProducts_Main;

    //Parameters:
    private Bundle parameters;
    private User objUser;
    private String saleId;

    //Asynchronous data:
    private Configuration objConfiguration;
    private Configuration objConfigurationXUser;
    private Company objCompany;

    //Variables:
    private WebMethods objWebMethods;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parameters = getIntent().getExtras();
        LoadParameters(parameters);

        objWebMethods = new WebMethods(this, this);

        imgBackground_Main = (ImageView) findViewById(R.id.imgBackground_Main);
        tvCompanyName_Main = (TextView) findViewById(R.id.tvCompanyName_Main);
        tvUsername_Main = (TextView) findViewById(R.id.tvUsername_Main);
        tvOutlet_Main = (TextView) findViewById(R.id.tvOutlet_Main);
        btnNewSale_Main = (Button) findViewById(R.id.btnNewSale_Main);
        btnSalesBySeller_Main = (Button) findViewById(R.id.btnSalesBySeller_Main);
        btnAccountsReceivable_Main = (Button) findViewById(R.id.btnAccountsReceivable_Main);
        btnCollectionSheet_Main = (Button) findViewById(R.id.btnCollectionSheet_Main);
        btnCustomers_Main = (Button) findViewById(R.id.btnCustomers_Main);
        btnProducts_Main = (Button) findViewById(R.id.btnProducts_Main);

        //imgBackground_Main.setImageDrawable(R.drawable.app_background);

        btnNewSale_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (objConfigurationXUser != null && objConfigurationXUser.isOptionNewSale())
                {
                    objWebMethods.openSale((int)objUser.getEmployee().getPerson().getId(), objUser.getId());
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.message_unsupported_user, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSalesBySeller_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (objConfigurationXUser != null && objConfigurationXUser.isOptionSales())
                {
                    Intent activity = new Intent(getApplicationContext(), SalesActivity.class);
                    OutputParameters(activity);
                    startActivity(activity);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.message_unsupported_user, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAccountsReceivable_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (objConfigurationXUser != null && objConfigurationXUser.isOptionAccountReceivable())
                {
                    Intent activity = new Intent(getApplicationContext(), AccountsReceivableActivity.class);
                    OutputParameters(activity);
                    startActivity(activity);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.message_unsupported_user, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCollectionSheet_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (objConfigurationXUser != null && objConfigurationXUser.isOptionCollectionSheet())
                {
                    Intent activity = new Intent(getApplicationContext(), CollectionSheetActivity.class);
                    OutputParameters(activity);
                    startActivity(activity);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.message_unsupported_user, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCustomers_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(getApplicationContext(), CustomersActivity.class);
                OutputParameters(activity);
                activity.putExtra(CustomersActivity.CONST_RESULT, CustomersActivity.RESULT_VIEW);
                startActivity(activity);
            }
        });

        btnProducts_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(getApplicationContext(), ProductsActivity.class);
                OutputParameters(activity);
                activity.putExtra(ProductsActivity.CONST_RESULT, ProductsActivity.RESULT_VIEW);
                startActivity(activity);
            }
        });

        Load();

        objWebMethods.getConfiguration();
        objWebMethods.getConfigurationXUser(objUser.getEmployee().getPerson().getId());
        objWebMethods.getCompany();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            setResult(RESULT_OK);
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Pulse una vez más para salir", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void processFinish(WebServices.Result result, int processId) {
        try
        {
            if (result.getResultCode().getId() == WebServices.Result.RESULT_OK)
            {
                if (!WebServices.isNull(result))
                {
                    if (processId == WebMethods.TYPE_LIST_CONFIGURATIONS)
                    {
                        JSONObject jsonObject = new JSONObject(result.getResult().toString());
                        objConfiguration = Configuration.getItem(jsonObject);
                        if (objConfiguration != null)
                        {
                            btnNewSale_Main.setVisibility(objConfiguration.isOptionNewSale() ? View.VISIBLE : View.GONE);
                            btnSalesBySeller_Main.setVisibility(objConfiguration.isOptionSales() ? View.VISIBLE : View.GONE);
                            btnAccountsReceivable_Main.setVisibility(objConfiguration.isOptionAccountReceivable() ? View.VISIBLE : View.GONE);
                            btnCollectionSheet_Main.setVisibility(objConfiguration.isOptionCollectionSheet() ? View.VISIBLE : View.GONE);
                            btnCustomers_Main.setVisibility(View.VISIBLE);
                            btnProducts_Main.setVisibility(View.VISIBLE);

                            if (objConfiguration.isOptionShowNotifications())
                            {
                                objWebMethods.getNotifications();
                            }
                        }
                    }
                    else if (processId == WebMethods.TYPE_LIST_NOTIFICATIONS)
                    {
                        SoapObject array = (SoapObject)result.getResult();
                        if (array.getPropertyCount() > 0)
                        {
                            String[] _array = new String[array.getPropertyCount()];
                            for (int i = 0; i < array.getPropertyCount(); i++)
                            {
                                _array[i] = array.getProperty(i).toString();
                            }
                            showMessage(_array);
                        }
                    }
                    else if (processId == WebMethods.TYPE_LIST_CONFIGURATIONS_X_USER)
                    {
                        JSONObject jsonObject = new JSONObject(result.getResult().toString());
                        objConfigurationXUser = Configuration.getItem(jsonObject);
                    }
                    else if (processId == WebMethods.TYPE_LIST_PRINCIPAL_COMPANY)
                    {
                        JSONObject jsonObject = new JSONObject(result.getResult().toString());
                        objCompany = Company.getItem(jsonObject);
                        if (objCompany != null)
                        {
                            //tvCompanyName_Main.setText(objCompany.getBusinessName());
                            tvCompanyName_Main.setText(objCompany.getCommercialName());
                        }
                    }
                    else if (processId == WebMethods.TYPE_OPEN_SALE)
                    {
                        SoapObject array = (SoapObject)result.getResult();
                        if (array.getPropertyCount() > 0)
                        {
                            boolean reply = (boolean)array.getProperty(0);
                            String message = (String)array.getProperty(1);

                            if (reply)
                            {
                                this.saleId = message;

                                Intent activity = new Intent(getApplicationContext(), SaleActivity.class);
                                OutputParameters(activity);
                                startActivity(activity);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
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
            Log.e("WS_Main", String.format("%s (processId: %s)", error_message, processId));
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
        }
    }

    private void OutputParameters(Intent intent)
    {
        intent.putExtras(parameters);
        intent.putExtra("configuration", objConfiguration);
        intent.putExtra("configuration_user", objConfigurationXUser);
        intent.putExtra("saleId", saleId);
    }

    private void Load()
    {
        if (objUser != null)
        {
            tvUsername_Main.setText(String.format("Usuario: %s", objUser.getEmployee().getPerson().getNames()));
            tvOutlet_Main.setText(String.format("Punto de venta: %s", objUser.getEmployee().getOutlet().getDescription()));
        }
    }

    private void showMessage(String[] array)
    {
        String message = TextUtils.join("\n", array);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}