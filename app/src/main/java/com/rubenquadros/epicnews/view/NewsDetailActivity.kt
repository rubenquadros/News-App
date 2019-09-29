package com.rubenquadros.epicnews.view

import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.rubenquadros.epicnews.R
import com.rubenquadros.epicnews.utils.ApplicationConstants
import com.rubenquadros.epicnews.utils.ApplicationUtility
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.lang.Exception


class NewsDetailActivity : AppCompatActivity() {

    @BindView(R.id.parent) lateinit var rootLayout: ConstraintLayout
    @BindView(R.id.imageView) lateinit var mImageView: ImageView
    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.titleTv) lateinit var titleTv: TextView
    @BindView(R.id.sourceTv) lateinit var sourceTv: TextView
    @BindView(R.id.dateTv) lateinit var dateTv: TextView
    @BindView(R.id.descriptionTv) lateinit var contentTv: TextView

    private var image = ""
    private var title = ""
    private var content = ""
    private var date = ""
    private var source = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        ButterKnife.bind(this)
        configureDesign()
    }

    private fun configureDesign() {
        this.setupStatusBar()
        this.setupToolbar()
        this.getData()
        this.setupLayout()
    }

    private fun setupStatusBar() {
        rootLayout.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
    }

    private fun getData() {
        if(intent != null) {
            image = intent.getStringExtra(ApplicationConstants.IMAGE)
            title = intent.getStringExtra(ApplicationConstants.TITLE)
            source = intent.getStringExtra(ApplicationConstants.SOURCE)
            date = intent.getStringExtra(ApplicationConstants.DATE)
            content = if(intent.getStringExtra(ApplicationConstants.CONTENT) == null){
                intent.getStringExtra(ApplicationConstants.TITLE)
            }else {
                intent.getStringExtra(ApplicationConstants.CONTENT)
            }
        }
    }

    private fun setupLayout() {
        Picasso.get().load(image)
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .fit().centerCrop()
            .placeholder(R.color.colorPrimaryDark)
            .into(mImageView, object : Callback {
                override fun onSuccess() {
                    // Successfully loaded image from the server
                }

                override fun onError(e: Exception?) {
                    Picasso.get().load(image)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .fit().centerCrop()
                        .placeholder(R.color.colorPrimaryDark)
                        .into(mImageView, object : Callback {
                            override fun onSuccess() {
                                // Successfully loaded image from cache
                            }

                            override fun onError(e: Exception?) {
                                // No images in local db/no cache data available
                            }
                        })
                }
            })

        titleTv.text = title
        sourceTv.text = source
        dateTv.text = ApplicationUtility.formatDate(date)
        contentTv.text = content
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
