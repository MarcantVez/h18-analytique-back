-- Creates a view for the Browser Type graph
CREATE OR REPLACE VIEW browsertypes_view AS
  SELECT
    ROW_NUMBER() OVER() as id,
    100.0 * count(*)/(sum(count(*)) OVER(PARTITION BY id_sitewebadmin)) as ratio,
    au.navigateur,
    info.id_sitewebadmin as websiteID
  FROM infodesuivi info, agentutilisateur au
  WHERE info.id_agentutilisateur = au.id_agentutilisateur
  GROUP BY au.navigateur, websiteID
  ORDER BY websiteID ASC, ratio DESC;