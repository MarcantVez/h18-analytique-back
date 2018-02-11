-- Database: squidsquads

-- DROP DATABASE squidsquads;




--CREATE DATABASE squidsquads
--WITH
--OWNER = postgres
--ENCODING = 'UTF8'
--LC_COLLATE = 'English_Canada.1252'
--LC_CTYPE = 'English_Canada.1252'
--TABLESPACE = pg_default
--CONNECTION LIMIT = -1;


CREATE TABLE CompteUtilisateur
(
    Numero_Compte		SERIAL PRIMARY KEY,
    Type_Admin			VARCHAR(3),
    Courriel			VARCHAR(50),
    Mot_De_Passe		VARCHAR(50),
    No_Compte_Banque	VARCHAR(50),
    Date_Creation		TIMESTAMP
);

CREATE TABLE Paiement
(
    Numero_Paiement		SERIAL PRIMARY KEY,
    Numero_Compte		INTEGER NOT NULL,
    Montant				MONEY,
    Date_Paiement		TIMESTAMP
);

CREATE TABLE Campagne
(
    Numero_Campagne		SERIAL PRIMARY KEY,
    Numero_Compte		INTEGER NOT NULL,
    Nom					VARCHAR(50),
    Date_Creation		TIMESTAMP,
    Image_Hor			VARCHAR(100),
    Image_Ver			VARCHAR(100),
    Image_Mob			VARCHAR(100),
    Url_De_Redirection	VARCHAR(100),
    Date_Debut			TIMESTAMP,
    Date_Fin			TIMESTAMP,
    Budget				MONEY
);

CREATE TABLE ProfilDUtilisateur
(
    Numero_ProfilDUtilisateur	SERIAL PRIMARY KEY,
    Numero_Compte				INTEGER NOT NULL,
    Nom							VARCHAR(50),
    Description					VARCHAR(200),
    Date_Creation				TIMESTAMP
);

CREATE TABLE Campagne_ProfilDUtilisateur
(
    Numero_Campagne_ProfilDUtilisateur	SERIAL PRIMARY KEY,
    Numero_Campagne						INTEGER NOT NULL,
    Numero_ProfilDUtilisateur			INTEGER NOT NULL

);

CREATE TABLE Site
(
    Numero_Site					SERIAL PRIMARY KEY,
    Numero_ProfilDUtilisateur	INTEGER NOT NULL,
    Url							VARCHAR(150)
);

CREATE TABLE Banniere
(
    Numero_Banniere		SERIAL PRIMARY KEY,
    Numero_Compte		INTEGER NOT NULL,
    Id_Banniere				VARCHAR(30)
);

CREATE TABLE Orientation
(
    Numero_Orientation	SERIAL PRIMARY KEY,
    Nom					VARCHAR(50)
);

CREATE TABLE Orientation_Banniere
(
    Numero_Orientation_Banniere	SERIAL PRIMARY KEY,
    Numero_Orientation			INTEGER NOT NULL,
    Numero_Banniere				INTEGER NOT NULL
);

CREATE TABLE Visite
(
    Numero_Visite		SERIAL PRIMARY KEY,
    Numero_Banniere		INTEGER NOT NULL,
    Date_Heure			TIMESTAMP
);

CREATE TABLE Categorie
(
    Numero_Categorie	SERIAL PRIMARY KEY,
    Nom					VARCHAR(50)
);

CREATE TABLE Categorie_Visite
(
    Numero_Categorie_Visite		SERIAL PRIMARY KEY,
    Numero_Categorie			INTEGER NOT NULL,
    Numero_Visite				INTEGER NOT NULL
);

CREATE TABLE Redevance
(
    Numero_Redevance	SERIAL PRIMARY KEY,
    Numero_Compte		INTEGER NOT NULL,
    Numero_Visite		INTEGER NOT NULL,
    Montant				MONEY,
    Date_Creation		TIMESTAMP,
    Est_Reclame			BOOLEAN
);

CREATE TABLE SiteWebAdmin
(
    Numero_SiteWebAdmin	SERIAL PRIMARY KEY,
    Numero_Compte		INTEGER NOT NULL,
    Url					VARCHAR(150)
);

CREATE TABLE InfoDeSuivi
(
    Numero_InfoDeSuivi		SERIAL PRIMARY KEY,
    Numero_SiteWebAdmin		INTEGER NOT NULL,
    Empreinte				VARCHAR(100),
    UrlActuel				VARCHAR(150),
    UrlProvenance			VARCHAR(150),
    Adresse_IPV4			VARCHAR(100),
    Adresse_IPV6			VARCHAR(100),
    Taille_Ecran			VARCHAR(100),
    Langue					VARCHAR(50),
    TempsEcoule				TIME,
    Date_Heure				TIMESTAMP
);

CREATE TABLE AgentUtilisateur
(
    Numero_AgentUtilisateur		SERIAL PRIMARY KEY,
    Numero_InfoDeSuivi			INTEGER NOT NULL,
    AgentUtilisateurBrut		VARCHAR(250),
    VersionNavigateur			VARCHAR(100),
    Systeme_Operation			VARCHAR(100),
    Information_Navigateur		VARCHAR(100),
    Plateforme					VARCHAR(100),
    Information_Plateforme		VARCHAR(100),
    Extension_Navigateur		VARCHAR(100),
    Date_Heure				TIMESTAMP
);

CREATE TABLE TypeNavigateur
(
    Numero_TypeNavigateur	SERIAL PRIMARY KEY,
    Nom						VARCHAR(50)
);

CREATE TABLE TypeNavigateur_Agent
(
    Numero_TypeNavigateur_Agent		SERIAL PRIMARY KEY,
    Numero_TypeNavigateur			INTEGER NOT NULL,
    Numero_AgentUtilisateur			INTEGER NOT NULL
);