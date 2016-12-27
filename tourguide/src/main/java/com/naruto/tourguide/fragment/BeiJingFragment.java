package com.naruto.tourguide.fragment;
/*
 * Created with Android Studio.
 * User: narutonbm@gmail.com
 * Date: 2016-12-24
 * Time: 20:50
 * Desc: UdaLearn
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.naruto.tourguide.R;
import com.naruto.tourguide.adapter.InfoAdapter;
import com.naruto.tourguide.bean.Info;

import java.util.ArrayList;

public class BeiJingFragment extends Fragment {

	public BeiJingFragment() {
		// Required empty public constructor
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_attractions_info, container, false);

		// 创建景点信息数组
		ArrayList<Info> beiJingInfos = new ArrayList<>();
		beiJingInfos.add(new Info(R.mipmap.tam_square, R.string.tiananmen, R.string.tiananmen_position));
		beiJingInfos.add(new Info(R.mipmap.gjdjy, R.string.guojiadajuyuan, R.string.guojiadajuyuan_position));
		beiJingInfos.add(new Info(R.mipmap.ghotp, R.string.renmindahuitang, R.string.renmindahuitang_position));
		beiJingInfos.add(new Info(R.mipmap.forbidden_city, R.string.gugong, R.string.gugong_position));
		beiJingInfos.add(new Info(R.mipmap.wfj, R.string.wangfujing, R.string.wangfujing_position));
		beiJingInfos.add(new Info(R.mipmap.birds_nest, R.string.niaochao, R.string.niaochao_position));
		beiJingInfos.add(new Info(R.mipmap.yhy, R.string.yiheyuan, R.string.yiheyuan_position));
		beiJingInfos.add(new Info(R.mipmap.ymy, R.string.yuanmingyuan, R.string.yuanmingyuan_position));

		InfoAdapter adapter = new InfoAdapter(getActivity(), beiJingInfos, R.color.category_beijing);
		ListView listView = (ListView) view.findViewById(R.id.lv_attractions_info);
		listView.setAdapter(adapter);

		return view;
	}
}
