package br.ruspotlight;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.ruspotlight.api.AccessToken;
import br.ruspotlight.api.ServiceGenerator;
import br.ruspotlight.api.UFRNClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_home_black_24dp,
            R.drawable.ic_insert_chart_black_24dp,
            R.drawable.ic_person_black_24dp
    };

    private final String CLIENT_ID = "CLIENT_ID";
    private final String CLIENT_SECRET = "CLIENT_SECRET";
    private final String RESPONSE_TYPE = "code";
    private final String REDIRECT_URI = "br.ruspotlight://oauth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(REDIRECT_URI)) {
            String code = uri.getQueryParameter("code");
            if (code != null) {
                Log.d(TAG, "CODE: " + code);
                UFRNClient client = ServiceGenerator.createService(UFRNClient.class);
                Call<AccessToken> call = client.getAccessToken(code, CLIENT_ID,
                        CLIENT_SECRET, REDIRECT_URI, "authorization_code");

                call.enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                        int statusCode = response.code();

                        if (statusCode == 200) {
                            AccessToken token = response.body();
                            Log.d(TAG, "TOKEN: " + token.getAccessToken());
                        } else {
                            // TODO Handle errors on a failed response
                        }
                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
                        // TODO Handle failure
                    }
                });
            } else if (uri.getQueryParameter("error") != null) {
                // TODO Handle error on query
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_login) {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(ServiceGenerator.API_BASE_URL + "/authz-server/oauth/authorize" +
                            "?client_id=" + CLIENT_ID +
                            "&response_type=" + RESPONSE_TYPE +
                            "&redirect_uri=" + REDIRECT_URI));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "HOME");
        adapter.addFragment(new StatisticsFragment(), "STATISTICS");
        adapter.addFragment(new ProfileFragment(), "PROFILE");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    public void botaoAlmoco(View view){
        Intent i = new Intent(this, MealActivity.class);
        startActivity(i);
    }
}
