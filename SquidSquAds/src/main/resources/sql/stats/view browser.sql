CREATE OR REPLACE VIEW vw_stat_browser AS
SELECT ids.id_sitewebadmin,navigateur,count(navigateur),ROUND(count(navigateur) / SUM(COUNT(navigateur)) OVER (),6) ratio
FROM infodesuivi ids
INNER JOIN agentutilisateur a ON ids.id_agentutilisateur = a.id_agentutilisateur
GROUP BY ids.id_sitewebadmin,navigateur

