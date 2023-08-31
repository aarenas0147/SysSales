package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Person implements Parcelable {

    private Object Id, DocumentType;
    private String BusinessName, PaternalSurname, MaternalSurname, Names, DocumentNumber,
            Address, Reference, Region, Province, District,
            Phone, Mobile, Email;
    private boolean Enabled;

    public Person() {
    }

    protected Person(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        DocumentType = in.readValue(getClass().getClassLoader());
        BusinessName = in.readString();
        PaternalSurname = in.readString();
        MaternalSurname = in.readString();
        Names = in.readString();
        DocumentNumber = in.readString();
        Address = in.readString();
        Reference = in.readString();
        Region = in.readString();
        Province = in.readString();
        District = in.readString();
        Phone = in.readString();
        Mobile = in.readString();
        Email = in.readString();
        Enabled = in.readByte() != 0;
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public String getPaternalSurname() {
        return PaternalSurname;
    }

    public void setPaternalSurname(String paternalSurname) {
        PaternalSurname = paternalSurname;
    }

    public String getMaternalSurname() {
        return MaternalSurname;
    }

    public void setMaternalSurname(String maternalSurname) {
        MaternalSurname = maternalSurname;
    }

    public String getNames() {
        return Names;
    }

    public void setNames(String names) {
        Names = names;
    }

    public String getDocumentNumber() {
        return DocumentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        DocumentNumber = documentNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String reference) {
        Reference = reference;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean enabled) {
        Enabled = enabled;
    }

    public Object getDocumentType() {
        return DocumentType;
    }

    public void setDocumentType(Object documentType) {
        DocumentType = documentType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(Id);
        dest.writeValue(DocumentType);
        dest.writeString(BusinessName);
        dest.writeString(PaternalSurname);
        dest.writeString(MaternalSurname);
        dest.writeString(Names);
        dest.writeString(DocumentNumber);
        dest.writeString(Address);
        dest.writeString(Reference);
        dest.writeString(Region);
        dest.writeString(Province);
        dest.writeString(District);
        dest.writeString(Phone);
        dest.writeString(Mobile);
        dest.writeString(Email);
        dest.writeByte((byte) (Enabled ? 1 : 0));
    }

    public static Person getItem(JSONObject result)
    {
        try
        {
            Person objPerson = new Person();

            objPerson.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objPerson.setBusinessName(result.get("BusinessName") != JSONObject.NULL ? result.getString("BusinessName") : null);
            objPerson.setPaternalSurname(result.get("PaternalSurname") != JSONObject.NULL ? result.getString("PaternalSurname") : null);
            objPerson.setMaternalSurname(result.get("MaternalSurname") != JSONObject.NULL ? result.getString("MaternalSurname") : null);
            objPerson.setNames(result.get("Names") != JSONObject.NULL ? result.getString("Names") : null);
            objPerson.setDocumentNumber(result.get("DocumentNumber") != JSONObject.NULL ? result.getString("DocumentNumber") : null);
            objPerson.setAddress(result.get("Address") != JSONObject.NULL ? result.getString("Address") : null);
            objPerson.setReference(result.get("Reference") != JSONObject.NULL ? result.getString("Reference") : null);
            objPerson.setRegion(result.get("Region") != JSONObject.NULL ? result.getString("Region") : null);
            objPerson.setProvince(result.get("Province") != JSONObject.NULL ? result.getString("Province") : null);
            objPerson.setDistrict(result.get("District") != JSONObject.NULL ? result.getString("District") : null);
            objPerson.setPhone(result.get("Phone") != JSONObject.NULL ? result.getString("Phone") : null);
            objPerson.setMobile(result.get("Mobile") != JSONObject.NULL ? result.getString("Mobile") : null);
            objPerson.setEmail(result.get("Email") != JSONObject.NULL ? result.getString("Email") : null);
            objPerson.setEnabled(result.get("Enabled") != JSONObject.NULL && result.getBoolean("Enabled"));
            objPerson.setDocumentType(result.get("DocumentType") != JSONObject.NULL ? result.get("DocumentType") : null);

            return objPerson;
        }
        catch (Exception e)
        {
            Log.e("json_person", e.getMessage());
            return null;
        }
    }

    public static List<Person> getList(JSONArray result)
    {
        List<Person> list = null;
        try
        {
            if (!result.toString().equals("[]"))
            {
                list = new ArrayList<>();
                Person objPerson;
                for (int i = 0; i < result.length(); i++)
                {
                    JSONObject array = result.getJSONObject(i);
                    objPerson = getItem(array);

                    list.add(objPerson);
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
