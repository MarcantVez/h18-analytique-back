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
    Id_Compte		SERIAL PRIMARY KEY,
    Type_Admin			VARCHAR(3),
    Courriel			VARCHAR(50),
    Mot_De_Passe		VARCHAR(50),
    No_Compte_Banque	VARCHAR(50),
    Date_Creation		TIMESTAMP
);

CREATE TABLE Paiement
(
    Id_Paiement		SERIAL PRIMARY KEY,
    Id_Compte		INTEGER NOT NULL,
    Montant				MONEY,
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
    Budget				MONEY
);

CREATE TABLE ProfilDUtilisateur
(
    Id_ProfilDUtilisateur	SERIAL PRIMARY KEY,
    Id_Compte				INTEGER NOT NULL,
    Nom							VARCHAR(50),
    Description					VARCHAR(200),
    Date_Creation				TIMESTAMP
);

CREATE TABLE Campagne_ProfilDUtilisateur
(
    Id_Campagne_ProfilDUtilisateur	SERIAL PRIMARY KEY,
    Id_ProfilDUtilisateur			INTEGER NOT NULL,
    Id_Campagne						INTEGER NOT NULL
);

CREATE TABLE Site
(
    Id_Site					SERIAL PRIMARY KEY,
    Id_ProfilDUtilisateur	INTEGER NOT NULL,
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
    Montant				MONEY,
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
    Id_SiteWebAdmin		INTEGER NOT NULL,
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
    Id_AgentUtilisateur		SERIAL PRIMARY KEY,
    Id_InfoDeSuivi			INTEGER NOT NULL,
    AgentUtilisateurBrut		VARCHAR(250),
    VersionNavigateur			VARCHAR(100),
    Systeme_Operation			VARCHAR(100),
    Information_Navigateur		VARCHAR(100),
    Plateforme					VARCHAR(100),
    Information_Plateforme		VARCHAR(100),
    Extension_Navigateur		VARCHAR(100),
    Navigateur 					VARCHAR(100),
    Date_Heure				TIMESTAMP
);