-- Primary Key
--ALTER TABLE CompteUtilisateur ADD CONSTRAINT pk_compte_utilisateur PRIMARY KEY(Id_Compte);
--ALTER TABLE Paiement ADD CONSTRAINT pk_paiement PRIMARY KEY(Id_Paiement);
--ALTER TABLE Campagne ADD CONSTRAINT pk_campagne PRIMARY KEY(Id_Campagne);
--ALTER TABLE ProfilUtilisateur ADD CONSTRAINT pk_profilutilisateur PRIMARY KEY(Id_ProfilUtilisateur);
--ALTER TABLE Campagne_ProfilUtilisateur ADD CONSTRAINT pk_campagne_profilutilisateur PRIMARY KEY(Id_Campagne_ProfilUtilisateur);
--ALTER TABLE Site ADD CONSTRAINT pk_site PRIMARY KEY(Id_Site);
--ALTER TABLE Banniere ADD CONSTRAINT pk_banniere PRIMARY KEY(Id_Banniere);
--ALTER TABLE Visite ADD CONSTRAINT pk_visite PRIMARY KEY(Id_visite);
--ALTER TABLE Redevance ADD CONSTRAINT pk_redevance PRIMARY KEY(Id_Redevance);
--ALTER TABLE SiteWebAdmin ADD CONSTRAINT pk_sitewebadmin PRIMARY KEY(Id_SiteWebAdmin);
--ALTER TABLE InfoDeSuivi ADD CONSTRAINT pk_infodesuivi PRIMARY KEY(Id_InfoDeSuivi);
--ALTER TABLE AgentUtilisateur ADD CONSTRAINT pk_agentutilisateur PRIMARY KEY(Id_AgentUtilisateur);

-- Foreign Key

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