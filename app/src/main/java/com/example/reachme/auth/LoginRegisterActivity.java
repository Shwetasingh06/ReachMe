package com.example.reachme.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.reachme.R;

import java.util.ArrayList;

public class LoginRegisterActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        ViewPager viewPager=findViewById(R.id.viewpager);

        AuthenticationPagerAdapter pagerAdapter=new AuthenticationPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new LoginFragment());
        pagerAdapter.addFragment(new RegisterFragment());
        viewPager.setAdapter(pagerAdapter);

        String openPage = getIntent().getStringExtra("openingPage");
        if(openPage!=null){
            if(openPage.equals("register")){
                viewPager.setCurrentItem(1);
            }
        }
    }

    class AuthenticationPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragmentlist=new ArrayList<>();

        public AuthenticationPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentlist.get(position);
        }

        @Override
        public int getCount() {
            return fragmentlist.size();
        }

        void addFragment(Fragment fragment){
            fragmentlist.add(fragment);
        }
    }
}