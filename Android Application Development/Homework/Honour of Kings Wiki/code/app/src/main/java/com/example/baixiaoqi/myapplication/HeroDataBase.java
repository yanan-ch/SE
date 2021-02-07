package com.example.baixiaoqi.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class HeroDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "heroDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String HERO_TABLE = "Heroes";

    private static final String NAME_FIElD = "name";
    private static final String DEFAULT_IMAGE_FIElD = "defaultImage";
    private static final String TITLE_FIElD = "title";
    private static final String POSITION_FIElD = "position";
    private static final String SURVIVE_ABILITY_VALUE_FIElD = "SurvivabilityValue";
    private static final String ATTACK_DAMAGE_VALUE_FIElD = "AttackDamageValue";
    private static final String SKILL_EFFECTIVENESS_VALUE_FIElD = "SkillEffectivenessValue";
    private static final String BEST_PARTNER_HERO_FIElD = "BestPartnerHero";
    private static final String STARTING_ABILITY_VALUE_FIElD = "StartingAbilityValue";
    private static final String SUPPRESS_HEROES_FIElD = "SuppressHeroes";

    public HeroDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HEROES_TABLE = "CREATE TABLE if not exists " + HERO_TABLE + " ("
                + NAME_FIElD + " TEXT PRIMARY KEY, "
                + DEFAULT_IMAGE_FIElD + " TEXT, "
                + TITLE_FIElD + " TEXT, "
                + POSITION_FIElD + " TEXT, "
                + SURVIVE_ABILITY_VALUE_FIElD + " TEXT, "
                + ATTACK_DAMAGE_VALUE_FIElD + " TEXT, "
                + SKILL_EFFECTIVENESS_VALUE_FIElD + " TEXT, "
                + STARTING_ABILITY_VALUE_FIElD + " TEXT, "
                + BEST_PARTNER_HERO_FIElD + " TEXT, "
                + SUPPRESS_HEROES_FIElD + " TEXT"
                + ")";
        db.execSQL(CREATE_HEROES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HERO_TABLE);
        onCreate(db);
    }

    //添加英雄
    public void insertHero(Herolist hero) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_FIElD, hero.getName());
        values.put(DEFAULT_IMAGE_FIElD, hero.getDefaultImage());
        values.put(TITLE_FIElD, hero.getTitle());
        values.put(POSITION_FIElD, hero.getPosition());
        values.put(SURVIVE_ABILITY_VALUE_FIElD, hero.getSurvivabilityValue());
        values.put(ATTACK_DAMAGE_VALUE_FIElD, hero.getAttackDamageValue());
        values.put(SKILL_EFFECTIVENESS_VALUE_FIElD, hero.getSkillEffectivenessValue());
        values.put(STARTING_ABILITY_VALUE_FIElD, hero.getStartingAbilityValue());
        values.put(BEST_PARTNER_HERO_FIElD, hero.getBestPartnerHero());
        values.put(SUPPRESS_HEROES_FIElD, hero.getSuppressHeroes());
        db.insert(HERO_TABLE, null, values);
        db.close();
    }

    //初始化数据库
    public void initData() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + HERO_TABLE);
        String li =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.libai;
        String lu =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.lubanqihao;
        String wang =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.wangzhaojun;
        String diao =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.diaochan;
        String zhuang =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.zhuangzhou;
        String gong =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.gongbenwuzang;
        String ne =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.nezha;
        String wu =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.wuzetian;
        String sun =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.sunwukong;
        String cao =  "android.resource://com.example.baixiaoqi.myapplication/" + R.mipmap.caocao;

        Herolist libai = new Herolist(li, "李白", "青莲剑仙", "刺客", "40",
                "70", "60", "90", "鬼谷子", "妲己");
        Herolist luban = new Herolist(lu, "鲁班七号", "机关造物", "射手", "10",
                "100", "30", "40", "太乙真人", "王昭君");
        Herolist wangzhaojun = new Herolist(wang, "王昭君", "冰雪之华", "法师", "40",
                "40", "80", "40", "庄周", "鲁班七号");
        Herolist diaochan = new Herolist(diao, "貂蝉", "绝世舞姬", "法师", "40",
                "20", "70", "60", "庄周", "露娜");
        Herolist zhuangzhou = new Herolist(zhuang, "庄周", "逍遥幻梦", "辅助", "80",
                "20", "40", "20", "不知火舞", "后羿");
        Herolist gongbenwuzang = new Herolist(gong, "宫本武藏", "剑圣", "战士", "50",
                "70", "40", "50", "太乙真人", "关羽");
        Herolist nezha = new Herolist(ne, "哪吒", "桀骜炎枪", "战士", "80",
                "30", "60", "50", "刘邦", "后羿");
        Herolist wuzetian = new Herolist(wu, "武则天", "女帝", "法师", "20",
                "10", "100", "60", "庄周", "关羽");
        Herolist sunwukong = new Herolist(sun, "孙悟空", "齐天大圣", "战士", "50",
                "80", "50", "40", "墨子", "妲己");
        Herolist caocao = new Herolist(cao, "曹操", "鲜血枭雄", "战士", "60",
                "60", "50", "40", "孙膑", "王昭君");

        this.insertHero(libai);
        this.insertHero(luban);
        this.insertHero(wangzhaojun);
        this.insertHero(diaochan);
        this.insertHero(zhuangzhou);
        this.insertHero(gongbenwuzang);
        this.insertHero(nezha);
        this.insertHero(wuzetian);
        this.insertHero(sunwukong);
        this.insertHero(caocao);
    }

    //删除英雄
    public void deleteHero(String heroName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(HERO_TABLE, "name = ?", new String[] {heroName});
    }

    //判断英雄是否存在
    public Boolean ifHeroExist(String heroName) {
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + HERO_TABLE + " WHERE name = '"+heroName+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount() > 0 ? true : false;
    }

    //根据名字查找英雄
    public Herolist queryHero(String heroName) {
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + HERO_TABLE + " WHERE name = '"+heroName+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
        }
        String defaultImage = cursor.getString(cursor.getColumnIndex("defaultImage"));
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String position = cursor.getString(cursor.getColumnIndex("position"));
        String SurvivabilityValue = cursor.getString(cursor.getColumnIndex("SurvivabilityValue"));
        String AttackDamageValue = cursor.getString(cursor.getColumnIndex("AttackDamageValue"));
        String SkillEffectivenessValue = cursor.getString(cursor.getColumnIndex("SkillEffectivenessValue"));
        String StartingAbilityValue = cursor.getString(cursor.getColumnIndex("StartingAbilityValue"));
        String BestPartnerHero = cursor.getString(cursor.getColumnIndex("BestPartnerHero"));
        String SuppressHeroes = cursor.getString(cursor.getColumnIndex("SuppressHeroes"));

        Herolist hero = new Herolist(heroName, defaultImage, title, position, SurvivabilityValue,
                AttackDamageValue, SkillEffectivenessValue, StartingAbilityValue, BestPartnerHero, SuppressHeroes);
        return hero;
    }

    //更新英雄信息
    public boolean updateHero(String defaultImage, String name, String title, String position,
                              String SurvivabilityValue, String AttackDamageValue, String SkillEffectivenessValue,
                              String StartingAbilityValue, String BestPartnerHero, String SuppressHeroes) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_FIElD, name);
        values.put(DEFAULT_IMAGE_FIElD, defaultImage);
        values.put(TITLE_FIElD, title);
        values.put(POSITION_FIElD, position);
        values.put(SURVIVE_ABILITY_VALUE_FIElD, SurvivabilityValue);
        values.put(ATTACK_DAMAGE_VALUE_FIElD, AttackDamageValue);
        values.put(SKILL_EFFECTIVENESS_VALUE_FIElD, SkillEffectivenessValue);
        values.put(STARTING_ABILITY_VALUE_FIElD, StartingAbilityValue);
        values.put(BEST_PARTNER_HERO_FIElD, BestPartnerHero);
        values.put(SUPPRESS_HEROES_FIElD, SuppressHeroes);
        db.update(HERO_TABLE, values, "name = ? ", new String[] {name});
        return true;
    }

    //得到所有英雄，在ListView显示
    public ArrayList<Herolist> getAllComments() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Herolist> allHeroes = new ArrayList<Herolist>();
        String selectQuery = "SELECT * FROM " + HERO_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, new String[] {});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Herolist hero = new Herolist();
                hero.setName(cursor.getString(cursor.getColumnIndex("name")));
                hero.setDefaultImage(cursor.getString(cursor.getColumnIndex("defaultImage")));
                hero.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                hero.setPosition(cursor.getString(cursor.getColumnIndex("position")));
                hero.setSurvivabilityValue(cursor.getString(cursor.getColumnIndex("SurvivabilityValue")));
                hero.setAttackDamageValue(cursor.getString(cursor.getColumnIndex("AttackDamageValue")));
                hero.setSkillEffectivenessValue(cursor.getString(cursor.getColumnIndex("SkillEffectivenessValue")));
                hero.setStartingAbilityValue(cursor.getString(cursor.getColumnIndex("StartingAbilityValue")));
                hero.setBestPartnerHero(cursor.getString(cursor.getColumnIndex("BestPartnerHero")));
                hero.setSuppressHeroes(cursor.getString(cursor.getColumnIndex("SuppressHeroes")));

                allHeroes.add(hero);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return allHeroes;
    }
}
