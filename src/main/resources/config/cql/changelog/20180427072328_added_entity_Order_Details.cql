
CREATE TABLE IF NOT EXISTS order_Details (
    id uuid,
    order_id int,
    order_Date date,
    company_id int,
    product_id int,
    quantity int,
    total decimal,
    vat decimal,
    order_value decimal,
    PRIMARY KEY(id)
);
