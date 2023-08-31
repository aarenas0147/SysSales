package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodDetail implements Parcelable {

    private Object Id;
    private PaymentMethod PaymentMethod;
    private String Description;
    private Float CommissionPercentage;
    private boolean Enabled;

    public PaymentMethodDetail() {
    }

    protected PaymentMethodDetail(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        PaymentMethod = in.readParcelable(Data.Objects.PaymentMethod.class.getClassLoader());
        Description = in.readString();
        if (in.readByte() == 0) {
            CommissionPercentage = null;
        } else {
            CommissionPercentage = in.readFloat();
        }
        Enabled = in.readByte() != 0;
    }

    public static final Creator<PaymentMethodDetail> CREATOR = new Creator<PaymentMethodDetail>() {
        @Override
        public PaymentMethodDetail createFromParcel(Parcel in) {
            return new PaymentMethodDetail(in);
        }

        @Override
        public PaymentMethodDetail[] newArray(int size) {
            return new PaymentMethodDetail[size];
        }
    };

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

    public Data.Objects.PaymentMethod getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(Data.Objects.PaymentMethod paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Float getCommissionPercentage() {
        return CommissionPercentage;
    }

    public void setCommissionPercentage(Float commissionPercentage) {
        CommissionPercentage = commissionPercentage;
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean enabled) {
        Enabled = enabled;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(Id);
        parcel.writeParcelable(PaymentMethod, i);
        parcel.writeString(Description);
        if (CommissionPercentage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(CommissionPercentage);
        }
        parcel.writeByte((byte) (Enabled ? 1 : 0));
    }

    public static PaymentMethodDetail getItem(JSONObject result)
    {
        try
        {
            PaymentMethodDetail objPaymentMethod = new PaymentMethodDetail();

            objPaymentMethod.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objPaymentMethod.setPaymentMethod(result.get("PaymentMethod") != JSONObject.NULL ?
                    Data.Objects.PaymentMethod.getItem(new JSONObject(result.get("PaymentMethod").toString())) : null);
            objPaymentMethod.setDescription(result.get("Description") != JSONObject.NULL ? result.getString("Description") : null);
            objPaymentMethod.setCommissionPercentage(result.get("CommissionPercentage") != JSONObject.NULL
                    ? Float.parseFloat(result.get("CommissionPercentage").toString()) : 0F);
            objPaymentMethod.setEnabled(result.get("Enabled") != JSONObject.NULL && result.getBoolean("Enabled"));

            return objPaymentMethod;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static List<PaymentMethodDetail> getList(JSONArray result)
    {
        List<PaymentMethodDetail> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                PaymentMethodDetail objPaymentMethod;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objPaymentMethod = getItem(array);

                    list.add(objPaymentMethod);
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
