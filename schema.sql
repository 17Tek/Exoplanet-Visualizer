--
-- PostgreSQL database dump
--

\restrict pgS1tick0Tg2jwVdmTRNjnUZ3DdPagflot9ubBhaBOdFyG7O40dWKk1HxIeFiVT

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: exoplanet_dataset; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.exoplanet_dataset (
    id integer NOT NULL,
    planet_name character varying(100),
    host_star character varying(100),
    n_stars integer,
    n_planets integer,
    discovery_method character varying(100),
    disc_year numeric(10,2),
    disc_facility character varying(100),
    orbital_period_days numeric(20,8),
    planet_radius_earth numeric(15,8),
    planet_mass_earth numeric(15,8),
    equilibrium_temp_k numeric(10,3),
    orbital_eccentricity numeric(10,8),
    semi_major_axis_au numeric(15,8),
    star_temp_k numeric(10,3),
    star_radius_sun numeric(15,8),
    star_mass_sun numeric(15,8),
    star_age_gyr numeric(10,5),
    star_surface_gravity numeric(10,5),
    star_metallicity numeric(10,5),
    dist_from_earth_pc numeric(15,8),
    star_vmag numeric(10,5),
    ra numeric(15,8),
    "dec" numeric(15,8),
    controversial_flag boolean,
    planet_type character varying(50),
    habitable_zone_flag boolean,
    multi_planet_system boolean,
    is_recent_discovery boolean,
    dist_category character varying(50),
    star_type character varying(50),
    orbital_period_cat character varying(50)
);


ALTER TABLE public.exoplanet_dataset OWNER TO postgres;

--
-- Name: exoplanet_dataset_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.exoplanet_dataset_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.exoplanet_dataset_id_seq OWNER TO postgres;

--
-- Name: exoplanet_dataset_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.exoplanet_dataset_id_seq OWNED BY public.exoplanet_dataset.id;


--
-- Name: solarsystem_dataset; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.solarsystem_dataset (
    id integer NOT NULL,
    name character varying(20),
    type character varying(20),
    dominant_color character varying(20),
    mass numeric(20,5),
    radius numeric(20,5),
    density numeric(20,5),
    surface_gravity numeric(20,5),
    moons integer,
    ring_system boolean,
    surface_characteristics character varying(20),
    most_abundant_element character varying(20),
    orbital_period_days numeric(15,5),
    mean_temperature numeric(10,3),
    average_distance_from_sun numeric(20,5)
);


ALTER TABLE public.solarsystem_dataset OWNER TO postgres;

--
-- Name: solarsystem_dataset_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.solarsystem_dataset_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.solarsystem_dataset_id_seq OWNER TO postgres;

--
-- Name: solarsystem_dataset_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.solarsystem_dataset_id_seq OWNED BY public.solarsystem_dataset.id;


--
-- Name: exoplanet_dataset id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exoplanet_dataset ALTER COLUMN id SET DEFAULT nextval('public.exoplanet_dataset_id_seq'::regclass);


--
-- Name: solarsystem_dataset id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solarsystem_dataset ALTER COLUMN id SET DEFAULT nextval('public.solarsystem_dataset_id_seq'::regclass);


--
-- Name: exoplanet_dataset exoplanet_dataset_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exoplanet_dataset
    ADD CONSTRAINT exoplanet_dataset_pkey PRIMARY KEY (id);


--
-- Name: solarsystem_dataset solarsystem_dataset_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.solarsystem_dataset
    ADD CONSTRAINT solarsystem_dataset_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

\unrestrict pgS1tick0Tg2jwVdmTRNjnUZ3DdPagflot9ubBhaBOdFyG7O40dWKk1HxIeFiVT

