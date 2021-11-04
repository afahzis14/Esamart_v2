package com.example.myapplication.server;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.MapsActivity;
import com.example.myapplication.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DataParsing {
    private Context context;
    private ProgressDialog progressDialog;

    /**
     * Inisialisasi SharedPreferences
     **/
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private boolean setSharedPreferences(Context c) {
        boolean getCek;
        this.context = c;

        sharedPreferences = c.getSharedPreferences(AppVar.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences != null && this.context != null) {
            getCek = true;
        } else {
            getCek = false;
        }
        return getCek;
    }

    public void Login(Context c, String mPhone, String mPassword) {
        progressDialog = ProgressDialog.show(c, "Mohon tunggu....",
                "Sedang memeriksa data user.", true);
        progressDialog.setCancelable(false);
        if (setSharedPreferences(c) == true) {
            new LoginAsync().execute(
                    AppVar.PREF_NAME,
                    AppVar.KEY_LOGIN,
                    mPhone,
                    mPassword
            );
        }
    }

    class LoginAsync extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        private final String API_URL = AppVar.URL_SERVER;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {

                HashMap<String, String> params = new HashMap<>();
                params.put(AppVar.KEY_API, args[0]);
                params.put(AppVar.KEY_FUNCTION, args[1]);
                params.put(AppVar.DATA_USERNAME, args[2]);
                params.put(AppVar.DATA_PASSWORD, args[3]);
                Log.d("request", "starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        API_URL, "POST", params);

                if (json != null) {
                    Log.d("JSON result", json.toString());
                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(JSONObject json) {
            if (json != null) {
//                Toast.makeText(getApplicationContext(),json.toString(),Toast.LENGTH_SHORT).show();
                try {
                    JSONObject parentObject = new JSONObject(json.toString());
                    JSONObject userDetails = parentObject.getJSONObject("hasil");
                    String success = userDetails.getString("success");
                    String id = "", nama = "", level = "", phone = "", username = "";
                    if (success.equals("1")) {
                        id = userDetails.getString("var_id");
                        nama = userDetails.getString("var_full_name");
                        username = userDetails.getString("var_username");
                        phone = userDetails.getString("var_phone");
                        level = userDetails.getString("var_level");

                        editor.remove(AppVar.CEK_LOGIN);
                        editor.remove(AppVar.ID_USER);
                        editor.remove(AppVar.USERNAME);
                        editor.remove(AppVar.NAMA);
                        editor.remove(AppVar.PHONE);
                        editor.remove(AppVar.LEVEL_USER);

                        editor.putString(AppVar.CEK_LOGIN, "1");
                        editor.putString(AppVar.ID_USER, id);
                        editor.putString(AppVar.USERNAME, username);
                        editor.putString(AppVar.NAMA, nama);
                        editor.putString(AppVar.PHONE, phone);
                        editor.putString(AppVar.LEVEL_USER, level);

                        editor.commit();

                        Intent i = new Intent(context, MapsActivity.class);
                        context.startActivity(i);
                        ((LoginActivity) context).finish();

                    } else {
                        progressDialog.dismiss();
                        AlertDialog.Builder noticeLogin = new AlertDialog.Builder(context);
                        noticeLogin.setTitle("Login gagal");
                        noticeLogin.setMessage("\nSilakan periksa kembali Nomor HP/Username dan Password anda.");
                        noticeLogin.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        noticeLogin.create();
                        noticeLogin.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context.getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
            progressDialog.dismiss();
        }
    }

    public void Register(Context c, String mFullName, String mPhone, String mUsername, String mPassword) {
        progressDialog = ProgressDialog.show(c, "Mohon tunggu....",
                "Sedang memeriksa data user.", true);
        progressDialog.setCancelable(false);
        if (setSharedPreferences(c) == true) {
            new RegisterAsync().execute(
                    AppVar.PREF_NAME,
                    AppVar.KEY_REGISTER,
                    mFullName,
                    mPhone,
                    mUsername,
                    mPassword
            );
        }
    }

    class RegisterAsync extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        private final String API_URL = AppVar.URL_SERVER;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {

                HashMap<String, String> params = new HashMap<>();
                params.put(AppVar.KEY_API, args[0]);
                params.put(AppVar.KEY_FUNCTION, args[1]);
                params.put(AppVar.DATA_NAME, args[2]);
                params.put(AppVar.DATA_PHONE, args[3]);
                params.put(AppVar.DATA_USERNAME, args[4]);
                params.put(AppVar.DATA_PASSWORD, args[5]);
                Log.d("request", "starting");

                JSONObject json = jsonParser.makeHttpRequest(
                        API_URL, "POST", params);

                if (json != null) {
                    Log.d("JSON result", json.toString());
                    return json;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(JSONObject json) {
            if (json != null) {
//                Toast.makeText(getApplicationContext(),json.toString(),Toast.LENGTH_SHORT).show();
                try {
                    JSONObject parentObject = new JSONObject(json.toString());
                    JSONObject userDetails = parentObject.getJSONObject("hasil");
                    String success = userDetails.getString("success");
                    String id = "", nama = "", level = "", phone = "", username = "";
                    if (success.equals("1")) {
                        id = userDetails.getString("var_id");
                        nama = userDetails.getString("var_full_name");
                        username = userDetails.getString("var_username");
                        phone = userDetails.getString("var_phone");
                        level = userDetails.getString("var_level");

                        editor.remove(AppVar.CEK_LOGIN);
                        editor.remove(AppVar.ID_USER);
                        editor.remove(AppVar.USERNAME);
                        editor.remove(AppVar.NAMA);
                        editor.remove(AppVar.PHONE);
                        editor.remove(AppVar.LEVEL_USER);

                        editor.putString(AppVar.CEK_LOGIN, "1");
                        editor.putString(AppVar.ID_USER, id);
                        editor.putString(AppVar.USERNAME, username);
                        editor.putString(AppVar.NAMA, nama);
                        editor.putString(AppVar.PHONE, phone);
                        editor.putString(AppVar.LEVEL_USER, level);

                        editor.commit();

                        Intent i = new Intent(context, MapsActivity.class);
                        context.startActivity(i);
                        ((RegisterActivity) context).finish();

                    } else {
                        progressDialog.dismiss();
                        AlertDialog.Builder noticeLogin = new AlertDialog.Builder(context);
                        noticeLogin.setTitle("Register gagal");
                        noticeLogin.setMessage("\nSilakan periksa kembali Nomor HP dan Password anda.");
                        noticeLogin.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        noticeLogin.create();
                        noticeLogin.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context.getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
            progressDialog.dismiss();
        }
    }

    //[Mulai] Fungsi Logout =======================================================================
    public void logout(Context c) {
        if (setSharedPreferences(c) == true) {
            editor.remove(AppVar.CEK_LOGIN);
            editor.remove(AppVar.ID_USER);
            editor.remove(AppVar.USERNAME);
            editor.remove(AppVar.NAMA);
            editor.remove(AppVar.PHONE);
            editor.remove(AppVar.LEVEL_USER);
            editor.commit();
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((MapsActivity) context).finish();
        }
    }
    //[Selesai] -----------------------------------------------------------------------------------
}
