package Data;

import android.content.Context;
import android.content.Intent;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;

public class MyPdf {

    private Context context;

    public MyPdf(Context context)
    {
        this.context = context;
    }

    public void openDocument(byte[] fileArray, String fileName)
    {
        try
        {
            File file = new File(context.getCacheDir(), fileName);
            file.deleteOnExit();

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileArray);
            fileOutputStream.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(FileProvider.getUriForFile(context.getApplicationContext(),
                    context.getApplicationContext().getPackageName() + ".provider", file),
                    "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void clearCache()
    {
        File cacheDirectory = context.getCacheDir();
        deleteDir(cacheDirectory);
    }

    private void deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null)
            {
                for (int i = 0; i < children.length; i++) {
                    deleteDir(new File(dir, children[i]));
                }
            }
            dir.delete();
        }
        else if (dir != null && dir.isFile()) {
            dir.delete();
        }
    }
}
