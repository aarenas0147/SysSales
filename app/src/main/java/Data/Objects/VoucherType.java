package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VoucherType implements Parcelable {

    public Object Id;
    public String Description, Abbreviation;

    public VoucherType() {
    }

    protected VoucherType(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        Description = in.readString();
        Abbreviation = in.readString();
    }

    public static final Creator<VoucherType> CREATOR = new Creator<VoucherType>() {
        @Override
        public VoucherType createFromParcel(Parcel in) {
            return new VoucherType(in);
        }

        @Override
        public VoucherType[] newArray(int size) {
            return new VoucherType[size];
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

    public String getAbbreviation() {
        return Abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        Abbreviation = abbreviation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(Id);
        parcel.writeString(Description);
        parcel.writeString(Abbreviation);
    }

    public static VoucherType getItem(JSONObject result)
    {
        try
        {
            VoucherType objVoucherType = new VoucherType();

            objVoucherType.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objVoucherType.setDescription(result.get("Description") != JSONObject.NULL ? result.getString("Description") : null);
            objVoucherType.setAbbreviation(result.get("Abbreviation") != JSONObject.NULL ? result.getString("Abbreviation") : null);

            return objVoucherType;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static List<VoucherType> getList(JSONArray result)
    {
        List<VoucherType> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                VoucherType objVoucherType;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objVoucherType = getItem(array);

                    list.add(objVoucherType);
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
