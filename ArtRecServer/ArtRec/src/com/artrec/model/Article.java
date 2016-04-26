package com.artrec.model;

import java.net.URL;

public class Article {
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
    private String title;
    private URL link;
    private String identifier;
    private String creator;
    private int volume;
    private int number;
    private int startingPage;
    private int endingPage;
    private java.sql.Date publicationDate;
    private String journalIssn;

    public String getJournalIssn() {
		return journalIssn;
	}

	public void setJournalIssn(String journalIssn) {
		this.journalIssn = journalIssn;
	}

	public Article(String title, URL link, String identifier, String creator, int volume, int number, int startingPage, int endingPage, java.sql.Date publicationDate, String publisher) {
        this.title = title;
        this.link = link;
        this.identifier = identifier;
        this.creator = creator;
        this.volume = volume;
        this.number = number;
        this.startingPage = startingPage;
        this.endingPage = endingPage;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
    }

    public Article(String title, URL link, java.sql.Date publicationDate) {
        this.title = title;
        this.link = link;
        this.publicationDate = publicationDate;
    }

    public Article(int id, String description, String title, URL link, java.sql.Date publicationDate) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.link = link;
        this.publicationDate = publicationDate;
    }

    public Article(String title, URL link, java.sql.Date publicationDate, String journalIssn, String description) {
        this.description = description;
        this.journalIssn = journalIssn;
        this.title = title;
        this.link = link;
        this.publicationDate = publicationDate;
	}

	public String getPublisher() {

        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public java.sql.Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(java.sql.Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getEndingPage() {
        return endingPage;
    }

    public void setEndingPage(int endingPage) {
        this.endingPage = endingPage;
    }

    public int getStartingPage() {
        return startingPage;
    }

    public void setStartingPage(int startingPage) {
        this.startingPage = startingPage;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String publisher;
}
