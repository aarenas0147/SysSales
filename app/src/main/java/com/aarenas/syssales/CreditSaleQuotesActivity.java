package com.aarenas.syssales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.aarenas.syssales.databinding.ActivityCreditSaleQuotesBinding;

import org.json.JSONArray;

import java.util.List;

import Connection.WebMethods;
import Connection.WebServices;
import Data.Objects.CreditSale;
import Data.Objects.CreditSaleQuote;
import Data.Objects.SaleDetail;
import Design.CreditSaleQuotesAdapter;

public class CreditSaleQuotesActivity extends AppCompatActivity implements WebServices.OnResult {

    private ActivityCreditSaleQuotesBinding binding;

    //Controls:
    TextView tvItemsCount_CreditSaleQuotesActivity;
    GridView gvData_CreditSaleQuotesActivity;

    //Parameters:
    Bundle parameters;
    CreditSale objCreditSale;

    //Variables:
    private SharedPreferences preferences;
    private WebMethods objWebMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parameters = getIntent().getExtras();
        LoadParameters(parameters);

        binding = ActivityCreditSaleQuotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        tvItemsCount_CreditSaleQuotesActivity = findViewById(R.id.tvItemsCount_CreditSaleQuotesActivity);
        gvData_CreditSaleQuotesActivity = findViewById(R.id.gvData_CreditSaleQuotesActivity);

        preferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);
        objWebMethods = new WebMethods(this, this);

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK)
                        {
                            /*objWebMethods.getCreditSaleQuotes(objCreditSale.getId());*/

                            setResult(RESULT_OK, result.getData());
                            finish();
                        }
                    }
                });

        binding.fabAddCreditSaleQuotesActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(getApplicationContext(), CreditSaleQuoteActivity.class);
                activity.putExtras(parameters);
                resultLauncher.launch(activity);
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

        gvData_CreditSaleQuotesActivity.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                CreditSaleQuote objCreditSaleQuote = (CreditSaleQuote) adapterView.getItemAtPosition(i);

                if (objCreditSaleQuote != null)
                {
                    PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                    popupMenu.inflate(R.menu.custom_credit_sale_quote);
                    popupMenu.getMenu().findItem(R.id.action_delete_item).setVisible(true);
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            final int itemDelete = R.id.action_delete_item;

                            switch (menuItem.getItemId())
                            {
                                case itemDelete:
                                    objWebMethods.deleteCreditSaleQuote(objCreditSale.getId(), objCreditSaleQuote.getQuoteNumber());
                                    break;
                                default:
                                    break;
                            }
                            return true;
                        }
                    });

                    return true;
                }

                return false;
            }
        });

        objWebMethods.getCreditSaleQuotes(objCreditSale.getId());
    }

    @Override
    public void processFinish(WebServices.Result result, int processId) {
        try
        {
            if (result.getResultCode().getId() == WebServices.Result.RESULT_OK)
            {
                if (!WebServices.isNull(result.getResult()))
                {
                    if (processId == WebMethods.TYPE_LIST_CREDIT_SALE_QUOTES)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<CreditSaleQuote> list = CreditSaleQuote.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            CreditSaleQuotesAdapter adapter = new CreditSaleQuotesAdapter(getApplicationContext(), list);
                            gvData_CreditSaleQuotesActivity.setAdapter(adapter);

                            tvItemsCount_CreditSaleQuotesActivity.setText(String.format("%s item(s)", list.size()));
                        }
                    }
                    else if (processId == WebMethods.TYPE_DELETE_CREDIT_SALE_QUOTE)
                    {
                        boolean reply = Boolean.parseBoolean(result.getResult().toString());
                        if (reply)
                        {
                            Toast.makeText(getApplicationContext(), R.string.message_changes_performed_successfully, Toast.LENGTH_SHORT).show();

                            Intent _result = new Intent();
                            setResult(RESULT_OK, _result);
                            finish();
                        }
                    }
                }
                else
                {
                    if (processId == WebMethods.TYPE_LIST_CREDIT_SALE_QUOTES)
                    {
                        gvData_CreditSaleQuotesActivity.setAdapter(null);
                        tvItemsCount_CreditSaleQuotesActivity.setText(String.format("%s item(s)", 0));
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
            String error_message = preferences.getBoolean("depuration", false) ? e.getMessage() : getString(R.string.message_web_services_error);

            Toast.makeText(getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
            Log.e("WS_CreditSaleQs", String.format("%s (processId: %s)", error_message, processId));
        }
    }

    private void LoadParameters(Bundle data)
    {
        if (data != null)
        {
            if (data.get("credit_sale") != null)
            {
                this.objCreditSale = data.getParcelable("credit_sale");
            }
        }
    }
}