package Data;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Alan on 04/08/2021.
 */
public class MyMath {

    public static String toDecimal(Object number, int decimals)
    {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.ROOT);
        numberFormat.setMinimumFractionDigits(decimals);
        numberFormat.setMaximumFractionDigits(decimals);
        return numberFormat.format(number != null ? number : 0F);
    }

    public static String toRoundNumber(Object number)
    {
        BigDecimal numberDecimal = new BigDecimal(String.valueOf(number));
        long iPart = numberDecimal.longValue();
        BigDecimal fraction = numberDecimal.remainder(BigDecimal.ONE);

        return String.valueOf(fraction.floatValue() > 0F ? number : iPart);
    }

    public static boolean isNumeric(Object number)
    {
        try
        {
            Float.parseFloat(number.toString());
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
