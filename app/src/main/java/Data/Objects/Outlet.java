package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Outlet implements Parcelable {

    private Object Id;
    private String Description;
    private Company Company;
    private boolean Enabled;

    public Outlet() {
    }

    protected Outlet(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        Description = in.readString();
        Company = in.readParcelable(Data.Objects.Company.class.getClassLoader());
        Enabled = in.readByte() != 0;
    }

    public static final Creator<Outlet> CREATOR = new Creator<Outlet>() {
        @Override
        public Outlet createFromParcel(Parcel in) {
            return new Outlet(in);
        }

        @Override
        public Outlet[] newArray(int size) {
            return new Outlet[size];
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

    public Data.Objects.Company getCompany() {
        return Company;
    }

    public void setCompany(Data.Objects.Company company) {
        Company = company;
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean enabled) {
        Enabled = enabled;
    }

    public static Outlet getItem(JSONObject result)
    {
        try
        {
            Outlet objOutlet = new Outlet();

            objOutlet.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objOutlet.setDescription(result.get("Description") != JSONObject.NULL ? result.getString("Description") : null);
            objOutlet.setEnabled(result.get("Enabled") != JSONObject.NULL && result.getBoolean("Enabled"));

            return objOutlet;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(Id);
        parcel.writeString(Description);
        parcel.writeParcelable(Company, i);
        parcel.writeByte((byte) (Enabled ? 1 : 0));
    }
}
