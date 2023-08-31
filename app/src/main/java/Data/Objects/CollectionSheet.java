package Data.Objects;

import org.json.JSONObject;

import java.util.Date;

import Data.MyDateTime;

public class CollectionSheet {

    private Object Id;
    private Date Date;
    private Customer DebtCollector;
    private Route Route;
    private Float TotalAmount;

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public Customer getDebtCollector() {
        return DebtCollector;
    }

    public void setDebtCollector(Customer debtCollector) {
        DebtCollector = debtCollector;
    }

    public Data.Objects.Route getRoute() {
        return Route;
    }

    public void setRoute(Data.Objects.Route route) {
        Route = route;
    }

    public Float getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        TotalAmount = totalAmount;
    }

    public static CollectionSheet getItem(JSONObject result)
    {
        try
        {
            CollectionSheet objCollectionSheet = new CollectionSheet();

            objCollectionSheet.setId(result.get("Id") != JSONObject.NULL ? result.get("Id") : null);
            objCollectionSheet.setDate(result.get("Date") != JSONObject.NULL ? MyDateTime.parseNet(result.get("Date").toString()) : null);
            objCollectionSheet.setDebtCollector(result.get("DebtCollector") != JSONObject.NULL
                    ? Customer.getItem(new JSONObject(result.get("DebtCollector").toString())) : null);
            objCollectionSheet.setRoute(result.get("Route") != JSONObject.NULL
                    ? Data.Objects.Route.getItem(new JSONObject(result.get("Route").toString())) : null);
            objCollectionSheet.setTotalAmount(result.get("TotalAmount") != JSONObject.NULL ? Float.parseFloat(result.get("TotalAmount").toString()) : null);

            return objCollectionSheet;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
