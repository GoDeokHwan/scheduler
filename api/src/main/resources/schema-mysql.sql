create table sample_user
(
    id           int auto_increment,
    name         varchar(40) null,
    phone_number varchar(15) null,
    constraint sample_user_id_uindex
        unique (id)
);

alter table sample_user
    add primary key (id);

