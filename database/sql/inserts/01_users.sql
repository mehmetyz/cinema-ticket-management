USE SQLCinema;



CALL create_user("admin@sqlcinema.com", "admin", "$2a$09$/pWE.GSUH5NhmR4N5Y5gSuiDy9lO8Kv40EsB5epOijuTQ3XbCzNwu");
CALL create_user("mehmet@sqlcinema.com", "mehmet", "$2a$09$a2XQ1sogwesCQ82T4V403.HK2SilUuVK3yr0IPQhiKGexoNZ9HUhW");
CALL create_user("ozgur@sqlcinema.com", "ozgur", "$2a$09$6S2pdExo8FSaot4FUT.yfOI3wV8bMDbybh2WAXYBktBd1dKfUqJIa");
-- ASSIGN MANAGERS
CALL assign_manager((SELECT user_id FROM UserAccount WHERE username = "admin"), "ADMIN");
CALL assign_manager((SELECT user_id FROM UserAccount WHERE username = "mehmet"), "USER_MANAGER");
CALL assign_manager((SELECT user_id FROM UserAccount WHERE username = "ozgur"), "THEATRE_MANAGER");