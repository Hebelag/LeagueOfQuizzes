package com.scp.leagueofquiz.entrypoint;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.scp.leagueofquiz.R;

public class MainActivity extends AppCompatActivity {
  private static final int REQUEST_CODE_CHAMPION_QUIZ = 1;
  private static final int REQUEST_CODE_ITEM_QUIZ = 2;
  private static final int REQUEST_CODE_ABILITY_QUIZ = 3;
  private static final int REQUEST_CODE_TEST = 99;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
}
