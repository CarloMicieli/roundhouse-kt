CREATE TABLE public.brands
(
    brand_id varchar(50) NOT NULL,
    name varchar(50) NOT NULL,
    email varchar(100),
    website_url varchar(100),
    phone_number varchar(20),
    created timestamp without time zone NOT NULL,
    last_modified timestamp without time zone,
    version integer NOT NULL DEFAULT 1,
    CONSTRAINT "PK_brands" PRIMARY KEY (brand_id)
);

CREATE UNIQUE INDEX "Idx_brands_name"
    ON brands USING btree
    (name ASC NULLS LAST);
