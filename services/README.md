# Local run by docker-compose.

1. Running the app in a clean environment.
    
    All previous containers and images for these services
    are deleted, a clean `mvn package` is performed for each
    service, a new `docker build` is made for each service and,
    at the end, `docker-compose up` is done.
    
    `bash docker-starter.sh`.

2. Health-check of the applications.

    2.1. Service hello.
    
    `http://localhost:8084/` - just outputs "Hello, user! This
    seems to be initial page!".
    
    It ought to be mentioned that for this very service Grafana
    with Prometheus is up. Logs can be easily obtained on
    `http://localhost:8084/actuator`,
    `http://localhost:8084/actuator/prometheus`. Prometheus is
    working on `http://localhost:9090`. To work with Grahana, some
    should go to `http://localhost:3000`.
    
    2.2. Service rbc.
    
    `http://localhost:8083/rbc` - max US dollar exchange rate in
    relation to ruble for 30 business days. The results are
    cached in the database (Postgres).
    
    `http://localhost:8083/rbc/array` - outputs array
    'rate=UNIXTime' for 30 days, separated by commas.
    
    2.3 Service weather.
    
    `http://localhost:8081/weather?time=<your_UNIXTime>` -
    outputs the temperature in Moscow in `<your_UNIXTime>`
    (e.g. `1584651600`).
    
    `http://localhost:8081/weather/array` - outputs array
    'temperature in Moscow=UNIXTime' for 50 days, separated
    by commas.
    
    2.4 Service prediction.
    
    `http://localhost:8082/prediction?temperature=<your_temperature>` -
    outputs prediction of the US dollar exchange rate in relation
    to ruble relative to the temperature `<your_temperature>`
    (e.g. `2.71`). To do this, the following is performed: the
    'rate=UNIXTime' array for 30 days is taken from the rbc
    service, the 'temperature in Moscow=UNIXTime' array for 50
    days is taken from the weather service (since the stock
    doesn't work every day but the weather is measured daily),
    the correspondences 'rate=temperature in Moscow' by UNIXTime
    are found using Least Square Method (a straight line is
    constructed using the coefficients A and B, then the
    prediction is calculated using the following formula:
    `A + <your_temperature> * B`).
