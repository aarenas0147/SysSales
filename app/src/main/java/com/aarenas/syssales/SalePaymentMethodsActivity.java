package com.aarenas.syssales;

import android.app.Activity;
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
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.aarenas.syssales.databinding.ActivitySalePaymentMethodsBinding;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import Connection.WebMethods;
import Connection.WebServices;
import Data.MyMath;
import Data.Objects.Company;
import Data.Objects.Configuration;
import Data.Objects.SalePaymentMethod;
import Data.Objects.Sale;
import Design.PaymentMethodsBySaleAdapter;

public class SalePaymentMethodsActivity extends AppCompatActivity implements WebServices.OnResult {

    private AppBarConfiguration appBarConfiguration;
    private ActivitySalePaymentMethodsBinding binding;

    //Controls:
    private TextInputEditText etTotal_PaymentMethodsBySale, etBalance_PaymentMethodsBySale;
    private GridView gvData_PaymentMethodsBySale;

    //Parameters:
    private Bundle parameters;
    private Company objCompany;
    private Configuration objConfiguration;
    Sale objSale = new Sale();

    //Variables:
    float balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parameters = getIntent().getExtras();
        LoadParameters(parameters);

        binding = ActivitySalePaymentMethodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        etTotal_PaymentMethodsBySale = (TextInputEditText) findViewById(R.id.etTotal_PaymentMethodsBySale);
        etBalance_PaymentMethodsBySale = (TextInputEditText) findViewById(R.id.etBalance_PaymentMethodsBySale);
        gvData_PaymentMethodsBySale = (GridView) findViewById(R.id.gvData_PaymentMethodsBySale);

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

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK)
                        {
                            WebMethods objWebMethods = new WebMethods(SalePaymentMethodsActivity.this, SalePaymentMethodsActivity.this);
                            objWebMethods.getSalePaymentMethods(objSale.getId(), objCompany.getId());
                        }
                    }
                });

        gvData_PaymentMethodsBySale.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SalePaymentMethod objSalePaymentMethod = (SalePaymentMethod)adapterView.getItemAtPosition(i);

                if (objSalePaymentMethod != null)
                {
                    Intent activity = new Intent(getApplicationContext(), SalePaymentMethodActivity.class);
                    activity.putExtras(parameters);
                    activity.putExtra("sale_payment_method", objSalePaymentMethod);
                    activity.putExtra("balance", balance);
                    resultLauncher.launch(activity);
                }
            }
        });

        gvData_PaymentMethodsBySale.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                SalePaymentMethod objSalePaymentMethod = (SalePaymentMethod)adapterView.getItemAtPosition(i);

                if (objSalePaymentMethod != null)
                {
                    if (objSalePaymentMethod.getId() != null)
                    {
                        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                        popupMenu.inflate(R.menu.custom_payment_methods_by_sale);
                        popupMenu.getMenu().findItem(R.id.action_delete_item).setVisible(true);
                        popupMenu.show();
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                final int itemDelete = R.id.action_delete_item;

                                switch (menuItem.getItemId())
                                {
                                    case itemDelete:
                                        WebMethods objWebMethods = new WebMethods(SalePaymentMethodsActivity.this, SalePaymentMethodsActivity.this);
                                        objWebMethods.deleteSalePaymentMethod(objSale.getId(),
                                                objSalePaymentMethod.getPaymentMethod().getId(), objCompany.getId());
                                        break;
                                    default:
                                        break;
                                }
                                return true;
                            }
                        });

                        return true;
                    }
                }

                return false;
            }
        });

        WebMethods objWebMethods = new WebMethods(this, this);
        objWebMethods.getTempSaleHeader(objSale.getId(), objCompany.getId());
        objWebMethods.getSalePaymentMethods(objSale.getId(), objCompany.getId());
    }

    @Override
    public void onBackPressed() {
        Intent result = new Intent();
        result.putExtra("balance", balance);
        setResult(RESULT_OK, result);

        super.onBackPressed();
    }

    @Override
    public void processFinish(WebServices.Result result, int processId) {
        try
        {
            if (result.getResultCode().getId() == WebServices.Result.RESULT_OK)
            {
                if (!WebServices.isNull(result.getResult()))
                {
                    if (processId == WebMethods.TYPE_LIST_TEMP_SALE_HEADER)
                    {
                        JSONObject jsonObject = new JSONObject(result.getResult().toString());
                        Sale objSale = Sale.getItem(jsonObject);
                        if (objSale != null)
                        {
                            parameters.remove("sale");
                            this.objSale.setTotal(objSale.getTotal());
                            parameters.putParcelable("sale", objSale);
                            this.balance = objSale.getTotal();
                            etTotal_PaymentMethodsBySale.setText(MyMath.toDecimal(objSale.getTotal(), 2));
                        }
                    }
                    else if (processId == WebMethods.TYPE_LIST_SALE_PAYMENT_METHODS)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<SalePaymentMethod> list = SalePaymentMethod.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            float totalAmount = 0F;
                            for (int i = 0; i < list.size(); i++)
                            {
                                totalAmount += list.get(i).getAmount();
                            }
                            this.balance = this.objSale.getTotal() - totalAmount;
                            etBalance_PaymentMethodsBySale.setText(MyMath.toDecimal(this.balance, 2));

                            PaymentMethodsBySaleAdapter adapter =
                                    new PaymentMethodsBySaleAdapter(getApplicationContext(), list);

                            gvData_PaymentMethodsBySale.setAdapter(adapter);
                            //tvItemsCount_SaleSummaryFragment.setText(String.format("%s item(s)", list.size()));
                        }
                    }
                    else if (processId == WebMethods.TYPE_DELETE_PAYMENT_METHOD_BY_SALE)
                    {
                        boolean reply = Boolean.parseBoolean(result.getResult().toString());
                        if (reply)
                        {
                            Toast.makeText(getApplicationContext(), R.string.message_changes_performed_successfully, Toast.LENGTH_SHORT).show();

                            WebMethods objWebMethods = new WebMethods(SalePaymentMethodsActivity.this, SalePaymentMethodsActivity.this);
                            objWebMethods.getSalePaymentMethods(objSale.getId(), objCompany.getId());
                        }
                    }
                }
                else
                {
                    if (processId == WebMethods.TYPE_LIST_SALE_PAYMENT_METHODS)
                    {
                        gvData_PaymentMethodsBySale.setAdapter(null);
                        //tvItemsCount_SaleSummaryFragment.setText(String.format("%s item(s)", 0));
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
            Log.e("WS_PaymentMsBS", String.format("%s (processId: %s)", error_message, processId));
        }
    }

    private void LoadParameters(Bundle data)
    {
        if (data != null)
        {
            if (data.get("company") != null)
            {
                this.objCompany = data.getParcelable("company");
            }
            if (data.get("configuration") != null)
            {
                this.objConfiguration = data.getParcelable("configuration");
            }
            if (data.get("sale") != null)
            {
                Sale _sale = data.getParcelable("sale");
                if (_sale != null)
                {
                    this.objSale.setId(_sale.getId());
                }
            }
        }
    }
}