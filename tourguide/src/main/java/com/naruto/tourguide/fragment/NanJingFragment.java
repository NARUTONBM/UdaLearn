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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naruto.tourguide.R;
import com.naruto.tourguide.adapter.InfoAdapter;
import com.naruto.tourguide.bean.Info;

import java.util.ArrayList;

public class NanJingFragment extends Fragment {

	public NanJingFragment() {
		// Required empty public constructor
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_attractions_info, container, false);

		// 创建景点信息数组
		ArrayList<Info> nanJingInfos = new ArrayList<>();
		// 添加多个条目的相关信息
		nanJingInfos.add(new Info(R.mipmap.ts_hot_springs, R.string.tangshanwenquan, R.string.tangshanwenquan_position));
		nanJingInfos.add(new Info(R.mipmap.dbes, R.string.dabaoensi, R.string.dabaoensi_position));
		nanJingInfos.add(new Info(R.mipmap.zhm, R.string.zhonghuamen, R.string.zhonghuamen_position));
		nanJingInfos.add(new Info(R.mipmap.qh_rive, R.string.qinhuairiver, R.string.qinhuairiver_position));
		nanJingInfos.add(new Info(R.mipmap.ztf, R.string.zongtongfu, R.string.zongtongfu_position));
		nanJingInfos.add(new Info(R.mipmap.yjl, R.string.yujianglou, R.string.yujianglou_position));
		nanJingInfos.add(new Info(R.mipmap.zsl, R.string.zhongshanling, R.string.zhongshanling_position));

		InfoAdapter adapter = new InfoAdapter(getActivity(), nanJingInfos, R.color.category_nanjing);
		RecyclerView rv_attractions_info = (RecyclerView) view.findViewById(R.id.rv_attractions_info);
		rv_attractions_info.setLayoutManager(new LinearLayoutManager(getActivity()));
		rv_attractions_info.setAdapter(adapter);

		return view;
	}
}