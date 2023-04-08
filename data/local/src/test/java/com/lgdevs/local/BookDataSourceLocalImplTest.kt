package com.lgdevs.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lgdevs.local.dao.BookDao
import com.lgdevs.local.dao.BookDatabase
import com.lgdevs.local.datasource.BookDataSourceLocalImpl
import com.lgdevs.local.mapper.BookEntityMapper
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceLocal
import com.lgdevs.mynextbook.repository.model.BookData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.random.Random

@RunWith(RobolectricTestRunner::class)
class BookDataSourceLocalImplTest {

    private lateinit var db: BookDatabase
    private lateinit var dataSource: BookDataSourceLocal
    private val mapper: BookEntityMapper by lazy { BookEntityMapper() }
    private val userId = Random.nextInt(99).toString()
    @Before
    public fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, BookDatabase::class.java).build()
        dataSource = BookDataSourceLocalImpl(db.dao, mapper)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun whenSaveBookInFavorites_shouldSaveInLocalDatabase() = runTest {
        val book = BookData(Random.nextInt().toString())
        dataSource.setFavoriteBook(book, userId).collectLatest {
            val list = dataSource.getFavoritesBooks(userId).toList().last()
            assert(list.isNotEmpty())
        }
    }

    @Test
    fun whenDeleteBookInFavorites_shouldAlsoDeleteInLocalDatabase() = runTest {
        val book = BookData(Random.nextInt().toString())
        dataSource.setFavoriteBook(book, userId).collectLatest {
            dataSource.removeFavoriteBook(book).collectLatest {
                val list = dataSource.getFavoritesBooks(userId).toList().last()
                assert(list.isEmpty())
            }
        }
    }

    @Test
    fun whenLocalDatabaseIsEmpty_shouldReturnEmptyState() = runTest {
        val list = dataSource.getFavoritesBooks(userId).toList().last()
        assert(list.isEmpty())
    }
}