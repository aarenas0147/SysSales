package com.aarenas.syssales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.aarenas.syssales.databinding.ActivityProductsBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;

import java.util.List;

import Connection.WebMethods;
import Connection.WebServices;
import Data.Objects.Product;
import Data.Objects.Sale;
import Data.Objects.User;
import Data.Utilities;
import Design.ProductsAdapter;

public class ProductsActivity extends AppCompatActivity implements WebServices.OnResult {

    //Constants:
    public static final String CONST_RESULT = "result";
    public static final int RESULT_VIEW = 1;
    public static final int RESULT_PICKER = 2;

    private AppBarConfiguration appBarConfiguration;
    private ActivityProductsBinding binding;

    //Controls:
    GridView gvData_Products;
    SearchView searchView;

    //Parameters:
    Bundle parameters;
    int resultType;
    User objUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parameters = getIntent().getExtras();
        LoadParameters(parameters);

        binding = ActivityProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        gvData_Products = (GridView) findViewById(R.id.gvData_Products);

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
                            setResult(RESULT_OK, result.getData());
                            finish();
                        }
                    }
                });

        gvData_Products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product objProduct = (Product)adapterView.getItemAtPosition(i);

                if (objProduct != null)
                {
                    if (parameters.get(CONST_RESULT) != null)
                    {
                        if (resultType == RESULT_PICKER)
                        {
                            parameters.remove(CONST_RESULT);
                            parameters.putInt(SaleDetailActivity.CONST_RESULT, SaleDetailActivity.RESULT_ADD);

                            Intent activity = new Intent(getApplicationContext(), SaleDetailActivity.class);
                            OutputParameters(activity, objProduct);
                            resultLauncher.launch(activity);
                        }
                    }
                }
            }
        });
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
                    WebMethods objWebMethods = new WebMethods(ProductsActivity.this, ProductsActivity.this);
                    objWebMethods.getProductsByDescription(query, objUser.getEmployee().getOutlet().getId());

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    boolean autocomplete_search = preferences.getBoolean("autocomplete_search", false);
                    if (autocomplete_search)
                    {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("autocomplete_search_text", query);
                        editor.apply();
                    }
                }

                searchView.clearFocus();
                Utilities.clearFocus(ProductsActivity.this);

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
                    WebMethods objWebMethods = new WebMethods(ProductsActivity.this, ProductsActivity.this);
                    objWebMethods.getProductsByDescription("", objUser.getEmployee().getOutlet().getId());

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    boolean autocomplete_search = preferences.getBoolean("autocomplete_search", false);
                    if (autocomplete_search)
                    {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("autocomplete_search_text", null);
                        editor.apply();
                    }
                }

                return false;
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean autocomplete_search = preferences.getBoolean("autocomplete_search", false);
        if (autocomplete_search)
        {
            String text = preferences.getString("autocomplete_search_text", null);
            if (text != null && !text.isEmpty())
            {
                searchView.setQuery(text, true);
                searchView.setIconified(false);
                searchView.clearFocus();
            }
        }

        if (searchView.getQuery() == null || searchView.getQuery().length() == 0)
        {
            WebMethods objWebMethods = new WebMethods(this, this);
            objWebMethods.getProductsByDescription("", objUser.getEmployee().getOutlet().getId());
        }

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
                    if (processId == WebMethods.TYPE_FIND_PRODUCT_BY_ID ||
                            processId == WebMethods.TYPE_LIST_PRODUCTS_BY_DESCRIPTION)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<Product> list = Product.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            ProductsAdapter adapter = new ProductsAdapter(getApplicationContext(), list);
                            gvData_Products.setAdapter(adapter);
                        }
                    }
                }
                else
                {
                    if (processId == WebMethods.TYPE_FIND_PRODUCT_BY_ID ||
                            processId == WebMethods.TYPE_LIST_PRODUCTS_BY_DESCRIPTION)
                    {
                        gvData_Products.setAdapter(null);
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
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String error_message = preferences.getBoolean("depuration", false) ? e.getMessage() : getString(R.string.message_web_services_error);

            Toast.makeText(getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
            Log.e("WS_Products", String.format("%s (processId: %s)", error_message, processId));
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
            if (data.get("user") != null)
            {
                this.objUser = data.getParcelable("user");
            }
        }
    }

    private void OutputParameters(Intent intent, Product objProduct)
    {
        intent.putExtras(parameters);
        intent.putExtra("product", objProduct);
    }
}