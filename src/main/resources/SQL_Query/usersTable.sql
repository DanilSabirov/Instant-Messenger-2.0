DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS users_userid_seq;

CREATE SEQUENCE users_userid_seq;

CREATE TABLE users (
	userid integer NOT NULL PRIMARY KEY DEFAULT nextval('users_userid_seq'),
	username character varying(20) NOT NULL,
	email text,
	isRemoved BOOLEAN DEFAULT FALSE
);