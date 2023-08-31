package com.aarenas.syssales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import Connection.WebMethods;
import Connection.WebServices;
import Data.MyMath;
import Data.Objects.PaymentMethod;
import Data.Objects.PaymentMethodDetail;
import Data.Objects.Product;
import Data.Objects.SalePaymentMethod;
import Data.Objects.Sale;
import Design.SimpleClass;
import Design.SpinnerAdapter;

public class SalePaymentMethodActivity extends AppCompatActivity implements WebServices.OnResult {

    //Controls:
    TextInputEditText etAmount_PaymentMethodBySale, etCommissionPercentage_PaymentMethodBySale,
            etTotal_PaymentMethodBySale;
    Spinner spPaymentMethodDetail_PaymentMethodBySale;
    LinearLayout lytPaymentMethodDetail_PaymentMethodBySale;
    Button btnAccept_PaymentMethodBySale;

    //Parameters:
    Bundle parameters;
    Sale objSale = new Sale();
    SalePaymentMethod objSalePaymentMethod;
    float balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_payment_method);

        parameters = getIntent().getExtras();
        LoadParameters(parameters);

        etAmount_PaymentMethodBySale = (TextInputEditText) findViewById(R.id.etAmount_PaymentMethodBySale);
        etCommissionPercentage_PaymentMethodBySale = (TextInputEditText) findViewById(R.id.etCommissionPercentage_PaymentMethodBySale);
        etTotal_PaymentMethodBySale = (TextInputEditText) findViewById(R.id.etTotal_PaymentMethodBySale);
        spPaymentMethodDetail_PaymentMethodBySale = (Spinner) findViewById(R.id.spPaymentMethodDetail_PaymentMethodBySale);
        lytPaymentMethodDetail_PaymentMethodBySale = (LinearLayout) findViewById(R.id.lytPaymentMethodDetail_PaymentMethodBySale);
        btnAccept_PaymentMethodBySale = (Button) findViewById(R.id.btnAccept_PaymentMethodBySale);

        etAmount_PaymentMethodBySale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculate();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        spPaymentMethodDetail_PaymentMethodBySale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                calculate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAccept_PaymentMethodBySale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentMethodDetail objPaymentMethodDetail =
                        spPaymentMethodDetail_PaymentMethodBySale.getSelectedItem() != null ?
                        ((SimpleClass<PaymentMethodDetail>)spPaymentMethodDetail_PaymentMethodBySale.getSelectedItem()).getTag() : null;

                if (MyMath.isNumeric(Float.parseFloat(etAmount_PaymentMethodBySale.getText().toString())))
                {
                    float amount = Float.parseFloat(etAmount_PaymentMethodBySale.getText().toString());
                    if (amount > 0.0F)
                    {
                        if (amount <= Float.parseFloat(
                                MyMath.toDecimal(objSalePaymentMethod.getAmount() + balance, 2)))
                        {
                            WebMethods objWebMethods = new WebMethods(SalePaymentMethodActivity.this,
                                    SalePaymentMethodActivity.this);
                            if (objSalePaymentMethod.getId() == null)
                            {
                                objWebMethods.addSalePaymentMethod(objSale.getId(),
                                        objSalePaymentMethod.getPaymentMethod().getId(),
                                        objPaymentMethodDetail != null ? objPaymentMethodDetail.getId() : null,
                                        amount);
                            }
                            else
                            {
                                objWebMethods.modifySalePaymentMethod(objSale.getId(),
                                        objSalePaymentMethod.getPaymentMethod().getId(),
                                        objPaymentMethodDetail != null ? objPaymentMethodDetail.getId() : null,
                                        amount);
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "¡El monto a pagar excede el importe!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "¡El monto a pagar debe ser mayor a cero!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

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
                    if (processId == WebMethods.TYPE_LIST_PAYMENT_METHOD_DETAILS)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<PaymentMethodDetail> list = PaymentMethodDetail.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            List<SimpleClass<PaymentMethodDetail>> listAdapter = new ArrayList<>();
                            SimpleClass<PaymentMethodDetail> objSimpleClass;
                            for (int i=0; i < list.size(); i++)
                            {
                                objSimpleClass = new SimpleClass<>();
                                objSimpleClass.setId(list.get(i).getId());
                                objSimpleClass.setDescription(list.get(i).getDescription());
                                objSimpleClass.setTag(list.get(i));
                                listAdapter.add(objSimpleClass);
                            }

                            SpinnerAdapter<PaymentMethodDetail> adapter =
                                    new SpinnerAdapter<>(getApplicationContext(), listAdapter);
                            spPaymentMethodDetail_PaymentMethodBySale.setAdapter(adapter);

                            lytPaymentMethodDetail_PaymentMethodBySale.setVisibility(View.VISIBLE);
                        }
                    }
                    else if (processId == WebMethods.TYPE_ADD_PAYMENT_METHOD_BY_SALE
                            || processId == WebMethods.TYPE_MODIFY_PAYMENT_METHOD_BY_SALE)
                    {
                        boolean reply = Boolean.parseBoolean(result.getResult().toString());

                        if (reply)
                        {
                            Toast.makeText(getApplicationContext(), R.string.message_changes_performed_successfully, Toast.LENGTH_SHORT).show();

                            Intent _result = new Intent();
                            setResult(RESULT_OK, _result);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "¡Error al guardar!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    if (processId == WebMethods.TYPE_LIST_PAYMENT_METHOD_DETAILS) {
                        spPaymentMethodDetail_PaymentMethodBySale.setAdapter(null);

                        lytPaymentMethodDetail_PaymentMethodBySale.setVisibility(View.GONE);
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
            Log.e("WS_PaymentMBS", String.format("%s (processId: %s)", error_message, processId));
        }
    }

    private void LoadParameters(Bundle data)
    {
        if (data != null)
        {
            if (data.get("sale") != null)
            {
                Sale _sale = data.getParcelable("sale");
                if (_sale != null)
                {
                    this.objSale.setId(_sale.getId());
                    this.objSale.setTotal(_sale.getTotal());
                }
            }
            if (data.get("sale_payment_method") != null)
            {
                this.objSalePaymentMethod = data.getParcelable("sale_payment_method");
            }
            if (data.get("balance") != null)
            {
                this.balance = data.getFloat("balance");
            }
        }
    }

    private void Load()
    {
        if (this.objSalePaymentMethod != null)
        {
            etAmount_PaymentMethodBySale.setText(MyMath.toDecimal(this.objSalePaymentMethod.getId() != null
                    ? this.objSalePaymentMethod.getAmount() : balance, 2));
            etCommissionPercentage_PaymentMethodBySale.setText(MyMath.toDecimal(this.objSalePaymentMethod.getCommissionPercentage(), 2));
            etTotal_PaymentMethodBySale.setText(MyMath.toDecimal(this.objSalePaymentMethod.getTotal(), 2));
        }

        WebMethods objWebMethods = new WebMethods(SalePaymentMethodActivity.this, SalePaymentMethodActivity.this);
        objWebMethods.getPaymentMethodDetails(objSalePaymentMethod.getPaymentMethod().getId());
    }

    private void calculate()
    {
        PaymentMethodDetail objPaymentMethodDetail = spPaymentMethodDetail_PaymentMethodBySale.getSelectedItem() != null ?
                ((SimpleClass<PaymentMethodDetail>)spPaymentMethodDetail_PaymentMethodBySale.getSelectedItem()).getTag() : null;
        float commission = objPaymentMethodDetail != null ? objPaymentMethodDetail.getCommissionPercentage() : 0;
        etCommissionPercentage_PaymentMethodBySale.setText(MyMath.toDecimal(commission, 2));

        if (isDataCompleted())
        {
            float amount, total;

            amount = Float.parseFloat(etAmount_PaymentMethodBySale.getText().toString());
            total = amount + (amount * (commission / 100));

            etTotal_PaymentMethodBySale.setText(MyMath.toDecimal(total, 2));
        }
        else
        {
            etTotal_PaymentMethodBySale.setText(MyMath.toDecimal(0F, 2));
        }
    }

    private boolean isDataCompleted()
    {
        if (etAmount_PaymentMethodBySale.getText().length() > 0)
        {
            if (MyMath.isNumeric(etAmount_PaymentMethodBySale.getText().toString()))
            {
                return true;
            }
        }

        return false;
    }
}