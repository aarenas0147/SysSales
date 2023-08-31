package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaymentCondition implements Parcelable {

    private Object Id;
    private String Description;
    private boolean Enabled;

    public PaymentCondition() {
    }

    protected PaymentCondition(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        Description = in.readString();
        Enabled = in.readByte() != 0;
    }

    public static final Creator<PaymentCondition> CREATOR = new Creator<PaymentCondition>() {
        @Override
        public PaymentCondition createFromParcel(Parcel in) {
            return new PaymentCondition(in);
        }

        @Override
        public PaymentCondition[] newArray(int size) {
            return new PaymentCondition[size];
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
        parcel.writeByte((byte) (Enabled ? 1 : 0));
    }

    public static PaymentCondition getItem(JSONObject result)
    {
        try
        {
            PaymentCondition objPaymentCondition = new PaymentCondition();

            objPaymentCondition.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objPaymentCondition.setDescription(result.get("Description") != JSONObject.NULL ? result.getString("Description") : null);
            objPaymentCondition.setEnabled(result.get("Enabled") != JSONObject.NULL && result.getBoolean("Enabled"));

            return objPaymentCondition;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static List<PaymentCondition> getList(JSONArray result)
    {
        List<PaymentCondition> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                PaymentCondition objPaymentCondition;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objPaymentCondition = getItem(array);

                    list.add(objPaymentCondition);
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
