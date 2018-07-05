TRUNCATE authentication_data, dialogs, message, users CASCADE;
SELECT setval('dialog_dialogid_seq', 1); 
SELECT setval('message_messageid_seq', 1); 
SELECT setval('users_userid_seq', 1); 