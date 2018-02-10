-- Primary Key
--ALTER TABLE CompteUtilisateur ADD CONSTRAINT pk_compte_utilisateur PRIMARY KEY(Numero_Compte);
--ALTER TABLE Paiement ADD CONSTRAINT pk_paiement PRIMARY KEY(Numero_Paiement);
--ALTER TABLE Campagne ADD CONSTRAINT pk_campagne PRIMARY KEY(Numero_Campagne);
--ALTER TABLE ProfilDUtilisateur ADD CONSTRAINT pk_profildutilisateur PRIMARY KEY(Numero_ProfilDUtilisateur);
--ALTER TABLE Campagne_ProfilDUtilisateur ADD CONSTRAINT pk_campagne_profildutilisateur PRIMARY KEY(Numero_Campagne_ProfilDUtilisateur);
--ALTER TABLE Site ADD CONSTRAINT pk_site PRIMARY KEY(Numero_Site);
--ALTER TABLE Banniere ADD CONSTRAINT pk_banniere PRIMARY KEY(Numero_Banniere);
--ALTER TABLE Orientation ADD CONSTRAINT pk_orientation PRIMARY KEY(Numero_Orientation);
--ALTER TABLE Orientation_Banniere ADD CONSTRAINT pk_orientation_banniere PRIMARY KEY (Numero_Orientation_Banniere);
--ALTER TABLE Visite ADD CONSTRAINT pk_visite PRIMARY KEY(Numero_visite);
--ALTER TABLE Categorie ADD CONSTRAINT pk_categorie PRIMARY KEY(Numero_Categorie);
--ALTER TABLE Categorie_Visite ADD CONSTRAINT pk_categorie_visite PRIMARY KEY (Numero_Categorie_Visite);
--ALTER TABLE Redevance ADD CONSTRAINT pk_redevance PRIMARY KEY(Numero_Redevance);
--ALTER TABLE SiteWebAdmin ADD CONSTRAINT pk_sitewebadmin PRIMARY KEY(Numero_SiteWebAdmin);
--ALTER TABLE InfoDeSuivi ADD CONSTRAINT pk_infodesuivi PRIMARY KEY(Numero_InfoDeSuivi);
--ALTER TABLE AgentUtilisateur ADD CONSTRAINT pk_agentutilisateur PRIMARY KEY(Numero_AgentUtilisateur);
--ALTER TABLE TypeNavigateur ADD CONSTRAINT pk_typenavigateur PRIMARY KEY (Numero_TypeNavigateur);
--ALTER TABLE TypeNavigateur_Agent ADD CONSTRAINT pk_typenavigateur_agent PRIMARY KEY(Numero_TypeNavigateur_Agent);

-- Foreign Key

-- CLE TABLE Paiement
ALTER TABLE Paiement ADD CONSTRAINT fk_paiement_no_compte FOREIGN KEY (Numero_Compte) REFERENCES CompteUtilisateur (Numero_Compte);

-- CLE TABLE Campagne
ALTER TABLE Campagne ADD CONSTRAINT fk_campagne_no_compte FOREIGN KEY (Numero_Compte) REFERENCES CompteUtilisateur (Numero_Compte);

-- CLE TABLE ProfilDUtilisateur
ALTER TABLE ProfilDUtilisateur ADD CONSTRAINT fk_profildutilisateur_no_compte FOREIGN KEY (Numero_Compte) REFERENCES CompteUtilisateur (Numero_Compte);

-- CLE TABLE Campagne_ProfilDUtilisateur
ALTER TABLE Campagne_ProfilDUtilisateur ADD CONSTRAINT fk_campagne_profildutilisateur_no_profildutilisateur FOREIGN KEY (Numero_ProfilDUtilisateur) REFERENCES ProfilDUtilisateur (Numero_ProfilDUtilisateur);
ALTER TABLE Campagne_ProfilDUtilisateur ADD CONSTRAINT fk_campagne_profildutilisateur_no_campagne FOREIGN KEY (Numero_Campagne) REFERENCES Campagne (Numero_Campagne);

-- CLE TABLE Site
ALTER TABLE Site ADD CONSTRAINT fk_site_no_profildutilisateur FOREIGN KEY (Numero_ProfilDUtilisateur) REFERENCES ProfilDUtilisateur (Numero_ProfilDUtilisateur);

-- CLE TABLE Banniere
ALTER TABLE Banniere ADD CONSTRAINT fk_banniere_no_compte FOREIGN KEY (Numero_Compte) REFERENCES CompteUtilisateur (Numero_Compte);

-- CLE TABLE Orientation_Banniere
ALTER TABLE Orientation_Banniere ADD CONSTRAINT fk_orientation_banniere_no_banniere FOREIGN KEY (numero_banniere) REFERENCES Banniere (numero_banniere);
ALTER TABLE Orientation_Banniere ADD CONSTRAINT fk_orientation_banniere_no_orientation FOREIGN KEY (Numero_Orientation) REFERENCES Orientation (Numero_Orientation);

-- CLE TABLE Visite
ALTER TABLE Visite ADD CONSTRAINT fk_visite_no_banniere FOREIGN KEY (Numero_Banniere) REFERENCES Banniere (Numero_Banniere);

-- CLE TABLE Categorie_Visite
ALTER TABLE Categorie_Visite ADD CONSTRAINT fk_categorie_visite_no_visite FOREIGN KEY (Numero_Visite) REFERENCES Visite (Numero_Visite);
ALTER TABLE Categorie_Visite ADD CONSTRAINT fk_categorie_visite_no_categorie FOREIGN KEY (Numero_Categorie) REFERENCES Categorie (Numero_Categorie);

-- CLE TABLE Redevance
ALTER TABLE Redevance ADD CONSTRAINT fk_redevance_no_compte FOREIGN KEY (Numero_Compte) REFERENCES CompteUtilisateur (Numero_Compte);
ALTER TABLE Redevance ADD CONSTRAINT fk_redevance_no_visite FOREIGN KEY (Numero_Visite) REFERENCES Visite (Numero_Visite);

-- CLE TABLE SiteWebAdmin
ALTER TABLE SiteWebAdmin ADD CONSTRAINT fk_sitewebadmin_no_compte FOREIGN KEY (Numero_Compte) REFERENCES CompteUtilisateur (Numero_Compte);

-- CLE TABLE InfoDeSuivi
ALTER TABLE InfoDeSuivi ADD CONSTRAINT fk_infodesuivi_no_sitewebadmin FOREIGN KEY (Numero_SiteWebAdmin) REFERENCES SiteWebAdmin (Numero_SiteWebAdmin);

-- CLE TABLE AgentUtilisateur
ALTER TABLE AgentUtilisateur ADD CONSTRAINT fk_agentutilisateur_no_infodesuivi FOREIGN KEY (Numero_InfoDeSuivi) REFERENCES InfoDeSuivi (Numero_InfoDeSuivi);

-- CLE TABLE TypeNavigateur_Agent
ALTER TABLE TypeNavigateur_Agent ADD CONSTRAINT fk_typenavigateur_agent_no_typenavigateur FOREIGN KEY (Numero_TypeNavigateur) REFERENCES TypeNavigateur (Numero_TypeNavigateur);
ALTER TABLE TypeNavigateur_Agent ADD CONSTRAINT fk_typenavigateur_agent_no_agentutilisateur FOREIGN KEY (Numero_AgentUtilisateur) REFERENCES AgentUtilisateur (Numero_AgentUtilisateur);

