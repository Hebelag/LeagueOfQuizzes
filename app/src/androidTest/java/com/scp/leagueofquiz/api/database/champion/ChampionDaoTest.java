package com.scp.leagueofquiz.api.database.champion;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.google.common.util.concurrent.ListenableFuture;
import com.scp.leagueofquiz.api.database.LolDatabase;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
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
  public void insertAll_findAll() throws ExecutionException, InterruptedException {
    // Arrange
    Champion c1 = new Champion("id1", "name1");
    Champion c2 = new Champion("id2", "name2");

    // Act
    underTest.insertAll(Arrays.asList(c1, c2));
    ListenableFuture<List<Champion>> all = underTest.findAll();
    List<Champion> result = all.get();

    // Assert
    assertThat(result, is(Arrays.asList(c1, c2)));
  }

  @Test
  public void findRandomChampsExcept() throws ExecutionException, InterruptedException {
    // Arrange
    Champion c1 = new Champion("id1", "name1");
    Champion c2 = new Champion("id2", "name2");
    Champion c3 = new Champion("id3", "name3");
    Champion c4 = new Champion("id4", "name4");
    List<String> toExclude = Arrays.asList("name2", "name3");

    // Act
    underTest.insertAll(Arrays.asList(c1, c2, c3, c4));
    ListenableFuture<List<Champion>> future = underTest.findRandomChampsExcept(toExclude, 2);
    List<Champion> result = future.get();

    // Assert
    assertThat(result, notNullValue());
    assertThat(result.size(), is(2));
    assertThat(result, containsInAnyOrder(c1, c4));
  }
}
