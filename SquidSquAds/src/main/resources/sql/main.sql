-- DROP TABLES

DROP TABLE IF EXISTS agentutilisateur CASCADE;
DROP TABLE IF EXISTS infodesuivi CASCADE;
DROP TABLE IF EXISTS sitewebadmin CASCADE;
DROP TABLE IF EXISTS redevance CASCADE;
DROP TABLE IF EXISTS visite CASCADE;
DROP TABLE IF EXISTS banniere_campagne CASCADE;
DROP TABLE IF EXISTS banniere CASCADE;
DROP TABLE IF EXISTS site CASCADE;
DROP TABLE IF EXISTS campagne_profilutilisateur CASCADE;
DROP TABLE IF EXISTS profilutilisateur CASCADE;
DROP TABLE IF EXISTS campagne CASCADE;
DROP TABLE IF EXISTS paiement CASCADE;
DROP TABLE IF EXISTS compteutilisateur CASCADE;

-- DROP OLDER TABLES

DROP TABLE IF EXISTS campagne_profildutilisateur CASCADE;
DROP TABLE IF EXISTS categorie CASCADE;
DROP TABLE IF EXISTS categorie_visite CASCADE;
DROP TABLE IF EXISTS orientation CASCADE;
DROP TABLE IF EXISTS orientation_banniere CASCADE;
DROP TABLE IF EXISTS profildutilisateur CASCADE;
DROP TABLE IF EXISTS typenavigateur CASCADE;
DROP TABLE IF EXISTS typenavigateur_agent CASCADE;

-- DROP VIEWS

DROP VIEW IF EXISTS vw_stat_24h;
DROP VIEW IF EXISTS vw_stat_week;
DROP VIEW IF EXISTS vw_stat_month;
DROP VIEW IF EXISTS vw_stat_year;
DROP VIEW IF EXISTS vw_stat_browser;
DROP VIEW IF EXISTS vw_stat_royalty;


-- CREATE TABLES

CREATE TABLE compteutilisateur
(
  id_compte        SERIAL PRIMARY KEY,
  type_admin       VARCHAR(3),
  courriel         VARCHAR(100),
  mot_de_passe     VARCHAR(60),
  no_compte_banque VARCHAR(50),
  date_creation    TIMESTAMP
);

CREATE TABLE paiement
(
  id_paiement   SERIAL PRIMARY KEY,
  id_compte     INTEGER NOT NULL,
  montant       NUMERIC(8, 2),
  date_paiement TIMESTAMP
);

CREATE TABLE campagne
(
  id_campagne        SERIAL PRIMARY KEY,
  id_compte          INTEGER NOT NULL,
  nom                VARCHAR(100),
  date_creation      TIMESTAMP,
  image_hor          VARCHAR(100),
  image_ver          VARCHAR(100),
  image_mob          VARCHAR(100),
  url_de_redirection VARCHAR(100),
  date_debut         TIMESTAMP,
  date_fin           TIMESTAMP,
  budget             NUMERIC(8, 2)
);

CREATE TABLE profilutilisateur
(
  id_profilutilisateur SERIAL PRIMARY KEY,
  id_compte            INTEGER NOT NULL,
  nom                  VARCHAR(100),
  description          VARCHAR(200),
  date_creation        TIMESTAMP
);

CREATE TABLE campagne_profilutilisateur
(
  id_campagne_profilutilisateur SERIAL PRIMARY KEY,
  id_profilutilisateur          INTEGER NOT NULL,
  id_campagne                   INTEGER NOT NULL
);

CREATE TABLE site
(
  id_site              SERIAL PRIMARY KEY,
  id_profilutilisateur INTEGER NOT NULL,
  url                  VARCHAR(200)
);

CREATE TABLE banniere
(
  id_banniere SERIAL PRIMARY KEY,
  id_compte   INTEGER NOT NULL,
  orientation VARCHAR(30)
);

