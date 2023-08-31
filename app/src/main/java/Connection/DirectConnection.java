package Connection;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DirectConnection {

    private static Connection getConnection()
    {
        Connection connection = null;
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.0.114/BDComercial_Rosadito;instance=SQL2012;user=sa;password=root;");
        }
        catch (Exception exception)
        {
            Log.e("Error", exception.getMessage());
        }
        return connection;
    }

    public static void execQuery(String query)
    {
        StringBuilder res = new StringBuilder();
        try
        {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
            {
                for (int i=1; i <= resultSet.getMetaData().getColumnCount(); i++)
                {
                    if (i == resultSet.getMetaData().getColumnCount() - 1)
                    {
                        res.append(resultSet.getString(resultSet.getMetaData().getColumnName(i)) + "\n");
                    }
                    else
                    {
                        res.append(resultSet.getString(resultSet.getMetaData().getColumnName(i)) + "/");
                    }
                }
                //res.append(resultSet.getString("Producto_ID"));
            }
        }
        catch (Exception exception)
        {
            Log.e("Error", exception.getMessage());
        }
        Log.e("Resultado", res.toString());
    }
}
