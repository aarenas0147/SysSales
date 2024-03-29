package com.aarenas.syssales;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

import Connection.WebMethods;
import Connection.WebServices;
import Data.MyDateTime;
import Data.MyMath;
import Data.MyPdf;
import Data.Objects.Company;
import Data.Objects.Configuration;
import Data.Objects.CreditLine;
import Data.Objects.Sale;
import Data.Objects.User;
import Data.Utilities;

public class SaleSummaryFragment extends Fragment implements WebServices.OnResult {

    public SaleSummaryFragment() {
        // Required empty public constructor
    }

    public static SaleSummaryFragment newInstance(Bundle params) {
        SaleSummaryFragment fragment = new SaleSummaryFragment();
        fragment.setArguments(params);
        return fragment;
    }

    //Controls:
    private Button btnCalculator_SaleSummaryFragment, btnSend_SaleSummaryFragment, btnExit_SaleSummaryFragment;
    private TextInputEditText etCreditLineAmount_SaleSummaryFragment, etCreditLineBalance_SaleSummaryFragment,
            etSaleValue_SaleSummaryFragment, etSubtotal_SaleSummaryFragment,
            etTaxes_SaleSummaryFragment, etTotal_SaleSummaryFragment;
    private LinearLayout lytCreditLine_SaleSummaryFragment;

    //Parameters:
    private Bundle parameters;
    private Configuration objConfiguration;
    private User objUser;
    private Company objCompany;
    Sale objSale = new Sale();

    private float debtAmount, creditLineAmount;

    //Variables:
    private SharedPreferences preferences;
    private WebMethods objWebMethods;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parameters = getArguments();
        LoadParameters(parameters);

        preferences = getActivity().getSharedPreferences(getActivity().getPackageName() + "_preferences", Context.MODE_PRIVATE);
        objWebMethods = new WebMethods(getActivity(), SaleSummaryFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sale_summary, container, false);

        btnCalculator_SaleSummaryFragment = rootView.findViewById(R.id.btnCalculator_SaleSummaryFragment);
        btnSend_SaleSummaryFragment = rootView.findViewById(R.id.btnSend_SaleSummaryFragment);
        btnExit_SaleSummaryFragment = rootView.findViewById(R.id.btnExit_SaleSummaryFragment);
        etCreditLineAmount_SaleSummaryFragment = rootView.findViewById(R.id.etCreditLineAmount_SaleSummaryFragment);
        etCreditLineBalance_SaleSummaryFragment = rootView.findViewById(R.id.etCreditLineBalance_SaleSummaryFragment);
        etSaleValue_SaleSummaryFragment = rootView.findViewById(R.id.etSaleValue_SaleSummaryFragment);
        etSubtotal_SaleSummaryFragment = rootView.findViewById(R.id.etSubtotal_SaleSummaryFragment);
        etTaxes_SaleSummaryFragment = rootView.findViewById(R.id.etTaxes_SaleSummaryFragment);
        etTotal_SaleSummaryFragment = rootView.findViewById(R.id.etTotal_SaleSummaryFragment);
        lytCreditLine_SaleSummaryFragment = rootView.findViewById(R.id.lytCreditLine_SaleSummaryFragment);

        lytCreditLine_SaleSummaryFragment.setVisibility(objConfiguration != null && objConfiguration.isOptionCreditLine() ? View.VISIBLE : View.GONE);

