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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.aarenas.syssales.databinding.ActivityCustomersBinding;

import org.json.JSONArray;

import java.util.List;

import Connection.WebMethods;
import Connection.WebServices;
import Data.Objects.Configuration;
import Data.Objects.ConstantData;
import Data.Objects.Customer;
import Data.Utilities;
import Design.CustomersAdapter;

public class CustomersActivity extends AppCompatActivity implements WebServices.OnResult {

    //Constants:
    public static final String CONST_RESULT = "result";
    public static final int RESULT_VIEW = 1;
    public static final int RESULT_PICKER = 2;

    private AppBarConfiguration appBarConfiguration;
    private ActivityCustomersBinding binding;

    //Controls:
    private SearchView searchView;
    private GridView gvData_Customers;

    //Parameters:
    private Bundle parameters;
    private int resultType;
    private Configuration objConfiguration;

    //Variables:
    private SharedPreferences preferences;
    private WebMethods objWebMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parameters = getIntent().getExtras();
        LoadParameters(parameters);

        binding = ActivityCustomersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        gvData_Customers = (GridView) findViewById(R.id.gvData_Customers);

        preferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);
        objWebMethods = new WebMethods(this, this);

        binding.fabAddCustomersActivity.setVisibility(objConfiguration != null && objConfiguration.isOptionNewCustomer() ? View.VISIBLE : View.GONE);

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
                            gvData_Customers.setAdapter(null);
                            if (searchView != null)
                            {
                                searchView.setQuery(null, false);
                                searchView.clearFocus();
                                searchView.setIconified(true);
                            }
                        }
                    }
                });

        binding.fabAddCustomersActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(getApplicationContext(), CustomerActivity.class);
                activity.putExtras(parameters);
                activity.putExtra(CustomerActivity.CONST_ACTION, CustomerActivity.ACTION_ADD);
                resultLauncher.launch(activity);
            }
        });

        gvData_Customers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Customer objCustomer = (Customer)adapterView.getItemAtPosition(i);

                if (objCustomer != null)
                {
                    if (resultType == RESULT_PICKER)
                    {
                        Intent result = new Intent();
                        OutputParameters(result, objCustomer);
                        setResult(RESULT_OK, result);
                        finish();
                    }
                }
            }
        });

        gvData_Customers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Customer objCustomer = (Customer) adapterView.getItemAtPosition(i);

                if (objConfiguration != null && objConfiguration.isOptionEditCustomer())
                {
                    if (objCustomer != null)
                    {
                        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                        popupMenu.inflate(R.menu.custom_customer);
                        popupMenu.getMenu().findItem(R.id.action_edit_item).setVisible(true);
                        popupMenu.show();
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                final int itemEdit = R.id.action_edit_item;

                                switch (menuItem.getItemId())
                                {
                                    case itemEdit:
                                        Intent activity = new Intent(getApplicationContext(), CustomerActivity.class);
                                        activity.putExtras(parameters);
                                        activity.putExtra(CustomerActivity.CONST_ACTION, CustomerActivity.ACTION_EDIT);
                                        activity.putExtra("customer", objCustomer);
                                        resultLauncher.launch(activity);
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

        Load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_view, menu);

        MenuItem item = menu.findItem(R.id.action_search);

        searchView = (SearchView) item.getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setIconified(true);
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && query.trim().length() > 0)
                {
                    objWebMethods.getCustomersByBusinessName(query);
                }

                searchView.clearFocus();
                Utilities.clearFocus(CustomersActivity.this);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && searchView.getQuery().length() == 0) searchView.setIconified(true);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (searchView.getQuery().length() == 0)
                {
                    /*objWebMethods.getCustomers();*/
                    gvData_Customers.setAdapter(null);
                }

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void processFinish(WebServices.Result result, int processId) {
        try
        {
            if (result.getResultCode().getId() == WebServices.Result.RESULT_OK)
            {
                if (!WebServices.isNull(result.getResult()))
                {
                    if (processId == WebMethods.TYPE_LIST_CUSTOMERS
                            || processId == WebMethods.TYPE_LIST_CUSTOMERS_BY_BUSINESS_NAME)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<Customer> list = Customer.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            CustomersAdapter adapter = new CustomersAdapter(getApplicationContext(), list,
                                    (objConfiguration != null && objConfiguration.isOptionVendors()));
                            gvData_Customers.setAdapter(adapter);
                        }
                    }
                }
                else
                {
                    if (processId == WebMethods.TYPE_LIST_CUSTOMERS
                            || processId == WebMethods.TYPE_LIST_CUSTOMERS_BY_BUSINESS_NAME)
                    {
                        gvData_Customers.setAdapter(null);
                        Toast.makeText(getApplicationContext(), R.string.message_no_data, Toast.LENGTH_SHORT).show();
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
            Log.e("WS_Customers", String.format("%s (processId: %s)", error_message, processId));
        }
    }

    private void LoadParameters(Bundle data)
    {
        if (data != null)
        {
            if (data.get(CONST_RESULT) != null)
            {
                this.resultType = data.getInt(CONST_RESULT);
            }
            if (data.get("configuration") != null)
            {
                this.objConfiguration = data.getParcelable("configuration");
            }
        }
    }

    private void OutputParameters(Intent intent, Customer objCustomer)
    {
        intent.putExtra("customer", objCustomer);
    }

    private void Load()
    {
        /*objWebMethods.getCustomers();*/
    }
}