-- Data for Name: filter; Type: TABLE DATA
INSERT INTO filter (id, name, selection) VALUES (1, 'My Filter 1', 'SELECT_1');
INSERT INTO filter (id, name, selection) VALUES (2, 'My Filter 2', 'SELECT_2');

-- Data for Name: criterion; Type: TABLE DATA
INSERT INTO criterion (id, operator, type, value, filter_id) VALUES (1, 'GREATER_THAN', 'AMOUNT', '5', 1);
INSERT INTO criterion (id, operator, type, value, filter_id) VALUES (2, 'EQUALS', 'TITLE', 'test', 1);
INSERT INTO criterion (id, operator, type, value, filter_id) VALUES (3, 'UNTIL', 'DATE', '2022-04-15', 2);

-- Name: criterion_id_seq; Type: SEQUENCE SET
SELECT pg_catalog.setval('criterion_id_seq', 3, true);

-- Name: filter_id_seq; Type: SEQUENCE SET
SELECT pg_catalog.setval('filter_id_seq', 2, true);
