package com.naruto.tourguide.fragment;
/*
 * Created with Android Studio.
 * User: narutonbm@gmail.com
 * Date: 2016-12-24
 * Time: 20:49
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

public class DongJingFragment extends Fragment {

	public DongJingFragment() {
		// Required empty public constructor
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_attractions_info, container, false);

		// 创建景点信息数组
		ArrayList<Info> dongJingInfos = new ArrayList<>();
		dongJingInfos.add(new Info(R.mipmap.dj_towel, R.string.dongjing_towel, R.string.dongjing_towel_position));
		dongJingInfos.add(new Info(R.mipmap.diversity_shopping, R.string.diversity_shopping, R.string.diversity_shopping_position));
		dongJingInfos.add(new Info(R.mipmap.disney_land, R.string.disneyland, R.string.disneyland_position));
		dongJingInfos.add(new Info(R.mipmap.gqj_museum, R.string.gongjijun_museum, R.string.gongjijun_museum_position));
		dongJingInfos.add(new Info(R.mipmap.mount_fuji, R.string.fushishan, R.string.fushishan_position));
		dongJingInfos.add(new Info(R.mipmap.gotemba_field, R.string.yudianchang, R.string.yudianchang_position));
		dongJingInfos.add(new Info(R.mipmap.zdy_shopping, R.string.zhudiyu_shopping, R.string.zhudiyu_shopping_position));

		InfoAdapter adapter = new InfoAdapter(getActivity(), dongJingInfos, R.color.category_dongjing);
		ListView listView = (ListView) view.findViewById(R.id.lv_attractions_info);
		listView.setAdapter(adapter);

		return view;
	}
}