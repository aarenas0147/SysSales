package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Data.MyDateTime;

public class CreditSale implements Parcelable {

    private Object Id;
    private Sale Sale;
    private Float Amount, Interest;
    private Date Date;

    public CreditSale() {
    }

    protected CreditSale(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        Sale = in.readParcelable(Data.Objects.Sale.class.getClassLoader());
        if (in.readByte() == 0) {
            Amount = null;
        } else {
            Amount = in.readFloat();
        }
        if (in.readByte() == 0) {
            Interest = null;
        } else {
            Interest = in.readFloat();
        }
        Date = in.readByte() != 0 ? (Date) in.readSerializable() : null;
    }

    public static final Creator<CreditSale> CREATOR = new Creator<CreditSale>() {
        @Override
        public CreditSale createFromParcel(Parcel in) {
            return new CreditSale(in);
        }

        @Override
        public CreditSale[] newArray(int size) {
            return new CreditSale[size];
        }
    };

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

    public Data.Objects.Sale getSale() {
        return Sale;
    }

    public void setSale(Data.Objects.Sale sale) {
        Sale = sale;
    }

    public Float getAmount() {
        return Amount;
    }

    public void setAmount(Float amount) {
        Amount = amount;
    }

    public Float getInterest() {
        return Interest;
    }

    public void setInterest(Float interest) {
        Interest = interest;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public static CreditSale getItem(JSONObject result)
    {
        try
        {
            CreditSale objCreditSale = new CreditSale();

            objCreditSale.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objCreditSale.setSale(result.get("Sale") != JSONObject.NULL ?
                    Data.Objects.Sale.getItem(new JSONObject(result.get("Sale").toString())) : null);
            objCreditSale.setAmount(result.get("Amount") != JSONObject.NULL ? Float.parseFloat(result.get("Amount").toString()) : 0F);
            objCreditSale.setInterest(result.get("Interest") != JSONObject.NULL ? Float.parseFloat(result.get("Interest").toString()) : 0F);
            objCreditSale.setDate(result.get("Date") != JSONObject.NULL ? MyDateTime.parseNet(result.get("Date").toString()) : null);

            return objCreditSale;
        }
        catch (Exception e)
        {
            Log.e("json_credit_sale", e.getMessage());
            return null;
        }
    }

    public static List<CreditSale> getList(JSONArray result)
    {
        List<CreditSale> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                CreditSale objCreditSale;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objCreditSale = getItem(array);

                    list.add(objCreditSale);
                }
            }
        }
        catch (Exception e)
        {
            return null;
        }
        return list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(Id);
        parcel.writeParcelable(Sale, i);
        if (Amount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(Amount);
        }
        if (Interest == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(Interest);
        }
        parcel.writeSerializable(Date);
    }
}
