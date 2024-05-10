package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Data.MyDateTime;

public class Employee implements Parcelable {

    private Object Id;
    private int ManagerId;
    private Person Person;
    private String JobName;
    private Date HireDate;
    private float Salary, Commission;
    private Outlet Outlet;

    public Employee() {
    }

    protected Employee(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        ManagerId = in.readInt();
        Person = in.readParcelable(Data.Objects.Person.class.getClassLoader());
        JobName = in.readString();
        HireDate = (Date) in.readSerializable();
        Salary = in.readFloat();
        Commission = in.readFloat();
        Outlet = in.readParcelable(Data.Objects.Outlet.class.getClassLoader());
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

    public int getManagerId() {
        return ManagerId;
    }

    public void setManagerId(int managerId) {
        ManagerId = managerId;
    }

    public Data.Objects.Person getPerson() {
        return Person;
    }

    public void setPerson(Data.Objects.Person person) {
        Person = person;
    }

    public String getJobName() {
        return JobName;
    }

    public void setJobName(String jobName) {
        JobName = jobName;
    }

    public Date getHireDate() {
        return HireDate;
    }

    public void setHireDate(Date hireDate) {
        HireDate = hireDate;
    }

    public float getSalary() {
        return Salary;
    }

    public void setSalary(float salary) {
        Salary = salary;
    }

    public float getCommission() {
        return Commission;
    }

    public void setCommission(float commission) {
        Commission = commission;
    }

    public Data.Objects.Outlet getOutlet() {
        return Outlet;
    }

    public void setOutlet(Data.Objects.Outlet outlet) {
        Outlet = outlet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(Id);
        dest.writeInt(ManagerId);
        dest.writeParcelable(Person, flags);
        dest.writeString(JobName);
        dest.writeSerializable(HireDate);
        dest.writeFloat(Salary);
        dest.writeFloat(Commission);
        dest.writeParcelable(Outlet, flags);
    }

    public static Employee getItem(JSONObject result)
    {
        try
        {
            Employee objEmployee = new Employee();

            objEmployee.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objEmployee.setPerson(result.get("Person") != JSONObject.NULL ?
                    Data.Objects.Person.getItem(new JSONObject(result.get("Person").toString())) : null);
            objEmployee.setJobName(result.get("JobName") != JSONObject.NULL ? result.getString("JobName") : null);
            objEmployee.setManagerId(result.get("ManagerId") != JSONObject.NULL ? result.getInt("ManagerId") : 0);
            objEmployee.setHireDate(result.get("HireDate") != JSONObject.NULL ? MyDateTime.parseNet(result.get("HireDate").toString()) : null);
            objEmployee.setSalary(result.get("Salary") != JSONObject.NULL ? Float.parseFloat(result.get("Salary").toString()) : 0F);
            objEmployee.setCommission(result.get("Commission") != JSONObject.NULL ? Float.parseFloat(result.get("Commission").toString()) : 0F);
            objEmployee.setOutlet(result.get("Outlet") != JSONObject.NULL ?
                    Data.Objects.Outlet.getItem(new JSONObject(result.get("Outlet").toString())) : null);

            return objEmployee;
        }
        catch (Exception e)
        {
            Log.e("json_employee", e.getMessage() != null ? e.getMessage() : "");
            return null;
        }
    }

    public static List<Employee> getList(JSONArray result)
    {
        List<Employee> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                Employee objEmployee;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objEmployee = getItem(array);

                    list.add(objEmployee);
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
