package com.metacoders.communityapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.metacoders.communityapp.BuildConfig;
import com.metacoders.communityapp.R;
import com.metacoders.communityapp.api.NewsRmeApi;
import com.metacoders.communityapp.api.ServiceGenerator;
import com.metacoders.communityapp.models.allDataResponse;
import com.metacoders.communityapp.utils.SharedPrefManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    SharedPrefManager manager;
    TextView versionTv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        versionTv = findViewById(R.id.versionName) ;
        manager = new SharedPrefManager(getApplicationContext());

        getSupportActionBar().hide();

        try{
            versionTv.setText(BuildConfig.VERSION_NAME+"");
        }catch (Exception e ){

        }


        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean fstart = prefs.getBoolean("firstStart", true);
        if (fstart) {
            show_Dialog_after_Install();

        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // to the activity
                loadMiscData();

                // throw new RuntimeException("Test Crash");

            }
        }, 200);

        //printKeyHash();


    }

    private void loadMiscData() {
//        SharedPrefManager sharedPrefManager = new SharedPrefManager(getApplicationContext());
//        String accessTokens = sharedPrefManager.getUserToken();
//        Log.d("TAG", "loadList: activity " + accessTokens);


        NewsRmeApi api = ServiceGenerator.createService(NewsRmeApi.class, "00");

        Call<allDataResponse> catCall = api.getCategoryList();

        catCall.enqueue(new Callback<allDataResponse>() {
            @Override
            public void onResponse(Call<allDataResponse> call, Response<allDataResponse> response) {

                if (response.code() == 201) {

                    allDataResponse dataResponse = response.body();
                    Intent p = new Intent(getApplicationContext(), HomePage.class);
                    p.putExtra("MISC", dataResponse);

                    startActivity(p);
                    finish();

//                    categoryList = dataResponse.getCategories();
//                    languageList = dataResponse.getLanguageList();

                    // load all the category name
//                    for (int i = 0; i < categoryList.size(); i++) {
//                        categoryNameList.add(categoryList.get(i).getName().toString());
//
//                    }
//                    for (int i = 0; i < languageList.size(); i++) {
//                        languageNameList.add(languageList.get(i).getName().toString());
//
//                    }

                    // send it to adaper

                } else {
                    loadMiscData();
                }
            }

            @Override
            public void onFailure(Call<allDataResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void printKeyHash() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.metacoders.communityapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {


                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("Keyhash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }

    private void show_Dialog_after_Install() {

        SharedPrefManager.getInstance(getApplicationContext()).logout();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

}