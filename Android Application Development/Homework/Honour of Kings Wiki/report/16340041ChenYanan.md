# 中山大学数据科学与计算机学院本科生实验报告
## （2018年秋季学期）
| 课程名称 | 手机平台应用开发 | 任课老师 | 郑贵锋 |
| :------------: | :-------------: | :------------: | :-------------: |
| 年级 | 2016 | 专业（方向） | 数字媒体 |
| 学号 | 16340041 | 姓名 | 陈亚楠 |
| 电话 | 18709548602 | Email | chenyn97@outlook.com |
| 开始日期 | 2018.11.11 | 完成日期 |2018.11.25|

---

## 一、实验题目

期中项目——王者荣耀英雄大全

---

## 二、实现内容

在本次项目中，我负责实现项目中关于数据库保存英雄的部分，实现了对英雄的增、删、查、改操作。

---

## 三、课堂实验结果
### (1)实验截图

实验截图详见用户说明文档。

### (2)实验步骤以及关键代码

Android 提供了对 `SQLite` 数据库的完全支持，因此本次项目中我们选择 `SQLite` 数据库进行数据存储。

本次实验中关于数据库及其操作的所有代码均包含在`HeroDataBase.java`文件中，本次实验只有一个数据库，该数据库包括`Hero Table`一个表，该表包含了`name` 、`defaultImage`、 `title``、position`、`SurvivabilityValue`、`AttackDamageValue`、`SkillEffectivenessValue`、`BestPartnerHero`、`StartingAbilityValue`、`SuppressHeroes`十个字段，其中`name`为主码。

#### 创建数据库：

创建新 `SQLite` 数据库的方法是创建 `SQLiteOpenHelper` 的子类并覆盖 `onCreate()` 方法，本项目中创建的数据库名字为`HeroDB.db`，它的主要信息与创建方法如下：

```java
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
    //...
}
```

#### 创建表：

在`HeroDateBase.java`文件`public void onCreate(SQLiteDatabase db)`方法中创建数据库中的表，创建方法如下：

```java
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
```

#### 增、删、查、改：

如果我们要从数据库执行写入和读取操作，需要分别调用 `getWritableDatabase()` 和 `getReadableDatabase()`，二者都会返回一个表示数据库的 `SQLiteDatabase` 对象。

本次试验涉及到的关于数据库的操作包含了增、删、查、改四种操作，下面举例详解四种操作的实现方法：

- 增：

  ```java
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
  }
  ```

- 删：

  ```java
  //删除英雄
  public void deleteHero(String heroName) {
      SQLiteDatabase db = this.getWritableDatabase();
      db.delete(HERO_TABLE, "name = ?", new String[] {heroName});
  }
  ```

- 查：

  每个`SQLite`查询都会返回一个指向该查询找到的所有行的 `Cursor`， 我们可以使用 `Cursor` 机制来浏览数据库查询结果，以及读取行和列：

  ```java
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
  ```

- 改：

  ```java
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
  ```

下面给出`HeroDateBase.java`文件中各个方法的说明：

```java
//添加英雄
public void insertHero(Herolist hero);
//初始化数据库
public void initData();
//删除英雄
public void deleteHero(String heroName);
//判断英雄是否存在
public Boolean ifHeroExist(String heroName);
//根据名字查找英雄
public Herolist queryHero(String heroName);
//得到所有英雄，在ListView显示
public ArrayList<Herolist> getAllComments();
//更新英雄信息
public boolean updateHero(String defaultImage, String name, String title, 		String position, String SurvivabilityValue, String AttackDamageValue, 		String SkillEffectivenessValue, String StartingAbilityValue, String 		BestPartnerHero, String SuppressHeroes);
```

### (3)实验遇到的困难以及解决思路

**1.** `android.database.sqlite.SQLiteException: no such column`报错：

在进行数据库查询操作时，我刚开始的查询语句是这样的写的：

```java
String selectQuery = "SELECT * FROM " + HERO_TABLE + " WHERE name = “ + heroName;
```

结果就显示了上面的报错提示，经过上网查询得知，上面的查询方法适用于`name`为`int`数据类型，对于`String`类型，应使用下面的引用方法：

```java
String selectQuery = "SELECT * FROM " + HERO_TABLE + " WHERE name = '"+heroName+"'";
```

**2.** `Unable to start activity ComponentInfo: android.database.CursorIndexOutOfBoundsException: Index 0 requested, with a size of 0`报错：

在进行数据库查询操作时，经常会出现上面的这种错误，这是因为有的时候`cursor`可能不为`null`但是仍然可能为空，我们将`if (cursor != null)`替换为`if (cursor != null && cursor.getCount > 0)`可以解决问题。

---

## 四、实验思考及感想

在本次项目中，我主要负责数据库这部分内容的实现，实现通过`SQLite`数据库来对英雄数据的保存。由于大二的时候已经学习了数据库系统这门课程，对于`SQL`语句也有一定的学习基础，加之在上学期的现代操作系统应用开发课程中已经使用过`SQLite`数据库，所以这次数据库部分的实现难度较小。

---

