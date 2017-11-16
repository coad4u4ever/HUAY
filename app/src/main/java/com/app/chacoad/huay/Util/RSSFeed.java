package com.app.chacoad.huay.Util;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by N on 11/12/2017.
 */

@Root(name = "rss", strict = false)
public class RSSFeed {
    @ElementList(name = "item", inline = true)
    @Path("channel")
    private List<Article> articleList;

    public List<Article> getArticleList() {
        return articleList;
    }

    public Article getFirstArticle() {
        return articleList.get(0);
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

}
