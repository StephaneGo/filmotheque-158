delete from acteurs;
delete from films;
delete from  genres;
delete from  participants;
insert into genres ( id, libelle) values (1, 'Action');
insert into genres ( id, libelle) values (2, 'Animation');
insert into genres ( id, libelle) values (3, 'Comédie');
insert into genres ( id, libelle) values (4, 'Horreur');
insert into genres ( id, libelle) values (5, 'Thriller');
SET IDENTITY_INSERT PARTICIPANTS ON;
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (1, 'Steven', 'Spielberg');
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (2, 'David', 'Cronenberg');
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (3, 'Dany', 'Boon');
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (4, 'Richard', 'Attenborough');
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (5, 'Jeff', 'Goldblum');
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (6, 'Geena', 'Davis');
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (7, 'Mark', 'Rylance');
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (8, 'Ruby', 'Barnhill');
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (9, 'Kad', 'Merad');
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (10, 'Billy', 'Barnhill');
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (11, 'Billy', 'Wilder');
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (12, 'Marylin', 'Monroe');
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (13, 'Jack', 'Lemmon');
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (14, 'Tony', 'Curtis');
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (15, 'Shirley', 'Mac Laine');
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (16, 'Pierre', 'Coffin');
INSERT INTO PARTICIPANTS (id, prenom, nom) VALUES (17, 'Quentin', 'Dupieux');
SET IDENTITY_INSERT PARTICIPANTS OFF;
insert into films ( titre, annee, duree, synopsis, genreid, realisateurid) values ('Some like it hot', 1959, 120,'Lorsque deux musiciens sont témoins d''un succès populaire, ils fuient l''État dans un groupe entièrement féminin, déguisés en femmes, mais d''autres complications s''ensuivent'
    ,3, 11);
insert into acteurs (filmid, participantid) values ((select id from films where titre = 'Some like it hot'), 12)
insert into acteurs (filmid, participantid) values ((select id from films where titre = 'Some like it hot'), 13)
insert into acteurs (filmid, participantid) values ((select id from films where titre = 'Some like it hot'), 14)