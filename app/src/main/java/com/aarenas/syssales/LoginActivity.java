package com.aarenas.syssales;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aarenas.updater.UpdateChecker;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import com.aarenas.syssales.databinding.ActivityLoginBinding;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import Connection.DirectConnection;
import Connection.WebConfig;
import Connection.WebMethods;
import Connection.WebServices;
import Data.Objects.Company;
import Data.Objects.User;
import Data.Utilities;

public class LoginActivity extends AppCompatActivity implements WebServices.OnResult {

    private ActivityLoginBinding binding;

    //Controls:
    private ImageView imgBackground_Login, imgLogo_Login;
    private TextInputEditText etUsername_Login, etPassword_Login;
    private CheckBox chkRememberMe_Login;
    private Button btnLogin_Login;
    private TextView tvAppVersion_Login;

    //Asynchronous data:
    private User objUser;
    private Company objCompany;

    //Variables:
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ActivityResultLauncher<Intent> resultLauncher;
    private WebMethods objWebMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        imgBackground_Login = findViewById(R.id.imgBackground_Login);
        imgLogo_Login = findViewById(R.id.imgLogo_Login);
        etUsername_Login = findViewById(R.id.etUsername_Login);
        etPassword_Login = findViewById(R.id.etPassword_Login);
        chkRememberMe_Login = findViewById(R.id.chkRememberMe_Login);
        btnLogin_Login = findViewById(R.id.btnLogin_Login);
        tvAppVersion_Login = findViewById(R.id.tvAppVersion_Login);

        objWebMethods = new WebMethods(this, this);
        imgLogo_Login.setImageResource(R.drawable.icon);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        etPassword_Login.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    login();

                    return true;
                }

                return false;
            }
        });

        btnLogin_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.clearFocus(LoginActivity.this);
                login();
            }
        });

        tvAppVersion_Login.setText(String.format("Versión %s", BuildConfig.VERSION_NAME));

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        recreate();
                    }
                });

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            resultLauncher.launch(new Intent(getApplicationContext(), SettingsActivity.class));
            //startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            return true;
        }
        else if (id == R.id.action_check_updates)
        {
            checkUpdates();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(WebServices.Result result, int processId) {
        try
        {
            if (result.getResultCode().getId() == WebServices.Result.RESULT_OK)
            {
                if (!WebServices.isNull(result.getResult()))
                {
                    if (processId == WebMethods.TYPE_LIST_USER)
                    {
                        JSONArray array = new JSONArray(result.getResult().toString());
                        if (array.length() > 0)
                        {
                            boolean reply = array.getBoolean(0);
                            if (reply)
                            {
                                JSONObject jsonObject = array.getJSONObject(1);
                                objUser = User.getItem(jsonObject);
                                if (objUser != null &&
                                        objUser.getEmployee() != null &&
                                        objUser.getEmployee().getPerson() != null)
                                {
                                    objWebMethods.getCompany();
                                }
                            }
                            else
                            {
                                if (WebServices.isNull(array.get(1)))
                                {
                                    etPassword_Login.setText("");
                                    Toast.makeText(getApplicationContext(), R.string.message_wrong_credentials, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    String errorMessage = array.getString(1);
                                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                    else if (processId == WebMethods.TYPE_LIST_PRINCIPAL_COMPANY)
                    {
                        JSONObject jsonObject = new JSONObject(result.getResult().toString());
                        objCompany = Company.getItem(jsonObject);

                        if (objCompany != null)
                        {
                            Toast.makeText(getApplicationContext(),
                                    String.format("Bienvenido: %s", objUser.getEmployee().getPerson().getNames()),
                                    Toast.LENGTH_SHORT).show();

                            Intent activity = new Intent(getApplicationContext(), MainActivity.class);
                            outputParameters(activity);
                            startActivity(activity);
                        }
                    }
                }
            }
            else if (result.getResultCode().getId() == WebServices.Result.RESULT_OFFLINE)
            {
                throw new Exception(getString(R.string.message_without_connection));
            }
            else if (result.getResultCode().getId() == WebServices.Result.RESULT_ERROR)
            {
                throw new Exception(result.getResult() != null ? result.getResult().toString() : getString(R.string.message_web_services_error));
            }
            else
            {
                throw new Exception(getString(R.string.message_web_services_error));
            }
        }
        catch (Exception e)
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String error_message = preferences.getBoolean("depuration", false) ? e.getMessage() : getString(R.string.message_web_services_error);

            Toast.makeText(getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
            Log.e("WS_Login", String.format("%s (processId: %s)", error_message, processId));
        }
    }

    private void load()
    {
        //Provisional:
        /*if (preferences.getAll().size() == 0)
        {
            editor = preferences.edit();
            //editor.putString("hostname", "192.168.0.114");
            //editor.putInt("port", 8083);
            //editor.putString("application", "SysSales");

            //editor.putString("hostname", "sisdataperu-003-site1.itempurl.com");
            //editor.putInt("port", 80);
            //editor.putString("application", "SysSales_Pruebas");

            editor.putBoolean("depuration", false);
            editor.apply();
            editor = null;
        }*/

        boolean isRememberMe = preferences.getBoolean("remember_me", false);
        if (isRememberMe)
        {
            etUsername_Login.setText(preferences.getString("username", ""));
            etPassword_Login.setText(preferences.getString("password", ""));
            chkRememberMe_Login.setChecked(true);
        }
    }

    private void outputParameters(Intent intent)
    {
        intent.putExtra("user", objUser);
        intent.putExtra("company", objCompany);
    }

    private void checkUpdates()
    {
        boolean beta_upgrades = preferences.getBoolean("beta_upgrades", false);
        if (!beta_upgrades)
        {
            UpdateChecker.checkForDialog(LoginActivity.this, getString(R.string.update_url));
        }
        else
        {
            UpdateChecker.checkForDialog(LoginActivity.this, getString(R.string.update_url_beta));
        }
    }

    private void login()
    {
        if (Objects.requireNonNull(etUsername_Login.getText()).length() > 0 &&
         Objects.requireNonNull(etPassword_Login.getText()).length() > 0)
        {
            editor = preferences.edit();
            if (chkRememberMe_Login.isChecked())
            {
                editor.putBoolean("remember_me", true);
                editor.putString("username", etUsername_Login.getText().toString());
                editor.putString("password", etPassword_Login.getText().toString());
                editor.apply();
            }
            else
            {
                editor.remove("remember_me");
                editor.remove("username");
                editor.remove("password");
                editor.apply();
            }
            editor = null;

            //Ingresar:
            WebConfig config = new WebConfig(getApplicationContext());
            if (!config.getHostname().equals(""))
            {
                objWebMethods.findUser(etUsername_Login.getText().toString(), etPassword_Login.getText().toString());
            }
            else
            {
                Snackbar.make(btnLogin_Login, R.string.message_unconfigured_server_connection, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
        else
        {
            Snackbar.make(btnLogin_Login, R.string.message_incomplete_data, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void ConexionDirecta()
    {
        DirectConnection.execQuery("SELECT TOP 5 * FROM PRODUCTOS2");
    }
}