
CREATE OR REPLACE VIEW vw_stat_24h AS
  select ROW_NUMBER() OVER (ORDER BY id_sitewebadmin,heure),id_sitewebadmin ,heure timeOfDay, count(heure) sum,0 avg FROM
    (select id_sitewebadmin,(date_trunc('hour', date_heure)) heure from infodesuivi
    WHERE  date_heure > CURRENT_DATE - INTERVAL '1 day') subQueryA
  GROUP BY heure,id_sitewebadmin
  ORDER BY id_sitewebadmin,heure;

CREATE OR REPLACE VIEW vw_stat_week AS
  select ROW_NUMBER() OVER (ORDER BY id_sitewebadmin,jour),id_sitewebadmin ,cast(jour as BIGINT) dayOfweek,count(jour) sum,0 avg FROM
    (select id_sitewebadmin,extract(dow from date_heure) jour from infodesuivi
    WHERE  date_heure > CURRENT_DATE - INTERVAL '1 week') subQueryA
  GROUP BY jour,id_sitewebadmin
  ORDER BY id_sitewebadmin,jour;

CREATE OR REPLACE VIEW vw_stat_month AS
  select ROW_NUMBER() OVER (ORDER BY id_sitewebadmin,jour),id_sitewebadmin ,cast(jour as BIGINT) dayOfMonth, count(jour) sum,0 avg FROM
    (select id_sitewebadmin,date_part('day', date_heure) jour from infodesuivi
    WHERE  date_heure > CURRENT_DATE - INTERVAL '1 month') subQueryA
  GROUP BY jour,id_sitewebadmin
  ORDER BY id_sitewebadmin,jour;

CREATE OR REPLACE VIEW vw_stat_year AS
  select ROW_NUMBER() OVER (ORDER BY id_sitewebadmin,mois),id_sitewebadmin ,cast(mois as BIGINT) monthOfYear,count(mois) sum,0 avg FROM
    (select id_sitewebadmin,date_part('month', date_heure) mois from infodesuivi
    WHERE  date_heure > CURRENT_DATE - INTERVAL '1 year') subQueryA
  GROUP BY mois,id_sitewebadmin
  ORDER BY id_sitewebadmin,mois;