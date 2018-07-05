DROP TABLE IF EXISTS dialogs;
DROP SEQUENCE IF EXISTS dialog_dialogid_seq;

CREATE SEQUENCE dialog_dialogid_seq;
CREATE TABLE dialogs(
	dialogid integer NOT NULL DEFAULT nextval('dialog_dialogid_seq') PRIMARY KEY
);