package com.example.quiztest2;
// Für den Fall dass ich das ins Internet auslagern will: https://stackoverflow.com/questions/11299208/how-to-synchronize-android-database-with-an-online-sql-server
// Spart wahrscheinlich mega Platz ein (aber erhöht Internet-Traffic ungemein)
/*
TODO 29.11.2020:
        SQL-databases to create + variables:
        SQL_CHAMPIONS: champ_id, name, title, lore(abgewandelt, sodass der championname nicht sichtbar ist),
        primaryTag, secondaryTag, partype, info_att, info_def, info_mag, info_diff, release_date, origin,
        rangedType
        Wie füge ich die Origins und release-Dates hinzu? Zwei Hashmaps und dann mit den keys abrufen?  Nur wo mache ich das?
        Im DBHelber wäre es entgegen der Class-Regeln, da es ja nichts mit Datenbank zu tun hat sondern eher mit den Champions
        Deswegen vielleicht eher in Champion JSON????

        Was brauche ich für die Abilities-SQL?
        SQL_ABILITIES: champ_id,
        qName, qDescription, qCostBurn, qCooldownBurn, qType,
        wName, wDescription, wCostBurn, wCooldownBurn, wType,
        eName, eDescription, eCostBurn, eCooldownBurn, eType,
        rName, rDescription, rCostBurn, rCooldownBurn, rType.

        Was brauche ich für die Item-SQL?
TODO nach den ChampionSQL
*/

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quiztest2.QuizContract.*;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {
    private static final String databaseName = "league_of_quizzes.db";
    private static final int databaseVersion = 1;
    HashMap<String,String> champOrigin = new HashMap<>();

    private static DBHelper instance;

    Context context;

    private SQLiteDatabase db;

    private DBHelper (Context context) {
        super(context, databaseName, null, 1);
        this.context = context;
    }

    public static DBHelper getInstance(Context context){
        if (instance == null){
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        // createChampOrigin();

        final String SQL_CREATE_CHAMPION_TABLE = "CREATE TABLE IF NOT EXISTS " +
                ChampionsTable.TABLE_NAME + " ( " +
                ChampionsTable.COLUMN_ID + " TINYTEXT, " +
                ChampionsTable.COLUMN_NAME + " TINYTEXT, " +
                ChampionsTable.COLUMN_KEY + " TINYTEXT, " +
                ChampionsTable.COLUMN_TITLE + " TINYTEXT, " +
                ChampionsTable.COLUMN_LORE + " TEXT, " +
                ChampionsTable.COLUMN_PRIM_ROLE + " TINYTEXT, " +
                ChampionsTable.COLUMN_SEC_ROLE + " TINYTEXT, " +
                // ChampionsTable.COLUMN_ORIGIN + " TINYTEXT, " +
                // ChampionsTable.COLUMN_RELEASE_DATE + " CHAR(12), " +
                // ChampionsTable.COLUMN_RANGED + " TINYTEXT, " +
                ChampionsTable.COLUMN_ATTACK + " TINYINT, " +
                ChampionsTable.COLUMN_DEFENSE + " TINYINT, " +
                ChampionsTable.COLUMN_MAGIC + " TINYINT, " +
                ChampionsTable.COLUMN_DIFFICULTY + " TINYINT, " +
                " PRIMARY KEY (" + ChampionsTable.COLUMN_ID + ") " +
                ")";

        final String SQL_CREATE_CHAMPION_STATS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                ChampionStatsTable.TABLE_NAME + " ( " +
                ChampionsTable.COLUMN_ID + " TINYTEXT, " +
                ChampionStatsTable.COLUMN_STAT_AD + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_ADL + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_ARMOR + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_ARMORL + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_AS + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_ASL + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_CRIT + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_CRITL + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_HP + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_HPL + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_HPR + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_HPRL + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_MP + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_MPL + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_MPR + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_MPRL + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_MR + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_MRL + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_RANGE + " DOUBLE, " +
                ChampionStatsTable.COLUMN_STAT_MS + " DOUBLE, " +
                " FOREIGN KEY (" + ChampionsTable.COLUMN_ID + ") REFERENCES " +
                ChampionsTable.TABLE_NAME + "(" +
                ChampionsTable.COLUMN_ID + ")" +
                ")";

        final String SQL_CREATE_CHAMPION_ABILITY_TABLE = "CREATE TABLE IF NOT EXISTS " +
                ChampionAbilityTable.TABLE_NAME + " ( " +
                ChampionsTable.COLUMN_ID + " TINYTEXT, " +
                ChampionAbilityTable.COLUMN_Q_NAME + " TINYTEXT, " +
                ChampionAbilityTable.COLUMN_Q_DESCRIPTION + " TEXT, " +
                ChampionAbilityTable.COLUMN_Q_IMAGE_PATH + " TINYTEXT, " +
                ChampionAbilityTable.COLUMN_Q_COOLDOWN_BURN + " TINYTEXT, " +
                ChampionAbilityTable.COLUMN_Q_COST_BURN + " TINYTEXT, " +
                ChampionAbilityTable.COLUMN_W_NAME + " TINYTEXT, " +
                ChampionAbilityTable.COLUMN_W_DESCRIPTION + " TEXT, " +
                ChampionAbilityTable.COLUMN_W_IMAGE_PATH + " TINYTEXT, " +
                ChampionAbilityTable.COLUMN_W_COOLDOWN_BURN + " TINYTEXT, " +
                ChampionAbilityTable.COLUMN_W_COST_BURN + " TINYTEXT, " +
                ChampionAbilityTable.COLUMN_E_NAME + " TINYTEXT, " +
                ChampionAbilityTable.COLUMN_E_DESCRIPTION + " TEXT, " +
                ChampionAbilityTable.COLUMN_E_IMAGE_PATH + " TINYTEXT, " +
                ChampionAbilityTable.COLUMN_E_COOLDOWN_BURN + " TINYTEXT, " +
                ChampionAbilityTable.COLUMN_E_COST_BURN + " TINYTEXT, " +
                ChampionAbilityTable.COLUMN_R_NAME + " TINYTEXT, " +
                ChampionAbilityTable.COLUMN_R_DESCRIPTION + " TEXT, " +
                ChampionAbilityTable.COLUMN_R_IMAGE_PATH + " TINYTEXT, " +
                ChampionAbilityTable.COLUMN_R_COOLDOWN_BURN + " TINYTEXT, " +
                ChampionAbilityTable.COLUMN_R_COST_BURN + " TINYTEXT, " +
                " FOREIGN KEY (" + ChampionsTable.COLUMN_ID + ") REFERENCES " +
                ChampionsTable.TABLE_NAME + "(" +
                ChampionsTable.COLUMN_ID + ")" +
                ")";


        final String SQL_CREATE_ITEM_TABLE = "CREATE TABLE IF NOT EXISTS " +
                ItemsTable.TABLE_NAME + " ( " +
                ItemsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemsTable.COLUMN_NAME + " TEXT, " +
                ItemsTable.COLUMN_PRICE + " INTEGER, " +
                ItemsTable.COLUMN_STAT_AD + " INTEGER, " +
                ItemsTable.COLUMN_STAT_AH + " INTEGER, " +
                ItemsTable.COLUMN_STAT_AP + " INTEGER, " +
                ItemsTable.COLUMN_STAT_ARMOR + " INTEGER, " +
                ItemsTable.COLUMN_STAT_AS + " INTEGER, " +
                ItemsTable.COLUMN_STAT_MR + " INTEGER, " +
                ItemsTable.COLUMN_STAT_MS + " INTEGER " +
                ")";
        // Wenn Spalten abgefragt werden wo kein Wert gespeichert ist:
        //https://stackoverflow.com/questions/19268811/set-default-value-in-query-when-value-is-null/19268839

        db.execSQL(SQL_CREATE_CHAMPION_TABLE);
        db.execSQL(SQL_CREATE_CHAMPION_STATS_TABLE);
        db.execSQL(SQL_CREATE_CHAMPION_ABILITY_TABLE);
        db.execSQL(SQL_CREATE_ITEM_TABLE);
        fillChampionTable();
        //fillItemTable();
    }

    /* private void createChampOrigin(){
        champOrigin.put("Aatrox","Darkin");
        champOrigin.put("Akali","Ionia");
        champOrigin.put("Annie", "The Great Barrier");
        champOrigin.put("AurelionSol","Galaxy");
        champOrigin.put("Anivia","Freljord");
        champOrigin.put("Ashe", "Freljord");
        champOrigin.put("Amumu", "Shurima");
        champOrigin.put("Azir", "Shurima");
        champOrigin.put("Alistar", "The Great Barrier");
        champOrigin.put("Bard","Ancient");
        champOrigin.put("Brand","Unknown");
        champOrigin.put("Braum", "Freljord");
        champOrigin.put("Blitzcrank", "Zaun");
        champOrigin.put("Corki", "Bandle City");
        champOrigin.put("Camille", "Piltover");
        champOrigin.put("Caitlyn", "Piltover");
        champOrigin.put("Chogath", "Void");
        champOrigin.put("Cassiopeia", "Noxus");
        champOrigin.put("Darius", "Noxus");
        champOrigin.put("Draven", "Noxus");
        champOrigin.put("DrMundo", "Zaun");
        champOrigin.put("Diana", "Mount Targon");
        champOrigin.put("Evelynn","Unknown");
        champOrigin.put("Elise", "Noxus");
        champOrigin.put("Ekko", "Zaun");
        champOrigin.put("Ezreal", "Piltover");
        champOrigin.put("Fiddlesticks","Unknown");
        champOrigin.put("Fiora", "Demacia");
        champOrigin.put("Fizz", "Bilgewater");
        champOrigin.put("Graves", "Bilgewater");
        champOrigin.put("Galio", "Demacia");
        champOrigin.put("Garen", "Demacia");
        champOrigin.put("Gnar", "Bandle City");
        champOrigin.put("Gangplank", "Bilgewater");
        champOrigin.put("Gragas", "The Great Barrier");
        champOrigin.put("Heimerdinger", "Bandle City");
        champOrigin.put("Hecarim", "The Shadow Isles");
        champOrigin.put("Irelia","Ionia");
        champOrigin.put("Ivern", "Freljord");
        champOrigin.put("Illaoi", "Bilgewater");
        champOrigin.put("Janna","Zaun/Ancient");
        champOrigin.put("JarvanIV", "Demacia");
        champOrigin.put("Jinx", "Zaun");
        champOrigin.put("Jhin", "Ionia");
        champOrigin.put("Jayce", "Piltover");
        champOrigin.put("Jax", "Icathia");
        champOrigin.put("Kayle","Unknown");
        champOrigin.put("Kindred","Death");
        champOrigin.put("Karma","Ionia");
        champOrigin.put("Kennen", "Ionia");
        champOrigin.put("Katarina", "Noxus");
        champOrigin.put("Kled", "Noxus");
        champOrigin.put("Kogmaw", "Void");
        champOrigin.put("Kassadin", "Void");
        champOrigin.put("Khazix", "Void");
        champOrigin.put("Kalista", "The Shadow Isles");
        champOrigin.put("Karthus", "The Shadow Isles");
        champOrigin.put("LeBlanc", "Noxus");
        champOrigin.put("LeeSin","Ionia");
        champOrigin.put("Lissandra", "Freljord");
        champOrigin.put("Lucian", "Demacia");
        champOrigin.put("Lux", "Demacia");
        champOrigin.put("Lulu", "Bandle City");
        champOrigin.put("Leona", "Mount Targon");
        champOrigin.put("Morgana","Unknown");
        champOrigin.put("MasterYi","Ionia");
        champOrigin.put("Mordekaiser", "Noxus");
        champOrigin.put("MissFortune", "Bilgewater");
        champOrigin.put("Malphite", "Mount Targon");
        champOrigin.put("Malzahar", "Void");
        champOrigin.put("Maokai", "The Shadow Isles");
        champOrigin.put("Nocturne","Nightmare");
        champOrigin.put("Nunu", "Freljord");
        champOrigin.put("Nasus", "Shurima");
        champOrigin.put("Nidalee", "Kumungu");
        champOrigin.put("Nami", "The Sea");
        champOrigin.put("Olaf", "Freljord");
        champOrigin.put("Orianna", "Piltover");
        champOrigin.put("Pantheon", "Mount Targon");
        champOrigin.put("Poppy", "Demacia");
        champOrigin.put("Renekton", "Shurima");
        champOrigin.put("Rammus", "Shurima");
        champOrigin.put("Ryze", "Shurima");
        champOrigin.put("Riven", "Noxus");
        champOrigin.put("Rumble", "Bandle City");
        champOrigin.put("RekSai", "Void");
        champOrigin.put("Rengar", "The Great Barrier");
        champOrigin.put("Shaco","Unknown");
        champOrigin.put("Sona", "Ionia");
        champOrigin.put("Syndra", "Ionia");
        champOrigin.put("Shen", "Ionia");
        champOrigin.put("Soraka", "Ionia");
        champOrigin.put("Sejuani", "Freljord");
        champOrigin.put("Sivir", "Shurima");
        champOrigin.put("Skarner", "Shurima");
        champOrigin.put("Sion", "Noxus");
        champOrigin.put("Shyvana", "Demacia");
        champOrigin.put("Swain", "Noxus");
        champOrigin.put("Singed", "Zaun");
        champOrigin.put("Teemo", "Bandle City");
        champOrigin.put("Tristana", "Bandle City");
        champOrigin.put("Trundle", "Freljord");
        champOrigin.put("Tryndamere", "Freljord");
        champOrigin.put("Taric", "Demacia");
        champOrigin.put("Talon", "Noxus");
        champOrigin.put("Taliyah", "Shurima");
        champOrigin.put("TwistedFate", "Bilgewater");
        champOrigin.put("Twitch", "Zaun");
        champOrigin.put("Thresh", "The Shadow Isles");
        champOrigin.put("TahmKench", "The River");
        champOrigin.put("Udyr", "Ionia");
        champOrigin.put("Urgot", "Zaun/Noxus");
        champOrigin.put("Vayne", "Demacia");
        champOrigin.put("Volibear", "Freljord");
        champOrigin.put("Vladimir", "Noxus");
        champOrigin.put("Veigar", "Bandle City");
        champOrigin.put("Vi", "Zaun");
        champOrigin.put("Viktor", "Zaun");
        champOrigin.put("Velkoz", "Void");
        champOrigin.put("Warwick", "Zaun");
        champOrigin.put("Xerath", "Shurima");
        champOrigin.put("XinZhao", "Demacia");
        champOrigin.put("Yasuo", "Ionia");
        champOrigin.put("Yorick", "The Shadow Isles");
        champOrigin.put("Zed", "Ionia");
        champOrigin.put("Ziggs", "Bandle City");
        champOrigin.put("Zac", "Zaun");
        champOrigin.put("Zilean", "Uristan");
        champOrigin.put("Zyra", "Kumungu");



    }*/

    private void fillChampionTable(){
        Gson gson = new Gson();
        String jsonFormatted = "";

        try {
            InputStream is = context.getAssets().open("championFull.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonFormatted = new String(buffer);
        } catch (Exception e) { throw new RuntimeException(e); }

        ChampionJSONRoot allChampsJSON = gson.fromJson(jsonFormatted,ChampionJSONRoot.class);
        HashMap<String,ChampionJSON> allChamps = allChampsJSON.getData();

        Iterator<Map.Entry<String, ChampionJSON>> it = allChamps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ChampionJSON> pair = it.next();
            ChampionJSON champ = pair.getValue();
            insertChampion(champ);

        }
    }

    protected void insertChampion(ChampionJSON champion){
        ContentValues cv = new ContentValues();
        cv.put(ChampionsTable.COLUMN_ID, champion.getId());
        cv.put(ChampionsTable.COLUMN_NAME, champion.getName());
        cv.put(ChampionsTable.COLUMN_KEY, champion.getKey());
        cv.put(ChampionsTable.COLUMN_TITLE, champion.getTitle());
        cv.put(ChampionsTable.COLUMN_LORE, champion.getLore());
        // cv.put(ChampionsTable.COLUMN_PRIM_ROLE, champion.getTags()[0]);
        // cv.put(ChampionsTable.COLUMN_SEC_ROLE, champion.getTags()[1]);
        // cv.put(ChampionsTable.COLUMN_ORIGIN, champion.);
        // cv.put(ChampionsTable.COLUMN_RELEASE_DATE, champion.getAdaptiveType());
        // cv.put(ChampionsTable.COLUMN_RANGED, champion.getRangedType());
        Info champInfo = champion.getInfo();

        cv.put(ChampionsTable.COLUMN_ATTACK, champInfo.getAttack());
        cv.put(ChampionsTable.COLUMN_DEFENSE, champInfo.getDefense());
        cv.put(ChampionsTable.COLUMN_MAGIC, champInfo.getMagic());
        cv.put(ChampionsTable.COLUMN_DIFFICULTY, champInfo.getDifficulty());

        db.insert(ChampionsTable.TABLE_NAME,null,cv);

        ContentValues cv2 = new ContentValues();
        cv2.put(ChampionsTable.COLUMN_ID, champion.getId());
        cv2.put(ChampionStatsTable.COLUMN_STAT_AD, champion.getStats().get("attackdamage"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_ADL, champion.getStats().get("attackdamageperlevel"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_ARMOR, champion.getStats().get("armor"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_ARMORL, champion.getStats().get("armorperlevel"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_AS, champion.getStats().get("attackspeed"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_ASL, champion.getStats().get("attackspeedperlevel"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_CRIT, champion.getStats().get("crit"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_CRITL, champion.getStats().get("critperlevel"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_HP, champion.getStats().get("hp"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_HPL,champion.getStats().get("hpperlevel"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_HPR, champion.getStats().get("hpregen"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_HPRL, champion.getStats().get("hpregenperlevel"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_MP, champion.getStats().get("mp"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_MPL, champion.getStats().get("mpperlevel"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_MPR, champion.getStats().get("mpregen"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_MPRL, champion.getStats().get("mpregenperlevel"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_MR, champion.getStats().get("spellblock"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_MRL, champion.getStats().get("spellblockperlevel"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_MS, champion.getStats().get("movespeed"));
        cv2.put(ChampionStatsTable.COLUMN_STAT_RANGE, champion.getStats().get("attackrange"));
        db.insert(ChampionStatsTable.TABLE_NAME, null, cv2);

        ContentValues cv3 = new ContentValues();
        Spell[] championSpell = champion.getSpells();
        cv3.put(ChampionsTable.COLUMN_ID, champion.getId());
        cv3.put(ChampionAbilityTable.COLUMN_Q_NAME, championSpell[0].getName());
        cv3.put(ChampionAbilityTable.COLUMN_Q_DESCRIPTION, championSpell[0].getDescription());
        cv3.put(ChampionAbilityTable.COLUMN_Q_COOLDOWN_BURN, championSpell[0].getCooldownBurn());
        cv3.put(ChampionAbilityTable.COLUMN_Q_COST_BURN, championSpell[0].getCostBurn());
        cv3.put(ChampionAbilityTable.COLUMN_W_NAME, championSpell[1].getName());
        cv3.put(ChampionAbilityTable.COLUMN_W_DESCRIPTION, championSpell[1].getDescription());
        cv3.put(ChampionAbilityTable.COLUMN_W_COOLDOWN_BURN, championSpell[1].getCooldownBurn());
        cv3.put(ChampionAbilityTable.COLUMN_W_COST_BURN, championSpell[1].getCostBurn());
        cv3.put(ChampionAbilityTable.COLUMN_E_NAME, championSpell[2].getName());
        cv3.put(ChampionAbilityTable.COLUMN_E_DESCRIPTION, championSpell[2].getDescription());
        cv3.put(ChampionAbilityTable.COLUMN_E_COOLDOWN_BURN, championSpell[2].getCooldownBurn());
        cv3.put(ChampionAbilityTable.COLUMN_E_COST_BURN, championSpell[2].getCostBurn());
        cv3.put(ChampionAbilityTable.COLUMN_R_NAME, championSpell[3].getName());
        cv3.put(ChampionAbilityTable.COLUMN_R_DESCRIPTION, championSpell[3].getDescription());
        cv3.put(ChampionAbilityTable.COLUMN_R_COOLDOWN_BURN, championSpell[3].getCooldownBurn());
        cv3.put(ChampionAbilityTable.COLUMN_R_COST_BURN, championSpell[3].getCostBurn());
        db.insert(ChampionAbilityTable.TABLE_NAME, null, cv3);

    }

    public List<?> getAllCustomQuery(String queryParams){
        List<?> allChampionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery(queryParams,null);
        if (c.moveToFirst()){
            do{
                List<?> queryResult = new ArrayList<>();
            }
            while (c.moveToNext());
        }
        c.close();
        return allChampionList;

    }


    public List<ChampionJSON> getAllChampions(){
        List<ChampionJSON> allChampionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + ChampionsTable.TABLE_NAME, null);
        if(c.moveToFirst()){
            do {
                ChampionJSON champion = new ChampionJSON();
                champion.setId(c.getString(c.getColumnIndex(ChampionsTable.COLUMN_ID)));
                champion.setKey(c.getString(c.getColumnIndex(ChampionsTable.COLUMN_KEY)));
                champion.setLore(c.getString(c.getColumnIndex(ChampionsTable.COLUMN_LORE)));
                champion.setName(c.getString(c.getColumnIndex(ChampionsTable.COLUMN_NAME)));
                champion.setTitle(c.getString(c.getColumnIndex(ChampionsTable.COLUMN_TITLE)));
                String[] tags = new String[2];
                tags[0] = c.getString(c.getColumnIndex(ChampionsTable.COLUMN_PRIM_ROLE));
                tags[1] = c.getString(c.getColumnIndex(ChampionsTable.COLUMN_SEC_ROLE));
                champion.setTags(tags);

                allChampionList.add(champion);
            }
            while(c.moveToNext());
        }
        c.close();
        return allChampionList;
    }

    public String getChampKeyForChampQuiz(int index) {
        String champKey = "";
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " +
                ChampionsTable.COLUMN_KEY +
                " FROM " +
                ChampionsTable.TABLE_NAME, null);
        if (c.moveToPosition(index)){
            champKey = c.getString(c.getColumnIndex(ChampionsTable.COLUMN_KEY));
        }
        c.close();
        return champKey;
    }

    public String[] getChampNameFromKey(String key) {
        String[] champInfo = new String[2];
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " +
                ChampionsTable.COLUMN_NAME +
                ", " +
                ChampionsTable.COLUMN_ID +
                " FROM " +
                ChampionsTable.TABLE_NAME +
                " WHERE " +
                ChampionsTable.COLUMN_KEY +
                " == " +
                key, null);
        if (c.moveToFirst()){
            champInfo[0] = c.getString(c.getColumnIndex(ChampionsTable.COLUMN_NAME));
            champInfo[1] = c.getString(c.getColumnIndex(ChampionsTable.COLUMN_ID));
        }
        c.close();
        return champInfo;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ChampionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ItemsTable.TABLE_NAME);
        onCreate(db);
    }
}
