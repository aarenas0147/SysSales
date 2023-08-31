package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Route implements Parcelable {

    private Object Id;
    private Zone Zone;
    private String Description;
    private boolean Enabled;

    public Route() {
    }

    protected Route(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        Zone = in.readParcelable(Data.Objects.Zone.class.getClassLoader());
        Description = in.readString();
        Enabled = in.readByte() != 0;
    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

    public Data.Objects.Zone getZone() {
        return Zone;
    }

    public void setZone(Data.Objects.Zone zone) {
        Zone = zone;
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
        parcel.writeParcelable(Zone, i);
        parcel.writeString(Description);
        parcel.writeByte((byte) (Enabled ? 1 : 0));
    }

    public static Route getItem(JSONObject result)
    {
        try
        {
            Route objRoute = new Route();

            objRoute.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objRoute.setZone(result.get("Zone") != JSONObject.NULL ?
                    Data.Objects.Zone.getItem(new JSONObject(result.get("Zone").toString())) : null);
            objRoute.setDescription(result.get("Description") != JSONObject.NULL ? result.getString("Description") : null);
            objRoute.setEnabled(result.get("Enabled") != JSONObject.NULL && result.getBoolean("Enabled"));

            return objRoute;
        }
        catch (Exception e)
        {
            Log.e("json_route", e.getMessage());
            return null;
        }
    }

    public static List<Route> getList(JSONArray result)
    {
        List<Route> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                Route objRoute;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objRoute = getItem(array);

                    list.add(objRoute);
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
