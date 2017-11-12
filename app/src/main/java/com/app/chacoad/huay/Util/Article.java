package com.app.chacoad.huay.Util;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by N on 11/12/2017.
 */

@Root(name = "item", strict = false)
public class Article {

    @Element(name = "guid")
    private String guid;

    @Element(name = "pubDate")
    private String pubdate;

    @Element(name = "description")
    private String description;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormattedPubdate() {
        return pubdate.substring(0, 10).replace("-", "");
    }
}
