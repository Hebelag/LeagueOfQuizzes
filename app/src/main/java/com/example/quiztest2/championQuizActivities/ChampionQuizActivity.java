package com.example.quiztest2.championQuizActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quiztest2.ChampionQuizModeSelect;
import com.scp.leagueofquiz.R;
import com.example.quiztest2.dbstuff.DBHelper;

import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/*

 */

/*
 So heute ist der 10.01 nur kurz schreiben was ich vor habe.
Umschreiben von allen Quizactivities. Alle QuizActivities haben etwas gemeinsam.
Beispielsweise haben alle QuizActivities
->die Möglichkeit, vier Bilder zu haben und ein Ziel. Sei es Champion/Item/Ability.
->Den möglichen Timer
->Einen Score
->Anzahl wieviele dinger falsch sind
*/

/*TODO 18.01.2021
   In der Theorie müsste jeder einzelne Spielmodus eine eigene Klasse sein, aber keine eigene Activity?
    ChampionTimeAttack/ChampionTraining/ChampionEndless/ChampionMarathon IS-A ChampionQuiz
    Was soll im ChampionQuiz stehen?
    Also wäre vernünftig das alles aufzutrennen und vielleicht gemeinsame Funktionen aus einer
    ChampionQuizLogic-Klasse herausnehmen?!?!?!? DIE LÖSUNG?
    In ChampionQuizLogic wären dann funktionen wie fillChampionArray, showFinalScreen etc.
    Weil das ist ja das was sich alle Klassen teilen!
    könnte man dann die einzelnen views etc mit reinnehmen oder vllt das fertige array übergeben? :o
    das wäre so baba
*/

/*
Daraufhin extenden die einzelnen Champion/Item/Ability-QUizzes diese abstrakte(?) Klasse
Jedes Quiz hat seine separate Datenbank.
Die Datenbanken sollen bei Beginn der App eingeladen werden, nicht erst bei öffnen des jeweiligen QUizzes
Das Ding ist diese Datenbank wird aber nur geöffnet wenn das Quiz ausgewählt wird bzw die individuelle Frage.

GameMode 1: Training Mode mit 20/50/100 Champions:
4 Bilder, 1 Champion Name, 1 Bild von den 4 gehört zu dem Champion.
Mit Start Button (also den Default Bildern), Score, Genauigkeit und Final Screen

1. BuildGUI()
2. Buttons OnClickListener
3. loadNewChampions()
4. onRightAnswer() | onWrongAnswer()
5. showResults()
6. restartQuiz() | returnMainMenu()
 */
public class ChampionQuizActivity extends AppCompatActivity {


    // The keys are here to have static strings so i don't need them to declare locally
    // Makes only sense if used more than once tho.
    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_CHAMPION_TEXT = "keyChampionText";
    private static final String KEY_CURRENT_LEVEL = "keyCurrentLevel";
    private static final String KEY_WRONGS = "keyWrongs";
    private static final String KEY_IMG1 = "keyImg1";
    private static final String KEY_IMG2 = "keyImg2";
    private static final String KEY_IMG3 = "keyImg3";
    private static final String KEY_IMG4 = "keyImg4";

    private static final int CHAMPION_COUNT = 152;

    final String prefLevel = "currentLevel";
    
    ChampionQuizLogic logicHandler = new ChampionQuizLogic();

    // Not good habit to keep everything in the heap here, just use it where it needs to be used.
    // Instance variables are on the... stack? Need to read the book again
    TextView championText, scoreView;
    ImageButton btnAns1, btnAns2, btnAns3, btnAns4;
    TextView finalScore, finalAccuracy;
    TextView timerView, countDownTimerView, finalTimerView;
    Button returnButton, retryButton, buttonStartQuiz;
    ImageView timerImage;
    LinearLayout gameLayout, postGameLayout, timeAttackLayout;

    //Android Intent is the message that is passed between components such as activities, content providers, broadcast receivers, services etc.
    //It is generally used with startActivity() method to invoke activity, broadcast receivers etc.
    private Intent championQuizIntent;

    private int currentLevel = 1;
    private int score, wrongs, lives = 3;
    private long countDownMillis;
    private float accuracy;

