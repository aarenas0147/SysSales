package com.aarenas.syssales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

import Connection.WebMethods;
import Connection.WebServices;
import Data.Objects.BusinessLine;
import Data.Objects.Configuration;
import Data.Objects.Customer;
import Data.Objects.Employee;
import Data.Objects.Person;
import Data.Objects.Route;
import Data.Objects.Zone;
import Data.Utilities;
import Design.SimpleClass;
import Design.SpinnerAdapter;

public class CustomerActivity extends AppCompatActivity implements WebServices.OnResult {

    //Constants:
    public static final String CONST_ACTION = "action";
    public static final int ACTION_VIEW = 1;
    public static final int ACTION_ADD = 2;
    public static final int ACTION_ADD_BY_SEARCH = 3;
    public static final int ACTION_EDIT = 4;

    //Controls:
    TextInputEditText etCustomerId_Customer, etDocumentNumber_Customer, etBusinessName_Customer,
            etPaternalSurname_Customer, etMaternalSurname_Customer, etNames_Customer,
            etAddress_Customer, etReference_Customer, etPhone_Customer, etEmail_Customer, etVendor_Customer;
    Spinner spDocumentType_Customer, spBusinessLine_Customer, spRoute_Customer, spZone_Customer;
    CheckBox chkEdit_Customer;
    Button btnAccept_Customer;
    LinearLayout lytVendorByCustomer_Customer;

    //Parameters:
    Bundle parameters;
    Configuration objConfiguration;
    Person objPerson;
    int actionType;

    //Variables:
    String[] list_DocumentTypes = {"DNI", "RUC", "OTRO"};
    Customer objCustomer;
    WebMethods objWebMethods;
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        parameters = getIntent().getExtras();
        LoadParameters(parameters);

        etCustomerId_Customer = (TextInputEditText) findViewById(R.id.etCustomerId_Customer);
        etDocumentNumber_Customer = (TextInputEditText) findViewById(R.id.etDocumentNumber_Customer);
        etBusinessName_Customer = (TextInputEditText) findViewById(R.id.etBusinessName_Customer);
        etPaternalSurname_Customer = (TextInputEditText) findViewById(R.id.etPaternalSurname_Customer);
        etMaternalSurname_Customer = (TextInputEditText) findViewById(R.id.etMaternalSurname_Customer);
        etNames_Customer = (TextInputEditText) findViewById(R.id.etNames_Customer);
        etAddress_Customer = (TextInputEditText) findViewById(R.id.etAddress_Customer);
        etReference_Customer = (TextInputEditText) findViewById(R.id.etReference_Customer);
        etPhone_Customer = (TextInputEditText) findViewById(R.id.etPhone_Customer);
        etEmail_Customer = (TextInputEditText) findViewById(R.id.etEmail_Customer);
        etVendor_Customer = (TextInputEditText) findViewById(R.id.etVendor_Customer);
        chkEdit_Customer = (CheckBox) findViewById(R.id.chkEdit_Customer);
        spDocumentType_Customer = (Spinner) findViewById(R.id.spDocumentType_Customer);
        spBusinessLine_Customer = (Spinner) findViewById(R.id.spBusinessLine_Customer);
        spRoute_Customer = (Spinner) findViewById(R.id.spRoute_Customer);
        spZone_Customer = (Spinner) findViewById(R.id.spZone_Customer);
        btnAccept_Customer = (Button) findViewById(R.id.btnAccept_Customer);
        lytVendorByCustomer_Customer = (LinearLayout) findViewById(R.id.lytVendorByCustomer_Customer);

        lytVendorByCustomer_Customer.setVisibility(objConfiguration != null && objConfiguration.isOptionVendors() ? View.VISIBLE : View.GONE);

        objWebMethods = new WebMethods(this, this);

