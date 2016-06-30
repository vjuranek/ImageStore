CREATE TABLE client_version(id SERIAL PRIMARY KEY, major smallint, minor smallint, released timestamp);
ALTER SEQUENCE client_version_id_seq INCREMENT BY 50; -- Hibernate default allocation size

CREATE TABLE client(id SERIAL PRIMARY KEY, version_id integer REFERENCES client_version);
ALTER SEQUENCE client_id_seq INCREMENT BY 50; -- Hibernate default allocation size

CREATE TABLE image(id SERIAL PRIMARY KEY, client_id integer REFERENCES client, path varchar(4096), name varchar(256), sha256 varchar(256), created timestamp, uploaded timestamp, upload_finished boolean);
ALTER SEQUENCE image_id_seq INCREMENT BY 50; -- Hibernate default allocation size

CREATE TABLE roles(id SERIAL PRIMARY KEY, role VARCHAR(32) NOT NULL, description VARCHAR(256));
ALTER SEQUENCE roles_id_seq INCREMENT BY 50; -- Hibernate default allocation size

CREATE TABLE users(id SERIAL PRIMARY KEY, username VARCHAR(256) NOT NULL, password VARCHAR(256) NOT NULL);
ALTER SEQUENCE users_id_seq INCREMENT BY 50; -- Hibernate default allocation size

CREATE TABLE user_roles(user_id integer REFERENCES users, role_id integer REFERENCES roles);
