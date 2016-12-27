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

public class ShangHaiFragment extends Fragment {

	public ShangHaiFragment() {
		// Required empty public constructor
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_attractions_info, container, false);

		// 创建景点信息数组
		ArrayList<Info> shangHaiInfos = new ArrayList<>();
		shangHaiInfos.add(new Info(R.mipmap.dfmz, R.string.dongfangmingzhu, R.string.dongfangmingzhu_position));
		shangHaiInfos.add(new Info(R.mipmap.the_bund, R.string.waitan, R.string.waitan_position));
		shangHaiInfos.add(new Info(R.mipmap.nrps, R.string.nanjinglubuxingjie, R.string.nanjinglubuxingjie_position));
		shangHaiInfos.add(new Info(R.mipmap.chm, R.string.chenghuangmiao, R.string.chenghuangmiao_position));
		shangHaiInfos.add(new Info(R.mipmap.zjj, R.string.zhujiajiao, R.string.zhujiajiao_position));
		shangHaiInfos.add(new Info(R.mipmap.cf_zoo, R.string.changfengzoo, R.string.changfengzoo_position));
		shangHaiInfos.add(new Info(R.mipmap.happy_valley, R.string.huanlegu, R.string.huanlegu_position));

		InfoAdapter adapter = new InfoAdapter(getActivity(), shangHaiInfos, R.color.category_shanghai);
		ListView listView = (ListView) view.findViewById(R.id.lv_attractions_info);
		listView.setAdapter(adapter);

		return view;
	}
}
