package com.example.quiztest2;

import android.provider.BaseColumns;
public final class QuizContract {
    private QuizContract() {
    }
    public static class ChampionsTable implements BaseColumns {
        public static final String TABLE_NAME = "champions";
        public static final String COLUMN_ID = "champion_id";
        public static final String COLUMN_KEY = "champion_key";
        public static final String COLUMN_NAME = "champion_name";
        public static final String COLUMN_TITLE = "champion_title";
        public static final String COLUMN_LORE = "champion_lore";
        public static final String COLUMN_PRIM_ROLE = "primary_role";
        public static final String COLUMN_SEC_ROLE = "secondary_role";
        public static final String COLUMN_PARTYPE = "parType";
        // public static final String COLUMN_ORIGIN = "origin";
        // public static final String COLUMN_RELEASE_DATE = "release_date";
        // public static final String COLUMN_RANGED = "ranged_type";
        public static final String COLUMN_ATTACK = "info_att";
        public static final String COLUMN_DEFENSE = "info_def";
        public static final String COLUMN_MAGIC = "info_mag";
        public static final String COLUMN_DIFFICULTY = "info_diff";
    }

    public static class ChampionStatsTable implements BaseColumns {
        public static final String TABLE_NAME = "statistics";

        public static final String COLUMN_STAT_HP = "stat_hp";
        public static final String COLUMN_STAT_HPL = "stat_hp_level";

        public static final String COLUMN_STAT_MP = "stat_mp";
        public static final String COLUMN_STAT_MPL = "stat_mp_level";

        public static final String COLUMN_STAT_MS = "stat_ms";

        public static final String COLUMN_STAT_ARMOR = "stat_armor";
        public static final String COLUMN_STAT_ARMORL = "stat_armor_level";

        public static final String COLUMN_STAT_MR = "stat_magic_res";
        public static final String COLUMN_STAT_MRL = "stat_magic_res_level";

        public static final String COLUMN_STAT_RANGE = "stat_range";

        public static final String COLUMN_STAT_HPR = "stat_hp_regen";
        public static final String COLUMN_STAT_HPRL = "stat_hp_regen_level";

        public static final String COLUMN_STAT_MPR = "stat_mp_regen";
        public static final String COLUMN_STAT_MPRL = "stat_mp_regen_level";

        public static final String COLUMN_STAT_CRIT = "stat_crit";
        public static final String COLUMN_STAT_CRITL = "stat_crit_level";

        public static final String COLUMN_STAT_AD = "stat_ad";
        public static final String COLUMN_STAT_ADL = "stat_ad_level";

        public static final String COLUMN_STAT_AS = "stat_as";
        public static final String COLUMN_STAT_ASL = "stat_as_level";
    }

    public static class ChampionAbilityTable implements BaseColumns {
        public static final String TABLE_NAME = "abilities";
        public static final String COLUMN_Q_NAME = "spell_q_name";
        public static final String COLUMN_Q_DESCRIPTION = "spell_q_description";
        public static final String COLUMN_Q_COOLDOWN_BURN = "spell_q_cd_burn";
        public static final String COLUMN_Q_COST_BURN = "spell_q_cost_burn";
        public static final String COLUMN_Q_IMAGE_PATH = "spell_q_img_path";

        public static final String COLUMN_W_NAME = "spell_w_name";
        public static final String COLUMN_W_DESCRIPTION = "spell_w_description";
        public static final String COLUMN_W_COOLDOWN_BURN = "spell_w_cd_burn";
        public static final String COLUMN_W_COST_BURN = "spell_w_cost_burn";
        public static final String COLUMN_W_IMAGE_PATH = "spell_w_img_path";

        public static final String COLUMN_E_NAME = "spell_e_name";
        public static final String COLUMN_E_DESCRIPTION = "spell_e_description";
        public static final String COLUMN_E_COOLDOWN_BURN = "spell_e_cd_burn";
        public static final String COLUMN_E_COST_BURN = "spell_e_cost_burn";
        public static final String COLUMN_E_IMAGE_PATH = "spell_e_img_path";

        public static final String COLUMN_R_NAME = "spell_r_name";
        public static final String COLUMN_R_DESCRIPTION = "spell_r_description";
        public static final String COLUMN_R_COOLDOWN_BURN = "spell_r_cd_burn";
        public static final String COLUMN_R_COST_BURN = "spell_r_cost_burn";
        public static final String COLUMN_R_IMAGE_PATH = "spell_r_img_path";

        public static final String COLUMN_PASSIVE_NAME = "spell_passive_name";
        public static final String COLUMN_PASSIVE_DESCRIPTION = "spell_passive_description";
        public static final String COLUMN_PASSIVE_IMAGE_PATH = "spell_passive_img_path";
    }

    public static class ItemsTable implements BaseColumns {
        public static final String TABLE_NAME = "items_quiz";
        public static final String COLUMN_NAME = "item_name";
        public static final String COLUMN_PRICE = "item_price";
        //TO DO
        public static final String COLUMN_STAT_AD = "item_stats_ad";
        public static final String COLUMN_STAT_AP = "item_stats_ap";
        public static final String COLUMN_STAT_ARMOR = "item_stats_armor";
        public static final String COLUMN_STAT_AH = "item_stats_ah";
        public static final String COLUMN_STAT_AS = "item_stats_as";
        public static final String COLUMN_STAT_MR = "item_stats_mr";
        public static final String COLUMN_STAT_MS = "item_stats_ms";
    }
}
