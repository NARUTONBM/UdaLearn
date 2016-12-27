package com.naruto.reportcard;
/*
 * Created with Android Studio.
 * User: narutonbm@gmail.com
 * Date: 2016-12-22
 * Time: 15:16
 * Desc: UdaLearn
 */

import android.text.TextUtils;

public class ReportCard {

	private final int MIN_VALUE = 0;
	private final int MAX_VALUE = 100;
	private int mScore;
	private String mDegree;
	private String mSubject;

	public ReportCard(int score, String degree, String subject) {

		if (score < MIN_VALUE || score > MAX_VALUE || TextUtils.isEmpty(degree) || TextUtils.isEmpty(subject)) {

			throw new IllegalArgumentException("参数不合法");
		} else {

			mScore = score;
			mDegree = degree;
			mSubject = subject;
		}
	}

	public void setScore(int score) {

		if (score >= MIN_VALUE && score <= MAX_VALUE) {

			mScore = score;
		} else {
			throw new IllegalArgumentException("分数不合法");
		}
	}

	public int getScore() {

		return mScore;
	}

	public String getDegree() {

		return mDegree;
	}

	public String getSubject() {

		return mSubject;
	}

	@Override
	public String toString() {

		final StringBuffer sb = new StringBuffer("ReportCard{");
		sb.append("mScore=").append(mScore);
		sb.append(", mDegree='").append(mDegree).append('\'');
		sb.append(", mSubject='").append(mSubject).append('\'');
		sb.append('}');

		return sb.toString();
	}
}
