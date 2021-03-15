package com.xnsj.watering;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.xnsj.watering.DB.DBDao;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 游戏界面
 */
public class GameActivity extends AppCompatActivity  {

    private List<TreeBean> listTree = new ArrayList<>();
    private GridView game_tree_grid_view;
    private int level;//第多少关
    private long startTime;//开始时间
    private long submitTime;//提交时间
    private Context context;
    private View game_tree_start;
    private View game_next_level;
    private int numOfColumns;//每行的数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        context = this;
        game_tree_grid_view = findViewById(R.id.game_tree_grid_view);
        game_tree_start = findViewById(R.id.game_tree_start);
        game_next_level = findViewById(R.id.game_next_level);
        level = getIntent().getExtras().getInt("level");
        switch (level % 3) {
            case 0:
                numOfColumns =5;
                break;
            case 1:
                numOfColumns=7;
                break;
            case 2:
                numOfColumns=9;
                break;
        }
        game_tree_grid_view.setNumColumns(numOfColumns);
        for (int i = 0; i < level * 5; i++) {
            TreeBean treeBean = new TreeBean();
            if (i % 2 == 0) {//是树
                treeBean.setDotType(TreeBean.DotType.TREE);
                treeBean.setExist(true);//设置为存活
            } else {//是水龙头
                treeBean.setDotType(TreeBean.DotType.FAUCET);
            }
            listTree.add(treeBean);
        }
        GameAdapter gameAdapter = new GameAdapter(this, listTree);
        game_tree_grid_view.setAdapter(gameAdapter);
        startTime = System.currentTimeMillis();
        YCStringTool.logi(this.getClass(), "当前时间");
        game_tree_start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 规则 ：如果不是最后一排  那么必须只能有一个水龙头
                 *
                 * */
                int deathTree=0;
                for (int i = 0; i < listTree.size(); i++) {
                    boolean isPass = false;
                    if (i + 1 < listTree.size()) {
                        if(i+1%numOfColumns!=0)//不是每一排的第一个
                        if (listTree.get(i + 1).getIsExist())//后一个
                            isPass = true;//查看后一个
                    }
                    if (i > 0) {//不是第一个
                        if(i-1%numOfColumns!=numOfColumns-1)//不是每一排的最后一个
                        if (listTree.get(i - 1).getIsExist())//前一个
                            isPass = true;//查看后一个
                    }
                    if (i >= numOfColumns) {//上一排
                        if (listTree.get(i - numOfColumns).getIsExist())
                            isPass = true;
                    }
                    if (i + numOfColumns < listTree.size()) {
                        if (listTree.get(i + numOfColumns).getIsExist())//下一排
                            isPass = true;
                    }

                    if (isPass) {//闯关成功

                    } else {//闯关失败
                        if(listTree.get(i).getDotType()== TreeBean.DotType.TREE) {
                            listTree.get(i).setExist(false);//设置树死亡了
                            (((ViewGroup) game_tree_grid_view.getChildAt(i)).getChildAt(0))
                                    .setBackground(getResources().getDrawable(R.mipmap.icon_death));
                            //((ImageView)game_tree_grid_view.getChildAt(i)).setSelected(false);
                            game_tree_start.setEnabled(true);//设置不可点击
                            YCStringTool.logi(this.getClass(), "再来一次");
                            deathTree++;
                        }
                    }
                }
                if(deathTree>0){
                    Toast.makeText(GameActivity.this, "闯关失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                submitTime =System.currentTimeMillis()- startTime;//更新时间
                GameLevelBean gameLevelBean = DBDao.getInstance(context).queryLevel(level);
                int num=0;
                if (gameLevelBean != null) {
                    for (int i = 0; i < listTree.size(); i++) {
                        if(listTree.get(i).getDotType()== TreeBean.DotType.FAUCET){//水龙头
                            if(listTree.get(i).getIsExist()){//水龙头打开了
                                num++;
                                YCStringTool.logi(this.getClass(),"水龙头打开");
                            }
                        }
                    }
                    if (gameLevelBean.getTaps_num() == 0 || num < gameLevelBean.getTaps_num()||
                            num == gameLevelBean.getTaps_num()&&submitTime < gameLevelBean.getPassTime()) {
                        DBDao.getInstance(context).updateInfo(level, submitTime, num);
                        YCStringTool.logi(this.getClass(),"水龙    头  打开");
                    }/*
                    GameLevelShowDialog gameLevelShowDialog=new GameLevelShowDialog(context,gameLevelBean,new GameLevelBean(level,submitTime,num));
                    gameLevelShowDialog.show();*/
                }
                Toast.makeText(GameActivity.this, "闯关成功", Toast.LENGTH_SHORT).show();
                game_tree_start.setVisibility(View.GONE);
                game_next_level.setVisibility(View.VISIBLE);
            }
        });

        game_next_level.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,GameActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("level",level+1);
                intent.putExtras(bundle);
                ((Activity)context).startActivity(intent);
                finish();
            }
        });

    }
}