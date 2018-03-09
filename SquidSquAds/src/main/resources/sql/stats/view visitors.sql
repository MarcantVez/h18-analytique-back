
CREATE OR REPLACE VIEW vw_stat_24h AS
select ROW_NUMBER() OVER (ORDER BY id_agentutilisateur,heure),id_agentutilisateur ,heure::text period,count(heure)::int sum,0 avg FROM
  (select id_agentutilisateur,(date_trunc('hour', date_heure)::time) heure from infodesuivi
WHERE  date_heure > CURRENT_DATE - INTERVAL '1 day') subQueryA
GROUP BY heure,id_agentutilisateur
ORDER BY id_agentutilisateur,heure;

CREATE OR REPLACE VIEW vw_stat_week AS
select ROW_NUMBER() OVER (ORDER BY id_agentutilisateur,jour),id_agentutilisateur ,jour::text period,count(jour)::int sum,0 avg FROM
  (select id_agentutilisateur,extract(dow from date_heure) jour from infodesuivi
  WHERE  date_heure > CURRENT_DATE - INTERVAL '1 week') subQueryA
GROUP BY jour,id_agentutilisateur
ORDER BY id_agentutilisateur,jour;

CREATE OR REPLACE VIEW vw_stat_month AS
select ROW_NUMBER() OVER (ORDER BY id_agentutilisateur,jour),id_agentutilisateur ,jour::text period,count(jour)::int sum,0 avg FROM
  (select id_agentutilisateur,date_part('day', date_heure) jour from infodesuivi
  WHERE  date_heure > CURRENT_DATE - INTERVAL '1 month') subQueryA
GROUP BY jour,id_agentutilisateur
ORDER BY id_agentutilisateur,jour;

CREATE OR REPLACE VIEW vw_stat_year AS
select ROW_NUMBER() OVER (ORDER BY id_agentutilisateur,mois),id_agentutilisateur ,mois::text period,count(mois)::int sum,0 avg FROM
  (select id_agentutilisateur,date_part('month', date_heure) mois from infodesuivi
  WHERE  date_heure > CURRENT_DATE - INTERVAL '1 year') subQueryA
GROUP BY mois,id_agentutilisateur
ORDER BY id_agentutilisateur,mois;