CREATE TABLE debt (
    id UUID not null,
    debt_value DECIMAL not null,
    client_id UUID not null,
    foreign key (client_id) references client(id)
);
