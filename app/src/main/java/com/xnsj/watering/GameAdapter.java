package com.xnsj.watering;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class GameAdapter extends BaseAdapter {

    private List<TreeBean> treeBeanList;
    private Context context;

    public GameAdapter(Context context,List<TreeBean> treeBeanList) {
        this.treeBeanList = treeBeanList;
        this.context = context;
        Log.i("TAG123", "GameAdapter: 长度"+treeBeanList.size());
    }

    @Override
    public int getCount() {
        return treeBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return treeBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(treeBeanList.get(i).getDotType()== TreeBean.DotType.TREE){//类型是树
            view= LayoutInflater.from(context).inflate(R.layout.game_tree_item_layout,null);
            ImageView game_tree_item_tree=view.findViewById(R.id.game_tree_item_tree);
            if(treeBeanList.get(i).getIsExist()){//树
                game_tree_item_tree.setSelected(true);
            }else{
                game_tree_item_tree.setSelected(false);
            }
        }else {//是水龙头
            view= LayoutInflater.from(context).inflate(R.layout.game_faucet_item_layout,null);
            ImageView game_tree_item_faucet=view.findViewById(R.id.game_tree_item_faucet);
            if(treeBeanList.get(i).getIsExist()){//水龙头
                game_tree_item_faucet.setSelected(true);
            }else{
                game_tree_item_faucet.setSelected(false);
            }
            game_tree_item_faucet.setTag(i);
            game_tree_item_faucet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position= (int) view.getTag();
                    boolean isOpen=treeBeanList.get(position).getIsExist()^true;
                    treeBeanList.get(position).setExist(isOpen);
                    view.setSelected(isOpen);
                }
            });
        }
        return view;
    }
}
