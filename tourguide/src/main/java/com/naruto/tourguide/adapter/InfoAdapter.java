package com.naruto.tourguide.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.naruto.tourguide.R;
import com.naruto.tourguide.bean.Info;

import java.util.ArrayList;

/*
 * Created with Android Studio.
 * User: narutonbm@gmail.com
 * Date: 2016-12-24
 * Time: 21:23
 * Desc: UdaLearn
 */

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoViewHolder> {

	private int mColorResourceId;
	private ArrayList<Info> mInfos;
	private Context mContext;

	public InfoAdapter(Context context, ArrayList<Info> infos, int colorResourceId) {
		mContext = context;
		mInfos = infos;
		mColorResourceId = colorResourceId;
	}

	@Override
	public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// Inflate the view for this view holder
		View view = LayoutInflater.from(mContext).inflate(R.layout.item_info, parent, false);
		// Call the view holder's constructor, and pass the view to it;
		// return that new view holder
		return new InfoViewHolder(view);
	}

	@Override
	public void onBindViewHolder(InfoViewHolder holder, int position) {
		Info info = mInfos.get(position);
		holder.iv_picture.setImageResource(info.getImageResourceId());
		holder.tv_title.setText(info.getTitleId());
		holder.tv_position.setText(info.getPositionId());
		holder.ll_text_container.setBackgroundColor(mColorResourceId);
	}

	@Override
	public int getItemCount() {
		return mInfos.size();
	}

	static class InfoViewHolder extends RecyclerView.ViewHolder {
		ImageView iv_picture;
		TextView tv_title;
		TextView tv_position;
		LinearLayout ll_text_container;

		InfoViewHolder(View view) {
			super(view);
			iv_picture = (ImageView) view.findViewById(R.id.iv_picture);
			tv_title = (TextView) view.findViewById(R.id.tv_title);
			tv_position = (TextView) view.findViewById(R.id.tv_position);
			ll_text_container = (LinearLayout) view.findViewById(R.id.ll_text_container);
		}
	}
}
