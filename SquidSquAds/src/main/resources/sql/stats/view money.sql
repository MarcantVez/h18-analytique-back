
CREATE OR REPLACE VIEW vw_stat_money_24h AS
select id_campagne,
  SUM(
  CASE (est_ciblee::int+est_cliquee::int*10)
  WHEN 0 THEN 0.01
  WHEN 1 THEN 0.03
  WHEN 10 THEN 0.05
  WHEN 11 THEN 0.10
  END)
  montant

from visite v
  INNER JOIN banniere b ON v.id_banniere = b.id_banniere
  INNER JOIN banniere_campagne bc ON b.id_banniere = bc.id_banniere

WHERE  date_heure > CURRENT_DATE - INTERVAL '1 day'
GROUP BY id_campagne