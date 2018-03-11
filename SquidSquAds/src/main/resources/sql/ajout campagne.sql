--compteUtilisateur
INSERT INTO CompteUtilisateur VALUES
  (DEFAULT,'PUB','sa@squidsquads.site','$2y$10$AAALv7ANjHob1V5nl2wjXOEjs074bxqFy9UvtAZn2DJB.yeEkiadG','123-12345',NOW());


INSERT INTO Campagne VALUES
  (
    DEFAULT,
    (select max(compteutilisateur.id_compte) from compteUtilisateur),
    'Campagne SquidSquAds',
    NOW(),
    'https://i.imgur.com/eZUxeDA.png',
    'https://i.imgur.com/fdtZzwJ.png',
    'https://i.imgur.com/BdMNBkM.png',
    'https://www.squidsquads.site',
    NOW(),
    NOW(),
    1
  );

INSERT INTO ProfilUtilisateur VALUES
  (DEFAULT,
   (select max(compteutilisateur.id_compte) from compteUtilisateur),
   'SquidSquAds Profile',
   'A super admin profile for custom SquidSquAds banners.',
   NOW()
  );

INSERT INTO Campagne_ProfilUtilisateur VALUES
  (DEFAULT,
   (SELECT MAX(id_compte) FROM Campagne),
   (SELECT MAX(profilutilisateur.id_profilutilisateur) FROM ProfilUtilisateur)
  );

INSERT INTO Site VALUES
  (DEFAULT,
   (SELECT MAX(profilutilisateur.id_profilutilisateur) FROM ProfilUtilisateur),
   'https://www.squidsquads.site'
  );

