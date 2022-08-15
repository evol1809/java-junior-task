CREATE TABLE payment (
    id UUID not null,
    payment_value DECIMAL not null,
    client_id UUID not null,
    foreign key (client_id) references client(id)
);
