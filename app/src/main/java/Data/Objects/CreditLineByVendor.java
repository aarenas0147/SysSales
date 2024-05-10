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

public class CreditLineByVendor implements Parcelable {

    private Object Id;
    private Employee Employee;
    private Float Balance, Amount;
    private boolean Enabled;


    public CreditLineByVendor() {
    }

    protected CreditLineByVendor(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        Employee = in.readParcelable(Data.Objects.Employee.class.getClassLoader());
        if (in.readByte() == 0) {
            Balance = null;
        } else {
            Balance = in.readFloat();
        }
        if (in.readByte() == 0) {
            Amount = null;
        } else {
            Amount = in.readFloat();
        }
        Enabled = in.readByte() != 0;
    }

    public static final Creator<CreditLineByVendor> CREATOR = new Creator<CreditLineByVendor>() {
        @Override
        public CreditLineByVendor createFromParcel(Parcel in) {
            return new CreditLineByVendor(in);
        }

        @Override
        public CreditLineByVendor[] newArray(int size) {
            return new CreditLineByVendor[size];
        }
    };

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

    public Data.Objects.Employee getEmployee() {
        return Employee;
    }

    public void setEmployee(Data.Objects.Employee employee) {
        Employee = employee;
    }

    public Float getBalance() {
        return Balance;
    }

    public void setBalance(Float balance) {
        Balance = balance;
    }

    public Float getAmount() {
        return Amount;
    }

    public void setAmount(Float amount) {
        Amount = amount;
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean enabled) {
        Enabled = enabled;
    }

    public static CreditLineByVendor getItem(JSONObject result)
    {
        try
        {
            CreditLineByVendor objCreditLine = new CreditLineByVendor();

            objCreditLine.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objCreditLine.setEmployee(result.get("Employee") != JSONObject.NULL ?
                    Data.Objects.Employee.getItem(new JSONObject(result.get("Employee").toString())) : null);
            objCreditLine.setBalance(result.get("Balance") != JSONObject.NULL ? Float.parseFloat(result.get("Balance").toString()) : 0F);
            objCreditLine.setAmount(result.get("Amount") != JSONObject.NULL ? Float.parseFloat(result.get("Amount").toString()) : 0F);
            objCreditLine.setEnabled(result.get("Enabled") != JSONObject.NULL && result.getBoolean("Enabled"));

            return objCreditLine;
        }
        catch (Exception e)
        {
            Log.e("json_credit_line_vendor", e.getMessage() != null ? e.getMessage() : "");
            return null;
        }
    }

    public static List<CreditLineByVendor> getList(JSONArray result)
    {
        List<CreditLineByVendor> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                CreditLineByVendor objCreditLine;
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
        dest.writeParcelable(Employee, flags);
        if (Balance == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(Balance);
        }
        if (Amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(Amount);
        }
        dest.writeByte((byte) (Enabled ? 1 : 0));
    }
}