        btnCalculator_SaleSummaryFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalculator();
            }
        });

        btnSend_SaleSummaryFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDataCompleted())
                {
                    objWebMethods.saveSale(objSale.getId(), objCompany.getId(), objSale.getVoucherType().getId(),
                            objSale.getPaymentCondition().getId(), objSale.getPaymentMethod().getId(),
                            MyDateTime.format(objSale.getIssueDate(), MyDateTime.TYPE_DATE),
                            MyDateTime.format(objSale.getExpirationDate(), MyDateTime.TYPE_DATE),
                            MyDateTime.format(MyDateTime.toLocalTimeZone(MyDateTime.getCurrentDatetime()), MyDateTime.TYPE_DATETIME),
                            objSale.getClient().getId(), (int)objUser.getEmployee().getPerson().getId(), objUser.getId());
                }
                else
                {
                    Utilities.showMessage(getActivity(), new String[] { getString(R.string.message_incomplete_data) });
                    //Toast.makeText(getActivity().getApplicationContext(), R.string.message_incomplete_data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnExit_SaleSummaryFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        Load();

        return rootView;
    }

    @Override
    public void processFinish(WebServices.Result result, int processId) {
        try
        {
            if (result.getResultCode().getId() == WebServices.Result.RESULT_OK)
            {
                if (!WebServices.isNull(result.getResult()))
                {
                    if (processId == WebMethods.TYPE_SAVE_SALE)
                    {
                        SoapObject array = (SoapObject)result.getResult();
                        if (array.getPropertyCount() > 0)
                        {
                            boolean reply = (boolean)array.getProperty(0);
                            String message = (String)array.getProperty(1);

                            if (reply)
                            {
                                btnSend_SaleSummaryFragment.setEnabled(false);

                                if (objConfiguration != null && objConfiguration.isOptionPrintSale())
                                {
                                    print();
                                }
                                else
                                {
                                    Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                }
                            }
                            else
                            {
                                String errorMessage = (String)array.getProperty(2);

                                String customErrorMessage = String.format("%s%s", message,
                                        (errorMessage != null && !errorMessage.isEmpty()
                                                && preferences.getBoolean("depuration", false) ? String.format("\nError:\n%s", errorMessage) : ""));

                                /*Toast.makeText(getActivity().getApplicationContext(),
                                        errorMessage != null && !errorMessage.isEmpty() ? message : customErrorMessage,
                                        Toast.LENGTH_SHORT).show();*/
                                Utilities.showMessage(getActivity(), new String[] { errorMessage != null && !errorMessage.isEmpty() ? message : customErrorMessage });
                                Log.e("save_sale_error", (errorMessage != null && !errorMessage.isEmpty() ? errorMessage : "Error al grabar venta"));
                            }
                        }
                    }
                    else if (processId == WebMethods.TYPE_PRINT_SALE)
                    {
                        getActivity().finish();
                    }
                    else if (processId == WebMethods.TYPE_PRINT_SALE_IN_PDF)
                    {
                        SoapObject array = (SoapObject)result.getResult();
                        if (array.getPropertyCount() > 0)
                        {
                            boolean reply = Boolean.parseBoolean(array.getProperty(0).toString());
                            if (reply)
                            {
                                if (array.getPropertyCount() >= 3 && array.getProperty(1) != null)
                                {
                                    String fileName = (String) array.getProperty(1);
                                    byte[] fileArray = Base64.decode(((String) array.getProperty(2)).getBytes(), 0);
                                    if (fileArray != null)
                                    {
                                        MyPdf myPdf = new MyPdf(getActivity().getApplicationContext());
                                        myPdf.clearCache();
                                        myPdf.openDocument(fileArray, fileName);

                                        getActivity().finish();
                                    }
                                }
                            }
                            else
                            {
                                String errorMessage = (String) array.getProperty(1);
                                Toast.makeText(getActivity().getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
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
            String error_message = preferences.getBoolean("depuration", false) ? e.getMessage() : getString(R.string.message_web_services_error);

            Toast.makeText(getActivity().getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
            Log.e("WS_SaleSummaryF", String.format("%s (processId: %s)", error_message, processId));
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
            if (data.get("saleId") != null)
            {
                this.objSale.setId(data.getString("saleId"));
            }
        }
    }

    private void Load()
    {
        if (this.objSale != null)
        {
            etSaleValue_SaleSummaryFragment.setText(MyMath.toDecimal(this.objSale.getSaleValue(), 2));
            etSubtotal_SaleSummaryFragment.setText(MyMath.toDecimal(this.objSale.getSubTotal(), 2));
            etTaxes_SaleSummaryFragment.setText(MyMath.toDecimal(this.objSale.getTax(), 2));
            etTotal_SaleSummaryFragment.setText(MyMath.toDecimal(this.objSale.getTotal(), 2));

            etCreditLineAmount_SaleSummaryFragment.setText(MyMath.toDecimal(this.creditLineAmount, 2));
            etCreditLineBalance_SaleSummaryFragment.setText(MyMath.toDecimal(this.creditLineAmount -
                    this.objSale.getTotal(), 2));
        }
    }

    public void UpdateData(HashMap<String, Object> objects, int id)
    {
        final int fragment_1 = R.layout.fragment_sale_data;
        final int fragment_2 = R.layout.fragment_sale_details;

        if ((objects != null ? objects.size() : 0) > 0)
        {
            if (objects.get("sale") != null)
            {
                Sale _sale = (Sale)objects.get("sale");
                if (_sale != null)
                {
                    if (id == fragment_1)
                    {
                        this.objSale.setVoucherType(_sale.getVoucherType());
                        this.objSale.setPaymentCondition(_sale.getPaymentCondition());
                        this.objSale.setPaymentMethod(_sale.getPaymentMethod());
                        this.objSale.setClient(_sale.getClient());
                        this.objSale.setIssueDate(_sale.getIssueDate());
                        this.objSale.setExpirationDate(_sale.getExpirationDate());
                    }
                    else if (id == fragment_2)
                    {
                        this.objSale.setSaleValue(_sale.getSaleValue());
                        this.objSale.setSubTotal(_sale.getSubTotal());
                        this.objSale.setTax(_sale.getTax());
                        this.objSale.setTotal(_sale.getTotal());

                        if (getView() != null)
                        {
                            Load();
                        }
                    }
                }
            }

            if (objects.get("creditLineAmount") != null)
            {
                this.creditLineAmount = Float.parseFloat(objects.get("creditLineAmount").toString());

                if (getView() != null)
                {
                    Load();
                }
            }
        }
    }

    private boolean isDataCompleted()
    {
        if (this.objSale == null)
        {
            return false;
        }
        else
        {
            if (this.objSale.getVoucherType() == null || this.objSale.getPaymentCondition() == null
                    || this.objSale.getPaymentMethod() == null || this.objSale.getClient() == null
                    || this.objSale.getIssueDate() == null || this.objSale.getExpirationDate() == null)
            {
                return false;
            }
            else
            {
                if (this.objSale.getVoucherType().getId() == null || /*this.objSale.getPaymentCondition().getId() == null
                        ||*/ this.objSale.getPaymentMethod().getId() == null|| this.objSale.getClient().getId() == null)
                {
                    return false;
                }
            }
        }

        return true;
    }

    private void openCalculator()
    {
        Intent calculator;
        try {
            calculator = Utilities.openCalculator();
            startActivity(calculator);
        }
        catch (Exception e)
        {
            calculator = Utilities.openCalculator2(getActivity().getApplicationContext());
            if (calculator != null){
                startActivity(calculator);
            }
        }
    }

    private void print()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                    //Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}