create table if not exists cart_item
(
    id         bigint auto_increment
        primary key,
    cart_id    binary(16) not null,
    product_id bigint      not null,
    quantity   int         not null,
    constraint fk_cart_items_to_cart
        foreign key (cart_id) references cart (id)
            on delete cascade,
    constraint fk_products_in_cart
        foreign key (product_id) references products (id)
            on delete cascade
);