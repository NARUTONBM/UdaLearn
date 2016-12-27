package com.naruto.tourguide.bean;
/*
 * Created with Android Studio.
 * User: narutonbm@gmail.com
 * Date: 2016-12-24
 * Time: 21:16
 * Desc: UdaLearn
 */

public class Info {

	private int mImageResourceId;
	private int mTitleId;
	private int mPositionId;

	public Info(int imageResourceId, int titleId, int positionId) {
		mImageResourceId = imageResourceId;
		mTitleId = titleId;
		mPositionId = positionId;
	}

	public int getImageResourceId() {
		return mImageResourceId;
	}

	public int getTitleId() {
		return mTitleId;
	}

	public int getPositionId() {
		return mPositionId;
	}
}
