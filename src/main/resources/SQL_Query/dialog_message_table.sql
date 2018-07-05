DROP TABLE IF EXISTS dialog_message;

CREATE TABLE dialog_message(
	dialogid integer REFERENCES dialogs(dialogid),
	messageid bigint REFERENCES message(messageid)
)