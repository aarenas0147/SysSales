package com.aarenas.syssales;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aarenas.syssales.databinding.ActivitySalesBinding;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Connection.WebMethods;
import Connection.WebServices;
import Data.MyDateTime;
import Data.MyMath;
import Data.MyPdf;
import Data.Objects.Company;
import Data.Objects.Configuration;
import Data.Objects.Employee;
import Data.Objects.Sale;
import Data.Objects.User;
import Design.SalesAdapter;
import Design.SimpleClass;
import Design.SpinnerAdapter;

public class SalesActivity extends AppCompatActivity implements WebServices.OnResult {

    private ActivitySalesBinding binding;

    //Controls:
    private TextInputEditText etSaleDate_SalesActivity, etSalesValue_SalesActivity, etTotalSales_SalesActivity;
    private Spinner spVendor_SalesActivity;
    private TextView tvItemsCount_SalesActivity;
    private GridView gvData_SalesActivity;

    //Parameters:
    private Bundle parameters;
    private Configuration objConfiguration, objConfiguration_user;
    private User objUser;
    private Company objCompany;

    //Variables:
    WebMethods objWebMethods;
    Calendar saleDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parameters = getIntent().getExtras();
        LoadParameters(parameters);

        objWebMethods = new WebMethods(this, this);

        binding = ActivitySalesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        etSaleDate_SalesActivity = findViewById(R.id.etSaleDate_SalesActivity);
        etSalesValue_SalesActivity = findViewById(R.id.etSalesValue_SalesActivity);
        etTotalSales_SalesActivity = findViewById(R.id.etTotal_SalesActivity);
        spVendor_SalesActivity = findViewById(R.id.spVendor_SalesActivity);
        tvItemsCount_SalesActivity = findViewById(R.id.tvItemsCount_AccountsReceivableActivity);
        gvData_SalesActivity = findViewById(R.id.gvData_SalesActivity);

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