    String champGameMode;
    String championName;
    String imagePath;
    String timeString;

    String[] buttonChampions = new String[4];
    String[] buttonChampionImages = new String[4];
    Set<String> championsAnswered = new HashSet<String>();

    int maxLevel = 0;
    long startTime = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long gameTime = System.currentTimeMillis() - startTime;
            int seconds = (int) (gameTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timeString = String.format(Locale.getDefault(), "%d:%02d", minutes, seconds);
            timerView.setText(timeString);
            timerHandler.postDelayed(this, 500);

        }
    };


    //Shuffles the array which in which the championNames and ids are being held.
    public static void shuffleArray(String[] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }


    //onCreate is called when the activity starts up. So in my opinion way too much logic here.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championquiz);

        //Get the intent to get the data passed from the ChampionQuizGameMode activity into here.
        getGameMode();

        //UI-method which shows inside the ChampionQuizActivity screen the default helm champions
        initializeEmptyChampions();

        //UI-method which loads the whole UI, or rather connects the instance variables of different
        //UI types to the Views
        loadLayout();


        //Conditional shit but I try to explain it.
        // The conditional is here because depending on the game mode you either need the timer or
        // Not. So to code it fast I just copypasted the code and asked whats stored inside the intent.
        // I think it can be done super clean somehow
        if (champGameMode.equals(ChampionQuizModeSelect.MODE_TIME_ATTACK)) {
            timerView.setVisibility(View.INVISIBLE);
            timerImage.setVisibility(View.INVISIBLE);
            timeAttackLayout.setVisibility(View.VISIBLE);
            countDownMillis = championQuizIntent.getLongExtra(ChampionQuizModeSelect.KEY_TIME, 60000);
            int minutes = (int) (countDownMillis / 1000) / 60;
            int seconds = (int) (countDownMillis / 1000) % 60;
            String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            countDownTimerView.setText(timeFormatted);
            maxLevel = 1000;

        } else if (champGameMode.equals(ChampionQuizModeSelect.MODE_MARATHON)) {
            maxLevel = CHAMPION_COUNT;
            //is the same
            timerView.setVisibility(View.VISIBLE);

            //is the same
            timerImage.setVisibility(View.VISIBLE);

            //is still the same
            timeAttackLayout.setVisibility(View.GONE);
        } else {
            maxLevel = championQuizIntent.getIntExtra(ChampionQuizModeSelect.KEY_TRAIN, 20);
            //is the same
            timerView.setVisibility(View.VISIBLE);

            //is the same
            timerImage.setVisibility(View.VISIBLE);

            //is still the same
            timeAttackLayout.setVisibility(View.GONE);
        }

        //Conclusion of the ugly conditional section: instead of declaring it in a repetitive way,
        //I should consider maybe doing different classes like the dev did it in PixelDungeon with
        //the levels? Link: https://github.com/watabou/pixel-dungeon
        // What is even the final logic of this conditional?
        // Everything is about a TIMER.
        // Final conditional loop again does adjust the maximum level, so a GAME SETUP

        // Seems kinda hard coded?
        btnAns1.setOnClickListener(createChampionOnClickListener(0));
        btnAns2.setOnClickListener(createChampionOnClickListener(1));
        btnAns3.setOnClickListener(createChampionOnClickListener(2));
        btnAns4.setOnClickListener(createChampionOnClickListener(3));

        // Most fucked up thing in the entire code. :: like why
        // So I try to explain it to myself:
        //
        // public void setOnClickListener(@Nullable OnClickListener l) {
        //        if (!isClickable()) {
        //            setClickable(true);
        //        }
        //        getListenerInfo().mOnClickListener = l;
        //    }
        //
        // so setOnClickListener needs an OnClickListener
        // goToMainMenu takes a View and does things
        //
        // public interface OnClickListener {
        //        /**
        //         * Called when a view has been clicked.
        //         *
        //         * @param v The view that was clicked.
        //         */
        //        void onClick(View v);
        //    }
        //
        // So OnClickListener is an interface which has a method that takes a View?!?!?
        // It's called method reference. So 'this' is this class which
        returnButton.setOnClickListener(this::goToMainMenu);
        retryButton.setOnClickListener(this::handleRetryButtonClick);
        buttonStartQuiz.setOnClickListener(this::startQuiz);


        // Conditional for phone flipping, anything that triggers onDestroy();
        if (savedInstanceState == null) {
            currentLevel = 1;
            score = 0;
            wrongs = 0;
        } else {
            score = savedInstanceState.getInt(KEY_SCORE);
            championName = savedInstanceState.getString(KEY_CHAMPION_TEXT);
            wrongs = savedInstanceState.getInt(KEY_WRONGS);
            currentLevel = savedInstanceState.getInt(KEY_CURRENT_LEVEL);

            buttonChampions[0] = savedInstanceState.getString(KEY_IMG1);
            retrieveChampionImage(buttonChampions[0], 1);

            buttonChampions[1] = savedInstanceState.getString(KEY_IMG2);
            retrieveChampionImage(buttonChampions[1], 2);

            buttonChampions[2] = savedInstanceState.getString(KEY_IMG3);
            retrieveChampionImage(buttonChampions[2], 3);

            buttonChampions[3] = savedInstanceState.getString(KEY_IMG4);
            retrieveChampionImage(buttonChampions[3], 4);

        }
    }

    private void getGameMode() {
        championQuizIntent = this.getIntent();
        champGameMode = championQuizIntent.getStringExtra(ChampionQuizModeSelect.KEY_GAME_MODE);
    }


    // Finally a small method, initializes some logic of the game, in this case the "empty"
    // champion images
    private void initializeEmptyChampions() {
        buttonChampions[0] = "defaultchampion";
        buttonChampions[1] = "defaultchampion";
        buttonChampions[2] = "defaultchampion";
        buttonChampions[3] = "defaultchampion";
    }

    // Method to get the 4 Images to listen to being clicked, returning an onclicklistener
    private View.OnClickListener createChampionOnClickListener(int champion) {

        return (View v) -> {

            if (championText.getText().toString().equalsIgnoreCase(buttonChampions[champion])) {
                championsAnswered.add(buttonChampions[champion]);
                currentLevel++;
                score++;
                String scoreViewText = "Score: " + score;
                scoreView.setText(scoreViewText);
                loadLevel();
            } else {
                wrongs++;
                Toast.makeText(getApplicationContext(), "WRONG!", Toast.LENGTH_SHORT).show();
            }
        };

    }

    //startQuiz sounds very general, what does it actually do?
    // again ugly conditional statements.
    // binds UI element to code,
    // starts timer??
    private void startQuiz(View v) {
        buttonStartQuiz = (Button) v;
        if (buttonStartQuiz.getText().toString().equals("STOP")) {
            currentLevel = maxLevel + 1;
            loadLevel();
        }
        if (champGameMode.equals(ChampionQuizModeSelect.MODE_ENDLESS)) {
            buttonStartQuiz.setClickable(true);
            String stopQuiz = "STOP";
            buttonStartQuiz.setText(stopQuiz);
        } else {
            buttonStartQuiz.setClickable(false);
        }
        if (champGameMode.equals(ChampionQuizModeSelect.MODE_TIME_ATTACK)) {
            startCountDown();
        } else {
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
        }

        loadLevel();
    }

    private void loadLayout() {
        btnAns1 = findViewById(R.id.btnAns1);
        btnAns2 = findViewById(R.id.btnAns2);
        btnAns3 = findViewById(R.id.btnAns3);
        btnAns4 = findViewById(R.id.btnAns4);
        buttonStartQuiz = findViewById(R.id.startCountdownButton);
        championText = findViewById(R.id.championText);
        scoreView = findViewById(R.id.scoreView);
        finalAccuracy = findViewById(R.id.tvFinalAccuracy);
        finalScore = findViewById(R.id.tvFinalScore);
        returnButton = findViewById(R.id.returnButton);
        retryButton = findViewById(R.id.retryButton);
        timerView = findViewById(R.id.wrongsView);
        timerImage = findViewById(R.id.timerImageView);
        gameLayout = findViewById(R.id.gameLayout);
        postGameLayout = findViewById(R.id.postGameLayout);
        timeAttackLayout = findViewById(R.id.timeAttackLayout);
        finalTimerView = findViewById(R.id.tvFinalTime);
        countDownTimerView = findViewById(R.id.countdownView);
    }


    // pretty clear what it does
    // starts the countdown which takes countDownMillis
    private void startCountDown() {
        CountDownTimer countDownTimer = new CountDownTimer(countDownMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDownMillis = Math.round((float) millisUntilFinished / 1000.0f) * 1000;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                countDownMillis = 0;
                updateCountDownText();
                currentLevel = maxLevel + 1;
                loadLevel();
            }
        }.start();
    }


    // Connected to startCountDown()
    private void updateCountDownText() {
        int minutes = (int) (countDownMillis / 1000) / 60;
        int seconds = (int) (countDownMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        countDownTimerView.setText(timeFormatted);
        if (countDownMillis < 10000) {
            countDownTimerView.setTextColor(Color.RED);
        } else {
            countDownTimerView.setTextColor(Color.BLACK);
        }
    }

    // Get the championImage according to the argument number provided.
    // Used in onSaveInstanceState
    public void retrieveChampionImage(String chmpImg, int x) {
        int imageID = getResources().getIdentifier(chmpImg.toLowerCase(), "drawable", getPackageName());
        switch (x) {
            case 1:
                btnAns1.setImageResource(imageID);
                break;
            case 2:
                btnAns2.setImageResource(imageID);
                break;
            case 3:
                btnAns3.setImageResource(imageID);
                break;
            case 4:
                btnAns4.setImageResource(imageID);
                break;
        }
    }


    // Very Broad Meaning, should be only LOGIC, but also cares about some UI elements
    // Bad manner
    public void loadLevel() {
        // getInstance because Singleton Pattern, which doesn't allow you to have multiple instances
        // of the class

        // When maxLevel is not reached yet, enter conditional
        if (currentLevel <= maxLevel) {

            // OLD: Get the other 3 champions, LOGIC
            // NEW: Get all 4 champions and care about which is the right champion later!
            fillChampionArray();
            selectRightChampion();

            // Set UI up (way better!)
            setChampionImages();

            // UI, has nothing to do here per se. Maybe do it in loadLevelUI and pass the QuizMode?
            championText.setText(championName);
            gameLayout.setVisibility(View.VISIBLE);
            postGameLayout.setVisibility(View.INVISIBLE);
        } else {
            loadFinishScreen();
            // What did I think while doing this. This looks exactly like it should be done in separate classes?
            // The big question is: how?
            // In the end there are the same 3 things done over again
        }

    }

    private void loadFinishScreen() {
        // Maybe put everything in doAllAccuracy() method?
        accuracy = (float) score / ((float) score + (float) wrongs) * 100;
        String finalAccuracyText = String.format(Locale.getDefault(), "Accuracy: %.1f %%", accuracy);
        finalAccuracy.setText(finalAccuracyText);

        if (champGameMode.equals(ChampionQuizModeSelect.MODE_TIME_ATTACK)) {
            finalTimerView.setVisibility(View.GONE);
            showFinalScore();
        } else if (champGameMode.equals(ChampionQuizModeSelect.MODE_ENDLESS)) {
            stopTimer();
            showFinishTime();
        } else {
            finalScore.setVisibility(View.GONE);
            stopTimer();
            showFinishTime();
        }
        // UI? SO much UI!?!
        gameLayout.setVisibility(View.INVISIBLE);
        postGameLayout.setVisibility(View.VISIBLE);
    }

    private void showFinalScore() {
        finalScore.setVisibility(View.VISIBLE);
        String finalScoreText = "Score: " + score;
        finalScore.setText(finalScoreText);
    }

    private void showFinishTime() {
        finalTimerView.setVisibility(View.VISIBLE);
        String finalTimeText = String.format(Locale.getDefault(), "Done in %s", timeString);
        finalTimerView.setText(finalTimeText);
    }

    private void stopTimer() {
        timerHandler.removeCallbacks(timerRunnable);
    }

    private void selectRightChampion() {
        int rightChampionPosition = (int) (Math.random() * 4);
        championName = buttonChampions[rightChampionPosition];
        imagePath = buttonChampionImages[rightChampionPosition];
    }

    public void fillChampionArray() {
        //OLD: local championID for the generation of the 4 champions to choose from
        //can this be done in a loop?
        //this one especially generates the "true" champion
        //I wonder if I can't do this the other way around. First generate 4 random champions
        //and then decide which one is the true one and append that somehow
        //NEW: deleted whole "repeated" code to do all four champs at once and decide later
        //which champion is the "right" one

        //Very good example for only having logic
        DBHelper db = DBHelper.getInstance(this);
        int[] uniqueChampionArray = new int[4];
        int championIndex = 0;
        for (int i = 0; i < 4; i++) {
            do {
                championIndex = (int) (Math.random() * CHAMPION_COUNT);
            } while (contains(uniqueChampionArray, championIndex) || championIndex >= CHAMPION_COUNT);
            String championKey = db.getChampKeyForChampQuiz(championIndex);
            uniqueChampionArray[i] = championIndex;
            buttonChampions[i] = db.getChampNameFromKey(championKey);
            buttonChampionImages[i] = db.getChampIDFromKey(championKey);
        }
    }

    private boolean contains(int[] uniqueChampionArray, int championID) {
        for (int i : uniqueChampionArray) {
            if (championID == i) {
                return true;
            }
        }
        return false;
    }

    public void setChampionImages() {
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    int imageID1 = getResources().getIdentifier(buttonChampionImages[0].toLowerCase(), "drawable", getPackageName());
                    btnAns1.setImageResource(imageID1);
                    break;
                case 1:
                    int imageID2 = getResources().getIdentifier(buttonChampionImages[1].toLowerCase(), "drawable", getPackageName());
                    btnAns2.setImageResource(imageID2);
                    break;
                case 2:
                    int imageID3 = getResources().getIdentifier(buttonChampionImages[2].toLowerCase(), "drawable", getPackageName());
                    btnAns3.setImageResource(imageID3);
                    break;
                case 3:
                    int imageID4 = getResources().getIdentifier(buttonChampionImages[3].toLowerCase(), "drawable", getPackageName());
                    btnAns4.setImageResource(imageID4);
                    break;
            }

        }
    }

    private void goToMainMenu(View v) {
        SharedPreferences preferencesReturnLevel = getSharedPreferences(prefLevel, MODE_PRIVATE);
        SharedPreferences.Editor editorReturn = preferencesReturnLevel.edit();
        editorReturn.putInt(prefLevel, 1);
        editorReturn.apply();
        finish();
    }

    private void handleRetryButtonClick(View v) {
        SharedPreferences preferencesLevel = getSharedPreferences(prefLevel, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesLevel.edit();
        editor.putInt(prefLevel, 1);
        editor.apply();
        String scoreText = "Score: 0";
        scoreView.setText(scoreText);
        countDownMillis = championQuizIntent.getLongExtra(ChampionQuizModeSelect.KEY_TIME, 60000);
        String timerText = "0:00";
        timerView.setText(timerText);
        score = 0;
        currentLevel = 1;
        accuracy = 0;
        lives = 0;
        wrongs = 0;
        buttonStartQuiz.setClickable(true);
        String defaultChampionText = "Champion";
        championText.setText(defaultChampionText);
        int imageID = getResources().getIdentifier("defaultchampion", "drawable", getPackageName());
        btnAns1.setImageResource(imageID);
        btnAns2.setImageResource(imageID);
        btnAns3.setImageResource(imageID);
        btnAns4.setImageResource(imageID);

        postGameLayout.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_WRONGS, wrongs);
        outState.putInt(KEY_CURRENT_LEVEL, currentLevel);
        outState.putString(KEY_CHAMPION_TEXT, championName);
        outState.putString(KEY_IMG1, buttonChampions[0]);
        outState.putString(KEY_IMG2, buttonChampions[1]);
        outState.putString(KEY_IMG3, buttonChampions[2]);
        outState.putString(KEY_IMG4, buttonChampions[3]);
    }

}