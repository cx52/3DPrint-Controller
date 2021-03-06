package com.realwork.reol.a3dprint_controller.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.realwork.reol.a3dprint_controller.R;
import com.realwork.reol.a3dprint_controller.entity.ModelInfoEntity;
import com.realwork.reol.a3dprint_controller.ui.Adapter.RecyclerAdapter;
import com.realwork.reol.a3dprint_controller.ui.ModelInfoAct;
import com.realwork.reol.a3dprint_controller.widgets.ModelInfoPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/** 主界面
 * Created by reol on 2017/4/6.
 */

public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    @BindView(R.id.srl_main)
    SwipeRefreshLayout srlMain;
    @BindView(R.id.recycler_main)
    RecyclerView recyclerView;

    List<ModelInfoEntity> list = new ArrayList<>();
    RecyclerAdapter adapter;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            List<ModelInfoEntity> list = new ArrayList<>(3);

            ModelInfoEntity entity = new ModelInfoEntity();
            ModelInfoEntity entity1 = new ModelInfoEntity();
            ModelInfoEntity entity2 = new ModelInfoEntity();

            entity.setId(0x0011);
            entity.setName("小鸡叉");
            entity.setDescription("暂无简介");
            entity.setImgUrl("http://image.3dhoo.com/NewsDescImages/20161220/161220_091206_54084131.jpg");
            entity.setSize("225 kb");
            entity.setStlUrl("002.stl");

            entity1.setId(0x0100);
            entity1.setName("南瓜头");
            entity1.setDescription("暂无简介");
            entity1.setImgUrl("http://image.3dhoo.com/NewsDescImages/20160513/20160513_000233_795_26914.jpg");
            entity1.setSize("381 kb");
            entity1.setStlUrl("008.stl");

            entity2.setId(0x0101);
            entity2.setName("喷火龙");
            entity2.setDescription("暂无简介");
            entity2.setImgUrl("http://image.3dhoo.com/NewsDescImages/20160429/20160429_144938_811_Charizardstl.jpg");
            entity2.setSize("1783 kb");
            entity2.setStlUrl("011.stl");


            list.add(0,entity);
            list.add(0,entity1);
            list.add(0,entity2);


            adapter.updateData(list);
            srlMain.setRefreshing(false);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ModelInfoEntity entity = new ModelInfoEntity();
        entity.setId(0x0000);
        entity.setName("小恐龙");
        entity.setDescription("一只可爱的小恐龙，像素风格，不会喷火，说不定有颈椎病");
        entity.setImgUrl("http://image.3dhoo.com/NewsDescImages/20160526/20160526_002614_775_gigazaurstl.jpg");
        entity.setSize("72 kb");
        entity.setStlUrl("005.stl");

        list.add(entity);

        ModelInfoEntity entity1 = new ModelInfoEntity();

        entity1.setId(0x0001);
        entity1.setName("小椅子");
        entity1.setDescription("暂无简介");
        entity1.setImgUrl("http://image.3dhoo.com/NewsDescImages/20160429/20160429_144145_717_chairbysamuelstl.jpg");
        entity1.setSize("83 kb");
        entity1.setStlUrl("012.stl");
        list.add(entity1);

        ModelInfoEntity entity2 = new ModelInfoEntity();

        entity2.setId(0x0010);
        entity2.setName("铃铛杯");
        entity2.setDescription("");
        entity2.setImgUrl("http://image.3dhoo.com/NewsDescImages/20160526/20160526_003654_306_vasostl.jpg");
        entity2.setSize("896 kb");
        entity2.setStlUrl("004.stl");
        list.add(entity2);

        adapter = new RecyclerAdapter(getContext(),list);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_main,container,false);
        ButterKnife.bind(this,view);

        srlMain.setColorSchemeColors(getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_green));

        srlMain.setOnRefreshListener(this);

        adapter.setOnClickListener(new RecyclerAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                ModelInfoEntity tempEntity = list.get(position);
//                Intent intent = new Intent(getContext(),ModelInfoAct.class);
//                intent.putExtra("model",tempEntity);
//                startActivity(intent);
                ModelInfoPopupWindow popupWindow = new ModelInfoPopupWindow(getActivity(),tempEntity);
                popupWindow.show(container);

            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getContext(), "long long long~", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0,10,0,10); //只添加间隔，不画任何东西
            }
        });
        return view;
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    mHandler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
