package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class MeasureUnit implements Parcelable {

    private Object Id;
    private String Description;

    public MeasureUnit() {
    }

    protected MeasureUnit(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        Description = in.readString();
    }

    public static final Creator<MeasureUnit> CREATOR = new Creator<MeasureUnit>() {
        @Override
        public MeasureUnit createFromParcel(Parcel in) {
            return new MeasureUnit(in);
        }

        @Override
        public MeasureUnit[] newArray(int size) {
            return new MeasureUnit[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(Id);
        parcel.writeString(Description);
    }

    public static MeasureUnit getItem(JSONObject result)
    {
        try
        {
            MeasureUnit objMeasureUnit = new MeasureUnit();

            objMeasureUnit.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objMeasureUnit.setDescription(result.get("Description") != JSONObject.NULL ? result.getString("Description") : null);

            return objMeasureUnit;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
