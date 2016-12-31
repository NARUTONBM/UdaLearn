package com.naruto.booklisting;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

	private Context mContext;
	private EditText et_input;
	private ImageButton ib_search;
	private ListView lv_results;
	private TextView tv_result_title;
	private ConnectivityManager mConnectivityManager;
	private ArrayList<BookDetail> mBookDetails;
	private static int clickTime = 0;
	private BookDetailAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;

		// 初始化数据
		initData();
		// 初始化UI
		initUI();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		mBookDetails = new ArrayList<>();
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		// 找到页面上的控件
		et_input = (EditText) findViewById(R.id.et_input);
		ib_search = (ImageButton) findViewById(R.id.ib_search);
		lv_results = (ListView) findViewById(R.id.lv_results);
		tv_result_title = (TextView) findViewById(R.id.tv_result_title);
	}

	/**
	 * 搜索图标的点击事件
	 * 
	 * @param view
	 *            触发点击事件的控件
	 */
	public void queryBooks(View view) {
		if (clickTime == 0) {
			if (mBookDetails.size() != 0) {
				mBookDetails.clear();
			}
			// 获取用户输入的搜索关键字
			String searchKeyWord = et_input.getText().toString();
			if (!searchKeyWord.isEmpty()) {
				// 设置结果抬头
				tv_result_title.setText(String.format("Results for %s", searchKeyWord));
				// 关键字非空，将空格转换为“+”
				if (searchKeyWord.contains(" ")) {
					searchKeyWord = searchKeyWord.replace(" ", "+");
				}
				// 创建请求
				GoogleBooksApiRequestTask task = new GoogleBooksApiRequestTask();
				task.execute(searchKeyWord);
			} else {
				// 提示用户，关键字不能为空
				ToastUtil.showLong(mContext, "请至少告诉我该找点什么吧~");
			}
		} else {
			ToastUtil.showShort(mContext, "稍安勿躁，正在拼命查询中~");
		}
	}

	private class GoogleBooksApiRequestTask extends AsyncTask<String, Integer, JSONObject> implements AdapterView.OnItemClickListener {
		/**
		 * onPreExecute方法用于在执行后台任务前做一些UI操作
		 */
		@Override
		protected void onPreExecute() {
			clickTime ++;
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
		 *            传入的关键字
		 * @return 查询到的信息
		 */
		@Override
		protected JSONObject doInBackground(String... params) {
			String apiUrlString = "https://www.googleapis.com/books/v1/volumes?q=" + Arrays.toString(params);
			try {
				HttpURLConnection connection = null;
				// 建立连接
				try {
					URL url = new URL(apiUrlString);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setReadTimeout(10000);
					connection.setConnectTimeout(10000);
				} catch (MalformedURLException | ProtocolException e) {
					e.printStackTrace();
				}
				int responseCode;
				JSONObject responseJson = null;
				if (connection != null) {
					responseCode = connection.getResponseCode();
					if (responseCode != 200) {
						Log.w(getClass().getName(), "GoogleBooksAPI request failed. Response Code: " + responseCode);
						connection.disconnect();
						return null;
					}
					// 从response中读取数据
					StringBuilder builder = new StringBuilder();
					BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String line = responseReader.readLine();
					while (line != null) {
						builder.append(line);
						line = responseReader.readLine();
					}
					String responseString = builder.toString();
					responseJson = new JSONObject(responseString);
					// 最后关闭连接
					connection.disconnect();
				}

				return responseJson;
			} catch (SocketTimeoutException e) {
				Log.w(getClass().getName(), "连接超时");

				return null;
			} catch (IOException e) {
				Log.d(getClass().getName(), "IO异常");
				e.printStackTrace();

				return null;
			} catch (JSONException e) {
				Log.d(getClass().getName(), "JSON异常");
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
		protected void onPostExecute(JSONObject result) {
			clickTime --;
			try {
				// 获取items
				JSONArray items = result.getJSONArray("items");
				// 遍历item
				for (int i = 0; i < items.length(); i ++) {
					// 获取当前条目
					JSONObject itemDetails = (JSONObject) items.get(i);
					// 获取当前条目中的volumeInfo
					JSONObject volumeInfo = itemDetails.getJSONObject("volumeInfo");
					// 获取当前条目的书籍标题
					String title = volumeInfo.getString("title");
					// 获取当前书籍的作者们
					String authorsStr;
					// 非空判断
					if (!volumeInfo.isNull("authors")) {
						// 不为空，遍历每一个作者
						JSONArray authors = volumeInfo.getJSONArray("authors");
						StringBuilder builder = new StringBuilder();
						for (int j = 0; j < authors.length(); j ++) {
							builder.append(authors.get(j));
							if (j != authors.length() - 1) {
								builder.append(" , ");
							}
						}
						// 拼接所有作者
						authorsStr = builder.toString();
					} else {
						// 为空，没有查询到作者
						authorsStr = getString(R.string.tv_authors_null);
					}
					// 获取当前条目的出版社
					String publisher;
					if (!volumeInfo.isNull("publisher")) {
						// 不为空，获取出版社
						publisher = volumeInfo.getString("publisher");
					} else {
						// 为空，没有查询到出版社
						publisher = getString(R.string.tv_publisher_null);
					}
					// 获取当前条目的出版时间
					String publishedDate;
					if (!volumeInfo.isNull("publishedDate")) {
						// 不为空,获取当前条目的出版时间
						publishedDate = volumeInfo.getString("publishedDate");
					} else {
						// 为空，未查询到出版时间
						publishedDate = getString(R.string.tv_publishedDate_null);
					}
					// 获取当前条目的简介
					String description;
					if (!volumeInfo.isNull("description")) {
						// 不为空,获取当前条目的简介
						description = volumeInfo.getString("description");
					} else {
						// 为空，未查询到简介
						description = getString(R.string.tv_description_null);
					}
					// 获取当前条目的图片的url地址
					String smallThumbnail;
					if (!volumeInfo.isNull("imageLinks")) {
						// 不为空，获取url地址
						JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
						smallThumbnail = imageLinks.getString("smallThumbnail");
					} else {
						smallThumbnail = "Unknown url";
					}
					// 添加一条bookdetail
					mBookDetails.add(new BookDetail(title, authorsStr, publisher, publishedDate, description, smallThumbnail));
				}
			} catch (JSONException e) {
				Log.d(getClass().getName(), getString(R.string.exception_json));
				e.printStackTrace();
			} catch (Exception e) {
				Log.d(getClass().getName(), getString(R.string.exception));
				e.printStackTrace();
			}
			// 创建适配器
			mAdapter = new BookDetailAdapter(mContext, mBookDetails, 0);
			// 给listview设置适配器
			lv_results.setAdapter(mAdapter);
			// 给listview设置点击事件
			lv_results.setOnItemClickListener(this);
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(mContext, BookDetailsActivity.class);
			BookDetail bookDetail = mBookDetails.get(position);
			intent.putExtra("smallThumbnail", bookDetail.getSmallThumbnail());
			intent.putExtra("title", bookDetail.getTitle());
			intent.putExtra("authors", bookDetail.getAuthors());
			intent.putExtra("publisher", bookDetail.getPublisher());
			intent.putExtra("publishDate", bookDetail.getPublishedDate());
			intent.putExtra("description", bookDetail.getDescription());
			startActivity(intent);
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
	protected void onDestroy() {
		super.onDestroy();
		if (clickTime != 0) {
			clickTime --;
		}
	}
}