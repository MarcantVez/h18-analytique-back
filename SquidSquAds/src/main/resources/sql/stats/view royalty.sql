CREATE OR REPLACE VIEW vw_stat_royalty AS
select
  ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) id,
  *
FROM
  (
SELECT
     'daily' period,
     id_compte,
     id_campagne,
     SUM( CASE (est_ciblee :: INT + est_cliquee :: INT * 10) WHEN 0 THEN 0.01 ELSE 0 END ) sum_vue,
     SUM( CASE (est_ciblee :: INT + est_cliquee :: INT * 10) WHEN 1 THEN 0.03 ELSE 0 END ) sum_cible,
     SUM( CASE (est_ciblee :: INT + est_cliquee :: INT * 10) WHEN 10 THEN 0.05 ELSE 0 END ) sum_clique,
     SUM( CASE (est_ciblee :: INT + est_cliquee :: INT * 10) WHEN 11 THEN 0.10 ELSE 0 END ) sum_cible_clique
   FROM visite v
     INNER JOIN banniere b ON v.id_banniere = b.id_banniere
     INNER JOIN banniere_campagne bc ON b.id_banniere = bc.id_banniere
   WHERE date_heure > CURRENT_DATE - INTERVAL '1 day'
   GROUP BY id_compte, id_campagne

UNION ALL

   SELECT
  'weekly' period,
  id_compte,
  id_campagne,
  SUM( CASE (est_ciblee :: INT + est_cliquee :: INT * 10) WHEN 0 THEN 0.01 ELSE 0 END ) sum_vue,
  SUM( CASE (est_ciblee :: INT + est_cliquee :: INT * 10) WHEN 1 THEN 0.03 ELSE 0 END ) sum_cible,
  SUM( CASE (est_ciblee :: INT + est_cliquee :: INT * 10) WHEN 10 THEN 0.05 ELSE 0 END ) sum_clique,
  SUM( CASE (est_ciblee :: INT + est_cliquee :: INT * 10) WHEN 11 THEN 0.10 ELSE 0 END ) sum_cible_clique
  FROM visite v
  INNER JOIN banniere b ON v.id_banniere = b.id_banniere
  INNER JOIN banniere_campagne bc ON b.id_banniere = bc.id_banniere
  WHERE date_heure > CURRENT_DATE - INTERVAL '1 week'
  GROUP BY id_compte, id_campagne

UNION ALL

  SELECT
    'monthly' period,
    id_compte,
    id_campagne,
    SUM(CASE (est_ciblee :: INT + est_cliquee :: INT * 10) WHEN 0 THEN 0.01 ELSE 0 END) sum_vue,
    SUM(CASE (est_ciblee :: INT + est_cliquee :: INT * 10) WHEN 1 THEN 0.03 ELSE 0 END) sum_cible,
    SUM(CASE (est_ciblee :: INT + est_cliquee :: INT * 10) WHEN 10 THEN 0.05 ELSE 0 END) sum_clique,
    SUM(CASE (est_ciblee :: INT + est_cliquee :: INT * 10) WHEN 11 THEN 0.10 ELSE 0 END) sum_cible_clique
  FROM visite v
    INNER JOIN banniere b ON v.id_banniere = b.id_banniere
    INNER JOIN banniere_campagne bc ON b.id_banniere = bc.id_banniere
  WHERE date_heure > CURRENT_DATE - INTERVAL '1 month'
  GROUP BY id_compte,id_campagne

UNION ALL
  SELECT
    'annually' period,
    id_compte,
    id_campagne,
    SUM(CASE (est_ciblee :: INT + est_cliquee :: INT * 10) WHEN 0 THEN 0.01 ELSE 0 END) sum_vue,
    SUM(CASE (est_ciblee :: INT + est_cliquee :: INT * 10) WHEN 1 THEN 0.03 ELSE 0 END) sum_cible,
    SUM(CASE (est_ciblee :: INT + est_cliquee :: INT * 10) WHEN 10 THEN 0.05 ELSE 0 END) sum_clique,
    SUM(CASE (est_ciblee :: INT + est_cliquee :: INT * 10) WHEN 11 THEN 0.10 ELSE 0 END) sum_cible_clique
  FROM visite v
    INNER JOIN banniere b ON v.id_banniere = b.id_banniere
    INNER JOIN banniere_campagne bc ON b.id_banniere = bc.id_banniere
  WHERE date_heure > CURRENT_DATE - INTERVAL '1 year'
  GROUP BY id_compte,id_campagne
    ) UnAlias







