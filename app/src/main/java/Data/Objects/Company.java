package Data.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Company implements Parcelable {

    private Object Id;
    private String BusinessName, CommercialName, Ruc, Address, Region, Province,
            District, Phone, Email, WebPage;

    public Company() {
    }

    protected Company(Parcel in) {
        Id = in.readValue(getClass().getClassLoader());
        BusinessName = in.readString();
        CommercialName = in.readString();
        Ruc = in.readString();
        Address = in.readString();
        Region = in.readString();
        Province = in.readString();
        District = in.readString();
        Phone = in.readString();
        Email = in.readString();
        WebPage = in.readString();
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
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

    public String getCommercialName() {
        return CommercialName;
    }

    public void setCommercialName(String commercialName) {
        CommercialName = commercialName;
    }

    public String getRuc() {
        return Ruc;
    }

    public void setRuc(String ruc) {
        Ruc = ruc;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getWebPage() {
        return WebPage;
    }

    public void setWebPage(String webPage) {
        WebPage = webPage;
    }

    public static Company getItem(JSONObject result)
    {
        try
        {
            Company objCompany = new Company();

            objCompany.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objCompany.setBusinessName(result.get("BusinessName") != JSONObject.NULL ? result.getString("BusinessName") : null);
            objCompany.setCommercialName(result.get("CommercialName") != JSONObject.NULL ? result.getString("CommercialName") : null);
            objCompany.setRuc(result.get("Ruc") != JSONObject.NULL ? result.getString("Ruc") : null);
            objCompany.setAddress(result.get("Address") != JSONObject.NULL ? result.getString("Address") : null);
            objCompany.setRegion(result.get("Region") != JSONObject.NULL ? result.getString("Region") : null);
            objCompany.setProvince(result.get("Province") != JSONObject.NULL ? result.getString("Province") : null);
            objCompany.setDistrict(result.get("District") != JSONObject.NULL ? result.getString("District") : null);
            objCompany.setPhone(result.get("Phone") != JSONObject.NULL ? result.getString("Phone") : null);
            objCompany.setEmail(result.get("Email") != JSONObject.NULL ? result.getString("Email") : null);
            objCompany.setWebPage(result.get("WebPage") != JSONObject.NULL ? result.getString("WebPage") : null);

            return objCompany;
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
        parcel.writeString(BusinessName);
        parcel.writeString(CommercialName);
        parcel.writeString(Ruc);
        parcel.writeString(Address);
        parcel.writeString(Region);
        parcel.writeString(Province);
        parcel.writeString(District);
        parcel.writeString(Phone);
        parcel.writeString(Email);
        parcel.writeString(WebPage);
    }
}
