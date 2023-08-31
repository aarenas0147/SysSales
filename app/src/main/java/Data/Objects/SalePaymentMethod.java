package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SalePaymentMethod implements Parcelable {

    private Object Id, SaleId;
    private PaymentMethod PaymentMethod;
    private Float Amount, CommissionPercentage, Total;

    public SalePaymentMethod() {
    }

    protected SalePaymentMethod(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        SaleId = in.readValue(getClass().getClassLoader());
        PaymentMethod = in.readParcelable(Data.Objects.PaymentMethod.class.getClassLoader());
        if (in.readByte() == 0) {
            Amount = null;
        } else {
            Amount = in.readFloat();
        }
        if (in.readByte() == 0) {
            CommissionPercentage = null;
        } else {
            CommissionPercentage = in.readFloat();
        }
        if (in.readByte() == 0) {
            Total = null;
        } else {
            Total = in.readFloat();
        }
    }

    public static final Creator<SalePaymentMethod> CREATOR = new Creator<SalePaymentMethod>() {
        @Override
        public SalePaymentMethod createFromParcel(Parcel in) {
            return new SalePaymentMethod(in);
        }

        @Override
        public SalePaymentMethod[] newArray(int size) {
            return new SalePaymentMethod[size];
        }
    };

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

    public Object getSaleId() {
        return SaleId;
    }

    public void setSaleId(Object saleId) {
        SaleId = saleId;
    }

    public Data.Objects.PaymentMethod getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(Data.Objects.PaymentMethod paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public Float getAmount() {
        return Amount;
    }

    public void setAmount(Float amount) {
        Amount = amount;
    }

    public Float getCommissionPercentage() {
        return CommissionPercentage;
    }

    public void setCommissionPercentage(Float commissionPercentage) {
        CommissionPercentage = commissionPercentage;
    }

    public Float getTotal() {
        return Total;
    }

    public void setTotal(Float total) {
        Total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(Id);
        parcel.writeValue(SaleId);
        parcel.writeParcelable(PaymentMethod, i);
        if (Amount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(Amount);
        }
        if (CommissionPercentage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(CommissionPercentage);
        }
        if (Total == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(Total);
        }
    }

    public static SalePaymentMethod getItem(JSONObject result)
    {
        try
        {
            SalePaymentMethod objSalePaymentMethod = new SalePaymentMethod();

            objSalePaymentMethod.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objSalePaymentMethod.setSaleId(result.get("SaleId") != JSONObject.NULL ? result.get("SaleId") : null);
            objSalePaymentMethod.setPaymentMethod(result.get("PaymentMethod") != JSONObject.NULL ?
                    Data.Objects.PaymentMethod.getItem(new JSONObject(result.get("PaymentMethod").toString())) : null);
            objSalePaymentMethod.setAmount(result.get("Amount") != JSONObject.NULL
                    ? Float.parseFloat(result.get("Amount").toString()) : 0F);
            objSalePaymentMethod.setCommissionPercentage(result.get("CommissionPercentage") != JSONObject.NULL
                    ? Float.parseFloat(result.get("CommissionPercentage").toString()) : 0F);
            objSalePaymentMethod.setTotal(result.get("Total") != JSONObject.NULL
                    ? Float.parseFloat(result.get("Total").toString()) : 0F);

            return objSalePaymentMethod;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static List<SalePaymentMethod> getList(JSONArray result)
    {
        List<SalePaymentMethod> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                SalePaymentMethod objSalePaymentMethod;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objSalePaymentMethod = getItem(array);

                    list.add(objSalePaymentMethod);
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
