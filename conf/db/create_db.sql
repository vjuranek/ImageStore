CREATE TABLE client_version(id SERIAL PRIMARY KEY, major smallint, minor smallint, released timestamp);
ALTER SEQUENCE client_version_id_seq INCREMENT BY 50; -- Hibernate default allocation size

CREATE TABLE client(id SERIAL PRIMARY KEY, version_id integer REFERENCES client_version);
ALTER SEQUENCE client_id_seq INCREMENT BY 50; -- Hibernate default allocation size

CREATE TABLE image(id SERIAL PRIMARY KEY, client_id integer REFERENCES client, path varchar(4096), name varchar(256), sha256 varchar(256), created timestamp, uploaded timestamp, upload_finished boolean);
ALTER SEQUENCE image_id_seq INCREMENT BY 50; -- Hibernate default allocation size
