package com.naruto.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * Created with Android Studio.
 * User: narutonbm@gmail.com
 * Date: 2016-12-30
 * Time: 18:31
 * Desc: UdaLearn
 */

public class BookDetailAdapter extends ArrayAdapter<BookDetail> {

	private Context mContext;

	public BookDetailAdapter(Context context, ArrayList<BookDetail> bookDetails, int resource) {
		super(context, 0, bookDetails);
		mContext = context;
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 初始化viewholder
		ViewHolder holder;
		// view复用
		if (convertView == null) {
			// 初始化view
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_book_detail, parent, false);
			// 创建viewholder对象
			holder = new ViewHolder();
			// 找到条目中的每一个控件
			holder.tv_book_title = (TextView) convertView.findViewById(R.id.tv_book_title);
			holder.tv_book_authors = (TextView) convertView.findViewById(R.id.tv_book_authors);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 获取当前条目的数据
		BookDetail bookDetail = getItem(position);
		// 非空判断
		if (bookDetail != null) {
			// 给title设置标题
			holder.tv_book_title.setText(bookDetail.getTitle());
			// 给authors设置作者们
			holder.tv_book_authors.setText(bookDetail.getAuthors());
		}

		return convertView;
	}

	private static class ViewHolder {
		TextView tv_book_title;
		TextView tv_book_authors;
	}
}
