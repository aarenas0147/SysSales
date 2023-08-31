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
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import Connection.WebMethods;
import Connection.WebServices;
import Data.MyDateTime;
import Data.MyMath;
import Data.Objects.CreditSale;
import Data.Objects.CreditSaleQuote;
import Data.Objects.User;
import Design.CreditSaleQuotesAdapter;

public class CreditSaleQuoteActivity extends AppCompatActivity implements WebServices.OnResult {

    //Controls:
    TextInputEditText etQuoteNumber_CreditSaleQuote, etAmount_CreditSaleQuote, etPayment_CreditSaleQuote,
            etBalance_CreditSaleQuote, etCreationDate_CreditSaleQuote;
    Button btnAccept_CreditSaleQuote;

    //Parameters:
    Bundle parameters;
    CreditSale objCreditSale;

    //Variables:
    CreditSaleQuote objCreditSaleQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_sale_quote);

        parameters = getIntent().getExtras();
        LoadParameters(parameters);

        etQuoteNumber_CreditSaleQuote = findViewById(R.id.etQuoteNumber_CreditSaleQuote);
        etAmount_CreditSaleQuote = findViewById(R.id.etAmount_CreditSaleQuote);
        etPayment_CreditSaleQuote = findViewById(R.id.etPayment_CreditSaleQuote);
        etBalance_CreditSaleQuote = findViewById(R.id.etBalance_CreditSaleQuote);
        etCreationDate_CreditSaleQuote = findViewById(R.id.etCreationDate_CreditSaleQuote);
        btnAccept_CreditSaleQuote = findViewById(R.id.btnAccept_CreditSaleQuote);

        etPayment_CreditSaleQuote.addTextChangedListener(new TextWatcher() {
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

        btnAccept_CreditSaleQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (objCreditSaleQuote != null
                        && MyMath.isNumeric(etPayment_CreditSaleQuote.getText().toString()))
                {
                    float payment = Float.parseFloat(etPayment_CreditSaleQuote.getText().toString());

                    if (payment > 0.0F)
                    {
                        WebMethods objWebMethods = new WebMethods(CreditSaleQuoteActivity.this, CreditSaleQuoteActivity.this);
                        objWebMethods.saveCreditSaleQuote(objCreditSale.getId(), objCreditSaleQuote.getQuoteNumber(),
                                objCreditSale.getSale().getClient().getId(), payment, etCreationDate_CreditSaleQuote.getText().toString());
                    }
                }
            }
        });

        etAmount_CreditSaleQuote.setText(MyMath.toDecimal(objCreditSale.getAmount(), 2));
        etBalance_CreditSaleQuote.setText(MyMath.toDecimal(objCreditSale.getAmount(), 2));
        etCreationDate_CreditSaleQuote.setText(MyDateTime.format(new Date(), MyDateTime.TYPE_DATE));

        WebMethods objWebMethods = new WebMethods(this, this);
        objWebMethods.newCreditSaleQuote(objCreditSale.getId());
    }

    @Override
    public void processFinish(WebServices.Result result, int processId) {
        try
        {
            if (result.getResultCode().getId() == WebServices.Result.RESULT_OK)
            {
                if (!WebServices.isNull(result.getResult()))
                {
                    if (processId == WebMethods.TYPE_NEW_CREDIT_SALE_QUOTE)
                    {
                        int quoteNumber = Integer.parseInt(result.getResult().toString());

                        if (quoteNumber != 0)
                        {
                            objCreditSaleQuote = new CreditSaleQuote();
                            objCreditSaleQuote.setQuoteNumber(quoteNumber);

                            etQuoteNumber_CreditSaleQuote.setText(String.format("%s", objCreditSaleQuote.getQuoteNumber()));
                        }
                    }
                    else if (processId == WebMethods.TYPE_SAVE_CREDIT_SALE_QUOTE)
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
                    if (processId == WebMethods.TYPE_NEW_CREDIT_SALE_QUOTE)
                    {
                        objCreditSaleQuote = null;
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
            Log.e("WS_CreditSaleQ", String.format("%s (processId: %s)", error_message, processId));
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

    private void calculate()
    {
        if (etPayment_CreditSaleQuote.getText().length() > 0)
        {
            if (MyMath.isNumeric(etPayment_CreditSaleQuote.getText().toString()))
            {
                float payment, total;

                payment = Float.parseFloat(etPayment_CreditSaleQuote.getText().toString());
                total = objCreditSale.getAmount() - payment;

                etBalance_CreditSaleQuote.setText(MyMath.toDecimal(total, 2));
            }
        }
        else
        {
            etBalance_CreditSaleQuote.setText(MyMath.toDecimal(objCreditSale.getAmount(), 2));
        }
    }
}