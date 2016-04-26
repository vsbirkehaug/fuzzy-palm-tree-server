package com.artrec.model;

/**
 * Created by Vilde on 23.04.2016.
 */
public class Journal {

    private String issn;
    private String title;
    private String url;
    private String publisher;
    private String rights;

    public Journal(String issn, String title, String url,String publisher, String rights) {
        this.issn = issn;
        this.title = title;
        this.url = url;
        this.publisher = publisher;
        this.rights = rights;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }
}
