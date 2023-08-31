package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethod implements Parcelable {

    private Object Id;
    private String Description;
    private Float CommissionPercentage;
    private boolean Enabled;

    public PaymentMethod() {
    }

    protected PaymentMethod(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        Description = in.readString();
        if (in.readByte() == 0) {
            CommissionPercentage = null;
        } else {
            CommissionPercentage = in.readFloat();
        }
        Enabled = in.readByte() != 0;
    }

    public static final Creator<PaymentMethod> CREATOR = new Creator<PaymentMethod>() {
        @Override
        public PaymentMethod createFromParcel(Parcel in) {
            return new PaymentMethod(in);
        }

        @Override
        public PaymentMethod[] newArray(int size) {
            return new PaymentMethod[size];
        }
    };

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
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
        parcel.writeString(Description);
        if (CommissionPercentage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(CommissionPercentage);
        }
        parcel.writeByte((byte) (Enabled ? 1 : 0));
    }

    public static PaymentMethod getItem(JSONObject result)
    {
        try
        {
            PaymentMethod objPaymentMethod = new PaymentMethod();

            objPaymentMethod.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
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

    public static List<PaymentMethod> getList(JSONArray result)
    {
        List<PaymentMethod> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                PaymentMethod objPaymentMethod;
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
