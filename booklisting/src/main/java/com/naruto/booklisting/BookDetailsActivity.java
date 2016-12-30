package com.naruto.booklisting;
/*
 * Created with Android Studio.
 * User: narutonbm@gmail.com
 * Date: 2016-12-30
 * Time: 21:56
 * Desc: UdaLearn
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class BookDetailsActivity extends AppCompatActivity {

	private Context mContext;
	private String mTitle;
	private String mSmallThumbnail;
	private String mAuthors;
	private String mPublisher;
	private String mPublishDate;
	private String mDescription;
	private ConnectivityManager mConnectivityManager;
	private ImageView iv_book_detail_image;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookdetails);

		mContext = this;
		// 初始化数据
		initData();
		// 初始化UI
		initUI();
	}

	private void initData() {
		mTitle = getIntent().getStringExtra("title");
		mSmallThumbnail = getIntent().getStringExtra("smallThumbnail");
		mAuthors = getIntent().getStringExtra("authors");
		mPublisher = getIntent().getStringExtra("publisher");
		mPublishDate = getIntent().getStringExtra("publishDate");
		mDescription = getIntent().getStringExtra("description");
	}

	private void initUI() {
		getSupportActionBar().setTitle("Details of " + mTitle);
		iv_book_detail_image = (ImageView) findViewById(R.id.iv_book_detail_image);
		TextView tv_book_detail_title = (TextView) findViewById(R.id.tv_book_detail_title);
		tv_book_detail_title.setText(mTitle);
		TextView tv_book_detail_authors = (TextView) findViewById(R.id.tv_book_detail_authors);
		tv_book_detail_authors.setText(mAuthors);
		TextView tv_book_detail_publish = (TextView) findViewById(R.id.tv_book_detail_publish);
		tv_book_detail_publish.setText(String.format("Published by %s in %s", mPublisher, mPublishDate));
		TextView tv_book_detail_description = (TextView) findViewById(R.id.tv_book_detail_description);
		if (mDescription.equals("Unkonwn description")) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
				tv_book_detail_description.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
			}
		}
		tv_book_detail_description.setText(String.format("  %s", mDescription));
		ImageRequestTask imageRequestTask = new ImageRequestTask();
		imageRequestTask.execute(mSmallThumbnail);
	}

	private class ImageRequestTask extends AsyncTask<String, Integer, Bitmap> {
		/**
		 * onPreExecute方法用于在执行后台任务前做一些UI操作
		 */
		@Override
		protected void onPreExecute() {
			// 检查网络连接
			if (!isNetworkConnected()) {
				// 取消请求
				Log.i(getClass().getName(), "Not connected to the internet");
				cancel(true);
			}
		}

		/**
		 * doInBackground方法内部执行后台任务,不可在此方法内修改UI
		 *
		 * @param params
		 *            传入的url地址
		 * @return 查询到的信息
		 */
		@Override
		protected Bitmap doInBackground(String... params) {
			try {
				HttpURLConnection connection = null;
				// 建立连接
				try {
					URL url = new URL(params[0]);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(5000);
					connection.setDoInput(true);
				} catch (MalformedURLException | ProtocolException e) {
					e.printStackTrace();
				}
				int responseCode;
				Bitmap bitmap = null;
				if (connection != null) {
					responseCode = connection.getResponseCode();
					if (responseCode != 200) {
						Log.w(getClass().getName(), "Getting image request failed. Response Code: " + responseCode);
						connection.disconnect();
						return null;
					}
					// 获取服务器返回回来的流
					InputStream is = connection.getInputStream();
					bitmap = BitmapFactory.decodeStream(is);
					// 最后关闭连接
					connection.disconnect();
				}

				return bitmap;
			} catch (SocketTimeoutException e) {
				Log.w(getClass().getName(), "连接超时");

				return null;
			} catch (IOException e) {
				Log.d(getClass().getName(), "IO异常");
				e.printStackTrace();

				return null;
			}
		}

		/**
		 * onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		 *
		 * @param result
		 *            doinbackground查询到的结果
		 */
		@Override
		protected void onPostExecute(Bitmap result) {
			iv_book_detail_image.setImageBitmap(result);
		}
	}

	/**
	 * 检测设备当前是否联网
	 *
	 * @return 处于网络下，返回true；无网络，返回false
	 */
	private boolean isNetworkConnected() {
		if (mConnectivityManager == null) {
			mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		// 检测设备当前是否联网
		NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();

		return networkInfo != null && networkInfo.isConnected();
	}
}