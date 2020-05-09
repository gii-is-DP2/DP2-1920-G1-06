-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','v',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','1',TRUE);
INSERT INTO authorities VALUES ('owner1','owner');

INSERT INTO users(username,password,enabled) VALUES ('owner2','2',TRUE);
INSERT INTO authorities VALUES ('owner2','owner');

INSERT INTO users(username,password,enabled) VALUES ('owner3','3',TRUE);
INSERT INTO authorities VALUES ('owner3','owner');

INSERT INTO owners(first_name,last_name,telephone,username,gender,email,dni,birth_date) VALUES ('George', 'Franklin','6085551023', 'owner1',0,'email@email.es','0808129-D','2015-10-07');
INSERT INTO owners(first_name,last_name,telephone,username,gender,email,dni,birth_date) VALUES ('Georg', 'Franklin','6085551023', 'owner2',1,'email@email.es','0808129-D','2015-10-06');
INSERT INTO owners(first_name,last_name,telephone,username,gender,email,dni,birth_date) VALUES ('Georgina', 'Franklin','6085551023', 'owner3',1,'email@email.es','0808139-D','2015-10-06');



INSERT INTO users(username,password,enabled) VALUES ('student1','1',TRUE);
INSERT INTO authorities VALUES ('student1','student');

INSERT INTO users(username,password,enabled) VALUES ('student2','2',TRUE);
INSERT INTO authorities VALUES ('student2','student');

INSERT INTO users(username,password,enabled) VALUES ('student3','3',TRUE);
INSERT INTO authorities VALUES ('student3','student');

INSERT INTO students(first_name,last_name,telephone,username,gender,email,dni,birth_date) VALUES ('Paco', 'Franklin','6085551023', 'student1',0,'email@email.es','0808129-D','2015-10-07');
INSERT INTO students(first_name,last_name,telephone,username,gender,email,dni,birth_date) VALUES ('Manuel', 'Franklin','6085551023', 'student2',1,'email@email.es','0808129-D','2015-10-06');
INSERT INTO students(first_name,last_name,telephone,username,gender,email,dni,birth_date) VALUES ('Pedro', 'Franklin','6085551023', 'student3',1,'email@email.es','0808139-D','2015-10-06');



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

INSERT INTO rentals(start_date,end_date,price_month,isarequest,is_accepted,room_id,student_id,owner_id) VALUES ('2020-06-01', '2020-06-10', 20.0, false, true, 1, 1, 1);
INSERT INTO rentals(start_date,end_date,price_month,isarequest,is_accepted,room_id,student_id,owner_id) VALUES ('2019-06-01', '2019-06-10', 20.0, false, true, 1, 1, 1);
