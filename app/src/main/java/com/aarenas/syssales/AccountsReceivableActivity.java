package com.aarenas.syssales;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.aarenas.syssales.databinding.ActivityAccountsReceivableBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;

import java.util.List;

import Connection.WebMethods;
import Connection.WebServices;
import Data.MyMath;
import Data.Objects.CreditSale;
import Data.Objects.User;
import Design.AccountsReceivableAdapter;

public class AccountsReceivableActivity extends AppCompatActivity implements WebServices.OnResult {

    private ActivityAccountsReceivableBinding binding;

    //Controls:
    TextInputEditText etBalance_AccountsReceivableActivity;
    TextView tvItemsCount_AccountsReceivableActivity;
    GridView gvData_AccountsReceivableActivity;

    //Parameters:
    Bundle parameters;
    User objUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parameters = getIntent().getExtras();
        LoadParameters(parameters);

        binding = ActivityAccountsReceivableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        etBalance_AccountsReceivableActivity = findViewById(R.id.etTotal_SalesActivity);
        tvItemsCount_AccountsReceivableActivity = findViewById(R.id.tvItemsCount_AccountsReceivableActivity);
        gvData_AccountsReceivableActivity = findViewById(R.id.gvData_SalesActivity);

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK)
                        {
                            WebMethods objWebMethods = new WebMethods(AccountsReceivableActivity.this, AccountsReceivableActivity.this);
                            objWebMethods.getTotalAmountCreditSales(objUser.getEmployee().getId());
                        }
                    }
                });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Recargando...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                startActivity(getIntent());
                finish();
                overridePendingTransition(0, 0);
            }
        });

        gvData_AccountsReceivableActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CreditSale objCreditSale = (CreditSale) adapterView.getItemAtPosition(i);

                if (objCreditSale != null)
                {
                    Intent activity = new Intent(getApplicationContext(), CreditSaleQuotesActivity.class);
                    activity.putExtra("credit_sale", objCreditSale);
                    resultLauncher.launch(activity);
                }
            }
        });

        WebMethods objWebMethods = new WebMethods(this, this);
        objWebMethods.getTotalAmountCreditSales(objUser.getEmployee().getId());
    }

    @Override
    public void processFinish(WebServices.Result result, int processId) {
        try
        {
            if (result.getResultCode().getId() == WebServices.Result.RESULT_OK)
            {
                if (!WebServices.isNull(result.getResult()))
                {
                    if (processId == WebMethods.TYPE_TOTAL_AMOUNT_CREDIT_SALES)
                    {
                        Float total = Float.parseFloat(result.getResult().toString());
                        etBalance_AccountsReceivableActivity.setText(MyMath.toDecimal(total, 2));

                        WebMethods objWebMethods = new WebMethods(this, this);
                        objWebMethods.getCreditSales(objUser.getEmployee().getId());
                    }
                    else if (processId == WebMethods.TYPE_LIST_CREDIT_SALES)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<CreditSale> list = CreditSale.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            AccountsReceivableAdapter adapter = new AccountsReceivableAdapter(getApplicationContext(), list);
                            gvData_AccountsReceivableActivity.setAdapter(adapter);

                            tvItemsCount_AccountsReceivableActivity.setText(String.format("%s item(s)", list.size()));
                        }
                    }
                }
                else
                {
                    if (processId == WebMethods.TYPE_TOTAL_AMOUNT_CREDIT_SALES)
                    {
                        etBalance_AccountsReceivableActivity.setText(MyMath.toDecimal(0F, 2));
                    }
                    else if (processId == WebMethods.TYPE_LIST_CREDIT_SALES)
                    {
                        gvData_AccountsReceivableActivity.setAdapter(null);
                        Toast.makeText(getApplicationContext(), R.string.message_no_data, Toast.LENGTH_SHORT).show();

                        tvItemsCount_AccountsReceivableActivity.setText(String.format("%s item(s)", 0));
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
            Log.e("WS_AccountsR", String.format("%s (processId: %s)", error_message, processId));
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
    }
}