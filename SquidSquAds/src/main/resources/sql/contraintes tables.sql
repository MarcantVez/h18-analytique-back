-- primary key
--alter table compteutilisateur add constraint pk_compte_utilisateur primary key(numero_compte);
--alter table paiement add constraint pk_paiement primary key(numero_paiement);
--alter table campagne add constraint pk_campagne primary key(numero_campagne);
--alter table profildutilisateur add constraint pk_profildutilisateur primary key(numero_profildutilisateur);
--alter table campagne_profildutilisateur add constraint pk_campagne_profildutilisateur primary key(numero_campagne_profildutilisateur);
--alter table site add constraint pk_site primary key(numero_site);
--alter table banniere add constraint pk_banniere primary key(numero_banniere);
--alter table orientation add constraint pk_orientation primary key(numero_orientation);
--alter table orientation_banniere add constraint pk_orientation_banniere primary key (numero_orientation_banniere);
--alter table visite add constraint pk_visite primary key(numero_visite);
--alter table categorie add constraint pk_categorie primary key(numero_categorie);
--alter table categorie_visite add constraint pk_categorie_visite primary key (numero_categorie_visite);
--alter table redevance add constraint pk_redevance primary key(numero_redevance);
--alter table sitewebadmin add constraint pk_sitewebadmin primary key(numero_sitewebadmin);
--alter table infodesuivi add constraint pk_infodesuivi primary key(numero_infodesuivi);
--alter table agentutilisateur add constraint pk_agentutilisateur primary key(numero_agentutilisateur);
--alter table typenavigateur add constraint pk_typenavigateur primary key (numero_typenavigateur);
--alter table typenavigateur_agent add constraint pk_typenavigateur_agent primary key(numero_typenavigateur_agent);

-- foreign key

-- cle table paiement
alter table paiement add constraint fk_paiement_no_compte foreign key (numero_compte) references compteutilisateur (numero_compte);

-- cle table campagne
alter table campagne add constraint fk_campagne_no_compte foreign key (numero_compte) references compteutilisateur (numero_compte);

-- cle table profildutilisateur
alter table profildutilisateur add constraint fk_profildutilisateur_no_compte foreign key (numero_compte) references compteutilisateur (numero_compte);

-- cle table campagne_profildutilisateur
alter table campagne_profildutilisateur add constraint fk_campagne_profildutilisateur_no_profildutilisateur foreign key (numero_profildutilisateur) references profildutilisateur (numero_profildutilisateur);
alter table campagne_profildutilisateur add constraint fk_campagne_profildutilisateur_no_campagne foreign key (numero_campagne) references campagne (numero_campagne);

-- cle table site
alter table site add constraint fk_site_no_profildutilisateur foreign key (numero_profildutilisateur) references profildutilisateur (numero_profildutilisateur);

-- cle table banniere
alter table banniere add constraint fk_banniere_no_compte foreign key (numero_compte) references compteutilisateur (numero_compte);

-- cle table orientation_banniere
alter table orientation_banniere add constraint fk_orientation_banniere_no_banniere foreign key (numero_banniere) references banniere (numero_banniere);
alter table orientation_banniere add constraint fk_orientation_banniere_no_orientation foreign key (numero_orientation) references orientation (numero_orientation);

-- cle table visite
alter table visite add constraint fk_visite_no_banniere foreign key (numero_banniere) references banniere (numero_banniere);

-- cle table categorie_visite
alter table categorie_visite add constraint fk_categorie_visite_no_visite foreign key (numero_visite) references visite (numero_visite);
alter table categorie_visite add constraint fk_categorie_visite_no_categorie foreign key (numero_categorie) references categorie (numero_categorie);

-- cle table redevance
alter table redevance add constraint fk_redevance_no_compte foreign key (numero_compte) references compteutilisateur (numero_compte);
alter table redevance add constraint fk_redevance_no_visite foreign key (numero_visite) references visite (numero_visite);

-- cle table sitewebadmin
alter table sitewebadmin add constraint fk_sitewebadmin_no_compte foreign key (numero_compte) references compteutilisateur (numero_compte);

-- cle table infodesuivi
alter table infodesuivi add constraint fk_infodesuivi_no_sitewebadmin foreign key (numero_sitewebadmin) references sitewebadmin (numero_sitewebadmin);

-- cle table agentutilisateur
alter table agentutilisateur add constraint fk_agentutilisateur_no_infodesuivi foreign key (numero_infodesuivi) references infodesuivi (numero_infodesuivi);

-- cle table typenavigateur_agent
alter table typenavigateur_agent add constraint fk_typenavigateur_agent_no_typenavigateur foreign key (numero_typenavigateur) references typenavigateur (numero_typenavigateur);
alter table typenavigateur_agent add constraint fk_typenavigateur_agent_no_agentutilisateur foreign key (numero_agentutilisateur) references agentutilisateur (numero_agentutilisateur);

