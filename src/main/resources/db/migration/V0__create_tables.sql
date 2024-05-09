create table if not exists category
(
    category_id   serial unique primary key,
    category_name varchar(255) unique not null
);

create table if not exists mcc_per_category
(
    category_id integer references category (category_id) on delete cascade,
    mcc         varchar(4) not null unique,
    primary key (category_id, mcc)
);

create table if not exists category_per_category
(
    parent_category_id integer references category (category_id) on delete cascade,
    child_category_id  integer references category (category_id) on delete cascade,
    primary key (parent_category_id, child_category_id)
);