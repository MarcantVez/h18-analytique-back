--compteUtilisateur
INSERT INTO compteUtilisateur VALUES
  (DEFAULT,'WEB','adminWeb@test.com','mot_de_passe_en_clair','111AAA',NOW());


INSERT INTO SiteWebAdmin VALUES
  (DEFAULT,(select max(compteutilisateur.id_compte) from compteUtilisateur),'https://www.siteWebA.com');


INSERT INTO AgentUtilisateur VALUES
  (DEFAULT,
   'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36',
   '63.0.3239.132',
   'Windows NT 10.0',
   'Chrome/63.0.3239.132 Safari/537.36',
   'Win64',
   'x64',
   'Chrome',
   '2018-02-07 18:00'
  );

INSERT INTO InfoDeSuivi VALUES
  (DEFAULT,
    (select max(id_agentutilisateur) from agentutilisateur),
    (select max(id_compte) from compteUtilisateur),
    'e908f5b5ec9dedf0cb5bd964e1206f55',
    'https://www.siteWebA.com/produit/5',
    'https://www.siteWebA.com/produit/',
    '192.168.0.1',
    '2001:0db8:0000:85a3:0000:0000:ac1f:8001',
    '1920x1080',
    'fr-CA',
    10,
    '2018-02-07 18:00'
  );


INSERT INTO Banniere VALUES
  (DEFAULT,
   (select max(compteutilisateur.id_compte) from compteUtilisateur),
   'Horizontal'
  );

INSERT INTO Banniere VALUES
  (DEFAULT,
   (select max(compteutilisateur.id_compte) from compteUtilisateur),
   'Vertical'
  );

INSERT INTO Banniere VALUES
  (DEFAULT,
   (select max(compteutilisateur.id_compte) from compteUtilisateur),
   'Mobile'
  );

INSERT INTO Orientation_Banniere VALUES
  (DEFAULT,
   (SELECT MAX(numero_orientation) FROM Orientation),
   (SELECT MAX(numero_banniere) FROM Banniere)
  );

INSERT INTO Categorie VALUES
  (DEFAULT,
   'Une publicitée qui a été vue');

INSERT INTO Categorie VALUES
  (DEFAULT,
   'Une publicitée qui a été vue par un profil');

INSERT INTO Categorie VALUES
  (DEFAULT,
   'Une publicitée qui a été cliquée');

INSERT INTO Categorie VALUES
  (DEFAULT,
   'Une publicitée qui a été cliquée par un profil');

INSERT INTO Visite VALUES
  (DEFAULT,
   (SELECT MAX(banniere.id_banniere) FROM Banniere),
   '2018-02-07 18:00'
  );


INSERT INTO Redevance VALUES
  (DEFAULT,
   (select max(compteutilisateur.id_compte) from compteUtilisateur),
   (SELECT MAX(visite.id_visite) FROM Visite)
  );

INSERT INTO Paiement VALUES
  (DEFAULT,
   (select max(compteutilisateur.id_compte) from compteUtilisateur),
   25,
   '2018-02-07 18:00'
  );

INSERT INTO Campagne VALUES
  (DEFAULT,
    (select max(compteutilisateur.id_compte) from compteUtilisateur),
    'Campagne Alpha',
    '2018-02-07 18:00',
    'horizontal.jpg',
    'vertical.jpg',
    'mobile.jpg',
    'https://www.microsoft.com/fr-ca/hololens',
    '2018-02-08 18:00',
    '2020-02-08 18:00',
    200000
  );

INSERT INTO ProfilUtilisateur VALUES
  (DEFAULT,
   (select max(compteutilisateur.id_compte) from compteUtilisateur),
   'name profil',
   'description profil',
   '2018-02-07 18:00'
  );

INSERT INTO Campagne_ProfilUtilisateur VALUES
  (DEFAULT,
   (SELECT MAX(id_compte) FROM Campagne),
   (SELECT MAX(profilutilisateur.id_profilutilisateur) FROM ProfilUtilisateur)
  );

INSERT INTO Site VALUES
  (DEFAULT,
   (SELECT MAX(profilutilisateur.id_profilutilisateur) FROM ProfilUtilisateur),
   'https://www.siteWebA.com/produitB'
  );



--Deuxieme compte
INSERT INTO compteUtilisateur VALUES
  (DEFAULT,'PUB','adminPub@test.com','!Q@W#E4r','111BBB',NOW());

