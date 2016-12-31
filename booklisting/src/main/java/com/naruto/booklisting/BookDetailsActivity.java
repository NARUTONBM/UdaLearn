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
import android.view.MenuItem;
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

		// 初始化数据
		initData();
		// 初始化UI
		initUI();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 获取从mainactivity传递过来的数据
		mTitle = getIntent().getStringExtra("title");
		mSmallThumbnail = getIntent().getStringExtra("smallThumbnail");
		mAuthors = getIntent().getStringExtra("authors");
		mPublisher = getIntent().getStringExtra("publisher");
		mPublishDate = getIntent().getStringExtra("publishDate");
		mDescription = getIntent().getStringExtra("description");
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// 设置标题
		getSupportActionBar().setTitle("Details of " + mTitle);
		// 找到控件，并设置相应的内容
		iv_book_detail_image = (ImageView) findViewById(R.id.iv_book_detail_image);
		TextView tv_book_detail_title = (TextView) findViewById(R.id.tv_book_detail_title);
		tv_book_detail_title.setText(mTitle);
		TextView tv_book_detail_authors = (TextView) findViewById(R.id.tv_book_detail_authors);
		tv_book_detail_authors.setText(mAuthors);
		TextView tv_book_detail_publish = (TextView) findViewById(R.id.tv_book_detail_publish);
		tv_book_detail_publish.setText(String.format("Published by %s in %s", mPublisher, mPublishDate));
		TextView tv_book_detail_description = (TextView) findViewById(R.id.tv_book_detail_description);
		// 对描述非空判断
		if (mDescription.equals("Unknown description")) {
			// 为空则居中显示
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
				tv_book_detail_description.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
			}
		}
		tv_book_detail_description.setText(String.format("  %s", mDescription));
		// 对图片url地址做非空判断
		if (mSmallThumbnail.equals("Unknown url")) {
			// 为空则显示固定图片
			iv_book_detail_image.setImageResource(R.mipmap.image_fail);
		} else {
			// 不为空，则加载网络图片
			ImageRequestTask imageRequestTask = new ImageRequestTask();
			imageRequestTask.execute(mSmallThumbnail);
		}
	}

	/**
	 * 异步加载网络图片任务
	 */
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
			String urlStr = params[0].replace("http", "https");
			try {
				HttpURLConnection connection = null;
				// 建立连接
				try {
					URL url = new URL(urlStr);
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}

		return super.onOptionsItemSelected(item);
	}
}