package Data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;

import com.aarenas.syssales.R;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utilities {

    public static Intent openCalculator() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_CALCULATOR);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return intent;
    }

    public static Intent openCalculator2(Context context) {
        Intent intent = null;

        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();

        final PackageManager pm = context.getPackageManager();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        for (PackageInfo pi : packs) {
            if (pi.packageName.toLowerCase().contains("calcul")) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("appname", pi.applicationInfo.loadLabel(pm));
                map.put("packageName", pi.packageName);
                items.add(map);
            }
        }

        if (items.size() > 0) {
            String packageName = (String) items.get(0).get("packageName");

            intent = pm.getLaunchIntentForPackage(packageName);
        }

        return intent;
    }

    public static void clearFocus(Activity activity)
    {
        View view = activity.getCurrentFocus();
        if (view != null)
        {
            view.clearFocus();
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }

    public static void RelaunchApplication(Activity activity)   //Test
    {
        Intent i = activity.getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( activity.getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(i);
    }

    public static void setSpinnerSelection(Spinner spinner, Object value)
    {
        android.widget.SpinnerAdapter adapter = spinner.getAdapter();
        if (adapter != null && value != null)
        {
            for (int i=0; i < adapter.getCount(); i++)
            {
                if (adapter.getItem(i).equals(value))
                {
                    spinner.setSelection(i);
                    break;
                }
            }
        }
    }

    public static void showMessage(Context context, String[] array)
    {
        String message = TextUtils.join("\n", array);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.app_name);
        builder.setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
