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
username, address, city, country_code, phone_number,
password, enabled) VALUES
(1, '2d74b08c-b270-4d7a-ae26-c278bd13816c', 'John', 'Smith',
'smith25@email.com', 'Karadjordjeva bb', 'Novi Sad',
'RS', '0696969696', 'password', true);

INSERT INTO public.advertiser(
    id, enabled, address, city, country_code, username, first_name, last_name, password, phone_number, avatar_id)
VALUES (2, TRUE , 'Mise Dimitrijevica 1', 'Novi Sad', 'SR', 'maja@gmail.com', 'Maja', 'Majic', 'maja', '12345678', 'ac29818c-5e95-438c-85ff-da0a25cd188c');

INSERT INTO public.address(
    id, address, city, country_code, latitude, longitude, postal_code, state)
VALUES (2, 'Gogoljeva 2', 'Novi Sad', 'SR', '1111.1', '1334.2', '21000', 'Srbija');
/*
INSERT INTO public.resort_ad(
    id, available_after, available_until, currency, description, pricing_description, rules, title, address_id, advertiser_id, check_in_time, check_out_time, number_of_beds)
VALUES (1, now(), now(), '$', 'jako jako lepa', 'nema popusta', 'pravila neka', 'Vila brvnara', 2, 2, now(), now(), 3);
*/
INSERT INTO public.boat_ad(
    id, available_after, available_until, currency, description, pricing_description, rules, title, address_id, advertiser_id, check_in_time, check_out_time, boat_length, boat_speed, boat_type, cancellation_fee, capacity, engine_number, engine_power)
VALUES (1, now(), now(), '$', 'jako jako lepa', 'nema popusta', 'pravila neka', 'Vila brvnara', 2, 2, now(), now(), 'xl', 'jako brz', 'jahta', 100, 20, 'puno', 'bas jaki');

INSERT INTO public.fishing_equipment_ads(
    fishing_equipment_id, advertisement_id)
VALUES (1, 1);
INSERT INTO public.fishing_equipment_ads(
    fishing_equipment_id, advertisement_id)
VALUES (2, 1);
INSERT INTO public.fishing_equipment_ads(
    fishing_equipment_id, advertisement_id)
VALUES (3, 1);

INSERT INTO public.tags_ads(
    tag_id, advertisement_id)
VALUES (1, 1);
INSERT INTO public.tags_ads(
    tag_id, advertisement_id)
VALUES (2, 1);
INSERT INTO public.tags_ads(
    tag_id, advertisement_id)
VALUES (3, 1);


-- INSERT INTO public.role(id, authority) VALUES (1, 'ADMIN');
-- INSERT INTO public.role(id, authority) VALUES (2, 'CUSTOMER');
-- INSERT INTO public.role(id, authority) VALUES (3, 'RESORT_OWNER');
-- INSERT INTO public.role(id, authority) VALUES (4, 'BOAT_OWNER');
-- INSERT INTO public.role(id, authority) VALUES (5, 'FISHING_INSTRUCTOR');