package com.xnsj.watering;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xnsj.watering.DB.DBDao;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 游戏界面
 */
public class GameActivity extends AppCompatActivity {

    private List<TreeBean> listTree = new ArrayList<>();
    private GridView game_tree_grid_view;
    private int level;//第多少关
    private long startTime;//开始时间
    private long submitTime;//提交时间
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        context = this;
        game_tree_grid_view = findViewById(R.id.game_tree_grid_view);
        TextView game_tree_start = findViewById(R.id.game_tree_start);
        level = getIntent().getExtras().getInt("level");
        switch (level % 3) {
            case 0:
                game_tree_grid_view.setNumColumns(5);
                break;
            case 1:
                game_tree_grid_view.setNumColumns(7);
                break;
            case 2:
                game_tree_grid_view.setNumColumns(9);
                break;
        }
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
        game_tree_start.setOnClickListener(new View.OnClickListener() {
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
                        if(i+1%5!=0)//不是每一排的第一个
                        if (listTree.get(i + 1).getIsExist())//后一个
                            isPass = true;//查看后一个
                    }
                    if (i > 0) {//不是第一个
                        if(i-1%5!=4)//不是每一排的最后一个
                        if (listTree.get(i - 1).getIsExist())//前一个
                            isPass = true;//查看后一个
                    }
                    if (i >= 5) {//上一排
                        if (listTree.get(i - 5).getIsExist())
                            isPass = true;
                    }
                    if (i + 5 < listTree.size()) {
                        if (listTree.get(i + 5).getIsExist())//下一排
                            isPass = true;
                    }

                    if (isPass) {//闯关成功

                    } else {//闯关失败
                        listTree.get(i).setExist(false);//设置树死亡了
                        (((LinearLayout) game_tree_grid_view.getChildAt(i)).getChildAt(0)).setBackground(getResources().getDrawable(R.mipmap.icon_death));
                        //((ImageView)game_tree_grid_view.getChildAt(i)).setSelected(false);
                        game_tree_start.setEnabled(false);//设置不可点击
                        YCStringTool.logi(this.getClass(),"闯关失败");
                        deathTree++;
                    }
                }
                if(deathTree>0){
                    Toast.makeText(GameActivity.this, "闯关失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                submitTime =System.currentTimeMillis()- startTime;//更新时间
                GameLevelBean gameLevelBean = DBDao.getInstance(context).queryLevel(level);
                if (gameLevelBean != null) {
                    YCStringTool.logi(this.getClass(),"水龙    头  打开");
                    if (gameLevelBean.getPassTime() == 0 || submitTime < gameLevelBean.getPassTime()) {
                        int num=0;
                        for (int i = 0; i < listTree.size(); i++) {
                            if(listTree.get(i).getDotType()== TreeBean.DotType.FAUCET){//水龙头
                                if(listTree.get(i).getIsExist()){//水龙头打开了
                                    num++;
                                    YCStringTool.logi(this.getClass(),"水龙头打开");
                                }
                            }
                        }
                        DBDao.getInstance(context).updateInfo(level, submitTime, num);
                    }
                }
                Toast.makeText(GameActivity.this, "闯关成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}