--compteUtilisateur
INSERT INTO compteUtilisateur VALUES
  (DEFAULT,'WEB','adminWeb@test.com','mot_de_passe_en_clair','111AAA',NOW());


INSERT INTO SiteWebAdmin VALUES
  (DEFAULT,(select max(numero_compte) from compteUtilisateur),'https://www.siteWebA.com');

INSERT INTO InfoDeSuivi VALUES
  (DEFAULT,
    (select max(numero_compte) from compteUtilisateur),
    'e908f5b5ec9dedf0cb5bd964e1206f55',
    'https://www.siteWebA.com/produit/5',
    'https://www.siteWebA.com/produit/',
    '192.168.0.1',
    '2001:0db8:0000:85a3:0000:0000:ac1f:8001',
    '1920x1080',
    'fr-CA',
    '00:00:20',
    '2018-02-07 18:00'
  );

INSERT INTO AgentUtilisateur VALUES
  (DEFAULT,
   (SELECT MAX(numero_InfoDeSuivi) FROM InfoDeSuivi),
   'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36',
   '63.0.3239.132',
   'Windows NT 10.0',
   'Chrome/63.0.3239.132 Safari/537.36',
   'Win64',
   'x64',
   '',
   '2018-02-07 18:00'
  );

INSERT INTO TypeNavigateur VALUES
  (DEFAULT,
   'Chrome'
  );

INSERT INTO TypeNavigateur_Agent VALUES
  (DEFAULT,
   (SELECT MAX(numero_AgentUtilisateur) FROM AgentUtilisateur),
   (SELECT MAX(numero_TypeNavigateur) FROM TypeNavigateur)
  );

INSERT INTO Banniere VALUES
  (DEFAULT,
   (select max(numero_compte) from compteUtilisateur),
   'FFF000'
  );

INSERT INTO Orientation VALUES
  (DEFAULT,
   'Horizontal'
  );

INSERT INTO Orientation VALUES
  (DEFAULT,
   'Vertical'
  );

INSERT INTO Orientation VALUES
  (DEFAULT,
   'Mobile'
  );

INSERT INTO Orientation_Banniere (numero_banniere, numero_orientation) VALUES
  (1, 1);

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
   (SELECT MAX(numero_banniere) FROM Banniere),
   '2018-02-07 18:00'
  );

INSERT INTO Categorie_Visite VALUES
  (DEFAULT,
   (SELECT MAX(numero_categorie) FROM Categorie),
   (SELECT MAX(numero_visite) FROM Visite)
  );

INSERT INTO Redevance VALUES
  (DEFAULT,
   (select max(numero_compte) from compteUtilisateur),
   (SELECT MAX(numero_visite) FROM Visite)
  );

INSERT INTO Paiement VALUES
  (DEFAULT,
   (select max(numero_compte) from compteUtilisateur),
   25,
   '2018-02-07 18:00'
  );

INSERT INTO Campagne VALUES
  (DEFAULT,
    (select max(numero_compte) from compteUtilisateur),
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

INSERT INTO ProfilDUtilisateur VALUES
  (DEFAULT,
   (select max(numero_compte) from compteUtilisateur),
   'nom profil',
   'description profil',
   '2018-02-07 18:00'
  );

INSERT INTO Campagne_ProfilDutilisateur VALUES
  (DEFAULT,
   (SELECT MAX(numero_campagne) FROM Campagne),
   (SELECT MAX(numero_profildutilisateur) FROM ProfilDUtilisateur)
  );

INSERT INTO Site VALUES
  (DEFAULT,
   (SELECT MAX(numero_profildutilisateur) FROM ProfilDUtilisateur),
   'https://www.siteWebA.com/produitB'
  );



--Deuxieme compte
INSERT INTO compteUtilisateur VALUES
  (DEFAULT,'PUB','adminPub@test.com','!Q@W#E4r','111BBB',NOW());

