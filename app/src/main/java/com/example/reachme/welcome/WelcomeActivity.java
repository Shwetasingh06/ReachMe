package com.example.reachme.welcome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.Animator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.reachme.R;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    private final String TAG = "welcome-test";

    private ViewPager2 vp;
    private Button loginHere;
    private TextView tv; //create account
    private CardView cardView;

    private int pageCounter = 0 ; //page counter for scrolling horizontally
    private Handler handler;

    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        vp = findViewById(R.id.viewPager2);
        loginHere = findViewById(R.id.welcome_btn_loginhere);
        tv = findViewById(R.id.welcome_tv_register);
        cardView = findViewById(R.id.cardView);
        handler = new Handler();


        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), getLifecycle());
        adapter.addFragment(new WelcomePage1Fragment());
        adapter.addFragment(new WelcomePage2Fragment());
        adapter.addFragment(new WelcomePage3Fragment());

        //avoid human interaction for swipe
//        vp.setUserInputEnabled(false);
        vp.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        vp.setAdapter(adapter);

        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pageCounter = position+1;
                handler.removeCallbacks(runnable);

                handler.postDelayed(runnable, 4000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        /*
        intent this to login fragment in login signUp page
         */
        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Animation myAnim = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.button_press_pop_down);
                cardView.startAnimation(myAnim);
                Toast.makeText(WelcomeActivity.this, "Login Here", Toast.LENGTH_SHORT).show();
            }
        });

        //intent this to register page
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WelcomeActivity.this, "Create Account", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //runnnable for swapping effect in viewpager2
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(pageCounter>= vp.getAdapter().getItemCount()){
                pageCounter = 0;
            }
//            Log.d(TAG, "run: "+pageCounter);
            vp.setCurrentItem(pageCounter, true);
            handler.postDelayed(runnable, 4000); //set the limit for pager to move to next page

        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(runnable);
    }

    //Adapter for view pager2
    private class MyAdapter extends FragmentStateAdapter{
        private List<Fragment> list;

        public MyAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
            list = new ArrayList<>();
        }

        //this method is defined for adding new fragment in list so that it can
        //appear dynamically
        void addFragment(Fragment fragment){
            list.add(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}