create table if not exists category
(
    category_id          serial unique       not null,
    category_name        varchar(255) unique not null,
    category_description text
);

create table if not exists mcc_per_category
(
    category_id integer references category (category_id) on delete cascade,
    mcc         integer not null
)