package com.rubenquadros.epicnews.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.rubenquadros.epicnews.R
import com.rubenquadros.epicnews.adapter.NewsAdapter
import com.rubenquadros.epicnews.base.BaseActivity
import com.rubenquadros.epicnews.callback.ViewCallback
import com.rubenquadros.epicnews.data.local.entity.NewsEntity
import com.rubenquadros.epicnews.data.remote.model.Article
import com.rubenquadros.epicnews.utils.ApplicationConstants
import com.rubenquadros.epicnews.utils.ApplicationUtility
import com.rubenquadros.epicnews.viewmodel.AllNewsViewModel


@Suppress("SameParameterValue", "DEPRECATION", "ControlFlowWithEmptyBody")
class MainActivity : BaseActivity(), ViewCallback {

    private lateinit var newsViewModel: AllNewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var newsData: ArrayList<Article> = ArrayList()

    @BindView(R.id.root) lateinit var rootLayout: ConstraintLayout
    @BindView(R.id.recyclerView) lateinit var mRecyclerView: RecyclerView
    @BindView(R.id.progressBar) lateinit var mProgressBar: ProgressBar
    @BindView(R.id.toolbar) lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        configureDesign()
    }

    private fun configureDesign() {
        this.setupViewModel()
        this.checkInternet()
        this.setupToolbar()
        this.setupNavBar()
        this.setupRecView()
        this.observeData()
        this.fetchNews()
    }

    private fun setupViewModel() {
        newsViewModel = ViewModelProviders.of(this, viewModelFactory)[AllNewsViewModel::class.java]
    }

    private fun checkInternet() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI
            ).state == NetworkInfo.State.CONNECTED
        ) {
            //we are connected to a network
        } else {
            ApplicationUtility.showSnack(getString(R.string.no_internet), rootLayout, getString(R.string.ok))
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun setupNavBar() {
        val baseMoviesPadding = pxFromDp(16f)
        val toolbarHeight = toolbar.layoutParams.height
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rootLayout.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or SYSTEM_UI_FLAG_LAYOUT_STABLE or SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        } else {
            mRecyclerView.updatePadding(top = toolbarHeight + baseMoviesPadding)
        }

        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { _, insets ->
            toolbar.setMarginTop(insets.systemWindowInsetTop)
            mRecyclerView.updatePadding(top = toolbarHeight + baseMoviesPadding)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(mRecyclerView) { _, insets ->
            mRecyclerView.updatePadding(bottom = 0)
            insets
        }
    }

    private fun setupRecView() {
        val layoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = layoutManager
    }

    private fun observeData() {
        this.newsViewModel.getNewsResponse().observe(this, Observer { it?.let { updateUI(it) } })
        this.newsViewModel.isLoading.observe(this, Observer { it?.let { shouldShowProgress(it) } })
        this.newsViewModel.getErrorResponse().observe(this, Observer { it?.let { this.fetchNewsFromDB() } })
    }

    private fun fetchNews() {
        this.newsViewModel.getNews()
    }

    private fun fetchNewsFromDB() {
        this.newsViewModel.initLocalData()
        this.newsViewModel.getNewsResponseFromDB().observe(this,
            Observer<List<NewsEntity>> { t ->
                if(t!=null && t.isNotEmpty()) {
                    updateUIFromLocalData(t)
                }else {
                    // No data available
                    shouldShowProgress(false)
                }
            })
    }

    private fun updateUI(response: List<Article>?) {
        shouldShowProgress(false)
        this.newsViewModel.deleteNews()
        if(response!=null) {
            this.newsViewModel.saveNews(response)
        }
        newsAdapter = NewsAdapter(response,this)
        mRecyclerView.adapter = newsAdapter
    }

    private fun updateUIFromLocalData(savedNews: List<NewsEntity>) {
        shouldShowProgress(false)
        for (i in savedNews.indices) {
            val article = Article()
            article.title = savedNews[i].title
            article.urlToImage = savedNews[i].imageURL
            article.publishedAt = savedNews[i].createdOn
            article.content = savedNews[i].content
            article.author = savedNews[i].author
            article.source?.name = savedNews[i].source
            newsData.add(article)
        }
        newsAdapter = NewsAdapter(newsData,this)
        mRecyclerView.adapter = newsAdapter
    }

    private fun shouldShowProgress(isFetching: Boolean) {
        mProgressBar.indeterminateDrawable.setColorFilter(resources.getColor(R.color.colorWhite), android.graphics.PorterDuff.Mode.MULTIPLY)
        if(isFetching) {
            mProgressBar.visibility = VISIBLE
        }else {
            mProgressBar.visibility = GONE
        }
    }

    private fun View.setMarginTop(value: Int) = updateLayoutParams<ViewGroup.MarginLayoutParams> {
        topMargin = value
    }

    private fun pxFromDp(dp: Float): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    override fun onActionPerformed(action: String, data: NewsEntity?) {
        val intent = Intent(this, NewsDetailActivity::class.java)
        if (data != null) {
            intent.putExtra(ApplicationConstants.TITLE, data.title)
            intent.putExtra(ApplicationConstants.CONTENT, data.content)
            intent.putExtra(ApplicationConstants.DATE, data.createdOn)
            intent.putExtra(ApplicationConstants.IMAGE, data.imageURL)
            intent.putExtra(ApplicationConstants.SOURCE, data.source)
        }
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
