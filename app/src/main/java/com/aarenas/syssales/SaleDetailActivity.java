package com.aarenas.syssales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Presentation;
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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

import Connection.WebMethods;
import Connection.WebServices;
import Data.MyMath;
import Data.Objects.Product;
import Data.Objects.Sale;
import Data.Objects.SaleDetail;
import Data.Objects.User;
import Data.Objects.VoucherType;
import Data.Utilities;
import Design.SimpleClass;
import Design.SpinnerAdapter;

public class SaleDetailActivity extends AppCompatActivity implements WebServices.OnResult {

    //Constants:
    public static final String CONST_RESULT = "result";
    public static final int RESULT_VIEW = 1;
    public static final int RESULT_ADD = 2;
    public static final int RESULT_MODIFY = 3;

    //Controls:
    TextInputEditText etProductId_SaleDetail, etDescription_SaleDetail, etStock_SaleDetail,
            etQuantity_SaleDetail, etSellingPrice_SaleDetail, etLimitPrice_SaleDetail,
            etCreditPrice_SaleDetail, etAmount_SaleDetail;
    Spinner spMeasureUnit_SaleDetail;
    Button btnAccept_SaleDetail;

    //Parameters:
    Bundle parameters;
    int resultType;
    User objUser;
    SaleDetail objSaleDetail = new SaleDetail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        parameters = getIntent().getExtras();
        LoadParameters(parameters);

        etProductId_SaleDetail = findViewById(R.id.etProductId_SaleDetail);
        etDescription_SaleDetail = findViewById(R.id.etDescription_SaleDetail);
        etStock_SaleDetail = findViewById(R.id.etStock_SaleDetail);
        etQuantity_SaleDetail = findViewById(R.id.etQuantity_SaleDetail);
        etSellingPrice_SaleDetail = findViewById(R.id.etSellingPrice_SaleDetail);
        etLimitPrice_SaleDetail = findViewById(R.id.etLimitPrice_SaleDetail);
        etCreditPrice_SaleDetail = findViewById(R.id.etCreditPrice_SaleDetail);
        etAmount_SaleDetail = findViewById(R.id.etAmount_SaleDetail);
        spMeasureUnit_SaleDetail = findViewById(R.id.spMeasureUnit_SaleDetail);
        btnAccept_SaleDetail = findViewById(R.id.btnAccept_SaleDetail);

