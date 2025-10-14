alter table orders
    alter column created_at set default (current_timestamp);

