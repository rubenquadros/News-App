package com.rubenquadros.epicnews.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.rubenquadros.epicnews.base.BaseTest
import com.rubenquadros.epicnews.data.local.dao.NewsDAO
import com.rubenquadros.epicnews.data.local.entity.NewsEntity
import com.rubenquadros.epicnews.utils.MockTestUtil
import com.rubenquadros.epicnews.viewmodel.AllNewsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.net.HttpURLConnection
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@Suppress("DEPRECATION")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class AllNewsTest: BaseTest() {

    private lateinit var activity: FragmentActivity
    private lateinit var newsViewModel: AllNewsViewModel
    private lateinit var newsDAO: NewsDAO

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setup() {
        super.setup()
        this.activity = Robolectric.setupActivity(FragmentActivity::class.java)
        this.newsViewModel = ViewModelProviders.of(this.activity, viewModelFactory)[AllNewsViewModel::class.java]
        this.newsDAO = newsDatabase.newsDAO()
    }

    @Test
    fun loadNewsSuccess() {
        this.mockResponse("getSuccessResponse.json", HttpURLConnection.HTTP_OK)
        assertEquals(null, this.newsViewModel.response.value, "Response should be null because stream is not started yet")
        this.newsViewModel.getNews()
        assertEquals(MockTestUtil.newsResponse().articles?.get(0)?.author, this.newsViewModel.response.value?.get(0)?.author, "Response must be fetched")
        assertEquals(false, this.newsViewModel.isLoading.value, "Should be reset to 'false' because stream ended")
        assertEquals(null, this.newsViewModel.error.value, "No error must be founded")
    }

    @Test
    fun loadNewsFail() {
        this.mockResponse("getSuccessResponse.json", HttpURLConnection.HTTP_BAD_GATEWAY)
        assertEquals(null, this.newsViewModel.response.value, "Response should be null because stream is not started yet")
        this.newsViewModel.getNews()
        assertEquals(null, this.newsViewModel.response.value, "Response must be null because of HTTP error")
        assertEquals(false, this.newsViewModel.isLoading.value, "Should be reset to 'false' because stream ended")
        assertNotEquals(null, this.newsViewModel.error.value, "Error value must not be empty")
    }

    @Test
    fun insertAndRetrieveNewsSuccess() {
        newsDAO.insertAll(MockTestUtil.newsEntity())
        val newsTest = getValue(newsDAO.getAllNews())
        assertEquals(MockTestUtil.newsEntity()[0].title, newsTest.title, "Data should be successfully inserted and retrieved")
    }

    @Test
    fun deleteNewsSuccess() {
        newsDAO.deleteAll()
        assertEquals(0, newsDAO.getAllNews().size, "Data should be successfully deleted")
    }

    private fun getValue(allNews: List<NewsEntity>): NewsEntity {
        return allNews[0]
    }

    override fun isMockServerEnabled(): Boolean = true
}