        etQuantity_SaleDetail.addTextChangedListener(new TextWatcher() {
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

        etSellingPrice_SaleDetail.addTextChangedListener(new TextWatcher() {
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

        spMeasureUnit_SaleDetail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //SimpleClass<Product> objPresentation = (SimpleClass<Product>)adapterView.getItemAtPosition(i);
                Product objPresentation = (Product)((SimpleClass<?>)adapterView.getItemAtPosition(i)).getTag();
                if (objPresentation != null)
                {
                    //etStock_SaleDetail.setText(MyMath.toRoundNumber(objPresentation.getTag().getStock()));
                    WebMethods objWebMethods = new WebMethods(SaleDetailActivity.this, SaleDetailActivity.this);
                    objWebMethods.getStockByPresentation(objPresentation.getId(), objUser.getEmployee().getOutlet().getId());

                    etSellingPrice_SaleDetail.setText(MyMath.toDecimal(objPresentation.getPrice(), 2));
                    etLimitPrice_SaleDetail.setText(MyMath.toDecimal(objPresentation.getLimitPrice(), 2));
                    etCreditPrice_SaleDetail.setText(MyMath.toDecimal(objPresentation.getCreditPrice(), 2));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAccept_SaleDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.clearFocus(SaleDetailActivity.this);

                if (isDataCompleted())
                {
                    float quantity, sellingPrice, total;

                    quantity = Float.parseFloat(etQuantity_SaleDetail.getText().toString());
                    sellingPrice = Float.parseFloat(etSellingPrice_SaleDetail.getText().toString());
                    //total = sellingPrice * quantity;
                    Product objPresentation = (Product)((SimpleClass<?>)spMeasureUnit_SaleDetail.getSelectedItem()).getTag();

                    if (quantity > 0.0F && sellingPrice > 0.0F
                            && objPresentation != null)
                    {
                        if(sellingPrice >= objPresentation.getLimitPrice())
                        {
                            String s = String.format("Id: %s; Product: %s; Employee: %s; Document: %s; " +
                                            "Condition: %s; Quantity: %s; Price: %s",
                                    objSaleDetail.getSale().getId(),
                                    objPresentation.getId(),
                                    objSaleDetail.getSale().getEmployee().getPerson().getId(),
                                    objSaleDetail.getSale().getVoucherType().getId(),
                                    objSaleDetail.getSale().getPaymentCondition(), quantity, sellingPrice);
                            Log.e("item_data", s);

                            if (resultType == RESULT_ADD)
                            {
                                WebMethods objWebMethods = new WebMethods(SaleDetailActivity.this, SaleDetailActivity.this);
                                objWebMethods.addItem(objSaleDetail.getSale().getId(),
                                        objPresentation.getId(),
                                        (int)objSaleDetail.getSale().getEmployee().getPerson().getId(),
                                        (String)objSaleDetail.getSale().getVoucherType().getId(),
                                        objSaleDetail.getSale().getPaymentCondition().getId(), quantity, sellingPrice);
                            }
                            else if (resultType == RESULT_MODIFY)
                            {
                                WebMethods objWebMethods = new WebMethods(SaleDetailActivity.this, SaleDetailActivity.this);
                                objWebMethods.modifyItem(objSaleDetail.getSale().getId(),
                                        objPresentation.getId(),
                                        (int)objSaleDetail.getSale().getEmployee().getPerson().getId(),
                                        (String)objSaleDetail.getSale().getVoucherType().getId(),
                                        objSaleDetail.getSale().getPaymentCondition().getId(), objSaleDetail.getQuantity(), quantity, sellingPrice);
                            }

                            /*Intent result = new Intent();
                            OutputParameters(result, objSaleDetail);
                            setResult(RESULT_OK, result);
                            finish();*/
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), R.string.message_limit_exceeds, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.message_incomplete_data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Load();
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
                    if (processId == WebMethods.TYPE_LIST_PRESENTATIONS)
                    {
                        JSONArray jsonArray = new JSONArray(result.getResult().toString());
                        List<Product> list = Product.getList(jsonArray);
                        if (list != null && list.size() > 0)
                        {
                            List<SimpleClass<Product>> listAdapter = new ArrayList<>();
                            SimpleClass<Product> objSimpleClass;
                            for (int i=0; i < list.size(); i++)
                            {
                                objSimpleClass = new SimpleClass<>();
                                objSimpleClass.setId(list.get(i).getId());
                                objSimpleClass.setDescription(list.get(i).getMeasureUnit().getDescription());
                                objSimpleClass.setTag(list.get(i));
                                listAdapter.add(objSimpleClass);
                            }

                            SpinnerAdapter<Product> adapter =
                                    new SpinnerAdapter<>(getApplicationContext(), listAdapter);
                            spMeasureUnit_SaleDetail.setAdapter(adapter);
                        }
                    }
                    else if (processId == WebMethods.TYPE_GET_STOCK_BY_PRESENTATION)
                    {
                        float stock = Float.parseFloat(result.getResult().toString());
                        etStock_SaleDetail.setText(MyMath.toRoundNumber(stock));
                    }
                    else if (processId == WebMethods.TYPE_ADD_ITEM_SALE)
                    {
                        SoapObject array = (SoapObject)result.getResult();
                        if (array.getPropertyCount() > 0)
                        {
                            boolean reply = (boolean)array.getProperty(0);
                            String message = (String)array.getProperty(1);

                            if (reply)
                            {
                                Toast.makeText(getApplicationContext(), R.string.message_changes_performed_successfully, Toast.LENGTH_SHORT).show();

                                Intent _result = new Intent();
                                setResult(RESULT_OK, _result);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else if (processId == WebMethods.TYPE_MODIFY_ITEM_SALE)
                    {
                        SoapObject array = (SoapObject)result.getResult();
                        if (array.getPropertyCount() > 0)
                        {
                            boolean reply = (boolean)array.getProperty(0);
                            String message = (String)array.getProperty(1);

                            if (reply)
                            {
                                Toast.makeText(getApplicationContext(), R.string.message_changes_performed_successfully, Toast.LENGTH_SHORT).show();

                                Intent _result = new Intent();
                                setResult(RESULT_OK, _result);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String error_message = preferences.getBoolean("depuration", false) ? e.getMessage() : getString(R.string.message_web_services_error);

            Toast.makeText(getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
            Log.e("WS_SaleDetail", String.format("%s (processId: %s)", error_message, processId));
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
            if (data.get("sale") != null)
            {
                Sale _sale = data.getParcelable("sale");
                if (_sale != null)
                {
                    this.objSaleDetail.setSale(new Sale());
                    this.objSaleDetail.getSale().setId(_sale.getId());
                    this.objSaleDetail.getSale().setVoucherType(_sale.getVoucherType());
                    this.objSaleDetail.getSale().setPaymentCondition(_sale.getPaymentCondition());
                    this.objSaleDetail.getSale().setEmployee(_sale.getEmployee());
                }
            }
            if (data.get("product") != null)
            {
                this.objSaleDetail.setProduct(data.getParcelable("product"));
            }
            if (data.get("quantity") != null)
            {
                this.objSaleDetail.setQuantity(data.getFloat("quantity"));
            }
        }
    }

    private void OutputParameters(Intent intent, SaleDetail objSaleDetail)
    {
        intent.putExtra("saleDetail", objSaleDetail);
    }

    private void Load()
    {
        if (objSaleDetail != null && objSaleDetail.getProduct() != null)
        {
            etProductId_SaleDetail.setText(String.format("%s", objSaleDetail.getProduct().getId()));
            etDescription_SaleDetail.setText(objSaleDetail.getProduct().getDescription());
            //etStock_SaleDetail.setText(MyMath.toRoundNumber(objSaleDetail.getProduct().getStock()));
            /*etSellingPrice_SaleDetail.setText(MyMath.toDecimal(objSaleDetail.getProduct().getPrice(), 2));
            etLimitPrice_SaleDetail.setText(MyMath.toDecimal(objSaleDetail.getProduct().getLimitPrice(), 2));
            etCreditPrice_SaleDetail.setText(MyMath.toDecimal(objSaleDetail.getProduct().getCreditPrice(), 2));*/

            WebMethods objWebMethods = new WebMethods(this, this);
            objWebMethods.getPresentations(objSaleDetail.getProduct().getId(), objUser.getEmployee().getOutlet().getId());
        }
    }

    private void calculate()
    {
        if (isDataCompleted())
        {
            float quantity, sellingPrice, total;

            quantity = Float.parseFloat(etQuantity_SaleDetail.getText().toString());
            sellingPrice = Float.parseFloat(etSellingPrice_SaleDetail.getText().toString());
            total = sellingPrice * quantity;

            /*total -= CalcularDescuento(quantity);
            this.importeTotal = total;*/

            etAmount_SaleDetail.setText(MyMath.toDecimal(total, 2));
        }
        else
        {
            etAmount_SaleDetail.setText(MyMath.toDecimal(0F, 2));
        }
    }

    private boolean isDataCompleted()
    {
        if (etQuantity_SaleDetail.getText().length() > 0
                && etSellingPrice_SaleDetail.getText().length() > 0)
        {
            return MyMath.isNumeric(etQuantity_SaleDetail.getText().toString())
                    && MyMath.isNumeric(etSellingPrice_SaleDetail.getText().toString());
        }

        return false;
    }
}