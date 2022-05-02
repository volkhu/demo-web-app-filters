-- Data for Name: filter; Type: TABLE DATA
INSERT INTO public.filter (id, name, selection) VALUES (1, 'My Filter 1', '1');
INSERT INTO public.filter (id, name, selection) VALUES (2, 'My Filter 2', '2');

-- Data for Name: criterion; Type: TABLE DATA
INSERT INTO public.criterion (id, operator, type, value, filter_id) VALUES (1, 'greater than', 'Amount', '5', 1);
INSERT INTO public.criterion (id, operator, type, value, filter_id) VALUES (2, 'equals', 'Title', 'test', 1);
INSERT INTO public.criterion (id, operator, type, value, filter_id) VALUES (3, 'until', 'Date', '2022-04-15', 2);

-- Name: criterion_id_seq; Type: SEQUENCE SET
SELECT pg_catalog.setval('public.criterion_id_seq', 3, true);

-- Name: filter_id_seq; Type: SEQUENCE SET
SELECT pg_catalog.setval('public.filter_id_seq', 2, true);
