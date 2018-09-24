package com.distracteddevelopment.peptalk;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

/**
 *
 */
public class MainActivity extends AppCompatActivity {
    TextToSpeech textToSpeech;
    SquareImageButton complimentButton;
    TextView titleText;
    TextView subtitleText;
    String[] compliments;

    /**
     * YE OLDE ONCREATE OVERRIDE
     * - Load preferences
     * - Initialize text to speech
     * - Set up the nice compliment giving button
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LOAD PREFERENCES
        new ThemeColors(getApplicationContext());
        setTheme(ThemeColors.MENU_THEME_ID);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout currentLayout = findViewById(R.id.main_layout);
        currentLayout.setBackgroundColor(ThemeColors.BACKGROUND_COLOR_INT);

        // code I can probably get rid of if I was use kotlin
        complimentButton = findViewById(R.id.speakButton);
        subtitleText = findViewById(R.id.mainSubtitleText);
        titleText = findViewById(R.id.mainTitleText);

        // INITIALIZE TEXT TO SPEECH
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });

        // OUR LOYAL BUTTON
        loadCompliments();

        complimentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtitleText.setVisibility(View.INVISIBLE);
                titleText.setVisibility(View.INVISIBLE);

                String toSpeak = getCompliment();
                showToast(toSpeak,toSpeak.split("\\s+").length);
                textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    /*
     * Don't forget to shutdown text to speech!
     */
    public void onDestroy(){
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    /**
     * MENU STUFF
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * HELPER METHODS of varying degrees of necessity.
     */
    public void loadCompliments(){
        compliments = getResources().getStringArray(R.array.compliments_array);
    }

    public String getCompliment(){
        Random random = new Random();
        int index = random.nextInt(compliments.length);
        return compliments[index];
    }

    public void showToast(String message, int wordCount) {
        // Set the toast and duration
        int toastDurationInMilliSeconds = wordCount * 330;
        final Toast mToastToShow = Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG);

        // Set the countdown to display the toast
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 500 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                mToastToShow.show();
            }
            public void onFinish() {
                mToastToShow.cancel();
            }
        };

        // Show the toast and starts the countdown
        mToastToShow.show();
        toastCountDown.start();
    }
}