        etSaleDate_SalesActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(SalesActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        saleDate.set(year, month, day);

                        String date = MyDateTime.format(saleDate.getTime(), MyDateTime.TYPE_DATE);
                        etSaleDate_SalesActivity.setText(date);

                        Employee objEmployee = (Employee)((SimpleClass<?>)spVendor_SalesActivity.getSelectedItem()).getTag();
                        if (objEmployee != null)
                        {
                            objWebMethods.getSalesByVendorHeader(date, objEmployee.getPerson().getId(), objCompany.getId());
                        }
                    }
                }, saleDate.get(Calendar.YEAR), saleDate.get(Calendar.MONTH), saleDate.get(Calendar.DAY_OF_MONTH));

                dialog.show();
            }
        });

        spVendor_SalesActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (etSaleDate_SalesActivity.getText().length() > 0)
                {
                    Employee objEmployee = (Employee)((SimpleClass<?>)spVendor_SalesActivity.getSelectedItem()).getTag();
                    if (objEmployee != null)
                    {
                        objWebMethods.getSalesByVendorHeader(etSaleDate_SalesActivity.getText().toString(), objEmployee.getPerson().getId(), objCompany.getId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gvData_SalesActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Sale objSale = (Sale) adapterView.getItemAtPosition(i);

                if (objSale != null)
                {
                    Intent activity = new Intent(getApplicationContext(), SaleDetailsActivity.class);
                    activity.putExtras(parameters);
                    activity.putExtra("sale", objSale);
                    startActivity(activity);
                }
            }
        });

        gvData_SalesActivity.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Sale objSale = (Sale) adapterView.getItemAtPosition(i);

                if (objSale != null)
                {
                    PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                    popupMenu.inflate(R.menu.custom_sales);
                    popupMenu.getMenu().findItem(R.id.action_cancel_item).setVisible(true);
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            final int itemPrint = R.id.action_print_item;
                            final int itemDelete = R.id.action_cancel_item;

                            switch (menuItem.getItemId())
                            {
                                case itemPrint:
                                    if (objConfiguration != null && objConfiguration.isOptionPrintSale())
                                    {
                                        print(objSale);
                                    }
                                    break;
                                case itemDelete:
                                    objWebMethods.cancelSale(objSale.getId(),
                                            objSale.getEmployee().getPerson().getId(), objCompany.getId());
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

        objWebMethods.getVendors();

        Load();
    }

    @Override
    public void processFinish(WebServices.Result result, int processId) {
        try
        {
            if (result.getResultCode().getId() == WebServices.Result.RESULT_OK)
            {
                if (!WebServices.isNull(result.getResult()))
                {
                    if (processId == WebMethods.TYPE_LIST_VENDORS)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<Employee> list = Employee.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            List<SimpleClass<Employee>> listAdapter = new ArrayList<>();
                            SimpleClass<Employee> objSimpleClass;
                            for (int i=0; i < list.size(); i++)
                            {
                                objSimpleClass = new SimpleClass<>();
                                objSimpleClass.setId(list.get(i).getPerson().getId());
                                objSimpleClass.setDescription(list.get(i).getPerson().getNames());
                                objSimpleClass.setTag(list.get(i));
                                listAdapter.add(objSimpleClass);
                            }

                            SpinnerAdapter<Employee> adapter =
                                    new SpinnerAdapter<>(getApplicationContext(), listAdapter);
                            spVendor_SalesActivity.setAdapter(adapter);

                            findCurrentUser();
                        }
                    }
                    else if (processId == WebMethods.TYPE_LIST_SALES_BY_VENDOR_HEADER)
                    {
                        JSONObject jsonObject = new JSONObject(result.getResult().toString());
                        Sale objSale = Sale.getItem(jsonObject);
                        if (objSale != null)
                        {
                            etSalesValue_SalesActivity.setText(MyMath.toDecimal(objSale.getSaleValue(), 2));
                            etTotalSales_SalesActivity.setText(MyMath.toDecimal(objSale.getTotal(), 2));

                            if (objSale.getEmployee() != null)
                            {
                                objWebMethods.getSalesByVendorDetails(etSaleDate_SalesActivity.getText().toString(),
                                        objSale.getEmployee().getPerson().getId(), objCompany.getId());
                            }
                        }
                    }
                    else if (processId == WebMethods.TYPE_LIST_SALES_BY_VENDOR_DETAILS)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<Sale> list = Sale.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            SalesAdapter adapter = new SalesAdapter(getApplicationContext(), list);
                            gvData_SalesActivity.setAdapter(adapter);
                            tvItemsCount_SalesActivity.setText(String.format("%s item(s)", list.size()));
                        }

                        /*float total = 0F;
                        for (int i=0; i < list.size(); i++)
                        {
                            total += list.get(i).getTotal();
                        }

                        etTotalSales_SalesActivity.setText(MyMath.toDecimal(total, 2));*/
                    }
                    else if (processId == WebMethods.TYPE_PRINT_SALE)
                    {
                        boolean rpta = Boolean.parseBoolean(result.getResult().toString());
                        if (rpta)
                        {
                            Toast.makeText(getApplicationContext(), "Imprimiendo...", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "¡Error al imprimir!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if (processId == WebMethods.TYPE_PRINT_SALE_IN_PDF)
                    {
                        SoapObject array = (SoapObject)result.getResult();
                        if (array.getPropertyCount() > 0)
                        {
                            boolean reply = Boolean.parseBoolean(array.getProperty(0).toString());
                            if (reply)
                            {
                                String fileName = (String) array.getProperty(1);
                                if (array.getPropertyCount() > 2 && array.getProperty(2) != null)
                                {
                                    byte[] fileArray = Base64.decode(((String) array.getProperty(2)).getBytes(), 0);
                                    if (fileArray != null)
                                    {
                                        MyPdf myPdf = new MyPdf(getApplicationContext());
                                        myPdf.clearCache();
                                        myPdf.openDocument(fileArray, fileName);
                                    }
                                }
                            }
                            else
                            {
                                String errorMessage = (String) array.getProperty(1);
                                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else if (processId == WebMethods.TYPE_CANCEL_SALE)
                    {
                        SoapObject array = (SoapObject)result.getResult();
                        if (array.getPropertyCount() > 0)
                        {
                            boolean reply = (boolean) array.getProperty(0);
                            String message = (String) array.getProperty(1);

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            if (reply)
                            {
                                finish();
                            }
                            else
                            {
                                String errorMessage = array.getPropertyCount() > 2 && array.getProperty(2) != null
                                        ? (String) array.getProperty(2) : message;
                                Log.e("cancel_sale_error", errorMessage);
                            }
                        }
                    }
                }
                else
                {
                    if (processId == WebMethods.TYPE_LIST_VENDORS)
                    {
                        spVendor_SalesActivity.setAdapter(null);
                    }
                    else if (processId == WebMethods.TYPE_LIST_SALES_BY_VENDOR_HEADER)
                    {
                        etSalesValue_SalesActivity.setText(MyMath.toDecimal(0F, 2));
                        etTotalSales_SalesActivity.setText(MyMath.toDecimal(0F, 2));

                        gvData_SalesActivity.setAdapter(null);
                        tvItemsCount_SalesActivity.setText(String.format("%s item(s)", 0));
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
            Log.e("WS_Sales", String.format("%s (processId: %s)", error_message, processId));
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
            if (data.get("company") != null)
            {
                this.objCompany = data.getParcelable("company");
            }
            if (data.get("configuration") != null)
            {
                this.objConfiguration = data.getParcelable("configuration");
            }
            if (data.get("configuration_user") != null)
            {
                this.objConfiguration_user = data.getParcelable("configuration_user");
            }
        }
    }

    private void Load()
    {
        if (objConfiguration_user != null)
        {
            spVendor_SalesActivity.setEnabled(objConfiguration_user.isOptionSalesByVendor());
        }
    }

    private void findCurrentUser()
    {
        etSaleDate_SalesActivity.setText(MyDateTime.format(new Date(), MyDateTime.TYPE_DATE));
        etTotalSales_SalesActivity.setText(MyMath.toDecimal(0F, 2));

        SpinnerAdapter<?> adapter = (SpinnerAdapter<?>)spVendor_SalesActivity.getAdapter();
        if (adapter != null)
        {
            for (int i = 0; i < adapter.getCount(); i++)
            {
                SimpleClass<?> itemEmployee = (SimpleClass<?>)adapter.getItem(i);
                if (itemEmployee != null && itemEmployee.getTag() instanceof Employee)
                {
                    if (objUser.getEmployee().getPerson().getId().equals(itemEmployee.getId()))
                    {
                        spVendor_SalesActivity.setSelection(i);
                        break;
                    }
                }
            }
        }
    }

    private void print(Sale objSale)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage(R.string.message_print)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                    //objConfiguration != null && objConfiguration.isOptionPrintSalePdf()
                    objWebMethods.printSale(objSale.getId(), objUser.getId(), objCompany.getId());
                })
                .setNeutralButton(R.string.preview, (dialog, id) -> {
                    objWebMethods.printSaleInPDF(objSale.getId(), objCompany.getId());
                })
                .setNegativeButton(android.R.string.cancel, (dialog, id) -> {

                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}