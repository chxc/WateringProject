package com.xnsj.watering;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
/**
 * 游戏等级界面
 * */
public class GameLevelAdapter extends BaseAdapter {
    private Context context;
    private List<GameLevelBean> list;

    public GameLevelAdapter(Context context, List<GameLevelBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        GameLevelHolder gameLevelHolder;
        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.game_level_layout, viewGroup, false);
            gameLevelHolder=new GameLevelHolder(view);
            view.setTag(gameLevelHolder);
        }else
            gameLevelHolder= (GameLevelHolder) view.getTag();
        gameLevelHolder.game_level_state.setText("第"+list.get(position).getLevel()+"关");
        if(list.get(position).getPassTime()==0){
            gameLevelHolder.game_past_time.setText("未完成");
        }else
            gameLevelHolder.game_past_time.setText("花费时间"+list.get(position).getPassTime());
        gameLevelHolder.game_past_time.setTag(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,GameActivity.class);
                int position= (int) ((ViewGroup)view).getChildAt(1).getTag();
                Bundle bundle=new Bundle();
                bundle.putInt("level",position+1);
                intent.putExtras(bundle);
                ((Activity)context).startActivity(intent);
            }
        });
        return view;
    }

    protected static class GameLevelHolder {
        private View view;
        private TextView game_level_state;
        private TextView game_past_time;

        public GameLevelHolder(View view) {
            this.view = view;
            initView();
        }

        //初始化视图
        private void initView() {
            game_level_state = view.findViewById(R.id.game_level_state);
            game_past_time = view.findViewById(R.id.game_past_time);
        }
    }

}
