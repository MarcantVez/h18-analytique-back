--compteUtilisateur
INSERT INTO compteutilisateur VALUES
(0,'web','adminWeb@test.com','mot_de_passe_en_clair','111AAA',NOW());

INSERT INTO SiteWebAdmin VALUES
(0,(select max(numero_compte) from compteutilisateur),'https://www.000123AAA.com');

INSERT INTO compteutilisateur VALUES
(1,'pub','adminPub@test.com','!Q@W#E4r','111BBB',NOW());