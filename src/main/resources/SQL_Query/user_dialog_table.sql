DROP TABLE IF EXISTS user_dialog;

CREATE TABLE user_dialog(
	userid integer REFERENCES users(userid),
	dialogid integer REFERENCES dialogs(dialogid)
);