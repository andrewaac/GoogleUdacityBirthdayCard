package com.googleudacity.andrewcunningham.birthdaycard;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Final Fields
    private final int SWITCH_TIME = 5000;
    private final String TO = "Charlie";
    private final String FROM = "Andrew";
    private final String WHERE_OLD = "Zoo";
    private final String WHERE_CURRENT = "Bar";
    private final String WHEN_OLD = "2007";
    private final String WHEN_CURRENT = "2017";

    // Strings
    private String to = String.format("Happy Birthday %s!", TO);
    private String from = String.format("From %s!", FROM);
    private String whereOld = String.format("At the %s!", WHERE_OLD);
    private String whereCurrent = String.format("At the %s!", WHERE_CURRENT);

    // Views
    private TextSwitcher toWhereTextSwitcher, fromWhenTextSwitcher;
    private TextView toTextView, fromTextView, whereTextView, whenTextView;
    private ViewFlipper pictureSwitcher;

    // Handler and Runnable
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            pictureSwitcher.showNext();
            updateText();
            handler.postDelayed(runnable, SWITCH_TIME);
        }
    };

    // Flags
    private boolean showInfo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind Views
        toTextView = (TextView) findViewById(R.id.to_textview);
        fromTextView = (TextView) findViewById(R.id.from_textview);
        whereTextView = (TextView) findViewById(R.id.where_textView);
        whenTextView = (TextView) findViewById(R.id.when_textView);
        pictureSwitcher = (ViewFlipper) findViewById(R.id.picture_switcher);
        toWhereTextSwitcher = (TextSwitcher) findViewById(R.id.to_and_where_textswitch);
        fromWhenTextSwitcher = (TextSwitcher) findViewById(R.id.from_and_when_textswitch);

        // Setting Text
        toTextView.setText(to);
        fromTextView.setText(from);

        // Set OnClickListener
        pictureSwitcher.setOnClickListener(this);

        // Set up Other
        setUpAnimations();

        handler.postDelayed(runnable, SWITCH_TIME);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.picture_switcher) {
            showInfo = !showInfo;
            updateText();
        }
    }

    private void setUpAnimations() {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(500);
        pictureSwitcher.setInAnimation(fadeIn);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(200);
        pictureSwitcher.setOutAnimation(fadeOut);
    }

    public void updateText() {
        if (showInfo) {
            if (pictureSwitcher.getDisplayedChild() == 0) {
                whenTextView.setText(WHEN_OLD);
                whereTextView.setText(whereOld);
            } else {
                whenTextView.setText(WHEN_CURRENT);
                whereTextView.setText(whereCurrent);
            }
            toWhereTextSwitcher.setDisplayedChild(1);
            fromWhenTextSwitcher.setDisplayedChild(1);
        } else{
            toWhereTextSwitcher.setDisplayedChild(0);
            fromWhenTextSwitcher.setDisplayedChild(0);
        }
    }

}
