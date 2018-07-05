DROP TABLE IF EXISTS authentication_data;

CREATE TABLE authentication_data(
	login character varying(20) NOT NULL PRIMARY KEY CHECK (length(login) >= 6),
	passwd character varying(256) NOT NULL,
	userid integer REFERENCES users(userid)
);