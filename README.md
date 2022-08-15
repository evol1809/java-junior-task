
## Technological stack

1. JPA (Hibernate) as a persistence layer
2. Java 11
3. Spring shell as cli
4. Flyway
5. REST API
6. Web client (ui)
7. Unit & Integration tests
8. Docker


## Quick start (with H2)

1. Clone this repo to your computer.
3. Run app `./mvnw spring-boot:run`


## Run with a docker container

1. Build `./mvnw package`
2. Build container `docker-compose build`
3. Start `docker-compose run -p 8080:8080 javajuniortask`
4. Log files will be created in the directory: docker-logs
5. Stop and remove `docker-compose down`

or Unix-like `./mvnw package && docker-compose build && docker-compose run -p 8080:8080 javajuniortask`

## Run tests

- Run `./mvnw test`


## Web client (ui)

- Open the page in your browser. For example `http://localhost:8080`


## REST API

- `GET /api/v1/clients` get clients

- `POST /api/v1/clients`
  body:`{
  "id": null,
  "name": "BestClient",
  "totalDebt": 0
  }` add client with name = BestClient

- `GET /api/v1/debts/ec94432d-99cb-48d9-828b-cec94d6328a9` get client debts with clientId = ec94432d-99cb-48d9-828b-cec94d6328a9

- `POST /api/v1/debts` body: `{
  "id": null,
  "value": 20,
  "clientId": "ec94432d-99cb-48d9-828b-cec94d6328a9"
  }` add client debt with clientId = ec94432d-99cb-48d9-828b-cec94d6328a9

- `GET /api/v1/payments/ec94432d-99cb-48d9-828b-cec94d6328a9` get client payments with clientId = ec94432d-99cb-48d9-828b-cec94d6328a9

- `POST /api/v1/payments` body: `{
  "id": null,
  "value": 10,
  "clientId": "ec94432d-99cb-48d9-828b-cec94d6328a9"
  }` add client payment with clientId = ec94432d-99cb-48d9-828b-cec94d6328a9


## Shell API

- `show-all-clients` get clients
- `add-client BestClient` add client with name = BestClient
- `show-all-debts ec94432d-99cb-48d9-828b-cec94d6328a9` get client debts with clientId = ec94432d-99cb-48d9-828b-cec94d6328a9
- `add-debt ec94432d-99cb-48d9-828b-cec94d6328a9 176` add client debt with clientId = ec94432d-99cb-48d9-828b-cec94d6328a9
- `show-all-payments ec94432d-99cb-48d9-828b-cec94d6328a9`get client payments with clientId = ec94432d-99cb-48d9-828b-cec94d6328a9
- `add-payment ec94432d-99cb-48d9-828b-cec94d6328a9 1` add client payment with clientId = ec94432d-99cb-48d9-828b-cec94d6328a9

![commands](/commands.jpeg)


## License

This project is licensed under the MIT license.

