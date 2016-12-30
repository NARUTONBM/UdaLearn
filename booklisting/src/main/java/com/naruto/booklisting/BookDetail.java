package com.naruto.booklisting;
/*
 * Created with Android Studio.
 * User: narutonbm@gmail.com
 * Date: 2016-12-30
 * Time: 17:15
 * Desc: UdaLearn
 */

import android.graphics.Bitmap;

public class BookDetail {

	private String mTitle;
	private String mAuthors;
	private String mThumbnail;
	private Bitmap mBitmap;

	public BookDetail(String title, String authors, String thumbnail) {
		mTitle = title;
		mAuthors = authors;
		mThumbnail = thumbnail;
	}

	public void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public String getTitle() {
		return mTitle;
	}

	public String getAuthors() {
		return mAuthors;
	}

	public String getThumbnail() {
		return mThumbnail;
	}

}
