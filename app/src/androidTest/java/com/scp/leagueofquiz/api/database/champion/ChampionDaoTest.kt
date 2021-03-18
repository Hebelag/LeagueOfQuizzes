package com.scp.leagueofquiz.api.database.champion

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.scp.leagueofquiz.api.database.LolDatabase
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("EXPERIMENTAL_API_USAGE")
@RunWith(AndroidJUnit4::class)
class ChampionDaoTest {
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    lateinit var db: LolDatabase
    lateinit var underTest: ChampionDao

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, LolDatabase::class.java)
                .setTransactionExecutor(testDispatcher.asExecutor())
                .setQueryExecutor(testDispatcher.asExecutor()).build()
        underTest = db.championDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAll_findAll() = testScope.runBlockingTest {
        // Arrange
        val c1 = Champion(identifier = "id1", name = "name1")
        val c2 = Champion(identifier = "id2", name = "name2")

        // Act
        underTest.insertAll(listOf(c1, c2))
        val result = underTest.findAll()

        // Assert
        Assert.assertThat(result, Matchers.`is`(listOf(c1, c2)))
    }

    @Test
    fun findRandomChampsExcept() = testScope.runBlockingTest {
        // Arrange
        val c1 = Champion(identifier = "id1", name = "name1")
        val c2 = Champion(identifier = "id2", name = "name2")
        val c3 = Champion(identifier = "id3", name = "name3")
        val c4 = Champion(identifier = "id4", name = "name4")
        val toExclude = listOf("name2", "name3")

        // Act
        underTest.insertAll(listOf(c1, c2, c3, c4))
        val result = underTest.findRandomChampsExcept(toExclude, 2)

        // Assert
        Assert.assertThat(result, Matchers.notNullValue())
        Assert.assertThat(result.size, Matchers.`is`(2))
        Assert.assertThat(result, Matchers.containsInAnyOrder(c1, c4))
    }
}