package com.rubenquadros.epicnews.viewTests

import android.widget.TextView
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.rubenquadros.epicnews.R
import com.rubenquadros.epicnews.view.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private var mainActivity: MainActivity? = null
    private lateinit var toolbarTitle: TextView

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        mainActivity = activityTestRule.activity
        toolbarTitle = mainActivity!!.findViewById(R.id.toolbarTitle)
    }

    @Test
    fun activityTest() {
        assertEquals("HEADLINES", toolbarTitle.text.toString(), "Title is HEADLINES")
    }

    @After
    fun tearDown() {
        mainActivity = null
    }
}