        etDocumentNumber_Customer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (etDocumentNumber_Customer.getText() != null)
                {
                    if (etDocumentNumber_Customer.getText().length() == 8)
                    {
                        setDocumentType("DNI");
                        //Utilities.setSpinnerSelection(spDocumentType_Customer,"DNI");
                    }
                    else if (etDocumentNumber_Customer.getText().length() == 11)
                    {
                        setDocumentType("RUC");
                        //Utilities.setSpinnerSelection(spDocumentType_Customer,"RUC");
                    }
                }
                else
                {
                    setDocumentType("OTRO");
                    //Utilities.setSpinnerSelection(spDocumentType_Customer,"OTRO");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        chkEdit_Customer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //spDocumentType_Customer.setEnabled(!b);
                etDocumentNumber_Customer.setEnabled(b);
                etBusinessName_Customer.setEnabled(b);
                etAddress_Customer.setEnabled(b);
            }
        });

        spZone_Customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Zone objZone = ((SimpleClass<Zone>)adapterView.getItemAtPosition(i)).getTag();

                if (objZone != null)
                {
                    objWebMethods.getRoutesByZone(objZone.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spRoute_Customer.setAdapter(null);
            }
        });

        spRoute_Customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Route objRoute = ((SimpleClass<Route>)adapterView.getItemAtPosition(i)).getTag();

                if (objRoute != null)
                {
                    objWebMethods.findVendorByRoute(objRoute.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAccept_Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.clearFocus(CustomerActivity.this);

                if (isDataCompleted())
                {
                    UploadData();

                    Save();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.message_incomplete_data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list_DocumentTypes);
        adapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spDocumentType_Customer.setAdapter(adapter);

        if (objConfiguration != null && objConfiguration.isOptionVendors())
        {
            isLoading = true;

            objWebMethods.getBusinessLine();
        }
        else
        {
            Load();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(WebServices.Result result, int processId) {
        try
        {
            if (result.getResultCode().getId() == WebServices.Result.RESULT_OK)
            {
                if (!WebServices.isNull(result.getResult()))
                {
                    if (processId == WebMethods.TYPE_NEW_CUSTOMER)
                    {
                        objCustomer.setId(result.getResult().toString());
                        etCustomerId_Customer.setText(String.format("%s", objCustomer.getId()));
                    }
                    else if (processId == WebMethods.TYPE_LIST_BUSINESS_LINE)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<BusinessLine> list = BusinessLine.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            List<SimpleClass<BusinessLine>> listAdapter = new ArrayList<>();
                            SimpleClass<BusinessLine> objSimpleClass;
                            for (int i=0; i < list.size(); i++)
                            {
                                objSimpleClass = new SimpleClass<>();
                                objSimpleClass.setId(list.get(i).getId());
                                objSimpleClass.setDescription(list.get(i).getDescription());
                                objSimpleClass.setTag(list.get(i));
                                listAdapter.add(objSimpleClass);
                            }

                            SpinnerAdapter<BusinessLine> adapter =
                                    new SpinnerAdapter<>(getApplicationContext(), listAdapter);
                            spBusinessLine_Customer.setAdapter(adapter);

                            objWebMethods.getZones();
                        }
                    }
                    else if (processId == WebMethods.TYPE_LIST_ZONES)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<Zone> list = Zone.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            List<SimpleClass<Zone>> listAdapter = new ArrayList<>();
                            SimpleClass<Zone> objSimpleClass;
                            for (int i=0; i < list.size(); i++)
                            {
                                objSimpleClass = new SimpleClass<>();
                                objSimpleClass.setId(list.get(i).getId());
                                objSimpleClass.setDescription(list.get(i).getDescription());
                                objSimpleClass.setTag(list.get(i));
                                listAdapter.add(objSimpleClass);
                            }

                            SpinnerAdapter<Zone> adapter =
                                    new SpinnerAdapter<>(getApplicationContext(), listAdapter);
                            spZone_Customer.setAdapter(adapter);

                            Load();
                        }
                    }
                    else if (processId == WebMethods.TYPE_LIST_ROUTES_BY_ZONE)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<Route> list = Route.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            List<SimpleClass<Route>> listAdapter = new ArrayList<>();
                            SimpleClass<Route> objSimpleClass;
                            for (int i=0; i < list.size(); i++)
                            {
                                objSimpleClass = new SimpleClass<>();
                                objSimpleClass.setId(list.get(i).getId());
                                objSimpleClass.setDescription(list.get(i).getDescription());
                                objSimpleClass.setTag(list.get(i));
                                listAdapter.add(objSimpleClass);
                            }

                            SpinnerAdapter<Route> adapter =
                                    new SpinnerAdapter<>(getApplicationContext(), listAdapter);
                            spRoute_Customer.setAdapter(adapter);

                            if (actionType == ACTION_EDIT && isLoading)
                            {
                                setRoute(objCustomer.getRoute().getId());

                                isLoading = false;
                            }
                        }
                    }
                    else if (processId == WebMethods.TYPE_FIND_VENDOR_BY_ROUTE)
                    {
                        JSONObject jsonObject = new JSONObject(result.getResult().toString());
                        Employee objEmployee = Employee.getItem(jsonObject);
                        if (objEmployee != null)
                        {
                            etVendor_Customer.setText(String.format("(%s) %s",
                                    objEmployee.getPerson().getId(),
                                    objEmployee.getPerson().getNames()));
                        }
                    }
                    else if (processId == WebMethods.TYPE_INSERT_CUSTOMER ||
                            processId == WebMethods.TYPE_MODIFY_CUSTOMER)
                    {
                        SoapObject array = (SoapObject)result.getResult();
                        if (array.getPropertyCount() > 0)
                        {
                            boolean reply = (boolean)array.getProperty(0);
                            if (reply)
                            {
                                Toast.makeText(getApplicationContext(),
                                        R.string.message_changes_performed_successfully,
                                        Toast.LENGTH_SHORT).show();
                                Intent _result = new Intent();
                                OutputParameters(_result, objCustomer);
                                setResult(RESULT_OK, _result);
                                finish();
                            }
                            else
                            {
                                String errorMessage = (array.getPropertyCount() > 1) ? (String)array.getProperty(1)
                                        : getString(R.string.message_unrealized_changes);
                                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                                if (etDocumentNumber_Customer.getText().toString().isEmpty())
                                {
                                    etDocumentNumber_Customer.setText(String.format("%s", objCustomer.getId()));
                                }
                            }
                        }
                    }
                }
                else
                {
                    if (processId == WebMethods.TYPE_LIST_ROUTES_BY_ZONE)
                    {
                        spRoute_Customer.setAdapter(null);
                    }
                }
            }
            else if (result.getResultCode().getId() == WebServices.Result.RESULT_OFFLINE)
            {
                throw new Exception(getString(R.string.message_without_connection));
            }
            else if (result.getResultCode().getId() == WebServices.Result.RESULT_ERROR)
            {
                throw new Exception(result != null ?
                        result.toString() :
                        getString(R.string.message_web_services_error));
            }
            else
            {
                throw new Exception(getString(R.string.message_web_services_error));
            }
        }
        catch (Exception e)
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String error_message = preferences.getBoolean("depuration", false) ?
                    e.getMessage() :
                    getString(R.string.message_web_services_error);

            Toast.makeText(getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
            Log.e("WS_Customer", String.format("%s (processId: %s)", error_message, processId));
        }
    }

    private void LoadParameters(Bundle data)
    {
        if (data != null)
        {
            if (data.get(CONST_ACTION) != null)
            {
                this.actionType = data.getInt(CONST_ACTION);
            }
            if (data.get("customer") != null)
            {
                this.objCustomer = data.getParcelable("customer");
            }
            if (data.get("configuration") != null)
            {
                this.objConfiguration = data.getParcelable("configuration");
            }
            if (data.get("person") != null)
            {
                this.objPerson = data.getParcelable("person");
            }
        }
    }

    private void OutputParameters(Intent intent, Customer objCustomer)
    {
        if (objCustomer.getId() == null)
        {
            objCustomer.setId(objCustomer.getPerson().getDocumentNumber());
        }

        intent.putExtra("customer", objCustomer);
    }

    private void Load()
    {
        if (actionType == ACTION_ADD)
        {
            objCustomer = new Customer();

            objWebMethods.newCustomer();
        }
        else if (actionType == ACTION_ADD_BY_SEARCH)  //Registro de clientes de otra plataforma
        {
            if (objPerson != null)
            {
                objCustomer = new Customer();

                etDocumentNumber_Customer.setText(objPerson.getDocumentNumber());
                etBusinessName_Customer.setText(objPerson.getBusinessName());
                etPaternalSurname_Customer.setText(objPerson.getPaternalSurname());
                etMaternalSurname_Customer.setText(objPerson.getMaternalSurname());
                etNames_Customer.setText(objPerson.getNames());
                etAddress_Customer.setText(objPerson.getAddress());
                etReference_Customer.setText(objPerson.getReference());
                etPhone_Customer.setText(objPerson.getPhone());
                etEmail_Customer.setText(objPerson.getEmail());

                chkEdit_Customer.setVisibility(View.VISIBLE);
                spDocumentType_Customer.setEnabled(false);
                etDocumentNumber_Customer.setEnabled(false);
                etBusinessName_Customer.setEnabled(false);
                etAddress_Customer.setEnabled(false);
                etPaternalSurname_Customer.setEnabled(false);
                etMaternalSurname_Customer.setEnabled(false);
                etNames_Customer.setEnabled(false);

                objWebMethods.newCustomer();
            }
        }
        else if (actionType == ACTION_EDIT)
        {
            if (objCustomer != null)
            {
                etCustomerId_Customer.setText(String.format("%s", objCustomer.getId()));
                etDocumentNumber_Customer.setText(objCustomer.getPerson().getDocumentNumber());
                etBusinessName_Customer.setText(objCustomer.getPerson().getBusinessName());
                etPaternalSurname_Customer.setText(objCustomer.getPerson().getPaternalSurname());
                etMaternalSurname_Customer.setText(objCustomer.getPerson().getMaternalSurname());
                etNames_Customer.setText(objCustomer.getPerson().getNames());
                etAddress_Customer.setText(objCustomer.getPerson().getAddress());
                etReference_Customer.setText(objCustomer.getPerson().getReference());
                etPhone_Customer.setText(objCustomer.getPerson().getPhone());
                etEmail_Customer.setText(objCustomer.getPerson().getEmail());

                if (objConfiguration != null && objConfiguration.isOptionVendors())
                {
                    if (objCustomer.getBusinessLine() != null
                            && objCustomer.getRoute() != null
                            && objCustomer.getRoute().getZone() != null)
                    {
                        setBusinessLine(objCustomer.getBusinessLine().getId());
                        setZone(objCustomer.getRoute().getZone().getId());
                    }
                }
            }
        }
    }

    private boolean isDataCompleted()
    {
        if (objConfiguration != null && objConfiguration.isOptionVendors())
        {
            if (spDocumentType_Customer.getSelectedItem() == null || spBusinessLine_Customer.getSelectedItem() == null
                    || spZone_Customer.getSelectedItem() == null || spRoute_Customer.getSelectedItem() == null)
            {
                return false;
            }
            else
            {
                if (etBusinessName_Customer.getText().length() == 0 && etPaternalSurname_Customer.getText().length() == 0
                        && etNames_Customer.getText().length() == 0)
                {
                    return false;
                }
            }
        }
        else
        {
            if (spDocumentType_Customer.getSelectedItem() == null)
            {
                return false;
            }
            else
            {
                if (etBusinessName_Customer.getText().length() == 0 && etPaternalSurname_Customer.getText().length() == 0
                        && etNames_Customer.getText().length() == 0)
                {
                    return false;
                }
            }
        }

        return true;
    }

    private void UploadData()
    {
        //objCustomer.setId(id);
        objCustomer.setPerson(new Person());
        objCustomer.getPerson().setDocumentType(spDocumentType_Customer.getSelectedItem().toString());
        objCustomer.getPerson().setDocumentNumber(etDocumentNumber_Customer.getText().toString());
        objCustomer.getPerson().setBusinessName(etBusinessName_Customer.getText().toString());
        objCustomer.getPerson().setPaternalSurname(etPaternalSurname_Customer.getText().toString());
        objCustomer.getPerson().setMaternalSurname(etMaternalSurname_Customer.getText().toString());
        objCustomer.getPerson().setNames(etNames_Customer.getText().toString());
        objCustomer.getPerson().setAddress(etAddress_Customer.getText().toString());
        objCustomer.getPerson().setReference(etReference_Customer.getText().toString());
        objCustomer.getPerson().setPhone(etPhone_Customer.getText().toString());
        objCustomer.getPerson().setEmail(etEmail_Customer.getText().toString());

        if (objConfiguration != null && objConfiguration.isOptionVendors())
        {
            objCustomer.setBusinessLine(((SimpleClass<BusinessLine>)spBusinessLine_Customer.getSelectedItem()).getTag());
            objCustomer.setRoute(((SimpleClass<Route>)spRoute_Customer.getSelectedItem()).getTag());
            objCustomer.getRoute().setZone(((SimpleClass<Zone>)spZone_Customer.getSelectedItem()).getTag());
        }
    }

    private void Save()
    {
        if (objConfiguration != null && objConfiguration.isOptionVendors())
        {
            String s = String.format("Id: %s; Document Number: %s; Business Name: %s; Business Line: %s; " +
                            "Paternal LN: %s; Maternal LN: %s; Names: %s; Document Type: %s; Address: %s; " +
                            "Reference: %s; Phone: %s; Email: %s; Zone: %s; Route: %s;",
                    objCustomer.getId(), objCustomer.getPerson().getDocumentNumber(),
                    objCustomer.getPerson().getBusinessName(), objCustomer.getBusinessLine().getId(),
                    objCustomer.getPerson().getPaternalSurname(), objCustomer.getPerson().getMaternalSurname(),
                    objCustomer.getPerson().getNames(), objCustomer.getPerson().getDocumentType(),
                    objCustomer.getPerson().getAddress(), objCustomer.getPerson().getReference(),
                    objCustomer.getPerson().getPhone(), objCustomer.getPerson().getEmail(),
                    objCustomer.getRoute().getZone().getId(), objCustomer.getRoute().getId());
            Log.e("customer_data", s);

            if (actionType == ACTION_ADD || actionType == ACTION_ADD_BY_SEARCH)
            {
                objWebMethods.insertCustomer(objCustomer.getId(), objCustomer.getPerson().getDocumentNumber(),
                        objCustomer.getPerson().getBusinessName(), objCustomer.getBusinessLine().getId(),
                        objCustomer.getPerson().getPaternalSurname(), objCustomer.getPerson().getMaternalSurname(),
                        objCustomer.getPerson().getNames(), (String)objCustomer.getPerson().getDocumentType(),
                        objCustomer.getPerson().getAddress(), objCustomer.getPerson().getReference(),
                        objCustomer.getPerson().getPhone(), objCustomer.getPerson().getEmail(),
                        objCustomer.getRoute().getZone().getId(), objCustomer.getRoute().getId());
            }
            if (actionType == ACTION_EDIT)
            {
                objWebMethods.modifyCustomer(objCustomer.getId(), objCustomer.getPerson().getDocumentNumber(),
                        objCustomer.getPerson().getBusinessName(), objCustomer.getBusinessLine().getId(),
                        objCustomer.getPerson().getPaternalSurname(), objCustomer.getPerson().getMaternalSurname(),
                        objCustomer.getPerson().getNames(), (String)objCustomer.getPerson().getDocumentType(),
                        objCustomer.getPerson().getAddress(), objCustomer.getPerson().getReference(),
                        objCustomer.getPerson().getPhone(), objCustomer.getPerson().getEmail(),
                        objCustomer.getRoute().getZone().getId(), objCustomer.getRoute().getId());
            }
        }
        else
        {
            String s = String.format("Id: %s; Document Number: %s; Business Name: %s; Business Line: %s; " +
                            "Paternal LN: %s; Maternal LN: %s; Names: %s; Document Type: %s; Address: %s; " +
                            "Reference: %s; Phone: %s; Email: %s; Zone: %s; Route: %s;",
                    objCustomer.getId() != null ? objCustomer.getId() : "", objCustomer.getPerson().getDocumentNumber(),
                    objCustomer.getPerson().getBusinessName(), "",
                    objCustomer.getPerson().getPaternalSurname(), objCustomer.getPerson().getMaternalSurname(),
                    objCustomer.getPerson().getNames(), objCustomer.getPerson().getDocumentType(),
                    objCustomer.getPerson().getAddress(), objCustomer.getPerson().getReference(),
                    objCustomer.getPerson().getPhone(), objCustomer.getPerson().getEmail(),
                    "", "");
            Log.e("customer_data", s);

            if (actionType == ACTION_ADD || actionType == ACTION_ADD_BY_SEARCH)
            {
                objWebMethods.insertCustomer("", objCustomer.getPerson().getDocumentNumber(),
                        objCustomer.getPerson().getBusinessName(), "",
                        objCustomer.getPerson().getPaternalSurname(), objCustomer.getPerson().getMaternalSurname(),
                        objCustomer.getPerson().getNames(), (String)objCustomer.getPerson().getDocumentType(),
                        objCustomer.getPerson().getAddress(), objCustomer.getPerson().getReference(),
                        objCustomer.getPerson().getPhone(), objCustomer.getPerson().getEmail(),
                        "", "");
            }
            if (actionType == ACTION_EDIT)
            {
                objWebMethods.modifyCustomer(objCustomer.getId(), objCustomer.getPerson().getDocumentNumber(),
                        objCustomer.getPerson().getBusinessName(), "",
                        objCustomer.getPerson().getPaternalSurname(), objCustomer.getPerson().getMaternalSurname(),
                        objCustomer.getPerson().getNames(), (String)objCustomer.getPerson().getDocumentType(),
                        objCustomer.getPerson().getAddress(), objCustomer.getPerson().getReference(),
                        objCustomer.getPerson().getPhone(), objCustomer.getPerson().getEmail(),
                        "", "");
            }
        }
    }

    public void setDocumentType(Object value)
    {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>)spDocumentType_Customer.getAdapter();
        if (adapter != null && value != null)
        {
            String item;
            for (int i=0; i < adapter.getCount(); i++)
            {
                item = adapter.getItem(i);
                if (item.equals(value.toString()))
                {
                    spDocumentType_Customer.setSelection(i);
                    break;
                }
            }
        }
    }

    public void setBusinessLine(Object value)
    {
        SpinnerAdapter<BusinessLine> adapter = (SpinnerAdapter<BusinessLine>)spBusinessLine_Customer.getAdapter();
        if (adapter != null && value != null)
        {
            SimpleClass<BusinessLine> item;
            for (int i=0; i < adapter.getCount(); i++)
            {
                item = (SimpleClass<BusinessLine>)adapter.getItem(i);
                if (item != null && item.getId() != null && item.getId().equals(value))
                {
                    spBusinessLine_Customer.setSelection(i);
                    break;
                }
            }
        }
    }

    public void setZone(Object value)
    {
        SpinnerAdapter<Zone> adapter = (SpinnerAdapter<Zone>)spZone_Customer.getAdapter();
        if (adapter != null && value != null)
        {
            SimpleClass<Zone> item;
            for (int i=0; i < adapter.getCount(); i++)
            {
                item = (SimpleClass<Zone>)adapter.getItem(i);
                if (item.getId().equals(value))
                {
                    spZone_Customer.setSelection(i);
                    break;
                }
            }
        }
    }

    public void setRoute(Object value)
    {
        SpinnerAdapter<Route> adapter = (SpinnerAdapter<Route>)spRoute_Customer.getAdapter();
        if (adapter != null && value != null)
        {
            SimpleClass<Route> item;
            for (int i=0; i < adapter.getCount(); i++)
            {
                item = (SimpleClass<Route>)adapter.getItem(i);
                if (item.getId().equals(value))
                {
                    spRoute_Customer.setSelection(i);
                    break;
                }
            }
        }
    }
}