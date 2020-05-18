# Deployment of remote machines with docker-compose.

0. Configuring ssh connections to servers.

   It is needed to throw ssh keys to the servers, and then configure
   a convenient a non-paroled connection.

1. Running the app in a clean environment.
    
    All previous containers and images for these services
    are deleted, a clean `mvn package` is performed for each
    service, a new `docker build` is made for each service and,
    at the end, `docker-compose up` is done.
        
    Далее выполняется деплой сервисов на два сервера.
    На каждом из серверов пересоздаётся папка с ресурсами, удаляются 
    абсолютно все контейнеры и образы докера на каждом из серверов.
    После чего через docker-compose запускаются все четыре сервиса.
    Стоит ометить, что информация о паролях и логинах для 
    серверов передаётся секьюрно (для начала стадии деплоя нужно
    ввести пароль `iamthebest`).
    
    Next, the services are uploaded to two servers. On each of
    the servers, the folder with resources is re-created,
    absolutely all containers are deleted and docker images on
    each of the servers. After that, all four services are launched
    via `docker-compose up`. It should be mentioned that
    information about passwords and usernames for servers given
    are transferred securely (to start Deploy stage, some needs to
    enter the password `iamthebest`).
    
    `bash ansible-run.sh`.

2. Health-check of the applications.

    2.1. Service hello.
    
    `http://176.119.156.76:8084/` - just outputs "Hello, user! This
    seems to be initial page!".
    
    2.2. Service rbc.
    
    `http://localhost:8083/rbc` - max US dollar exchange rate in
    relation to ruble for 30 business days. The results are
    cached in the database (Postgres).
    
    `http://194.87.146.4:8083/rbc/array` - outputs array
    'rate=UNIXTime' for 30 days, separated by commas.
    
    2.3 Service weather.
    
    `http://176.119.156.76:8081/weather?time=<your_UNIXTime>` -
    outputs the temperature in Moscow in `<your_UNIXTime>`
    (e.g. `1584651600`).
    
    `http://176.119.156.76:8081/weather/array` - outputs array
    'temperature in Moscow=UNIXTime' for 50 days, separated
    by commas.
    
    2.4 Service prediction.
    
    `http://176.119.156.76:8082/prediction?temperature=<your_temperature>` -
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
