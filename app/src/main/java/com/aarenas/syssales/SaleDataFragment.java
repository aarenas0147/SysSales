package com.aarenas.syssales;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import Connection.WebMethods;
import Connection.WebServices;
import Data.MyDateTime;
import Data.MyMath;
import Data.Objects.Company;
import Data.Objects.Configuration;
import Data.Objects.ConstantData;
import Data.Objects.CreditLine;
import Data.Objects.CreditLineByVendor;
import Data.Objects.CreditSale;
import Data.Objects.Customer;
import Data.Objects.PaymentCondition;
import Data.Objects.PaymentMethod;
import Data.Objects.Person;
import Data.Objects.Sale;
import Data.Objects.User;
import Data.Objects.VoucherType;
import Data.Utilities;
import Design.SimpleClass;
import Design.SpinnerAdapter;
import okhttp3.internal.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaleDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaleDataFragment extends Fragment implements WebServices.OnResult {

    private OnFragmentInteractionListener mListener;

    public SaleDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param params Parameter 1.
     * @return A new instance of fragment SaleDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SaleDataFragment newInstance(Bundle params) {
        SaleDataFragment fragment = new SaleDataFragment();
        fragment.setArguments(params);
        return fragment;
    }

    //Controls:
    private TextView tvOrderId_SaleDataFragment;
    private TextInputEditText etCustomerId_SaleDataFragment, etCustomerDocumentNumber_SaleDataFragment,
            etCustomerBusinessName_SaleDataFragment, etCustomerAddress_SaleDataFragment,
            etIssueDate_SaleDataFragment, etExpiryDate_SaleDataFragment,
            etSeller_SaleDataFragment, etSellerZone_SaleDataFragment, etSellerRoute_SaleDataFragment;
    private Spinner spVoucherType_SaleDataFragment, spPaymentCondition_SaleDataFragment, spPaymentMethod_SaleDataFragment;
    private ImageButton btnFindCustomer_SaleDataFragment, btnCustomers_SaleDataFragment, btnSalePaymentMethods_SaleDataFragment;
    private LinearLayout lytVendorByCustomer_SaleDataFragment;

    //Parameters:
    private Bundle parameters;
    private Configuration objConfiguration, objConfigurationXUser;
    private User objUser;
    private Company objCompany;
    private Sale objSale = new Sale();

    //Activities result:
    private ActivityResultLauncher<Intent> resultLauncherCustomerAdd;

    //Asynctask:
    private List<CreditSale> listCreditSaleByCustomer = null;
    private List<CreditLine> listCreditLineByCustomer = null;
    private List<CreditLineByVendor> listCreditLineByVendor = null;

    //Variables:
    private SharedPreferences preferences;
    private WebMethods objWebMethods;
    private Calendar issueDate = Calendar.getInstance();
    private Calendar expiryDate = Calendar.getInstance();
    private float balance = 0F;
    private float creditLineAmount = 0F, debtAmount = 0F;
    private float creditLineByVendorAmount = 0F, creditLineByVendorBalance = 0F;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parameters = getArguments();
        LoadParameters(parameters);

        preferences = getActivity().getSharedPreferences(getActivity().getPackageName() + "_preferences", Context.MODE_PRIVATE);
        objWebMethods = new WebMethods(getActivity(), SaleDataFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sale_data, container, false);

        tvOrderId_SaleDataFragment = rootView.findViewById(R.id.tvOrderId_SaleDataFragment);
        etCustomerId_SaleDataFragment = rootView.findViewById(R.id.etCustomerId_SaleDataFragment);
        etCustomerDocumentNumber_SaleDataFragment = rootView.findViewById(R.id.etCustomerDocumentNumber_SaleDataFragment);
        etCustomerBusinessName_SaleDataFragment = rootView.findViewById(R.id.etCustomerBusinessName_SaleDataFragment);
        etCustomerAddress_SaleDataFragment = rootView.findViewById(R.id.etCustomerAddress_SaleDataFragment);
        etIssueDate_SaleDataFragment = rootView.findViewById(R.id.etIssueDate_SaleDataFragment);
        etExpiryDate_SaleDataFragment = rootView.findViewById(R.id.etExpiryDate_SaleDataFragment);
        etSeller_SaleDataFragment = rootView.findViewById(R.id.etSeller_SaleDataFragment);
        etSellerZone_SaleDataFragment = rootView.findViewById(R.id.etSellerZone_SaleDataFragment);
        etSellerRoute_SaleDataFragment = rootView.findViewById(R.id.etSellerRoute_SaleDataFragment);
        spVoucherType_SaleDataFragment = rootView.findViewById(R.id.spVoucherType_SaleDataFragment);
        spPaymentCondition_SaleDataFragment = rootView.findViewById(R.id.spPaymentCondition_SaleDataFragment);
        spPaymentMethod_SaleDataFragment = rootView.findViewById(R.id.spPaymentMethod_SaleDataFragment);
        btnFindCustomer_SaleDataFragment = rootView.findViewById(R.id.btnFindCustomer_SaleDataFragment);
        btnCustomers_SaleDataFragment = rootView.findViewById(R.id.btnCustomers_SaleDataFragment);
        btnSalePaymentMethods_SaleDataFragment = rootView.findViewById(R.id.btnSalePaymentMethods_SaleDataFragment);
        lytVendorByCustomer_SaleDataFragment = rootView.findViewById(R.id.lytVendorByCustomer_SaleDataFragment);

        btnSalePaymentMethods_SaleDataFragment.setVisibility(objConfiguration != null && objConfiguration.isOptionCustomPaymentMethod() ? View.VISIBLE : View.GONE);
        lytVendorByCustomer_SaleDataFragment.setVisibility(objConfiguration != null && objConfiguration.isOptionVendors() ? View.VISIBLE : View.GONE);

        ActivityResultLauncher<Intent> resultLauncherCustomer = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK)
                        {
                            Bundle extras = result.getData() != null ? result.getData().getExtras() : null;

                            if (extras != null)
                            {
                                Customer objCustomer = extras.getParcelable("customer");
                                setCustomer(objCustomer);
                                InteractionFragment();
                            }
                        }
                    }
                });

        resultLauncherCustomerAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK)
                        {
                            Bundle extras = result.getData() != null ? result.getData().getExtras() : null;

                            if (extras != null)
                            {
                                Customer objCustomer = extras.getParcelable("customer");
                                setCustomer(objCustomer);
                                InteractionFragment();
                            }
                        }
                        else
                        {
                            setCustomer(null);
                            InteractionFragment();
                        }
                    }
                });

        ActivityResultLauncher<Intent> resultLauncherPaymentMethod = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK)
                        {
                            Bundle extras = result.getData() != null ? result.getData().getExtras() : null;

                            if (extras != null)
                            {
                                balance = extras.get("balance") != null ? extras.getFloat("balance") : 0F;
                                InteractionFragment();
                            }
                        }
                    }
                });

        etIssueDate_SaleDataFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (objConfiguration != null && objConfiguration.isOptionIssueDate())
                {
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            setIssueDate(day, month, year);

                            InteractionFragment();
                        }
                    }, issueDate.get(Calendar.YEAR), issueDate.get(Calendar.MONTH), issueDate.get(Calendar.DAY_OF_MONTH));

                    dialog.show();
                }
            }
        });

        etExpiryDate_SaleDataFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (objConfiguration != null && objConfiguration.isOptionExpiryDate())
                {
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            setExpiryDate(day, month, year);

                            InteractionFragment();
                        }
                    }, expiryDate.get(Calendar.YEAR), expiryDate.get(Calendar.MONTH), expiryDate.get(Calendar.DAY_OF_MONTH));

                    dialog.getDatePicker().setMinDate(issueDate.getTimeInMillis());
                    dialog.show();
                }
            }
        });

        spVoucherType_SaleDataFragment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                InteractionFragment();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spPaymentCondition_SaleDataFragment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SimpleClass<PaymentCondition> objPaymentCondition = (SimpleClass<PaymentCondition>)adapterView.getItemAtPosition(i);

                if (objPaymentCondition != null)
                {
                    etExpiryDate_SaleDataFragment.setEnabled(objPaymentCondition.getTag().isDueDaysEditable());

                    validatePaymentCondition();
                }

                InteractionFragment();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spPaymentMethod_SaleDataFragment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SimpleClass<PaymentMethod> objPaymentMethod = (SimpleClass<PaymentMethod>)adapterView.getItemAtPosition(i);

                if (objConfiguration != null && objConfiguration.isOptionCustomPaymentMethod())
                {
                    if (objPaymentMethod != null && objPaymentMethod.getId().equals("PERSONALIZADO"))
                    {
                        //Falta validar si el monto de venta es mayor a cero
                        Intent intent = new Intent(getActivity(), SalePaymentMethodsActivity.class);
                        intent.putExtras(parameters);
                        intent.putExtra("sale", objSale);
                        resultLauncherPaymentMethod.launch(intent);
                    }
                    else
                    {
                        InteractionFragment();
                    }
                }
                else
                {
                    InteractionFragment();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnFindCustomer_SaleDataFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.clearFocus(getActivity());

                String customerId = etCustomerId_SaleDataFragment.getText().toString();

                if (!customerId.isEmpty())
                {
                    objWebMethods.getCustomerById(customerId);
                }
                else
                {
                    setCustomer(null);
                    InteractionFragment();
                }
            }
        });

        btnCustomers_SaleDataFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CustomersActivity.class);
                intent.putExtras(parameters);
                intent.putExtra(CustomersActivity.CONST_RESULT, CustomersActivity.RESULT_PICKER);
                resultLauncherCustomer.launch(intent);
            }
        });

        btnSalePaymentMethods_SaleDataFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Falta validar si el monto de venta es mayor a cero
                Intent intent = new Intent(getActivity(), SalePaymentMethodsActivity.class);
                intent.putExtras(parameters);
                intent.putExtra("sale", objSale);
                resultLauncherPaymentMethod.launch(intent);
            }
        });

        Load();

        return rootView;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void processFinish(WebServices.Result result, int processId) {
        try
        {
            if (result.getResultCode().getId() == WebServices.Result.RESULT_OK)
            {
                if (!WebServices.isNull(result.getResult()))
                {
                    if (processId == WebMethods.TYPE_LIST_VOUCHER_TYPES)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<VoucherType> list = VoucherType.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            List<SimpleClass<VoucherType>> listAdapter = new ArrayList<>();
                            SimpleClass<VoucherType> objSimpleClass;
                            for (int i=0; i < list.size(); i++)
                            {
                                objSimpleClass = new SimpleClass<>();
                                objSimpleClass.setId(list.get(i).getId());
                                objSimpleClass.setDescription(list.get(i).getDescription());
                                objSimpleClass.setTag(list.get(i));
                                listAdapter.add(objSimpleClass);
                            }

                            SpinnerAdapter<VoucherType> adapter =
                                    new SpinnerAdapter<>(getActivity().getApplicationContext(), listAdapter);
                            spVoucherType_SaleDataFragment.setAdapter(adapter);
                        }
                    }
                    else if (processId == WebMethods.TYPE_LIST_PAYMENT_CONDITIONS)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<PaymentCondition> list = PaymentCondition.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            List<SimpleClass<PaymentCondition>> listAdapter = new ArrayList<>();
                            SimpleClass<PaymentCondition> objSimpleClass;
                            for (int i=0; i < list.size(); i++)
                            {
                                objSimpleClass = new SimpleClass<>();
                                objSimpleClass.setId(list.get(i).getId());
                                objSimpleClass.setDescription(list.get(i).getDescription());
                                objSimpleClass.setTag(list.get(i));
                                listAdapter.add(objSimpleClass);
                            }

                            SpinnerAdapter<PaymentCondition> adapter =
                                    new SpinnerAdapter<>(getActivity().getApplicationContext(), listAdapter);
                            spPaymentCondition_SaleDataFragment.setAdapter(adapter);
                        }
                    }
                    else if (processId == WebMethods.TYPE_LIST_PAYMENT_METHODS)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<PaymentMethod> list = PaymentMethod.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            List<SimpleClass<PaymentMethod>> listAdapter = new ArrayList<>();
                            SimpleClass<PaymentMethod> objSimpleClass;
                            for (int i=0; i < list.size(); i++)
                            {
                                objSimpleClass = new SimpleClass<>();
                                objSimpleClass.setId(list.get(i).getId());
                                objSimpleClass.setDescription(list.get(i).getDescription());
                                objSimpleClass.setTag(list.get(i));
                                listAdapter.add(objSimpleClass);
                            }

                            SpinnerAdapter<PaymentMethod> adapter =
                                    new SpinnerAdapter<>(getActivity().getApplicationContext(), listAdapter);
                            spPaymentMethod_SaleDataFragment.setAdapter(adapter);
                        }
                    }
                    else if (processId == WebMethods.TYPE_GET_CUSTOMER_DEFAULT)
                    {
                        JSONObject jsonObject = new JSONObject(result.getResult().toString());
                        Customer objCustomer = Customer.getItem(jsonObject);
                        setCustomer(objCustomer);
                        InteractionFragment();
                    }
                    else if (processId == WebMethods.TYPE_LIST_TEMP_SALE_HEADER)
                    {
                        JSONObject jsonObject = new JSONObject(result.getResult().toString());
                        Sale objSale = Sale.getItem(jsonObject);
                        if (objSale != null)
                        {
                            etCustomerId_SaleDataFragment.setText(String.format("%s", objSale.getClient().getId()));
                        }
                    }
                    else if (processId == WebMethods.TYPE_FIND_CUSTOMER_BY_ID)
                    {
                        JSONObject jsonObject = new JSONObject(result.getResult().toString());
                        Customer objCustomer = Customer.getItem(jsonObject);
                        setCustomer(objCustomer);
                        InteractionFragment();
                    }
                    else if (processId == WebMethods.TYPE_FIND_PERSON_ONLINE)
                    {
                        JSONObject jsonObject = new JSONObject(result.getResult().toString());
                        Person objPerson = Person.getItem(jsonObject);
                        newCustomer(objPerson);
                    }
                    else if (processId == WebMethods.TYPE_LIST_CREDIT_SALES_PENDING_BY_CUSTOMER)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        listCreditSaleByCustomer = CreditSale.getList(jsonArray);
                        if (listCreditSaleByCustomer != null && !listCreditSaleByCustomer.isEmpty())
                        {
                            debtAmount = 0F;
                            for (int i = 0; i < listCreditSaleByCustomer.size(); i++)
                            {
                                debtAmount += listCreditSaleByCustomer.get(i).getAmount();
                            }

                            objSale.getClient().setStatus(ConstantData.CustomerStatus.DELINQUENT);
                            /*Utilities.showMessage(getActivity(), new String[]{
                                    getString(R.string.message_customer_delinqued),
                                    String.format("Total: S/ %s", MyMath.toDecimal(debtAmount, 2))});
                            String message = TextUtils.join("\n", array);*/

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(R.string.app_name);
                            builder.setMessage(getString(R.string.message_customer_delinqued))
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    })
                                    .setNegativeButton("Ver pagos...", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if (objConfigurationXUser != null && objConfigurationXUser.isOptionAccountReceivable())
                                            {
                                                Intent activity = new Intent(getActivity(), AccountsReceivableActivity.class);
                                                activity.putExtras(parameters);
                                                startActivity(activity);
                                            }
                                            else
                                            {
                                                Toast.makeText(getActivity(), R.string.message_unsupported_user, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.show();
                        }

                        if (objConfiguration != null && objConfiguration.isOptionCreditLine())
                        {
                            objWebMethods.getCreditLineByCustomer(objSale.getClient().getId(), objCompany.getId());
                        }
                        else
                        {
                            validatePaymentCondition();
                            InteractionFragment();
                        }
                    }
                    else if (processId == WebMethods.TYPE_LIST_CREDIT_LINE_BY_CUSTOMER)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        listCreditLineByCustomer = CreditLine.getList(jsonArray);
                        if (listCreditLineByCustomer != null && !listCreditLineByCustomer.isEmpty())
                        {
                            this.creditLineAmount = (debtAmount < listCreditLineByCustomer.get(0).getAmount() ?
                                    listCreditLineByCustomer.get(0).getAmount() - debtAmount : 0F);
                        }

                        validatePaymentCondition();
                        InteractionFragment();
                    }
                    else if (processId == WebMethods.TYPE_LIST_CREDIT_LINE_BY_VENDOR)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        listCreditLineByVendor = CreditLineByVendor.getList(jsonArray);
                        if (listCreditLineByVendor != null && !listCreditLineByVendor.isEmpty())
                        {
                            this.creditLineByVendorAmount = listCreditLineByVendor.get(0).getAmount();
                            this.creditLineByVendorBalance = listCreditLineByVendor.get(0).getBalance();
                        }

                        InteractionFragment();
                    }
                }
                else
                {
                    if (processId == WebMethods.TYPE_FIND_CUSTOMER_BY_ID)
                    {
                        if (objConfiguration != null && objConfiguration.isOptionFindPersonOnline())
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(R.string.app_name);
                            builder.setMessage(R.string.message_find_customer)
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            String customerId = etCustomerId_SaleDataFragment.getText().toString();

                                            objWebMethods.findPersonOnlineByDocumentNumber(customerId);
                                        }
                                    })
                                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            setCustomer(null);
                                            InteractionFragment();
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        else
                        {
                            if (objConfiguration != null && objConfiguration.isOptionNewCustomer())
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle(R.string.app_name);
                                builder.setMessage(R.string.message_not_exist_and_create_customer)
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                String customerId = etCustomerId_SaleDataFragment.getText().toString();

                                                Person objPerson = new Person();
                                                objPerson.setDocumentNumber(customerId);
                                                if (customerId.length() == 9) objPerson.setPhone(customerId);    //Provisional
                                                newCustomer(objPerson);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                setCustomer(null);
                                                InteractionFragment();
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                            else
                            {
                                setCustomer(null);
                                //Toast.makeText(getActivity().getApplicationContext(), R.string.message_not_exist_customer, Toast.LENGTH_SHORT).show();
                                Utilities.showMessage(getActivity(), new String[]{ getString(R.string.message_not_exist_customer) });
                                InteractionFragment();
                            }
                        }
                    }
                    else if (processId == WebMethods.TYPE_FIND_PERSON_ONLINE)
                    {
                        if (objConfiguration != null && objConfiguration.isOptionNewCustomer())
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(R.string.app_name);
                            builder.setMessage(R.string.message_not_exist_and_create_customer)
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            String customerId = etCustomerId_SaleDataFragment.getText().toString();

                                            Person objPerson = new Person();
                                            objPerson.setDocumentNumber(customerId);
                                            if (customerId.length() == 9) objPerson.setPhone(customerId);    //Provisional
                                            newCustomer(objPerson);
                                        }
                                    })
                                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            setCustomer(null);
                                            InteractionFragment();
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        else
                        {
                            setCustomer(null);
                            Toast.makeText(getActivity().getApplicationContext(), R.string.message_not_exist_customer, Toast.LENGTH_SHORT).show();
                            InteractionFragment();
                        }
                    }
                    else if (processId == WebMethods.TYPE_LIST_CREDIT_SALES_PENDING_BY_CUSTOMER)
                    {
                        debtAmount = 0F;

                        if (objConfiguration != null && objConfiguration.isOptionCreditLine())
                        {
                            objWebMethods.getCreditLineByCustomer(objSale.getClient().getId(), objCompany.getId());
                        }
                    }
                    else if (processId == WebMethods.TYPE_LIST_CREDIT_LINE_BY_CUSTOMER)
                    {
                        this.creditLineAmount = 0F;

                        validatePaymentCondition();
                    }
                    else if (processId == WebMethods.TYPE_LIST_CREDIT_LINE_BY_VENDOR)
                    {
                        this.creditLineByVendorAmount = 0F;
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

            Toast.makeText(getActivity().getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
            Log.e("WS_SaleDataF", String.format("%s (processId: %s)", error_message, processId));
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(HashMap<String, Object> objects, int id);
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
                this.objConfigurationXUser = data.getParcelable("configuration_user");
            }
            if (data.get("saleId") != null)
            {
                this.objSale.setId(data.getString("saleId"));
            }
        }
    }

    private void Load()
    {
        tvOrderId_SaleDataFragment.setText(String.format("%s", this.objSale.getId()));
        etIssueDate_SaleDataFragment.setText(MyDateTime.format(MyDateTime.getCurrentDatetime(), MyDateTime.TYPE_DATE));
        etExpiryDate_SaleDataFragment.setText(MyDateTime.format(MyDateTime.getCurrentDatetime(), MyDateTime.TYPE_DATE));

        objWebMethods.getVoucherTypes();
        objWebMethods.getPaymentConditions();
        objWebMethods.getPaymentMethods();
        objWebMethods.getCustomerDefault();
        objWebMethods.getCreditLineByVendor(objUser.getEmployee().getPerson().getId(), objCompany.getId());
        //objWebMethods.getTempSaleHeader(saleId);
    }

    public void UpdateData(HashMap<String, Object> objects, int id)
    {
        final int fragment_2 = R.layout.fragment_sale_details;

        if ((objects != null ? objects.size() : 0) > 0)
        {
            if (objects.get("sale") != null)
            {
                Sale _sale = (Sale)objects.get("sale");
                if (_sale != null)
                {
                    if (id == fragment_2)
                    {
                        this.objSale.setSaleValue(_sale.getSaleValue());
                        this.objSale.setSubTotal(_sale.getSubTotal());
                        this.objSale.setTax(_sale.getTax());
                        this.objSale.setTotal(_sale.getTotal());
                    }
                }
            }
        }
    }

    private void InteractionFragment()
    {
        HashMap<String, Object> objects = new HashMap<>();

        final int fragment_1 = R.layout.fragment_sale_data;

        if (objSale != null)
        {
            if (spVoucherType_SaleDataFragment.getAdapter() != null)
            {
                objSale.setVoucherType(((SimpleClass<VoucherType>)spVoucherType_SaleDataFragment.getSelectedItem()).getTag());
            }
            if (spPaymentCondition_SaleDataFragment.getAdapter() != null)
            {
                objSale.setPaymentCondition(((SimpleClass<PaymentCondition>)spPaymentCondition_SaleDataFragment.getSelectedItem()).getTag());
            }
            if (spPaymentMethod_SaleDataFragment.getAdapter() != null)
            {
                objSale.setPaymentMethod(((SimpleClass<PaymentMethod>)spPaymentMethod_SaleDataFragment.getSelectedItem()).getTag());
            }

            /*if (objSale.getClient() == null) objSale.setClient(new Customer());
            objSale.getClient().setId(etCustomerId_SaleDataFragment.getText().length() > 0
                    ? etCustomerId_SaleDataFragment.getText().toString()
                    : null);*/

            objSale.setIssueDate(MyDateTime.parse(etIssueDate_SaleDataFragment.getText().toString(), MyDateTime.TYPE_DATE));
            objSale.setExpirationDate(MyDateTime.parse(etExpiryDate_SaleDataFragment.getText().toString(), MyDateTime.TYPE_DATE));

            objects.put("sale", objSale);
        }
        //objects.put("balance", balance);
        objects.put("creditLineAmount", this.creditLineAmount);
        objects.put("creditLineByVendorAmount", this.creditLineByVendorAmount);
        objects.put("creditLineByVendorBalance", this.creditLineByVendorBalance);

        mListener.onFragmentInteraction(objects, fragment_1);
    }

    private void setCustomer(Customer objCustomer)
    {
        boolean result = false;
        String message = "";

        if (objCustomer != null)
        {
            if (objCustomer.isEnabled())
            {
                if (objConfiguration != null &&
                        objConfiguration.isOptionVendors() &&
                        objConfiguration.isOptionVendorByCustomer())
                {
                    if (objCustomer.getEmployee() != null &&
                            (objCustomer.getEmployee().getPerson().getId().equals(objUser.getEmployee().getId())))
                    {
                        result = true;
                    }
                    else
                    {
                        message = getString(R.string.message_customer_not_belong_seller);
                    }
                }
                else
                {
                    result = true;
                }
            }
            else
            {
                message = getString(R.string.message_customer_disabled);
            }
        }

        objSale.setClient(result ? objCustomer : null);
        listCreditLineByCustomer = null;

        if (result)
        {
            etCustomerId_SaleDataFragment.setText(String.format("%s", objCustomer.getId()));
            etCustomerDocumentNumber_SaleDataFragment.setText(objCustomer.getPerson().getDocumentNumber());
            etCustomerBusinessName_SaleDataFragment.setText(objCustomer.getPerson().getBusinessName());
            etCustomerAddress_SaleDataFragment.setText(objCustomer.getPerson().getAddress());
            if (objCustomer.getEmployee() != null)
            {
                etSeller_SaleDataFragment.setText(objCustomer.getEmployee().getPerson().getNames());
            }
            if (objCustomer.getRoute() != null && objCustomer.getRoute().getZone() != null)
            {
                etSellerZone_SaleDataFragment.setText(String.format("%s", objCustomer.getRoute().getZone().getId()));
                etSellerRoute_SaleDataFragment.setText(String.format("%s", objCustomer.getRoute().getId()));
            }

            objWebMethods.getCreditSalesPendingByCustomer(objCustomer.getId(), objCompany.getId());
        }
        else
        {
            etCustomerId_SaleDataFragment.getText().clear();
            etCustomerDocumentNumber_SaleDataFragment.getText().clear();
            etCustomerBusinessName_SaleDataFragment.getText().clear();
            etCustomerAddress_SaleDataFragment.getText().clear();
            etSeller_SaleDataFragment.getText().clear();
            etSellerZone_SaleDataFragment.getText().clear();
            etSellerRoute_SaleDataFragment.getText().clear();

            if (!message.isEmpty())
            {
                Utilities.showMessage(getActivity(), new String[]{ message });
                //Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            validatePaymentCondition();
        }
    }

    private void validatePaymentCondition()
    {
        SimpleClass<PaymentCondition> objPaymentCondition = (SimpleClass<PaymentCondition>)spPaymentCondition_SaleDataFragment.getSelectedItem();

        if (objPaymentCondition.getTag().getId() == ConstantData.PaymentCondition.CREDIT &&
                objSale.getClient() != null &&
                (listCreditLineByCustomer != null && listCreditLineByCustomer.size() > 0))
        {
            /*debtAmount = 0F;
            if (listCreditSaleByCustomer != null)
            {
                for (int i = 0; i < listCreditSaleByCustomer.size(); i++)
                {
                    debtAmount += listCreditSaleByCustomer.get(i).getAmount();
                }
            }*/

            if (objSale.getClient().getStatus() == ConstantData.CustomerStatus.DELINQUENT &&
                    debtAmount >= listCreditLineByCustomer.get(0).getAmount())
            {
                spPaymentCondition_SaleDataFragment.setSelection(0);
                Utilities.showMessage(getActivity(), new String[]{ getString(R.string.message_customer_delinqued_no_credit) });
            }
            else
            {
                Calendar _expiryDate = (Calendar) issueDate.clone();
                _expiryDate.add(Calendar.DAY_OF_YEAR, listCreditLineByCustomer.get(0).getDays());
                setExpiryDate(_expiryDate.get(Calendar.DAY_OF_MONTH), _expiryDate.get(Calendar.MONTH), _expiryDate.get(Calendar.YEAR));
            }
        }
        else
        {
            Calendar _expiryDate = (Calendar) issueDate.clone();
            _expiryDate.add(Calendar.DAY_OF_YEAR, objPaymentCondition.getTag().getDueDays() != null ? objPaymentCondition.getTag().getDueDays() : 0);
            setExpiryDate(_expiryDate.get(Calendar.DAY_OF_MONTH), _expiryDate.get(Calendar.MONTH), _expiryDate.get(Calendar.YEAR));
        }
    }

    private void setIssueDate(int day, int month, int year)
    {
        issueDate.set(year, month, day);

        String date = MyDateTime.format(issueDate.getTime(), MyDateTime.TYPE_DATE);
        etIssueDate_SaleDataFragment.setText(date);

        if (expiryDate.getTimeInMillis() < issueDate.getTimeInMillis())
        {
            setExpiryDate(day, month, year);
        }
    }

    private void setExpiryDate(int day, int month, int year)
    {
        expiryDate.set(year, month, day);

        String date = MyDateTime.format(expiryDate.getTime(), MyDateTime.TYPE_DATE);
        etExpiryDate_SaleDataFragment.setText(date);
    }

    private void newCustomer(Person objPerson)
    {
        Intent activity = new Intent(getActivity().getApplicationContext(), CustomerActivity.class);
        activity.putExtra("configuration", objConfiguration);
        activity.putExtra("person", objPerson);
        activity.putExtra(CustomerActivity.CONST_ACTION, CustomerActivity.ACTION_ADD_BY_SEARCH);
        resultLauncherCustomerAdd.launch(activity);
    }
}