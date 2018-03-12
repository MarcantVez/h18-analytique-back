
CREATE OR REPLACE VIEW vw_stat_24h AS
  select ROW_NUMBER() OVER (ORDER BY id_sitewebadmin,heure),id_sitewebadmin ,heure timeOfDay, sum(CASE WHEN countData=1 THEN 1 ELSE 0 END ) sum,0 avg FROM
    (select id_sitewebadmin,(date_trunc('hour', date_heure)) heure, 1 countData from infodesuivi
    WHERE  date_heure > CURRENT_DATE - INTERVAL '1 day'
     UNION ALL
     SELECT id_sitewebadmin,generate_series heure,0 countData FROM generate_series((CURRENT_DATE - INTERVAL '1 day')::timestamp,now()::timestamp, '4 hours'),sitewebadmin order by id_sitewebadmin
    ) subQueryA
  GROUP BY heure,id_sitewebadmin
  ORDER BY id_sitewebadmin,heure;

CREATE OR REPLACE VIEW vw_stat_week AS
  select ROW_NUMBER() OVER (ORDER BY id_sitewebadmin,jour),id_sitewebadmin ,jour::int dayOfweek,sum(CASE WHEN countData=1 THEN 1 ELSE 0 END ) sum,0 avg FROM
    (select id_sitewebadmin,extract(dow from date_heure) jour, 1 countData from infodesuivi
    WHERE  date_heure > CURRENT_DATE - INTERVAL '1 week'
     UNION ALL
     select id_sitewebadmin, generate_series(0,6,1) jour, 0 countData from sitewebadmin
    ) subQueryA
  GROUP BY jour,id_sitewebadmin
  ORDER BY id_sitewebadmin,jour;

CREATE OR REPLACE VIEW vw_stat_month AS
  select ROW_NUMBER() OVER (ORDER BY id_sitewebadmin,jour),id_sitewebadmin ,cast(jour as int) dayOfMonth,mois monthNumber, sum(CASE WHEN countData=1 THEN 1 ELSE 0 END ) sum,0 avg FROM
    (select id_sitewebadmin,date_part('day', date_heure) jour,date_part('month', date_heure) mois,1 countData from infodesuivi
    WHERE  date_heure > CURRENT_DATE - INTERVAL '1 month'
     UNION ALL
     SELECT id_sitewebadmin,date_part('day', generate_series) jour,date_part('month', generate_series) mois,0 countData FROM generate_series((CURRENT_DATE - INTERVAL '1 month')::timestamp,now()::timestamp, '1 day'),sitewebadmin order by id_sitewebadmin
    ) subQueryA
  GROUP BY jour,id_sitewebadmin,monthNumber
  ORDER BY id_sitewebadmin,monthNumber,jour;

CREATE OR REPLACE VIEW vw_stat_year AS
  select ROW_NUMBER() OVER (ORDER BY id_sitewebadmin, annee),id_sitewebadmin ,cast(mois as int) monthOfYear, cast(annee as INTEGER) yearValue, sum(CASE WHEN countData=1 THEN 1 ELSE 0 END ) sum,0 avg FROM
    (
      select id_sitewebadmin, date_part('month', date_heure) mois, date_part('year', date_heure) annee,1 countData from infodesuivi WHERE  date_heure > CURRENT_DATE - INTERVAL '1 year'
      UNION ALL
      SELECT id_sitewebadmin, date_part('month', generate_series) mois, date_part('year', generate_series) annee, 0 countData FROM generate_series((CURRENT_DATE - INTERVAL '1 year')::timestamp,now()::timestamp, '1 month'),sitewebadmin order by id_sitewebadmin
    ) subQueryA
  GROUP BY annee,mois,id_sitewebadmin
  ORDER BY id_sitewebadmin ASC, yearValue DESC,  mois DESC;