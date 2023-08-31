package Data.Objects;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Data.MyDateTime;

public class CreditSaleQuote {

    private Object Id;
    private int QuoteNumber;
    private CreditSale CreditSale;
    private Float Amount, Payment, Balance;
    private java.util.Date Date;

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

    public int getQuoteNumber() {
        return QuoteNumber;
    }

    public void setQuoteNumber(int quoteNumber) {
        QuoteNumber = quoteNumber;
    }

    public Data.Objects.CreditSale getCreditSale() {
        return CreditSale;
    }

    public void setCreditSale(Data.Objects.CreditSale creditSale) {
        CreditSale = creditSale;
    }

    public Float getAmount() {
        return Amount;
    }

    public void setAmount(Float amount) {
        Amount = amount;
    }

    public Float getPayment() {
        return Payment;
    }

    public void setPayment(Float payment) {
        Payment = payment;
    }

    public Float getBalance() {
        return Balance;
    }

    public void setBalance(Float balance) {
        Balance = balance;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public static CreditSaleQuote getItem(JSONObject result)
    {
        try
        {
            CreditSaleQuote objCreditSaleQuote = new CreditSaleQuote();

            objCreditSaleQuote.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objCreditSaleQuote.setQuoteNumber(result.get("QuoteNumber") != JSONObject.NULL ? result.getInt("QuoteNumber") : 0);
            objCreditSaleQuote.setCreditSale(result.get("CreditSale") != JSONObject.NULL ?
                    Data.Objects.CreditSale.getItem(new JSONObject(result.get("CreditSale").toString())) : null);
            objCreditSaleQuote.setAmount(result.get("Amount") != JSONObject.NULL ? Float.parseFloat(result.get("Amount").toString()) : 0F);
            objCreditSaleQuote.setPayment(result.get("Payment") != JSONObject.NULL ? Float.parseFloat(result.get("Payment").toString()) : 0F);
            objCreditSaleQuote.setBalance(result.get("Balance") != JSONObject.NULL ? Float.parseFloat(result.get("Balance").toString()) : 0F);
            objCreditSaleQuote.setDate(result.get("Date") != JSONObject.NULL ? MyDateTime.parseNet(result.get("Date").toString()) : null);

            return objCreditSaleQuote;
        }
        catch (Exception e)
        {
            Log.e("json_credit_sale_quote", e.getMessage());
            return null;
        }
    }

    public static List<CreditSaleQuote> getList(JSONArray result)
    {
        List<CreditSaleQuote> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                CreditSaleQuote objCreditSaleQuote;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objCreditSaleQuote = getItem(array);

                    list.add(objCreditSaleQuote);
                }
            }
        }
        catch (Exception e)
        {
            return null;
        }
        return list;
    }
}
