-- DROP

DROP TABLE IF EXISTS AgentUtilisateur CASCADE;
DROP TABLE IF EXISTS InfoDeSuivi CASCADE;
DROP TABLE IF EXISTS SiteWebAdmin CASCADE;
DROP TABLE IF EXISTS Redevance CASCADE;
DROP TABLE IF EXISTS Visite CASCADE;
DROP TABLE IF EXISTS Banniere_Campagne CASCADE;
DROP TABLE IF EXISTS Banniere CASCADE;
DROP TABLE IF EXISTS Site CASCADE;
DROP TABLE IF EXISTS Campagne_ProfilUtilisateur CASCADE;
DROP TABLE IF EXISTS ProfilUtilisateur CASCADE;
DROP TABLE IF EXISTS Campagne CASCADE;
DROP TABLE IF EXISTS Paiement CASCADE;
DROP TABLE IF EXISTS CompteUtilisateur CASCADE;

-- CREATE

CREATE TABLE CompteUtilisateur
(
  Id_Compte		SERIAL PRIMARY KEY,
  Type_Admin			VARCHAR(3),
  Courriel			VARCHAR(50),
  Mot_De_Passe		VARCHAR(60),
  No_Compte_Banque	VARCHAR(50),
  Date_Creation		TIMESTAMP
);

CREATE TABLE Paiement
(
  Id_Paiement		SERIAL PRIMARY KEY,
  Id_Compte		INTEGER NOT NULL,
  Montant				NUMERIC(8,2),
  Date_Paiement		TIMESTAMP
);

CREATE TABLE Campagne
(
  Id_Campagne		SERIAL PRIMARY KEY,
  Id_Compte		INTEGER NOT NULL,
  Nom					VARCHAR(50),
  Date_Creation		TIMESTAMP,
  Image_Hor			VARCHAR(100),
  Image_Ver			VARCHAR(100),
  Image_Mob			VARCHAR(100),
  Url_De_Redirection	VARCHAR(100),
  Date_Debut			TIMESTAMP,
  Date_Fin			TIMESTAMP,
  Budget				NUMERIC(8,2)
);

CREATE TABLE ProfilUtilisateur
(
  Id_ProfilUtilisateur	SERIAL PRIMARY KEY,
  Id_Compte				INTEGER NOT NULL,
  Nom							VARCHAR(50),
  Description					VARCHAR(200),
  Date_Creation				TIMESTAMP
);

CREATE TABLE Campagne_ProfilUtilisateur
(
  Id_Campagne_ProfilUtilisateur	SERIAL PRIMARY KEY,
  Id_ProfilUtilisateur			INTEGER NOT NULL,
  Id_Campagne						INTEGER NOT NULL
);

CREATE TABLE Site
(
  Id_Site					SERIAL PRIMARY KEY,
  Id_ProfilUtilisateur	INTEGER NOT NULL,
  Url							VARCHAR(150)
);

CREATE TABLE Banniere
(
  Id_Banniere		SERIAL PRIMARY KEY,
  Id_Compte		INTEGER NOT NULL,
  Orientation				VARCHAR(30)
);

CREATE TABLE Banniere_Campagne
(
  Id_Banniere_Campagne			SERIAL PRIMARY KEY,
  Id_Banniere						INTEGER NOT NULL,
  Id_Campagne						INTEGER NOT NULL
);

CREATE TABLE Visite
(
  Id_Visite		SERIAL PRIMARY KEY,
  Id_Banniere		INTEGER NOT NULL,
  Date_Heure			TIMESTAMP,
  Est_Cliquee			BOOLEAN,
  Est_Ciblee			BOOLEAN
);

CREATE TABLE Redevance
(
  Id_Redevance	SERIAL PRIMARY KEY,
  Id_Compte		INTEGER NOT NULL,
  Id_Visite		INTEGER NOT NULL,
  Montant				NUMERIC(8,2),
  Date_Creation		TIMESTAMP,
  Est_Reclame			BOOLEAN
);

CREATE TABLE SiteWebAdmin
(
  Id_SiteWebAdmin	SERIAL PRIMARY KEY,
  Id_Compte		INTEGER NOT NULL,
  Url					VARCHAR(150)
);

CREATE TABLE InfoDeSuivi
(
  Id_InfoDeSuivi		SERIAL PRIMARY KEY,
  Id_AgentUtilisateur	INTEGER NOT NULL,
  Id_SiteWebAdmin		INTEGER NOT NULL,
  Empreinte				VARCHAR(100),
  UrlActuel				VARCHAR(150),
  UrlProvenance			VARCHAR(150),
  Adresse_IPV4			VARCHAR(100),
  Adresse_IPV6			VARCHAR(100),
  Taille_Ecran			VARCHAR(100),
  Langue					VARCHAR(50),
  TempsEcoule				INTEGER,
  Date_Heure				TIMESTAMP
);

CREATE TABLE AgentUtilisateur
(
  Id_AgentUtilisateur		SERIAL PRIMARY KEY,
  AgentUtilisateurBrut		VARCHAR(350),
  VersionNavigateur			VARCHAR(150),
  Systeme_Operation			VARCHAR(150),
  Information_Navigateur		VARCHAR(150),
  Plateforme					VARCHAR(150),
  Information_Plateforme		VARCHAR(150),
  Extension_Navigateur		VARCHAR(150),
  Navigateur 					VARCHAR(150),
  Date_Heure				TIMESTAMP
);

