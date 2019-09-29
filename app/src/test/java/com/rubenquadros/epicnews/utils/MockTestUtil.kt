package com.rubenquadros.epicnews.utils

import com.rubenquadros.epicnews.data.local.entity.NewsEntity
import com.rubenquadros.epicnews.data.remote.model.Article
import com.rubenquadros.epicnews.data.remote.model.Source
import com.rubenquadros.epicnews.data.remote.model.TopNewsResponse

class MockTestUtil {

    companion object {
        fun newsResponse(): TopNewsResponse {
            return mockTopNewsResponse()
        }

        fun newsEntity(): ArrayList<NewsEntity> {
            return mockNewsEntity()
        }

        private fun mockTopNewsResponse(): TopNewsResponse {
            val topNewsResponse = TopNewsResponse()
            topNewsResponse.status = "ok"
            topNewsResponse.totalResults = 2
            topNewsResponse.articles = mockArticles()
            return topNewsResponse
        }

        private fun mockSource(id: String?, name: String): Source {
            val source = Source()
            source.id = id
            source.name = name
            return source
        }

        private fun mockArticles(): List<Article> {
            val article = ArrayList<Article>()

            val article1 = Article()
            article1.source = mockSource(null, "News18.com")
            article1.author = "News18.com"
            article1.title = "Chidambaram Avoids ED Arrest Till Tomorrow But Fails to Evade CBI as Delhi Court Extends Custody by 5 Day... - News18"
            article1.description = "The CBI sought extension of Chidambaram's custody by four days to confront him with certain e-mails and to unearth larger conspiracy."
            article1.url = "https://www.news18.com/news/india/chidambaram-case-live-updates-latest-news-cbi-arrest-inx-media-property-2283873.html"
            article1.urlToImage = "https://images.news18.com/ibnlive/uploads/2019/08/Chiddu.jpg"
            article1.publishedAt = "2019-08-26T14:34:00Z"
            article1.content = "New Delhi: In a major setback to Congress leader and former finance minister P Chidambaram, the Supreme Court on Monday rejected his petition against the Delhi High Court verdict which had dismissed his anticipatory bail plea in a corruption case lodged by th… [+5696 chars]"
            article.add(article1)

            val article2 = Article()
            article2.source = mockSource("the-times-of-india", "The Times of India")
            article2.author = "Times Of India"
            article2.title = "Prepared to thwart JeM's underwater plans: Indian Navy - Times of India"
            article2.description = "India News: PUNE: An \\\"underwater wing\\\" of Pakistan- based terror group Jaish-e-Mohammed (JeM) is training people to carry out attacks but the Indian Navy is fully."
            article2.url = "https://timesofindia.indiatimes.com/india/prepared-to-thwart-jems-underwater-plans-indian-navy/articleshow/70844700.cms"
            article2.urlToImage = "https://static.toiimg.com/thumb/msid-70844880,width-1070,height-580,imgsize-115591,resizemode-6,overlay-toi_sw,pt-32,y_pad-40/photo.jpg"
            article2.publishedAt = "2019-08-26T13:46:48Z"
            article2.content = "Copyright © 2019 Bennett, Coleman &amp; Co. Ltd. All rights reserved. For reprint rights: Times Syndication Service"

            article.add(article2)

            return article
        }

        private fun mockNewsEntity(): ArrayList<NewsEntity> {
            val newsEntities = ArrayList<NewsEntity>()

            val newsEntity1 = NewsEntity(1,"Prepared to thwart JeM's underwater plans: Indian Navy - Times of India",
                "New Delhi: In a major setback to Congress leader and former finance minister P Chidambaram, the Supreme Court on Monday rejected his petition against the Delhi High Court verdict which had dismissed his anticipatory bail plea in a corruption case lodged by th… [+5696 chars]",
                "https://images.news18.com/ibnlive/uploads/2019/08/Chiddu.jpg", "2019-08-26T14:34:00Z", "News18.com","")

            newsEntities.add(newsEntity1)

            val newsEntity2 = NewsEntity(2, "Prepared to thwart JeM's underwater plans: Indian Navy - Times of India",
                "Copyright © 2019 Bennett, Coleman &amp; Co. Ltd. All rights reserved. For reprint rights: Times Syndication Service",
                "https://static.toiimg.com/thumb/msid-70844880,width-1070,height-580,imgsize-115591,resizemode-6,overlay-toi_sw,pt-32,y_pad-40/photo.jpg",
                "2019-08-26T13:46:48Z", "Times Of India","")

            newsEntities.add(newsEntity2)

            return newsEntities
        }
    }
}