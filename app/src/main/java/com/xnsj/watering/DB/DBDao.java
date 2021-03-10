package com.xnsj.watering.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xnsj.watering.GameLevelBean;
import com.xnsj.watering.YCStringTool;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库的Dao，主要是获取单例、以及增删改查数据库内容
 */
public class DBDao {
    private static DBDao dao = null;
    private Context context;

    public DBDao(Context context) {
        this.context = context;
    }

    public static DBDao getInstance(Context context) {
        if (dao == null) {
            dao = new DBDao(context);
        }
        return dao;
    }

    public SQLiteDatabase getConnection() {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = new DBHelper(context).getReadableDatabase();
        } catch (Exception e) {
            YCStringTool.logi(this.getClass(), "数据库建立连接异常" + e);
        }
        return sqLiteDatabase;
    }

    /**
     * 查询所有的关卡信息
     */
    public synchronized List<GameLevelBean> getAllInfos() {
        SQLiteDatabase database = getConnection();
        List<GameLevelBean> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            String sql = "select level, pass_time " + "  from game_level_info ";
            cursor = database.rawQuery(sql, new String[]{});
            while (cursor.moveToNext()) {
                GameLevelBean gameLevelBean = new GameLevelBean(cursor.getInt(0), cursor.getLong(1));
                YCStringTool.logi(DBDao.class, "查到的数据库内容" + gameLevelBean.toString());
                list.add(gameLevelBean);
            }
        } catch (SQLiteConstraintException e) {
            YCStringTool.logi(this.getClass(), "数据库读取异常" + e);
        } finally {
            if (null != database) {
                database.close();
            }
            if (null != cursor) {
                cursor.close();
            }
        }
        return list;
    }

    /**
     * 插入 游戏关卡  ()
     */
    public synchronized void insertInfo(GameLevelBean info) {
        SQLiteDatabase database = getConnection();
        try {
            String sql = "insert into game_level_info (level,pass_time)  " + "values (?,?)";
            Object[] bindArgs = {info.getLevel(), info.getPassTime()};
            database.execSQL(sql, bindArgs);
        } catch (SQLiteConstraintException e) {
            database.close();
            YCStringTool.logi(this.getClass(),"插入数据库异常1");
        } catch (Exception e) {
            e.printStackTrace();
            YCStringTool.logi(this.getClass(),"插入数据库异常2");
        } finally {
            if (null != database) {
                database.close();
            }
        }
    }


    /**
     * 删除  特定关卡
     */
    public synchronized void deleteForLevel(int level) {
        SQLiteDatabase database = getConnection();
        try {
            database.delete("game_level_info", "level=?", new String[]{level + ""});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.close();
            }
        }
    }

    /**
     * 修改特定关卡数据
     */
    public void updateTime(int level, long pass_time) {
        SQLiteDatabase database = getConnection();
        try {
            String sql = "update game_level_info set pass_time=? where level=?";
            Object[] bindArgs = {pass_time, level};
            database.execSQL(sql, bindArgs);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "数据库修改时间异常" + e);
        } finally {
            if (null != database) {
                database.close();
            }
        }
    }

    /**
     * 根据关卡查询花费时间
     */
    public synchronized GameLevelBean queryLevel(int level) {
        SQLiteDatabase database = getConnection();
        GameLevelBean gameLevelBean=null;
        Cursor cursor = null;
        try {
            String sql = "select count(*)   from game_level_info where level=?";
            cursor = database.rawQuery(sql, new String[]{level + ""});
            if (cursor.moveToFirst()) {
                gameLevelBean =new GameLevelBean(level, cursor.getInt(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.close();
            }
            if (null != cursor) {
                cursor.close();
            }
        }
        return gameLevelBean;
    }
}
