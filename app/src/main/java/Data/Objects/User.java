package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

public class User implements Parcelable {

    private int Id;
    private Employee Employee;

    public User() {
    }

    protected User(Parcel in) {
        Id = in.readInt();
        Employee = in.readParcelable(Data.Objects.Employee.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Data.Objects.Employee getEmployee() {
        return Employee;
    }

    public void setEmployee(Data.Objects.Employee employee) {
        Employee = employee;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeParcelable(Employee, flags);
    }

    public static User getItem(JSONObject result)
    {
        try
        {
            User objUser = new User();

            objUser.setId(result.get("Id") != JSONObject.NULL ? result.getInt("Id") : 0);
            objUser.setEmployee(result.get("Employee") != JSONObject.NULL ?
                    Data.Objects.Employee.getItem(new JSONObject(result.get("Employee").toString())) : null);

            return objUser;
        }
        catch (Exception e)
        {
            Log.e("json_user", e.getMessage());
            return null;
        }
    }
}
