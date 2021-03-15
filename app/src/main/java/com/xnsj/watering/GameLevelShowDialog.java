package com.xnsj.watering;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.TextView;

public class GameLevelShowDialog extends AlertDialog {
    protected GameLevelShowDialog(Context context,GameLevelBean lastGameLevelBean,
                                  GameLevelBean newestGameLevelBean) {
        super(context);
        setContentView(R.layout.game_level_show_dailog_layout);
        TextView game_level_show_dialog_last_grade=(TextView) findViewById(R.id.game_level_show_dialog_last_grade);
        TextView game_level_show_dialog_newest_grade=(TextView) findViewById(R.id.game_level_show_dialog_newest_grade);
        game_level_show_dialog_last_grade.setText("历史最优记录\n"+
                "使用水龙头个数："+lastGameLevelBean.getTaps_num()+"\n"+"通关时间："
                +YCStringTool.formatS(lastGameLevelBean.getPassTime()));
        game_level_show_dialog_newest_grade.setText("本次记录\n"+
                "使用水龙头个数："+newestGameLevelBean.getTaps_num()+"\n"+"通关时间："
                +YCStringTool.formatS(newestGameLevelBean.getPassTime()));

    }
}
