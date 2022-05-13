-- Data for Name: filter; Type: TABLE DATA
INSERT INTO filter (id, name, selection) VALUES (1, 'My Filter 1', 'SELECT_1') ON CONFLICT DO NOTHING;
INSERT INTO filter (id, name, selection) VALUES (2, 'My Filter 2', 'SELECT_2') ON CONFLICT DO NOTHING;

-- Data for Name: criterion; Type: TABLE DATA
INSERT INTO criterion (id, filter_id) VALUES (1, 1) ON CONFLICT DO NOTHING;
INSERT INTO criterion (id, filter_id) VALUES (2, 1) ON CONFLICT DO NOTHING;
INSERT INTO criterion (id, filter_id) VALUES (3, 2) ON CONFLICT DO NOTHING;

-- Data for Name: amount_criterion; Type: TABLE DATA
INSERT INTO amount_criterion (id, operator, value) VALUES (1, 'GREATER_THAN', 1725) ON CONFLICT DO NOTHING;

-- Data for Name: title_criterion; Type: TABLE DATA
INSERT INTO title_criterion (id, operator, value) VALUES (2, 'EQUALS', 'test') ON CONFLICT DO NOTHING;

-- Data for Name: date_criterion; Type: TABLE DATA
INSERT INTO date_criterion (id, operator, value) VALUES (3, 'UNTIL', '2022-04-15') ON CONFLICT DO NOTHING;

-- Set the next auto increment value in case we inserted test data with predefined ids
SELECT setval('criterion_id_seq', MAX(id), true) FROM criterion;
SELECT setval('filter_id_seq', MAX(id), true) FROM filter;
