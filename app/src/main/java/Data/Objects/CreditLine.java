package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Data.MyDateTime;

public class CreditLine implements Parcelable {

    private Object Id;
    private Customer Customer;
    private Float Amount;
    private java.util.Date ActivationDate;
    private int Days, MaximumDays;
    private boolean Enabled;


    public CreditLine() {
    }

    protected CreditLine(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        Customer = in.readParcelable(Data.Objects.Customer.class.getClassLoader());
        if (in.readByte() == 0) {
            Amount = null;
        } else {
            Amount = in.readFloat();
        }
        ActivationDate = in.readByte() != 0 ? (Date) in.readSerializable() : null;
        Days = in.readInt();
        MaximumDays = in.readInt();
        Enabled = in.readByte() != 0;
    }

    public static final Creator<CreditLine> CREATOR = new Creator<CreditLine>() {
        @Override
        public CreditLine createFromParcel(Parcel in) {
            return new CreditLine(in);
        }

        @Override
        public CreditLine[] newArray(int size) {
            return new CreditLine[size];
        }
    };

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

    public Data.Objects.Customer getCustomer() {
        return Customer;
    }

    public void setCustomer(Data.Objects.Customer customer) {
        Customer = customer;
    }

    public Float getAmount() {
        return Amount;
    }

    public void setAmount(Float amount) {
        Amount = amount;
    }

    public Date getActivationDate() {
        return ActivationDate;
    }

    public void setActivationDate(Date activationDate) {
        ActivationDate = activationDate;
    }

    public int getDays() {
        return Days;
    }

    public void setDays(int days) {
        Days = days;
    }

    public int getMaximumDays() {
        return MaximumDays;
    }

    public void setMaximumDays(int maximumDays) {
        MaximumDays = maximumDays;
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean enabled) {
        Enabled = enabled;
    }

    public static CreditLine getItem(JSONObject result)
    {
        try
        {
            CreditLine objCreditLine = new CreditLine();

            objCreditLine.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objCreditLine.setCustomer(result.get("Customer") != JSONObject.NULL ?
                    Data.Objects.Customer.getItem(new JSONObject(result.get("Customer").toString())) : null);
            objCreditLine.setAmount(result.get("Amount") != JSONObject.NULL ? Float.parseFloat(result.get("Amount").toString()) : 0F);
            objCreditLine.setActivationDate(result.get("ActivationDate") != JSONObject.NULL ? MyDateTime.parseNet(result.get("ActivationDate").toString()) : null);
            objCreditLine.setDays(result.get("Days") != JSONObject.NULL ? result.getInt("Days") : 0);
            objCreditLine.setMaximumDays(result.get("MaximumDays") != JSONObject.NULL ? result.getInt("MaximumDays") : 0);
            objCreditLine.setEnabled(result.get("Enabled") != JSONObject.NULL && result.getBoolean("Enabled"));

            return objCreditLine;
        }
        catch (Exception e)
        {
            Log.e("json_credit_line", e.getMessage());
            return null;
        }
    }

    public static List<CreditLine> getList(JSONArray result)
    {
        List<CreditLine> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                CreditLine objCreditLine;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objCreditLine = getItem(array);

                    list.add(objCreditLine);
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
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeValue(Id);
        dest.writeParcelable(Customer, flags);
        if (Amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(Amount);
        }
        dest.writeSerializable(ActivationDate);
        dest.writeInt(Days);
        dest.writeInt(MaximumDays);
        dest.writeByte((byte) (Enabled ? 1 : 0));
    }
}
