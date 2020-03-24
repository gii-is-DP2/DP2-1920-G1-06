-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','v',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner1','owner');


INSERT INTO owners(first_name,last_name,telephone,username,gender,email,dni,birth_date) VALUES ('George', 'Franklin','6085551023', 'owner1',1,'email@email.es','0808129-D','2015-10-06');
INSERT INTO owners(first_name,last_name,telephone,username,gender,email,dni,birth_date) VALUES ('George', 'Franklin','6085551023', 'owner1',0,'email@email.es','0808129-D','2015-10-07');


INSERT INTO properties(address,city,description,property_type,surface,total_rooms,owner_id) VALUES ('Base Militar de Rotas', 'Cadiz','Con muchos militares', 0, 90,4,1);
INSERT INTO properties(address,city,description,property_type,surface,total_rooms,owner_id) VALUES ('Base Militar de Potas', 'Cadiz','Con muchos borrachos', 1, 30,4,1);
INSERT INTO properties(address,city,description,property_type,surface,total_rooms,owner_id) VALUES ('Base Militar de Jotas', 'Cadiz','Con muchos aragoneses', 1, 450,4,1);
INSERT INTO properties(address,city,description,property_type,surface,total_rooms,owner_id) VALUES ('Base Militar de Motas', 'Cadiz','Con muchos politoxicomanos', 0, 101,4,2);
INSERT INTO properties(address,city,description,property_type,surface,total_rooms,owner_id) VALUES ('Base Militar de Botas', 'Cadiz','Con muchos futbolistas', 1, 120,24,2);
INSERT INTO properties(address,city,description,property_type,surface,total_rooms,owner_id) VALUES ('Base Militar de Sotas', 'Cadiz','Con muchos jugadores de cuatrola', 0, 210,10,2);

INSERT INTO rooms(ext_window,tam_closet,price,room_number,surface,property_id) VALUES (2, 2, 10.0, '1', 10, 1);
INSERT INTO rooms(ext_window,tam_closet,price,room_number,surface,property_id) VALUES (1, 2, 11.0, '2', 10, 1);
INSERT INTO rooms(ext_window,tam_closet,price,room_number,surface,property_id) VALUES (0, 2, 12.0, '3', 10, 1);
INSERT INTO rooms(ext_window,tam_closet,price,room_number,surface,property_id) VALUES (0, 2, 13.0, '4', 10, 1);
INSERT INTO rooms(ext_window,tam_closet,price,room_number,surface,property_id) VALUES (1, 2, 10.0, '1', 8, 2);