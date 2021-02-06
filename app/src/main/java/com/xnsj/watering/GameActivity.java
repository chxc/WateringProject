package com.xnsj.watering;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
/**
 * 游戏界面
 * */
public class GameActivity extends AppCompatActivity {

    private List<TreeBean> listTree=new ArrayList<>();
    private GridView game_tree_grid_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        game_tree_grid_view= findViewById(R.id.game_tree_grid_view);
        TextView game_tree_start= findViewById(R.id.game_tree_start);
        for (int i = 0; i < 100; i++) {
            TreeBean treeBean=new TreeBean();
            if(i%2==0){//是树
                treeBean.setDotType(TreeBean.DotType.TREE);
                if(i%5!=0)
                    treeBean.setExist(true);
                //treeBean.setLinePosition();
            }else {//是水龙头
                treeBean.setDotType(TreeBean.DotType.FAUCET);
            }
            listTree.add(treeBean);
        }
        GameAdapter gameAdapter=new GameAdapter(this,listTree);
        game_tree_grid_view.setAdapter(gameAdapter);
        game_tree_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=0;
                for (int i = 0; i < listTree.size(); i++) {
                    if(listTree.get(i).getIsExist()){//如果是活着的树
                            try {
                            if (listTree.get(i + 1).getIsExist()) //后一个
                                continue;

                        }catch (Exception e){}
                        try {
                            if (listTree.get(i - 1).getIsExist())//前一个
                                continue;
                        }catch (Exception e){}
                        try {
                            if (listTree.get(i - 5).getIsExist())//上一个
                                continue;
                        }catch (Exception e){}
                        try {
                            if (listTree.get(i + 5).getIsExist())//下一个
                                continue;
                        }catch (Exception e){}
                        //((ImageView)game_tree_grid_view.getChildAt(i)).setImageResource(R.drawable.icon_thirsty_tree);
                         //((ImageView)game_tree_grid_view.getChildAt(i)).setSelected(false);
                        Toast.makeText(GameActivity.this, "闯关失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Toast.makeText(GameActivity.this, "闯关成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}