CREATE TABLE banniere_campagne
(
  id_banniere_campagne SERIAL PRIMARY KEY,
  id_banniere          INTEGER NOT NULL,
  id_campagne          INTEGER NOT NULL
);

CREATE TABLE visite
(
  id_visite   SERIAL PRIMARY KEY,
  id_banniere INTEGER NOT NULL,
  date_heure  TIMESTAMP,
  est_cliquee BOOLEAN,
  est_ciblee  BOOLEAN
);

CREATE TABLE redevance
(
  id_redevance  SERIAL PRIMARY KEY,
  id_compte     INTEGER NOT NULL,
  id_visite     INTEGER NOT NULL,
  montant       NUMERIC(8, 2),
  date_creation TIMESTAMP,
  est_reclame   BOOLEAN
);

CREATE TABLE sitewebadmin
(
  id_sitewebadmin SERIAL PRIMARY KEY,
  id_compte       INTEGER NOT NULL,
  url             VARCHAR(200)
);

CREATE TABLE infodesuivi
(
  id_infodesuivi      SERIAL PRIMARY KEY,
  id_agentutilisateur INTEGER NOT NULL,
  id_sitewebadmin     INTEGER NOT NULL,
  empreinte           UUID,
  urlactuel           VARCHAR(200),
  urlprovenance       VARCHAR(200),
  adresse_ipv4        VARCHAR(50),
  adresse_ipv6        VARCHAR(50),
  taille_ecran        VARCHAR(50),
  langue              VARCHAR(50),
  tempsecoule         INTEGER,
  date_heure          TIMESTAMP
);

CREATE TABLE agentutilisateur
(
  id_agentutilisateur    SERIAL PRIMARY KEY,
  agentutilisateurbrut   VARCHAR(350),
  versionnavigateur      VARCHAR(150),
  systeme_operation      VARCHAR(150),
  information_navigateur VARCHAR(150),
  plateforme             VARCHAR(150),
  information_plateforme VARCHAR(150),
  extension_navigateur   VARCHAR(500),
  navigateur             VARCHAR(150),
  date_heure             TIMESTAMP
);


-- CONTRAINTES

-- CLE TABLE paiement
ALTER TABLE paiement
  ADD CONSTRAINT fk_paiement_no_compte FOREIGN KEY (id_compte) REFERENCES compteutilisateur (id_compte);

-- CLE TABLE campagne
ALTER TABLE campagne
  ADD CONSTRAINT fk_campagne_no_compte FOREIGN KEY (id_compte) REFERENCES compteutilisateur (id_compte);

-- CLE TABLE profilutilisateur
ALTER TABLE profilutilisateur
  ADD CONSTRAINT fk_profilutilisateur_no_compte FOREIGN KEY (id_compte) REFERENCES compteutilisateur (id_compte);

-- CLE TABLE campagne_profilutilisateur
ALTER TABLE campagne_profilutilisateur
  ADD CONSTRAINT fk_campagne_profilutilisateur_no_profilutilisateur FOREIGN KEY (id_profilutilisateur) REFERENCES profilutilisateur (id_profilutilisateur);
ALTER TABLE campagne_profilutilisateur
  ADD CONSTRAINT fk_campagne_profilutilisateur_no_campagne FOREIGN KEY (id_campagne) REFERENCES campagne (id_campagne);

-- CLE TABLE site
ALTER TABLE site
  ADD CONSTRAINT fk_site_no_profilutilisateur FOREIGN KEY (id_profilutilisateur) REFERENCES profilutilisateur (id_profilutilisateur);

-- CLE TABLE banniere
ALTER TABLE banniere
  ADD CONSTRAINT fk_banniere_no_compte FOREIGN KEY (id_compte) REFERENCES compteutilisateur (id_compte);

