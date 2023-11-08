package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaymentCondition implements Parcelable {

    private int Id;
    private String Description;
    private Integer DueDays;
    private boolean DueDaysEditable, Enabled;

    public PaymentCondition() {
    }

    protected PaymentCondition(Parcel in) {
        Id = in.readInt();
        Description = in.readString();
        if (in.readByte() == 0) {
            DueDays = null;
        } else {
            DueDays = in.readInt();
        }
        DueDaysEditable = in.readByte() != 0;
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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getDueDays() {
        return DueDays;
    }

    public void setDueDays(Integer dueDays) {
        DueDays = dueDays;
    }

    public boolean isDueDaysEditable() {
        return DueDaysEditable;
    }

    public void setDueDaysEditable(boolean dueDaysEditable) {
        DueDaysEditable = dueDaysEditable;
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
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Description);
        if (DueDays == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(DueDays);
        }
        dest.writeByte((byte) (DueDaysEditable ? 1 : 0));
        dest.writeByte((byte) (Enabled ? 1 : 0));
    }

    public static PaymentCondition getItem(JSONObject result)
    {
        try
        {
            PaymentCondition objPaymentCondition = new PaymentCondition();

            objPaymentCondition.setId(result.get("Id") != JSONObject.NULL ? result.getInt("Id") : ConstantData.PaymentCondition.CUSTOMIZED);
            objPaymentCondition.setDescription(result.get("Description") != JSONObject.NULL ? result.getString("Description") : null);
            objPaymentCondition.setDueDays(result.get("DueDays") != JSONObject.NULL ? result.getInt("DueDays") : null);
            objPaymentCondition.setDueDaysEditable(result.get("DueDaysEditable") != JSONObject.NULL && result.getBoolean("DueDaysEditable"));
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