-- CONTRAINTES

-- CLE TABLE Paiement
ALTER TABLE Paiement ADD CONSTRAINT fk_paiement_no_compte FOREIGN KEY (Id_Compte) REFERENCES CompteUtilisateur (Id_Compte);

-- CLE TABLE Campagne
ALTER TABLE Campagne ADD CONSTRAINT fk_campagne_no_compte FOREIGN KEY (Id_Compte) REFERENCES CompteUtilisateur (Id_Compte);

-- CLE TABLE ProfilUtilisateur
ALTER TABLE ProfilUtilisateur ADD CONSTRAINT fk_profilutilisateur_no_compte FOREIGN KEY (Id_Compte) REFERENCES CompteUtilisateur (Id_Compte);

-- CLE TABLE Campagne_ProfilUtilisateur
ALTER TABLE Campagne_ProfilUtilisateur ADD CONSTRAINT fk_campagne_profilutilisateur_no_profilutilisateur FOREIGN KEY (Id_ProfilUtilisateur) REFERENCES ProfilUtilisateur (Id_ProfilUtilisateur);
ALTER TABLE Campagne_ProfilUtilisateur ADD CONSTRAINT fk_campagne_profilutilisateur_no_campagne FOREIGN KEY (Id_Campagne) REFERENCES Campagne (Id_Campagne);

-- CLE TABLE Site
ALTER TABLE Site ADD CONSTRAINT fk_site_no_profilutilisateur FOREIGN KEY (id_profilutilisateur) REFERENCES ProfilUtilisateur (Id_ProfilUtilisateur);

-- CLE TABLE Banniere
ALTER TABLE Banniere ADD CONSTRAINT fk_banniere_no_compte FOREIGN KEY (Id_Compte) REFERENCES CompteUtilisateur (Id_Compte);

-- CLE TABLE Banniere_Campagne
ALTER TABLE Banniere_Campagne ADD CONSTRAINT fk_banniere_campagne_no_banniere FOREIGN KEY (Id_Banniere) REFERENCES Banniere (Id_Banniere);
ALTER TABLE Banniere_Campagne ADD CONSTRAINT fk_banniere_campagne_no_campagne FOREIGN KEY (Id_Campagne) REFERENCES Campagne (Id_Campagne);

-- CLE TABLE Visite
ALTER TABLE Visite ADD CONSTRAINT fk_visite_no_banniere FOREIGN KEY (Id_Banniere) REFERENCES Banniere (Id_Banniere);

-- CLE TABLE Redevance
ALTER TABLE Redevance ADD CONSTRAINT fk_redevance_no_compte FOREIGN KEY (Id_Compte) REFERENCES CompteUtilisateur (Id_Compte);
ALTER TABLE Redevance ADD CONSTRAINT fk_redevance_no_visite FOREIGN KEY (Id_Visite) REFERENCES Visite (Id_Visite);

-- CLE TABLE SiteWebAdmin
ALTER TABLE SiteWebAdmin ADD CONSTRAINT fk_sitewebadmin_no_compte FOREIGN KEY (Id_Compte) REFERENCES CompteUtilisateur (Id_Compte);

-- CLE TABLE InfoDeSuivi
ALTER TABLE InfoDeSuivi ADD CONSTRAINT fk_infodesuivi_no_sitewebadmin FOREIGN KEY (Id_SiteWebAdmin) REFERENCES SiteWebAdmin (Id_SiteWebAdmin);
ALTER TABLE InfoDeSuivi ADD CONSTRAINT fk_infodesuivi_no_agentutilisateur FOREIGN KEY (id_agentutilisateur) REFERENCES agentutilisateur (id_agentutilisateur);


-- AJOUT CAMPAGNE ET SUPER ADMIN


INSERT INTO CompteUtilisateur VALUES
  (DEFAULT,'PUB','sa@squidsquads.site','$2y$10$AAALv7ANjHob1V5nl2wjXOEjs074bxqFy9UvtAZn2DJB.yeEkiadG','123-12345',NOW());


INSERT INTO Campagne VALUES
  (
    DEFAULT,
    (select max(compteutilisateur.id_compte) from compteUtilisateur),
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

INSERT INTO ProfilUtilisateur VALUES
  (DEFAULT,
   (select max(compteutilisateur.id_compte) from compteUtilisateur),
   'SquidSquAds Profile',
   'A super admin profile for custom SquidSquAds banners.',
   NOW()
  );

INSERT INTO Campagne_ProfilUtilisateur VALUES
  (DEFAULT,
   (SELECT MAX(id_compte) FROM Campagne),
   (SELECT MAX(profilutilisateur.id_profilutilisateur) FROM ProfilUtilisateur)
  );

INSERT INTO Site VALUES
  (DEFAULT,
   (SELECT MAX(profilutilisateur.id_profilutilisateur) FROM ProfilUtilisateur),
   'https://www.squidsquads.site'
  );

