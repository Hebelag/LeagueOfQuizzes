package com.scp.leagueofquiz.api.database.metadata;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import com.google.common.util.concurrent.ListenableFuture;
import com.scp.leagueofquiz.api.database.LolDatabase;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("RedundantThrows")
public class MetadataDaoTest {
  private LolDatabase db;
  private MetadataDao underTest;

  @Before
  public void setUp() throws Exception {
    Context context = ApplicationProvider.getApplicationContext();
    db = Room.inMemoryDatabaseBuilder(context, LolDatabase.class).build();
    underTest = db.metadataDao();
  }

  @After
  public void tearDown() throws Exception {
    db.close();
  }

  @Test
  public void insert_findAll() throws ExecutionException, InterruptedException {
    // Arrange
    Metadata metadata = new Metadata(123456789);

    // Act
    ListenableFuture<Void> insertFuture = underTest.insert(metadata);
    insertFuture.get();
    ListenableFuture<List<Metadata>> future = underTest.findAll();
    List<Metadata> result = future.get();

    // Assert
    assertThat(result, notNullValue());
    assertThat(result.isEmpty(), is(false));
    assertThat(result.get(0), is(metadata));
  }
}
