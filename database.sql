CREATE DATABASE sysbank
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Colombia.1252'
    LC_CTYPE = 'Spanish_Colombia.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;


BEGIN;

CREATE TABLE IF NOT EXISTS public.clientes
(
    idcliente serial NOT NULL,
    nombre character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT clientes_pkey PRIMARY KEY (idcliente)
);

CREATE TABLE IF NOT EXISTS public.cuentas
(
    id serial NOT NULL,
    tipocuenta varchar NOT NULL,
    saldo numeric(10, 2) NOT NULL DEFAULT 0,
    numerocuenta character varying(20) COLLATE pg_catalog."default" NOT NULL,
    idcliente integer NOT NULL,
    CONSTRAINT cuentas_pkey PRIMARY KEY (id),
    CONSTRAINT cuentas_numerocuenta_key UNIQUE (numerocuenta)
);

CREATE TABLE IF NOT EXISTS public.historialtransacciones
(
    id serial NOT NULL,
    monto numeric(10, 2) NOT NULL,
    fechahora timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    codigotransaccion character varying(50) COLLATE pg_catalog."default" NOT NULL,
    idcuenta integer NOT NULL,
    codigotransaccionref integer NOT NULL,
    CONSTRAINT historialtransacciones_pkey PRIMARY KEY (id),
    CONSTRAINT historialtransacciones_codigotransaccion_key UNIQUE (codigotransaccion)
);

CREATE TABLE IF NOT EXISTS public.transacciones
(
    codigotransaccion serial NOT NULL,
    tipotransaccion character varying(100) NOT NULL,	
    costo numeric(5, 2) NOT NULL,
    CONSTRAINT transacciones_pkey PRIMARY KEY (codigotransaccion)
);

ALTER TABLE IF EXISTS public.cuentas
    ADD CONSTRAINT cuentas_idcliente_fkey FOREIGN KEY (idcliente)
    REFERENCES public.clientes (idcliente) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.historialtransacciones
    ADD CONSTRAINT historialtransacciones_codigotransaccionref_fkey FOREIGN KEY (codigotransaccionref)
    REFERENCES public.transacciones (codigotransaccion) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.historialtransacciones
    ADD CONSTRAINT historialtransacciones_idcuenta_fkey FOREIGN KEY (idcuenta)
    REFERENCES public.cuentas (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

END;