INSERT INTO tag(name) VALUES ('Boat');
INSERT INTO tag(name) VALUES ('TV');
INSERT INTO tag(name) VALUES ('Wi-fi');

INSERT INTO fishing_equipment(name) VALUES ('Boat');
INSERT INTO fishing_equipment(name) VALUES ('Fishing rods');
INSERT INTO fishing_equipment(name) VALUES ('Baits');


INSERT INTO photo(id, original_filename, stored_filename,
"size")
VALUES ('2d74b08c-b270-4d7a-ae26-c278bd13816c',
'onetwothree.png', '123123123.png', 3515313);

INSERT INTO photo(id, original_filename, stored_filename,
"size")
VALUES ('6b59eb17-a24f-4044-8868-8120a2168960',
'fourfivesix.png', '456456456.png', 4564636);

INSERT INTO photo(id, original_filename, stored_filename,
"size")
VALUES ('ac29818c-5e95-438c-85ff-da0a25cd188c',
'seveneightnine.png', '789789789.png', 1335053);

INSERT INTO customer(id, avatar_id, first_name, last_name,
email_address, address, city, country_code, phone_number,
password_hash, active) VALUES
(1, '2d74b08c-b270-4d7a-ae26-c278bd13816c', 'John', 'Smith',
'smith25@email.com', 'Karadjordjeva bb', 'Novi Sad',
'RS', '0696969696', 'password', true);
