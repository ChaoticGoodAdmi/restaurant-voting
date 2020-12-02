DELETE FROM restaurant;
DELETE FROM dish;
DELETE FROM menu_item;
DELETE FROM USERS;

INSERT INTO restaurant (id, name, address)
VALUES (100000, 'McDonalds', 'Street st., 10'),
       (100001, 'Tokio City', 'Prospect pr., 1');

INSERT INTO dish (id, name, price)
VALUES (100000, 'Burger', 200),
       (100001, 'Cola', 150),
       (100002, 'Soup', 1000),
       (100003, 'Fish', 1500),
       (100004, 'Milkshake', 300),
       (100005, 'Ice-cream', 500),
       (100006, 'Steak', 1500),
       (100007, 'Pasta', 1500);

INSERT INTO menu_item (id, restaurant_id, date, dish_id)
VALUES (100000, 100000, '2020-11-01', 100000),
       (100001, 100000, '2020-11-01', 100001),
       (100002, 100001, '2020-11-01', 100002),
       (100003, 100001, '2020-11-01', 100003),
       (100004, 100000, '2020-11-02', 100004),
       (100005, 100000, '2020-11-02', 100005),
       (100006, 100001, '2020-11-02', 100006),
       (100007, 100001, '2020-11-02', 100007);

INSERT INTO users (ID, NAME, EMAIL, PASSWORD, ENABLED)
VALUES (100000, 'user', 'user@user.com', 'pass', true),
       (100001, 'admin', 'admin@admin.com', 'admin', true);
