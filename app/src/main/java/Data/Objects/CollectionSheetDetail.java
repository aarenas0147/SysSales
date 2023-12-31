package Data.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Data.MyDateTime;

public class CollectionSheetDetail {

    private CollectionSheet CollectionSheet;
    private Sale Sale;
    private java.util.Date Date;
    private Float Amount, Amortization, Balance;

    public Data.Objects.CollectionSheet getCollectionSheet() {
        return CollectionSheet;
    }

    public void setCollectionSheet(Data.Objects.CollectionSheet collectionSheet) {
        CollectionSheet = collectionSheet;
    }

    public Sale getSale() {
        return Sale;
    }

    public void setSale(Sale sale) {
        Sale = sale;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public Float getAmount() {
        return Amount;
    }

    public void setAmount(Float amount) {
        Amount = amount;
    }

    public Float getAmortization() {
        return Amortization;
    }

    public void setAmortization(Float amortization) {
        Amortization = amortization;
    }

    public Float getBalance() {
        return Balance;
    }

    public void setBalance(Float balance) {
        Balance = balance;
    }

    public static CollectionSheetDetail getItem(JSONObject result)
    {
        try
        {
            CollectionSheetDetail objCollectionSheetDetail = new CollectionSheetDetail();

            objCollectionSheetDetail.setCollectionSheet(result.get("CollectionSheet") != JSONObject.NULL
                    ? Data.Objects.CollectionSheet.getItem(new JSONObject(result.get("CollectionSheet").toString())) : null);
            objCollectionSheetDetail.setSale(result.get("Sale") != JSONObject.NULL
                    ? Data.Objects.Sale.getItem(new JSONObject(result.get("Sale").toString())) : null);
            objCollectionSheetDetail.setDate(result.get("Date") != JSONObject.NULL ? MyDateTime.parseNet(result.get("Date").toString()) : null);
            objCollectionSheetDetail.setAmount(result.get("Amount") != JSONObject.NULL ? Float.parseFloat(result.get("Amount").toString()) : null);
            objCollectionSheetDetail.setAmortization(result.get("Amortization") != JSONObject.NULL ? Float.parseFloat(result.get("Amortization").toString()) : null);
            objCollectionSheetDetail.setBalance(result.get("Balance") != JSONObject.NULL ? Float.parseFloat(result.get("Balance").toString()) : null);

            return objCollectionSheetDetail;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static List<CollectionSheetDetail> getList(JSONArray result)
    {
        List<CollectionSheetDetail> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                CollectionSheetDetail objCollectionSheetDetail;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objCollectionSheetDetail = getItem(array);

                    list.add(objCollectionSheetDetail);
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
