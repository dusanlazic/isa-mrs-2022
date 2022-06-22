
/*
INSERT INTO public.photo(id, original_filename, stored_filename,
"size")
VALUES ('2d74b08c-b270-4d7a-ae26-c278bd13816c',
'onetwothree.png', '123123123.png', 3515313);

INSERT INTO public.customer(id, avatar_id, first_name, last_name,
username, address, city, country_code, phone_number,
password, enabled, points, penalties) VALUES
(1001, '2d74b08c-b270-4d7a-ae26-c278bd13816c', 'John', 'Smith',
'customer@example.com', 'Karadjordjeva bb', 'Novi Sad',
'RS', '0696969696', 'password', true, 0, 0);

INSERT INTO public.advertiser(
    id, enabled, address, city, country_code, username, first_name, last_name,
    password, phone_number, avatar_id, points)
VALUES (1002, TRUE , 'Mise Dimitrijevica 1', 'Novi Sad', 'SR', 'advertiser@example.com', 'Maja', 'Majic', 'majamaja',
'1234567863', 'ac29818c-5e95-438c-85ff-da0a25cd188c', 0);

INSERT INTO public.address(
    id, address, city, country_code, latitude, longitude, postal_code, state)
VALUES (1001, 'Gogoljeva 2', 'Novi Sad', 'RS', '0.0', '0.0', '21000', 'Srbija');

INSERT INTO public.resort_ad(
    id, available_after, available_until, currency, description, pricing_description, rules,
    title, address_id, advertiser_id, check_in_time, check_out_time, number_of_beds, capacity, price_per_day)
VALUES (1001, now(), now(), '$', 'example description', 'no discounts',
'example rules', 'Resort 1001', 1001, 1002, now(), now(), 3, 10, 100);


INSERT INTO public.review(
    id, createdAt, advertisement, customer, rating, comment, approvalStatus)
VALUES (1001, now(), 1001, 5, "example comment", 0);
)
*/