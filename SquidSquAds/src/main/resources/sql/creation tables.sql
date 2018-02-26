-- database: squidsquads

-- drop database squidsquads;




--create database squidsquads
--with
--owner = postgres
--encoding = 'utf8'
--lc_collate = 'english_canada.1252'
--lc_ctype = 'english_canada.1252'
--tablespace = pg_default
--connection limit = -1;


create table compteutilisateur
(
    numero_compte		serial primary key,
    type_admin			varchar(3),
    courriel			varchar(50),
    mot_de_passe		varchar(60),
    no_compte_banque	varchar(50),
    date_creation		timestamp
);

create table paiement
(
    numero_paiement		serial primary key,
    numero_compte		integer not null,
    montant				numeric(10, 2),
    date_paiement		timestamp
);

create table campagne
(
    numero_campagne		serial primary key,
    numero_compte		integer not null,
    nom					varchar(50),
    date_creation		timestamp,
    image_hor			varchar(100),
    image_ver			varchar(100),
    image_mob			varchar(100),
    url_de_redirection	varchar(100),
    date_debut			timestamp,
    date_fin			timestamp,
    budget				numeric(10, 2)
);

create table profildutilisateur
(
    numero_profildutilisateur	serial primary key,
    numero_compte				integer not null,
    nom							varchar(50),
    description					varchar(200),
    date_creation				timestamp
);

create table campagne_profildutilisateur
(
    numero	serial primary key,
    numero_campagne						integer not null,
    numero_profildutilisateur			integer not null

);

create table site
(
    numero_site					serial primary key,
    numero_profildutilisateur	integer not null,
    url							varchar(150)
);

create table banniere
(
    numero_banniere		serial primary key,
    numero_compte		integer not null,
    id_banniere				varchar(30)
);

create table orientation
(
    numero_orientation	serial primary key,
    nom					varchar(50)
);

create table orientation_banniere
(
    numero_orientation_banniere	serial primary key,
    numero_orientation			integer not null,
    numero_banniere				integer not null
);

create table visite
(
    numero_visite		serial primary key,
    numero_banniere		integer not null,
    date_heure			timestamp
);

create table categorie
(
    numero_categorie	serial primary key,
    nom					varchar(50)
);

create table categorie_visite
(
    numero_categorie_visite		serial primary key,
    numero_categorie			integer not null,
    numero_visite				integer not null
);

create table redevance
(
    numero_redevance	serial primary key,
    numero_compte		integer not null,
    numero_visite		integer not null,
    montant				numeric(10, 2),
    date_creation		timestamp,
    est_reclame			boolean
);

create table sitewebadmin
(
    numero_sitewebadmin	serial primary key,
    numero_compte		integer not null,
    url					varchar(150)
);

create table infodesuivi
(
    numero_infodesuivi		serial primary key,
    numero_sitewebadmin		integer not null,
    empreinte				varchar(100),
    urlactuel				varchar(150),
    urlprovenance			varchar(150),
    adresse_ipv4			varchar(100),
    adresse_ipv6			varchar(100),
    taille_ecran			varchar(100),
    langue					varchar(50),
    tempsecoule				time,
    date_heure				timestamp
);

create table agentutilisateur
(
    numero_agentutilisateur		serial primary key,
    numero_infodesuivi			integer not null,
    agentutilisateurbrut		varchar(250),
    versionnavigateur			varchar(100),
    systeme_operation			varchar(100),
    information_navigateur		varchar(100),
    plateforme					varchar(100),
    information_plateforme		varchar(100),
    extension_navigateur		varchar(100),
    date_heure				timestamp
);

create table typenavigateur
(
    numero_typenavigateur	serial primary key,
    nom						varchar(50)
);

create table typenavigateur_agent
(
    numero_typenavigateur_agent		serial primary key,
    numero_typenavigateur			integer not null,
    numero_agentutilisateur			integer not null
);