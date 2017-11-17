package com.app.chacoad.huay.Util;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by N on 11/12/2017.
 */

@Root(name = "item", strict = false)
public class Article {

    private static final String regex = "\\d{6}";
    private static final Pattern pattern = Pattern.compile(regex);
    private Long firstPrize;

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

    public Long getFirstPrize() {
        Matcher matcher = pattern.matcher(description);
        if (matcher.find()) {
            firstPrize = Long.parseLong(matcher.group(0));
        }
        return firstPrize;
    }
}
