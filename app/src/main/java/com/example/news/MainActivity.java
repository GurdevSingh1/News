package com.example.news;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

//Starting Activity
public class MainActivity extends AppCompatActivity {
    EditText et;
    ProgressBar pb;
    Button b;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       load();
    }

    void load()
    {
        et  = findViewById(R.id.editText);
        pb = (ProgressBar)(findViewById(R.id.progressBar));
        b = findViewById(R.id.buttonsv);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("app  ","button click1");
                System.out.println("133");
                AsyncJson aj = new AsyncJson(MainActivity.this);
                aj.execute(et.getText().toString());
                System.out.println("1");
                Log.d("app  ","button click2");
            }
        });

        sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
    }

    class AsyncJson extends AsyncTask<String,Void, String>
    {
        Context ct;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String responseJsonStr = null;

        AsyncJson(Context ctx)
        {
            ct  = ctx;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
            System.out.println("2");
        }
        @Override
        protected String doInBackground(String... strings) {
            String cn = strings[0];
            final String news_url ="https://api.nytimes.com/svc/search/v2/articlesearch.json?";
            System.out.println("3");
            final String QUERY_PARAM = "q";
            final String APPID_PARAM = "api-key";

            Uri finaluri = Uri.parse(news_url).buildUpon()
                    .appendQueryParameter(APPID_PARAM, "ejqTOJsgxyfk7GklpQ6A9yPbXlEA010Q")
                    .appendQueryParameter(QUERY_PARAM, cn)

                    .build();

            String recjson = fetchData(finaluri);

            return recjson;
        }

        String fetchData(Uri builtUri)
        {
            try{
                URL url = new URL(builtUri.toString());
                Log.d("URI", builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                Log.d("STATUS","connected");
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this,"Connected", Toast.LENGTH_SHORT).show();
                    }
                });


                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return "";
                }
                responseJsonStr = buffer.toString();
                System.out.println("Received : " + responseJsonStr);
            }
            catch(Exception e)
            {
                Log.d("Error",e.toString());
                e.printStackTrace();
            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("News", "Error closing stream", e);
                    }
                }
            }
            return responseJsonStr;
        }


        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);

            if(res!=null && res.length()>0)
            {
                try {
                    Log.d("Data", res);
                    JSONObject o1 = new JSONObject(res);
                    JSONObject o2=   o1.getJSONObject("response");
                    JSONArray o3 = o2.getJSONArray("docs");


                    ArrayList<News> newsarry = new ArrayList<>();
                    for(int i=0; i<o3.length();i++)
                    {
                        JSONObject newsobj = o3.getJSONObject(i);
                        String weburl = newsobj.getString("web_url");
                        String snippet = newsobj.getString("snippet");
                        String pub_date = newsobj.getString("pub_date");

                        JSONObject headlineobj = newsobj.getJSONObject("headline");
                        String headline = headlineobj.getString("main");

                        JSONObject bylineobj = newsobj.getJSONObject("byline");
                        String author = bylineobj.getString("original");

                        News n = new News(headline,weburl,author,pub_date,snippet);
                        newsarry.add(n);
                    }
                    ////////////////////////////////
///////////////////////////////////////////////
                    pb.setVisibility(View.GONE);   //progressbar gone
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

                    tabLayout.removeAllTabs();;
                    tabLayout.addTab(tabLayout.newTab().setText("Articles"));

                    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

                    final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

                    final PagerAdapter adapter = new MyCustomAdapter
                            (getSupportFragmentManager(), tabLayout.getTabCount(),newsarry);
                    System.out.println("0");
                    viewPager.setAdapter(adapter);
                    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            viewPager.setCurrentItem(tab.getPosition());
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });



                }
                catch(Exception e)
                {
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        }
    }



    public boolean onCreateOptionsMenu(Menu m) {
        //inflate the xml file into m
        getMenuInflater().inflate(R.menu.menu,m);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id ==R.id.m1)
        {
            Intent i = new Intent(this, ViewSavedNews.class);
            startActivity(i);
        }
        if(id==R.id.m3)
        {
            MyDialog md = new MyDialog(this);
            md.show();
        }
        if(id== R.id.m2) {
            Intent i = new Intent(this, PrefOption.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    void setLocale(Locale locale){

        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        Log.d("info","Locale SEt");
       onConfigurationChanged(config);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "OnResume Called", Toast.LENGTH_SHORT).show();
       configureLocale();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);
        load();
    }

    void configureLocale()
    {
        boolean b = sp.getBoolean("f1", false);
        Log.d("f1 value", ""+b);
        if (b) {
            setLocale(new Locale("fr"));
        }
        else
        {
            setLocale(new Locale("en"));
        }
    }

}
