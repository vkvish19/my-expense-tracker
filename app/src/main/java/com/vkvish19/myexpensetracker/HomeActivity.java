package com.vkvish19.myexpensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private int selectedID = -1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_add_transaction:
                        return setFragment(new AddTransactionFragment(), R.id.navigation_add_transaction);
                    case R.id.navigation_dashboard:
                        return setFragment(new DashboardFragment(), R.id.navigation_dashboard);
                    case R.id.navigation_settings:
                        return setFragment(new SettingsFragment(), R.id.navigation_settings);
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        LocalDatabaseHelper dbHelper = new LocalDatabaseHelper(this, null, null, 1);
        dbHelper.initializeUserData(UserData.userID);
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setFragment(new DashboardFragment(), R.id.navigation_dashboard);
//        FirebaseDBHelper.setMyRef();
    }

    private boolean setFragment(Fragment fragment, int id) {
        if(selectedID == id)
            return false;

        selectedID = id;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
        return true;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}