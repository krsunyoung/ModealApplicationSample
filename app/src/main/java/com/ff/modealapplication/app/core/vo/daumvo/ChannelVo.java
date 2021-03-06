package com.ff.modealapplication.app.core.vo.daumvo;

import java.util.List;

/**
 * Created by BIT on 2017-01-16.
 */

public class ChannelVo {
    private String totalCount;
    private String link;
    private String result;
    private String generator;
    private String pageCount;
    private String lastBuildDate;
    private List<DaumItemVo> item;
    private String title;
    private String description;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public List<DaumItemVo> getItem() {
        return item;
    }

    public void setItem(List<DaumItemVo> item) {
        this.item = item;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ChannelVo{" +
                "totalCount='" + totalCount + '\'' +
                ", link='" + link + '\'' +
                ", result='" + result + '\'' +
                ", generator='" + generator + '\'' +
                ", pageCount='" + pageCount + '\'' +
                ", lastBuildDate='" + lastBuildDate + '\'' +
                ", item=" + item +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}