--
-- PostgreSQL database dump
-- SkyNet Microservices System v1.1
-- Database: railway
-- Generated: 2025-11-08
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Drop existing tables
--

DROP TABLE IF EXISTS public.visitas CASCADE;
DROP TABLE IF EXISTS public.clientes CASCADE;
DROP TABLE IF EXISTS public.usuarios CASCADE;

--
-- Table: usuarios
--

CREATE TABLE public.usuarios (
    id bigint NOT NULL,
    email character varying(255),
    fecha_creacion timestamp(6) without time zone,
    nombre character varying(255),
    password character varying(255),
    rol character varying(255),
    CONSTRAINT usuarios_pkey PRIMARY KEY (id),
    CONSTRAINT usuarios_rol_check CHECK (((rol)::text = ANY ((ARRAY['ADMIN'::character varying, 'SUPERVISOR'::character varying, 'TECNICO'::character varying])::text[])))
);

CREATE SEQUENCE public.usuarios_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.usuarios_id_seq OWNED BY public.usuarios.id;
ALTER TABLE ONLY public.usuarios ALTER COLUMN id SET DEFAULT nextval('public.usuarios_id_seq'::regclass);

--
-- Table: clientes
--

CREATE TABLE public.clientes (
    latitud double precision,
    longitud double precision,
    fecha_actualizacion timestamp(6) without time zone,
    fecha_creacion timestamp(6) without time zone,
    id bigint NOT NULL,
    direccion character varying(255),
    email character varying(255),
    nombre character varying(255),
    telefono character varying(255),
    CONSTRAINT clientes_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.clientes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.clientes_id_seq OWNED BY public.clientes.id;
ALTER TABLE ONLY public.clientes ALTER COLUMN id SET DEFAULT nextval('public.clientes_id_seq'::regclass);

--
-- Table: visitas
--

CREATE TABLE public.visitas (
    cliente_latitud double precision,
    cliente_longitud double precision,
    fecha_planificada date,
    hora_planificada time(6) without time zone,
    latitud_ingreso double precision,
    longitud_ingreso double precision,
    cliente_id bigint,
    fecha_actualizacion timestamp(6) without time zone,
    fecha_creacion timestamp(6) without time zone,
    fecha_egreso timestamp(6) without time zone,
    fecha_ingreso timestamp(6) without time zone,
    id bigint NOT NULL,
    supervisor_id bigint,
    tecnico_id bigint,
    cliente_direccion character varying(255),
    cliente_email character varying(255),
    cliente_nombre character varying(255),
    estado character varying(255),
    reporte text,
    tecnico_nombre character varying(255),
    CONSTRAINT visitas_pkey PRIMARY KEY (id),
    CONSTRAINT visitas_estado_check CHECK (((estado)::text = ANY ((ARRAY['PLANIFICADA'::character varying, 'EN_CURSO'::character varying, 'COMPLETADA'::character varying, 'CANCELADA'::character varying])::text[])))
);

CREATE SEQUENCE public.visitas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.visitas_id_seq OWNED BY public.visitas.id;
ALTER TABLE ONLY public.visitas ALTER COLUMN id SET DEFAULT nextval('public.visitas_id_seq'::regclass);

--
-- Data for table: usuarios
--

INSERT INTO public.usuarios (id, email, fecha_creacion, nombre, password, rol) VALUES
(1, 'admin@skynet.com', '2025-11-06 12:12:25.99212', 'Administrador Principal', '123456', 'ADMIN'),
(2, 'supervisor@skynet.com', '2025-11-06 12:12:26.002088', 'Juan Pérez Supervisor', '123456', 'SUPERVISOR'),
(3, 'tecnico@skynet.com', '2025-11-06 12:12:26.006177', 'Carlos López Técnico', '123456', 'TECNICO');

--
-- Data for table: clientes
--

INSERT INTO public.clientes (latitud, longitud, fecha_actualizacion, fecha_creacion, id, direccion, email, nombre, telefono) VALUES
(14.6038, -90.5069, '2025-11-06 12:36:24.864552', '2025-11-06 12:36:24.864543', 1, '15 Calle 1-25, Zona 10, Guatemala City', 'contacto@empresaabc.com', 'Empresa ABC S.A.', '1234-5678'),
(14.8347, -91.5181, '2025-11-06 12:36:24.957449', '2025-11-06 12:36:24.957442', 2, '8a Avenida 12-45, Zona 1, Quetzaltenango', 'ventas@comercialxyz.com', 'Comercial XYZ Ltda.', '2345-6789'),
(14.5586, -90.7295, '2025-11-06 12:36:24.963729', '2025-11-06 12:36:24.96372', 3, '5a Calle 8-72, Zona 3, Antigua Guatemala', 'info@serviciostecnicos.com', 'Servicios Técnicos Modernos', '3456-7890');

--
-- Data for table: visitas (datos de prueba)
--

INSERT INTO public.visitas (cliente_latitud, cliente_longitud, fecha_planificada, hora_planificada, latitud_ingreso, longitud_ingreso, cliente_id, fecha_actualizacion, fecha_creacion, fecha_egreso, fecha_ingreso, id, supervisor_id, tecnico_id, cliente_direccion, cliente_email, cliente_nombre, estado, reporte, tecnico_nombre) VALUES
(14.6038, -90.5069, '2025-11-06', '09:00:00', NULL, NULL, 1, '2025-11-06 12:43:03.385864', '2025-11-06 12:43:03.38586', NULL, NULL, 1, 2, 3, '15 Calle 1-25, Zona 10, Guatemala City', 'contacto@empresaabc.com', 'Empresa ABC S.A.', 'PLANIFICADA', NULL, 'Carlos López Técnico'),
(14.8347, -91.5181, '2025-11-06', '11:30:00', NULL, NULL, 2, '2025-11-06 12:43:03.462186', '2025-11-06 12:43:03.462182', NULL, NULL, 2, 2, 3, '8a Avenida 12-45, Zona 1, Quetzaltenango', 'ventas@comercialxyz.com', 'Comercial XYZ Ltda.', 'PLANIFICADA', NULL, 'Carlos López Técnico'),
(14.5586, -90.7295, '2025-11-07', '14:00:00', 14.599929998860118, -90.50781821364119, 3, '2025-11-07 03:11:02.762401', '2025-11-06 12:43:03.468375', NULL, '2025-11-07 03:11:02.74601', 3, 2, 3, '5a Calle 8-72, Zona 3, Antigua Guatemala', 'info@serviciostecnicos.com', 'Servicios Técnicos Modernos', 'EN_CURSO', NULL, 'Carlos López Técnico'),
(14.6038, -90.5069, '2025-11-09', '10:00:00', 14.604, -90.507, 1, '2025-11-07 03:11:22.460658', '2025-11-07 03:11:11.093234', '2025-11-07 03:11:22.460067', '2025-11-07 03:11:16.764663', 4, 2, 3, '15 Calle 1-25, Zona 10, Guatemala City', 'contacto@empresaabc.com', 'Empresa ABC S.A.', 'COMPLETADA', 'Visita completada exitosamente. Se realizó mantenimiento preventivo del equipo. Todo funcionando correctamente. Cliente satisfecho con el servicio.', 'Carlos López Técnico'),
(14.6038, -90.5069, '2025-11-08', '15:00:00', 14.604, -90.507, 1, '2025-11-08 21:40:22.460658', '2025-11-08 21:40:11.093234', '2025-11-08 21:40:22.460067', '2025-11-08 21:40:16.764663', 5, 2, 3, '15 Calle 1-25, Zona 10, Guatemala City', 'contacto@empresaabc.com', 'Empresa ABC S.A.', 'COMPLETADA', 'Visita completada con éxito. Se realizó instalación de nuevo equipo. Cliente muy satisfecho con el servicio prestado. Equipo funcionando al 100%.', 'Carlos López Técnico'),
(14.6038, -90.5069, '2025-11-08', '15:00:00', 14.604, -90.507, 99, '2025-11-08 21:55:52.460658', '2025-11-08 21:55:11.093234', '2025-11-08 21:55:52.460067', '2025-11-08 21:55:16.764663', 6, 2, 3, 'Dirección de Prueba - Guatemala', 'bryancano14@hotmail.com', 'Bryan Cano - Prueba Email', 'COMPLETADA', 'Estimado Bryan, Esta es una prueba del sistema de reportes de SkyNet. Se realizó la instalación y configuración del sistema completo. Todo el sistema está funcionando correctamente. Gracias por probar el sistema.', 'Carlos López Técnico'),
(14.6038, -90.5069, '2025-11-08', '08:00:00', 14.604, -90.507, 1, '2025-11-08 22:28:38.460658', '2025-11-08 22:28:17.093234', '2025-11-08 22:28:38.460067', '2025-11-08 22:28:23.590606', 7, 2, 3, '15 Calle 1-25, Zona 10, Guatemala City', 'contacto@empresaabc.com', 'Empresa ABC S.A.', 'COMPLETADA', 'Visita realizada exitosamente. Se realizó mantenimiento preventivo del sistema de climatización. Se reemplazaron filtros y se verificó el correcto funcionamiento de todos los componentes. El cliente quedó satisfecho con el servicio. Próxima visita recomendada en 3 meses.', 'Carlos López Técnico'),
(14.8347, -91.5181, '2025-11-08', '10:30:00', 14.835, -91.518, 2, '2025-11-08 22:29:10.460658', '2025-11-08 22:28:50.093234', '2025-11-08 22:29:10.460067', '2025-11-08 22:28:59.590606', 8, 2, 3, '8a Avenida 12-45, Zona 1, Quetzaltenango', 'ventas@comercialxyz.com', 'Comercial XYZ Ltda.', 'COMPLETADA', 'Instalación de nuevo sistema de seguridad completada. Se instalaron 8 cámaras de vigilancia HD, sistema de alarma perimetral y control de acceso biométrico. Se realizó capacitación al personal sobre el uso del sistema. Todas las pruebas funcionaron correctamente. Garantía de 2 años en equipos.', 'Carlos López Técnico'),
(14.5586, -90.7295, '2025-11-08', '14:00:00', 14.5588, -90.7293, 3, '2025-11-08 22:29:44.460658', '2025-11-08 22:29:21.093234', '2025-11-08 22:29:44.460067', '2025-11-08 22:29:32.590606', 9, 2, 3, '5a Calle 8-72, Zona 3, Antigua Guatemala', 'info@serviciostecnicos.com', 'Servicios Técnicos Modernos', 'COMPLETADA', 'Reparación de red de datos completada. Se identificó y solucionó problema de conectividad en switches principales. Se reemplazaron 3 cables de red dañados y se reorganizó el cableado estructurado. Se actualizó firmware de equipos de red. Velocidad de conexión mejorada en un 40%. Cliente muy satisfecho con los resultados.', 'Carlos López Técnico');

--
-- Update sequences
--

SELECT setval('public.usuarios_id_seq', 3, true);
SELECT setval('public.clientes_id_seq', 3, true);
SELECT setval('public.visitas_id_seq', 9, true);

--
-- PostgreSQL database dump complete
--
