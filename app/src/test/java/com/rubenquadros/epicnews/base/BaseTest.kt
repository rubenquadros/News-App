package com.rubenquadros.epicnews.base

import androidx.fragment.app.FragmentActivity
import com.rubenquadros.epicnews.data.local.database.NewsDatabase
import com.rubenquadros.epicnews.di.*
import com.rubenquadros.epicnews.di.module.RepositoryModule
import com.rubenquadros.epicnews.factory.ViewModelFactory
import com.rubenquadros.epicnews.utils.ApplicationConstants
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.robolectric.Robolectric
import java.io.File
import javax.inject.Inject

abstract class BaseTest {

    lateinit var mockServer: MockWebServer
    lateinit var appComponentTest: AppComponentTest
    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var newsDatabase: NewsDatabase

    @Before
    open fun setup() {
        this.configureWebServer()
        this.configureDi()
    }

    @After
    fun tearDown() {
        this.stopMockServer()
    }

    abstract fun isMockServerEnabled(): Boolean

    open fun configureWebServer() {
        if(isMockServerEnabled()) {
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    open fun configureDi() {
        this.appComponentTest = DaggerAppComponentTest.builder()
            .apiModuleTest(ApiModuleTest(if (isMockServerEnabled()) mockServer.url("/").toString() else ApplicationConstants.BASE_URL))
            .dbModuleTest(DbModuleTest(Robolectric.setupActivity(FragmentActivity::class.java).application))
            .repositoryModule(RepositoryModule())
            .rxJavaModuleTest(RxJavaModuleTest())
            .build()
        this.appComponentTest.inject(this)
    }

    open fun stopMockServer() {
        if(isMockServerEnabled()) {
            mockServer.shutdown()
        }
    }

    open fun mockResponse(fileName: String, responseCode: Int) = mockServer.enqueue(MockResponse()
        .setResponseCode(responseCode)
        .setBody(getJson(fileName)))

    private fun getJson(path: String) : String {
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}