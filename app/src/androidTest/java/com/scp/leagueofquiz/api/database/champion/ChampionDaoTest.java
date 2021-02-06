package com.scp.leagueofquiz.api.database.champion;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.scp.leagueofquiz.api.database.LolDatabase;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings("RedundantThrows")
@RunWith(AndroidJUnit4.class)
public class ChampionDaoTest {
  private LolDatabase db;
  private ChampionDao underTest;

  @Before
  public void setUp() throws Exception {
    Context context = ApplicationProvider.getApplicationContext();
    db = Room.inMemoryDatabaseBuilder(context, LolDatabase.class).build();
    underTest = db.championDao();
  }

  @After
  public void tearDown() throws Exception {
    db.close();
  }

  @Test
  public void insertAll_findAll() {
    // Arrange
    Champion c1 = new Champion("id1", "name1");
    Champion c2 = new Champion("id2", "name2");

    // Act
    underTest.insertAll(c1, c2);
    List<Champion> result = underTest.findAll();

    // Assert
    assertThat(result, is(Arrays.asList(c1, c2)));
  }

  @Test
  public void findRandomChampsExcept() {
    // Arrange
    Champion c1 = new Champion("id1", "name1");
    Champion c2 = new Champion("id2", "name2");
    Champion c3 = new Champion("id3", "name3");
    Champion c4 = new Champion("id4", "name4");
    List<String> toExclude = Arrays.asList("name2", "name3");

    // Act
    underTest.insertAll(c1, c2, c3, c4);
    List<Champion> result = underTest.findRandomChampsExcept(toExclude, 2);

    // Assert
    assertThat(result, notNullValue());
    assertThat(result.size(), is(2));
    assertThat(result, containsInAnyOrder(c1, c4));
  }
}
