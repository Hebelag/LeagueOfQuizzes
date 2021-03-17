package com.scp.leagueofquiz.api.database.metadata

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.scp.leagueofquiz.api.database.LolDatabase
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Test

@Suppress("EXPERIMENTAL_API_USAGE")
class MetadataDaoTest {
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    lateinit var db: LolDatabase
    lateinit var underTest: MetadataDao

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, LolDatabase::class.java)
                .setTransactionExecutor(testDispatcher.asExecutor())
                .setQueryExecutor(testDispatcher.asExecutor()).build()
        underTest = db.metadataDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insert_findAll() = testScope.runBlockingTest {
        // Arrange
        val metadata = Metadata(123456789, 123456789)

        // Act
        underTest.insert(metadata)
        val result = underTest.findAll()

        // Assert
        MatcherAssert.assertThat(result, Matchers.notNullValue())
        MatcherAssert.assertThat(result.isEmpty(), Matchers.`is`(false))
        MatcherAssert.assertThat(result[0], Matchers.`is`(metadata))
    }
}