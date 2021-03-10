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
    private long currentTime;//当前时间
    private long submitTime;//提交时间
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        context=this;
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
        currentTime=System.currentTimeMillis();
        YCStringTool.logi(this.getClass(),"当前时间");
        game_tree_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = 0;
                for (int i = 0; i < listTree.size(); i++) {
                    if (listTree.get(i).getIsExist()) {//如果是活着的树
                        try {
                            if (listTree.get(i + 1).getIsExist()) //后一个
                                continue;
                        } catch (Exception e) {
                            try {
                                if (listTree.get(i - 1).getIsExist())//前一个
                                    continue;
                            } catch (Exception e1) {
                                try {
                                    if (listTree.get(i - 5).getIsExist())//上一个
                                        continue;
                                } catch (Exception e2) {
                                    try {
                                        if (listTree.get(i + 5).getIsExist())//下一个
                                            continue;
                                    } catch (Exception e3) {
                                    }
                                }
                            }
                        }
                        (((LinearLayout) game_tree_grid_view.getChildAt(i)).getChildAt(0)).setBackground(getResources().getDrawable(R.mipmap.icon_death));
                        //((ImageView)game_tree_grid_view.getChildAt(i)).setSelected(false);
                        Toast.makeText(GameActivity.this, "闯关失败", Toast.LENGTH_SHORT).show();
                        submitTime=System.currentTimeMillis();
                        GameLevelBean gameLevelBean=DBDao.getInstance(context).queryLevel(level);
                        if(gameLevelBean!=null){
                            if(gameLevelBean.getPassTime()==0||submitTime<gameLevelBean.getPassTime())
                                DBDao.getInstance(context).updateTime(level,gameLevelBean.getPassTime(),gameLevelBean.getTaps_num());
                        }
                        return;
                    }
                }
                Toast.makeText(GameActivity.this, "闯关成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}