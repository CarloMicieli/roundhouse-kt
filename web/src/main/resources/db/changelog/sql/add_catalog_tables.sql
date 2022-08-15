CREATE TABLE public.brands
(
    brand_id varchar(50) NOT NULL,
    name varchar(50) NOT NULL,
    registered_company_name varchar(50),
    group_name varchar(50),
    description varchar(1000),
    email varchar(100),
    website_url varchar(100),
    phone_number varchar(20),
    kind varchar(25) NOT NULL,
    active boolean,
    address_street_address varchar(255),
    address_extended_address varchar(255),
    address_city varchar(50),
    address_region varchar(50),
    address_postal_code varchar(10),
    address_country_code varchar(2),
    created timestamp without time zone NOT NULL,
    last_modified timestamp without time zone,
    version integer NOT NULL DEFAULT 1,
    CONSTRAINT "PK_brands" PRIMARY KEY (brand_id)
);

CREATE UNIQUE INDEX "Idx_brands_name"
    ON brands USING btree
    (name ASC NULLS LAST);