-- CLE TABLE banniere_campagne
ALTER TABLE banniere_campagne
  ADD CONSTRAINT fk_banniere_campagne_no_banniere FOREIGN KEY (id_banniere) REFERENCES banniere (id_banniere);
ALTER TABLE banniere_campagne
  ADD CONSTRAINT fk_banniere_campagne_no_campagne FOREIGN KEY (id_campagne) REFERENCES campagne (id_campagne);

-- CLE TABLE visite
ALTER TABLE visite
  ADD CONSTRAINT fk_visite_no_banniere FOREIGN KEY (id_banniere) REFERENCES Banniere (Id_Banniere);

-- CLE TABLE redevance
ALTER TABLE redevance
  ADD CONSTRAINT fk_redevance_no_compte FOREIGN KEY (id_compte) REFERENCES compteutilisateur (id_compte);
ALTER TABLE redevance
  ADD CONSTRAINT fk_redevance_no_visite FOREIGN KEY (id_visite) REFERENCES visite (id_visite);

-- CLE TABLE sitewebadmin
ALTER TABLE sitewebadmin
  ADD CONSTRAINT fk_sitewebadmin_no_compte FOREIGN KEY (id_compte) REFERENCES compteutilisateur (id_compte);

-- CLE TABLE infodesuivi
ALTER TABLE infodesuivi
  ADD CONSTRAINT fk_infodesuivi_no_sitewebadmin FOREIGN KEY (id_sitewebadmin) REFERENCES sitewebadmin (id_sitewebadmin);
ALTER TABLE infodesuivi
  ADD CONSTRAINT fk_infodesuivi_no_agentutilisateur FOREIGN KEY (id_agentutilisateur) REFERENCES agentutilisateur (id_agentutilisateur);


-- AJOUT CAMPAGNE ET SUPER ADMIN

INSERT INTO compteutilisateur VALUES
  (DEFAULT, 'PUB', 'sa@squidsquads.site', '$2y$10$AAALv7ANjHob1V5nl2wjXOEjs074bxqFy9UvtAZn2DJB.yeEkiadG', '123-12345', NOW());


INSERT INTO campagne VALUES
  (
    DEFAULT,
    (SELECT max(compteutilisateur.id_compte) FROM compteutilisateur),
    'Campagne SquidSquAds',
    NOW(),
    'https://i.imgur.com/eZUxeDA.png',
    'https://i.imgur.com/fdtZzwJ.png',
    'https://i.imgur.com/BdMNBkM.png',
    'https://www.squidsquads.site',
    NOW(),
    NOW(),
    1
  );

INSERT INTO profilutilisateur VALUES
  (DEFAULT,
   (SELECT max(compteutilisateur.id_compte) FROM compteutilisateur),
   'SquidSquAds Profile',
   'A super admin profile for custom SquidSquAds banners.',
   NOW()
  );

INSERT INTO campagne_profilutilisateur VALUES
  (DEFAULT,
   (SELECT MAX(id_compte) FROM campagne),
   (SELECT MAX(profilutilisateur.id_profilutilisateur) FROM profilutilisateur)
  );

INSERT INTO site VALUES
  (DEFAULT,
   (SELECT MAX(profilutilisateur.id_profilutilisateur) FROM profilutilisateur),
   'https://www.squidsquads.site'
  );


-- VIEWS

CREATE OR REPLACE VIEW vw_stat_browser AS
  SELECT ROW_NUMBER() OVER() as rowid, ids.id_sitewebadmin, navigateur, count(navigateur), ROUND(count(navigateur)/SUM(COUNT(navigateur)) OVER (PARTITION BY ids.id_sitewebadmin), 6) as ratio
  FROM infodesuivi ids
    INNER JOIN agentutilisateur a ON ids.id_agentutilisateur = a.id_agentutilisateur
  GROUP BY ids.id_sitewebadmin,navigateur;

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

       ) UnAlias;

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
  ORDER BY id_sitewebadmin ASC, yearValue ASC,  mois ASC;
