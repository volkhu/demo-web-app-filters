-- TABLE criterion
CREATE TABLE criterion (
    id bigint NOT NULL,
    filter_id bigint NOT NULL
);
CREATE SEQUENCE criterion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE criterion_id_seq OWNED BY criterion.id;
ALTER TABLE ONLY criterion ADD CONSTRAINT criterion_pkey PRIMARY KEY (id);
ALTER TABLE ONLY criterion ALTER COLUMN id SET DEFAULT nextval('criterion_id_seq'::regclass);

-- TABLE amount_criterion
CREATE TABLE amount_criterion (
    operator character varying(255) NOT NULL,
    value bigint NOT NULL,
    id bigint NOT NULL
);
ALTER TABLE ONLY amount_criterion ADD CONSTRAINT amount_criterion_pkey PRIMARY KEY (id);

-- TABLE date_criterion
CREATE TABLE date_criterion (
    operator character varying(255) NOT NULL,
    value timestamp without time zone NOT NULL,
    id bigint NOT NULL
);
ALTER TABLE ONLY date_criterion ADD CONSTRAINT date_criterion_pkey PRIMARY KEY (id);

-- TABLE title_criterion
CREATE TABLE title_criterion (
    operator character varying(255) NOT NULL,
    value character varying(255),
    id bigint NOT NULL
);
ALTER TABLE ONLY title_criterion ADD CONSTRAINT title_criterion_pkey PRIMARY KEY (id);

-- TABLE filter
CREATE TABLE filter (
    id bigint NOT NULL,
    match_type character varying(255) NOT NULL,
    name character varying(255)
);
CREATE SEQUENCE filter_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER SEQUENCE filter_id_seq OWNED BY filter.id;
ALTER TABLE ONLY filter ADD CONSTRAINT filter_pkey PRIMARY KEY (id);
ALTER TABLE ONLY filter ALTER COLUMN id SET DEFAULT nextval('filter_id_seq'::regclass);

-- Foreign keys
ALTER TABLE ONLY criterion ADD CONSTRAINT fk14gklu50813bmkf3q4q0ncbmb FOREIGN KEY (filter_id) REFERENCES filter(id);
ALTER TABLE ONLY amount_criterion ADD CONSTRAINT fkgcfgcvnf7bi71b0tcgjr7yabr FOREIGN KEY (id) REFERENCES criterion(id);
ALTER TABLE ONLY title_criterion ADD CONSTRAINT fkgfm5d88clt155uf2e1q1rdbhv FOREIGN KEY (id) REFERENCES criterion(id);
ALTER TABLE ONLY date_criterion ADD CONSTRAINT fkguknha84igubp9e3hd80ih5e2 FOREIGN KEY (id) REFERENCES criterion(id);
