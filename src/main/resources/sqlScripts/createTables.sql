use test3;

create table role (id bigint not null auto_increment, role varchar(255), primary key (id)) engine=InnoDB;
create table users (id bigint not null auto_increment, birthdate date, enabled bit, name varchar(255), password varchar(255), surname varchar(255), primary key (id)) engine=InnoDB;
create table users_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=InnoDB;