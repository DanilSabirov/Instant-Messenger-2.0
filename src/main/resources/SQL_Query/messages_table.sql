DROP TABLE IF EXISTS message;

CREATE TABLE message(
	messageid bigserial PRIMARY KEY,
	autorid integer REFERENCES users(userid),
	date TIMESTAMP WITH TIME ZONE,
	textmess text
)