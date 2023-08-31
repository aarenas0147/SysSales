package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BusinessLine implements Parcelable {

    private Object Id;
    private String Description;
    private Boolean Enabled;

    public BusinessLine() {
    }

    protected BusinessLine(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        Description = in.readString();
        byte tmpEnabled = in.readByte();
        Enabled = tmpEnabled == 0 ? null : tmpEnabled == 1;
    }

    public static final Creator<BusinessLine> CREATOR = new Creator<BusinessLine>() {
        @Override
        public BusinessLine createFromParcel(Parcel in) {
            return new BusinessLine(in);
        }

        @Override
        public BusinessLine[] newArray(int size) {
            return new BusinessLine[size];
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

    public Boolean getEnabled() {
        return Enabled;
    }

    public void setEnabled(Boolean enabled) {
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
        parcel.writeByte((byte) (Enabled == null ? 0 : Enabled ? 1 : 2));
    }

    public static BusinessLine getItem(JSONObject result)
    {
        try
        {
            BusinessLine objBusinessLine = new BusinessLine();

            objBusinessLine.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objBusinessLine.setDescription(result.get("Description") != JSONObject.NULL ? result.getString("Description") : null);
            objBusinessLine.setEnabled(result.get("Enabled") != JSONObject.NULL ? result.getBoolean("Enabled") : null);

            return objBusinessLine;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static List<BusinessLine> getList(JSONArray result)
    {
        List<BusinessLine> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                BusinessLine objBusinessLine;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objBusinessLine = getItem(array);

                    list.add(objBusinessLine);
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
