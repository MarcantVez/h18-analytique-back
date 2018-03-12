CREATE OR REPLACE VIEW vw_stat_royalty AS
select
  ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) id,
  *
FROM (

  SELECT 'daily' period, id_compte, 0 sum_vue, 0 sum_cible, 0 sum_clique, 0 sum_cible_clique
  FROM visite v INNER JOIN banniere b ON v.id_banniere = b.id_banniere
  WHERE NOT EXISTS(
     SELECT
       id_compte
     FROM visite v
       INNER JOIN banniere b ON v.id_banniere = b.id_banniere
     WHERE date_heure > CURRENT_DATE - INTERVAL '1 day'
     GROUP BY b.id_compte
  ) GROUP BY id_compte

UNION ALL

  SELECT
    'daily' period,
    id_compte,
    SUM(CASE (NOT est_ciblee AND NOT est_cliquee) WHEN TRUE THEN 0.01 ELSE 0 END) sum_vue,
    SUM(CASE (est_ciblee AND NOT est_cliquee) WHEN TRUE THEN 0.03 ELSE 0 END) sum_cible,
    SUM(CASE (NOT est_ciblee AND est_cliquee) WHEN TRUE THEN 0.05 ELSE 0 END) sum_clique,
    SUM(CASE (est_ciblee AND est_cliquee) WHEN TRUE THEN 0.10 ELSE 0 END) sum_cible_clique
  FROM visite v
    INNER JOIN banniere b ON v.id_banniere = b.id_banniere
  WHERE date_heure > CURRENT_DATE - INTERVAL '1 day'
  GROUP BY b.id_compte

  UNION ALL

  SELECT 'weekly' period, id_compte, 0 sum_vue, 0 sum_cible, 0 sum_clique, 0 sum_cible_clique
  FROM visite v INNER JOIN banniere b ON v.id_banniere = b.id_banniere
  WHERE NOT EXISTS (
    SELECT
      id_compte
    FROM visite v
      INNER JOIN banniere b ON v.id_banniere = b.id_banniere
    WHERE date_heure > CURRENT_DATE - INTERVAL '1 week'
    GROUP BY id_compte
  ) GROUP BY id_compte

  UNION ALL

  SELECT
    'weekly' period,
    id_compte,
    SUM(CASE (NOT est_ciblee AND NOT est_cliquee) WHEN TRUE THEN 0.01 ELSE 0 END) sum_vue,
    SUM(CASE (est_ciblee AND NOT est_cliquee) WHEN TRUE THEN 0.03 ELSE 0 END) sum_cible,
    SUM(CASE (NOT est_ciblee AND est_cliquee) WHEN TRUE THEN 0.05 ELSE 0 END) sum_clique,
    SUM(CASE (est_ciblee AND est_cliquee) WHEN TRUE THEN 0.10 ELSE 0 END) sum_cible_clique
  FROM visite v
    INNER JOIN banniere b ON v.id_banniere = b.id_banniere
  WHERE date_heure > CURRENT_DATE - INTERVAL '1 week'
  GROUP BY id_compte

UNION ALL

  SELECT 'monthly' period, id_compte, 0 sum_vue, 0 sum_cible, 0 sum_clique, 0 sum_cible_clique
  FROM visite v INNER JOIN banniere b ON v.id_banniere = b.id_banniere
  WHERE NOT EXISTS (
      SELECT
        id_compte
      FROM visite v
        INNER JOIN banniere b ON v.id_banniere = b.id_banniere
      WHERE date_heure > CURRENT_DATE - INTERVAL '1 month'
      GROUP BY id_compte
  ) GROUP BY id_compte

UNION ALL

  SELECT
    'monthly' period,
    id_compte,
    SUM(CASE (NOT est_ciblee AND NOT est_cliquee) WHEN TRUE THEN 0.01 ELSE 0 END) sum_vue,
    SUM(CASE (est_ciblee AND NOT est_cliquee) WHEN TRUE THEN 0.03 ELSE 0 END) sum_cible,
    SUM(CASE (NOT est_ciblee AND est_cliquee) WHEN TRUE THEN 0.05 ELSE 0 END) sum_clique,
    SUM(CASE (est_ciblee AND est_cliquee) WHEN TRUE THEN 0.10 ELSE 0 END) sum_cible_clique
  FROM visite v
    INNER JOIN banniere b ON v.id_banniere = b.id_banniere
  WHERE date_heure > CURRENT_DATE - INTERVAL '1 month'
  GROUP BY id_compte

UNION ALL

  SELECT 'annually' period, id_compte, 0 sum_vue, 0 sum_cible, 0 sum_clique, 0 sum_cible_clique
  FROM visite v INNER JOIN banniere b ON v.id_banniere = b.id_banniere
  WHERE NOT EXISTS (
      SELECT
        id_compte
      FROM visite v
        INNER JOIN banniere b ON v.id_banniere = b.id_banniere
      WHERE date_heure > CURRENT_DATE - INTERVAL '1 year'
      GROUP BY id_compte
  ) GROUP BY id_compte

UNION ALL

  SELECT
    'annually' period,
    id_compte,
    SUM(CASE (NOT est_ciblee AND NOT est_cliquee) WHEN TRUE THEN 0.01 ELSE 0 END) sum_vue,
    SUM(CASE (est_ciblee AND NOT est_cliquee) WHEN TRUE THEN 0.03 ELSE 0 END) sum_cible,
    SUM(CASE (NOT est_ciblee AND est_cliquee) WHEN TRUE THEN 0.05 ELSE 0 END) sum_clique,
    SUM(CASE (est_ciblee AND est_cliquee) WHEN TRUE THEN 0.10 ELSE 0 END) sum_cible_clique
  FROM visite v
    INNER JOIN banniere b ON v.id_banniere = b.id_banniere
  WHERE date_heure > CURRENT_DATE - INTERVAL '1 year'
  GROUP BY id_compte

) UnAlias
