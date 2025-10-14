create table orders
(
    id          bigint auto_increment
        primary key,
    customer_id bigint                        not null,
    status      varchar(20) default 'PENDING' not null,
    created_at  datetime                      not null,
    total_price decimal(10, 2)                not null,
    constraint orders_users_id_fk
        foreign key (customer_id) references users (id)
);


