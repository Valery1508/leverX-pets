create table person(
    id serial PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50)
);
create table pet(
    id serial PRIMARY KEY,
    name VARCHAR(50),
    type VARCHAR(10),
    person_id bigint,
    FOREIGN KEY (person_id) REFERENCES person (id)
);