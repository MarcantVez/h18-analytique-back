--compteUtilisateur
INSERT INTO compteutilisateur VALUES
(0,'WEB','adminWeb@test.com','mot_de_passe_en_clair','111AAA',NOW());

INSERT INTO SiteWebAdmin VALUES
(0,(select max(numero_compte) from compteutilisateur),'https://www.000123AAA.com');

INSERT INTO compteutilisateur VALUES
(1,'PUB','adminPub@test.com','!Q@W#E4r','111BBB',NOW());