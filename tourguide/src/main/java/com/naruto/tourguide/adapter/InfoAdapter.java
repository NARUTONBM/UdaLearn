package com.naruto.tourguide.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.naruto.tourguide.R;
import com.naruto.tourguide.bean.Info;

import java.util.ArrayList;

import static com.naruto.tourguide.R.id.ll_text_container;

/*
 * Created with Android Studio.
 * User: narutonbm@gmail.com
 * Date: 2016-12-24
 * Time: 21:23
 * Desc: UdaLearn
 */

public class InfoAdapter extends ArrayAdapter<Info> {

	private int mColorResourceId;

	public InfoAdapter(Context context, ArrayList<Info> infos, int colorResourceId) {
		super(context, 0, infos);
		mColorResourceId = colorResourceId;
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, @NonNull ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_info, parent, false);
			// 创建viewholder对象
			holder = new ViewHolder();
			// 找到控件
			holder.iv_picture = (ImageView) convertView.findViewById(R.id.iv_picture);
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_position = (TextView) convertView.findViewById(R.id.tv_position);
			holder.ll_text_container = (LinearLayout) convertView.findViewById(ll_text_container);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 获当前条目的相关内容
		Info currentInfo = getItem(position);
		// 对对条目内容进行非空判断
		if (currentInfo != null) {
			// 给imageView设置图片
			holder.iv_picture.setImageResource(currentInfo.getImageResourceId());
			// 给title设置标题
			holder.tv_title.setText(currentInfo.getTitleId());
			// 给position设置地点
			holder.tv_position.setText(currentInfo.getPositionId());
			// 给条目设置背景色
			holder.ll_text_container.setBackgroundColor(ContextCompat.getColor(getContext(), mColorResourceId));
		}

		return convertView;
	}

	private static class ViewHolder {
		ImageView iv_picture;
		TextView tv_title;
		TextView tv_position;
		LinearLayout ll_text_container;
	}
}
