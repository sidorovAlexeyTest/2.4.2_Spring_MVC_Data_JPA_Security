use test3;
insert into role(id, role) values(1, "ROLE_ADMIN");
insert into role(id, role) values(2, "ROLE_USER");
insert into users(id, name, surname, password, birthdate, enabled) values(1, "Alex", "Torkov", "password", '1989-06-02', true);
insert into users(id, name, surname, password, birthdate, enabled) values(2, "Igor", "Polishev", "password", '1989-04-01', true);
insert into users(id, name, surname, password, birthdate, enabled) values(3, "Олег", "Перельман", "password", '1980-10-11', true);
insert into users(id, name, surname, password, birthdate, enabled) values(4, "Vilan", "Koval", "password", '1981-09-08', true);

insert into users_roles(user_id, role_id) values(1, 1);
insert into users_roles(user_id, role_id) values(1, 2);
insert into users_roles(user_id, role_id) values(2, 2);
insert into users_roles(user_id, role_id) values(3, 2);