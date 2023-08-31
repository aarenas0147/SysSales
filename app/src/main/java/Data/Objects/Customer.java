package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Data.MyDateTime;

public class Customer implements Parcelable{

    private Object Id;
    private Person Person;
    private BusinessLine BusinessLine;
    private Route Route;
    private Employee Employee;
    private boolean Enabled;
    private int Status;

    public Customer() {
    }

    protected Customer(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        Person = in.readParcelable(Data.Objects.Person.class.getClassLoader());
        BusinessLine = in.readParcelable(Data.Objects.BusinessLine.class.getClassLoader());
        Route = in.readParcelable(Data.Objects.Route.class.getClassLoader());
        Employee = in.readParcelable(Data.Objects.Employee.class.getClassLoader());
        Enabled = in.readByte() != 0;
        Status = in.readInt();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

    public Data.Objects.Person getPerson() {
        return Person;
    }

    public void setPerson(Data.Objects.Person person) {
        Person = person;
    }

    public Data.Objects.BusinessLine getBusinessLine() {
        return BusinessLine;
    }

    public void setBusinessLine(Data.Objects.BusinessLine businessLine) {
        BusinessLine = businessLine;
    }

    public Data.Objects.Route getRoute() {
        return Route;
    }

    public void setRoute(Data.Objects.Route route) {
        Route = route;
    }

    public Data.Objects.Employee getEmployee() {
        return Employee;
    }

    public void setEmployee(Data.Objects.Employee employee) {
        Employee = employee;
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean enabled) {
        Enabled = enabled;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(Id);
        parcel.writeParcelable(Person, i);
        parcel.writeParcelable(BusinessLine, i);
        parcel.writeParcelable(Route, i);
        parcel.writeParcelable(Employee, i);
        parcel.writeByte((byte) (Enabled ? 1 : 0));
        parcel.writeInt(Status);
    }

    public static Customer getItem(JSONObject result)
    {
        try
        {
            Customer objCustomer = new Customer();

            objCustomer.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objCustomer.setPerson(result.get("Person") != JSONObject.NULL ?
                    Data.Objects.Person.getItem(new JSONObject(result.get("Person").toString())) : null);
            objCustomer.setBusinessLine(result.get("BusinessLine") != JSONObject.NULL ?
                    Data.Objects.BusinessLine.getItem(new JSONObject(result.get("BusinessLine").toString())) : null);
            objCustomer.setRoute(result.get("Route") != JSONObject.NULL ?
                    Data.Objects.Route.getItem(new JSONObject(result.get("Route").toString())) : null);
            objCustomer.setEmployee(result.get("Employee") != JSONObject.NULL ?
                    Data.Objects.Employee.getItem(new JSONObject(result.get("Employee").toString())) : null);
            objCustomer.setEnabled(result.get("Enabled") != JSONObject.NULL && result.getBoolean("Enabled"));
            objCustomer.setStatus(result.get("Status") != JSONObject.NULL ? result.getInt("Status") : -1);

            return objCustomer;
        }
        catch (Exception e)
        {
            Log.e("json_customer", e.getMessage());
            return null;
        }
    }

    public static List<Customer> getList(JSONArray result)
    {
        List<Customer> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                Customer objCustomer;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objCustomer = getItem(array);

                    list.add(objCustomer);
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
