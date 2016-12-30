package com.naruto.booklisting;
/*
 * Created with Android Studio.
 * User: narutonbm@gmail.com
 * Date: 2016-12-30
 * Time: 17:15
 * Desc: UdaLearn
 */

public class BookDetail {

	private final String mTitle;
	private final String mAuthors;
	private final String mPublisher;
	private final String mPublishedDate;
	private final String mDescription;
	private final String mSmallThumbnail;

	public BookDetail(String title, String authorsStr, String publisher, String publishedDate, String description, String smallThumbnail) {
		mTitle = title;
		mAuthors = authorsStr;
		mPublisher = publisher;
		mPublishedDate = publishedDate;
		mDescription = description;
		mSmallThumbnail = smallThumbnail;
	}

	public String getTitle() {
		return mTitle;
	}

	public String getAuthors() {
		return mAuthors;
	}

	public String getPublisher() {
		return mPublisher;
	}

	public String getPublishedDate() {
		return mPublishedDate;
	}

	public String getDescription() {
		return mDescription;
	}

	public String getSmallThumbnail() {
		return mSmallThumbnail;
	}
}