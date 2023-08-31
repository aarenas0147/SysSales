package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Zone implements Parcelable {

    private Object Id;
    private String Description;
    private boolean Enabled;

    public Zone() {
    }

    protected Zone(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        Description = in.readString();
        Enabled = in.readByte() != 0;
    }

    public static final Creator<Zone> CREATOR = new Creator<Zone>() {
        @Override
        public Zone createFromParcel(Parcel in) {
            return new Zone(in);
        }

        @Override
        public Zone[] newArray(int size) {
            return new Zone[size];
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

    public static Zone getItem(JSONObject result)
    {
        try
        {
            Zone objZone = new Zone();

            objZone.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objZone.setDescription(result.get("Description") != JSONObject.NULL ? result.getString("Description") : null);
            objZone.setEnabled(result.get("Enabled") != JSONObject.NULL && result.getBoolean("Enabled"));

            return objZone;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static List<Zone> getList(JSONArray result)
    {
        List<Zone> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                Zone objZone;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objZone = getItem(array);

                    list.add(objZone);
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
