package com.xnsj.watering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.xnsj.watering.DB.DBDao;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<GameLevelBean> listGameLevel = new ArrayList<>();
    private ListView main_list_view;
    private Activity activity;

    /*
     * 1、每个龙头可以喷上下左右各一个  再加上一个（n）
     * 2、等级乘以x(某个级别的数量)
     * 3、等级可以剩余金币  剩下的个数
     * 4、钱可以买喷头的种类
     * --------  --------
     * 随机分布，用花费的和最佳的水龙头数量相比，来给打分
     * 一行树木   一行水龙头
     * 一行单数  一行双数  放树木   水龙头也是
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_list_view = (ListView) findViewById(R.id.main_list_view);
        activity = this;
        initData();
        initView();
    }

    //初始化数据
    private void initData() {
        listGameLevel.addAll(DBDao.getInstance(this).getAllInfos());
        if (listGameLevel.size() <= 0)
            for (int i = 0; i < 40; i++) {
                GameLevelBean gameLevelBean = new GameLevelBean(i, 0,0);
                listGameLevel.add(gameLevelBean);
                DBDao.getInstance(this).insertInfo(gameLevelBean);
            }
        GameLevelAdapter gameLevelAdapter = new GameLevelAdapter(activity, listGameLevel);
        main_list_view.setAdapter(gameLevelAdapter);
    }

    //初始化视图
    private void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_tip://跳转到提示页面
                Intent intent = new Intent(this, TipActivity.class);
                startActivity(intent);
                break;
        }
    }
}