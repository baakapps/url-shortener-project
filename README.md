
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) 

# Url Shortener

Url shortener application with microservice architecture pattern

- Highly maintainable and testable
- Independently deployable
- Capable of being developed by a small team

# Infrastructure

Spring Cloud facilitates the development of applications by providing solutions such as load balancing, service registry, monitoring, and configuration.

![Infrastructure plan](url-shortener-project.png)

## Services

### Url shortener service


| Method | Path              | Description                                
|--------|-------------------|-------------------------------------------
| POST   | api/v1/shortUrl/              | Create long url from short url 
| GET   | api/v1/shortUrl/{shortenedUrl} | Redirect to website using short url 


### Range service

| Method | Path           | Description 
|--------|----------------|-------------
| GET    | /api/v1/range  | Get range


### Config server
Config server allows to externally store variables. Application properties can be updated using config server without restarting microservice.

#### Notes
* Each microservice has it's own database and there is no way to access the database directly from other services.
* The services in this project are using MySQL for the persistent storage. In other case, it is also possible for one service 
to use any type of database (SQL or NoSQL).
* Service-to-service communiation is done by using REST API. It is pretty convenient to use HTTP call in Spring
since it provides a simplify HTTP layer service called Feign (discussed later). For the next iteration, I'm also planningto use
 asyncronous message transfer using Apache Kafka since Kafka will allow us to send message to several service easily.

### Monitoring Dashboard *

In this project configuration, each microservice with Hystrix on board pushes metrics to Turbine via Spring Cloud Bus (with AMQP broker). The Monitoring project is just a small Spring boot application with [Turbine](https://github.com/Netflix/Turbine) and [Hystrix Dashboard](https://github.com/Netflix/Hystrix/tree/master/hystrix-dashboard).

See below [how to get it up and running](https://github.com/imrenagi/microservice-skeleton/tree/master/readme/README.md#how-to-run-all-things).

Let's see our system behavior under load: A service calls another service and it responses with a vary imitation delay. Response timeout threshold is set to 1 second.

<img width="880" src="https://cloud.githubusercontent.com/assets/6069066/14194375/d9a2dd80-f7be-11e5-8bcc-9a2fce753cfe.png">

<img width="212" src="https://cloud.githubusercontent.com/assets/6069066/14127349/21e90026-f628-11e5-83f1-60108cb33490.gif">	| <img width="212" src="https://cloud.githubusercontent.com/assets/6069066/14127348/21e6ed40-f628-11e5-9fa4-ed527bf35129.gif"> | <img width="212" src="https://cloud.githubusercontent.com/assets/6069066/14127346/21b9aaa6-f628-11e5-9bba-aaccab60fd69.gif"> | <img width="212" src="https://cloud.githubusercontent.com/assets/6069066/14127350/21eafe1c-f628-11e5-8ccd-a6b6873c046a.gif">
--- |--- |--- |--- |
| `0 ms delay` | `500 ms delay` | `800 ms delay` | `1100 ms delay`
| Well behaving system. The throughput is about 22 requests/second. Small number of active threads in Statistics service. The median service time is about 50 ms. | The number of active threads is growing. We can see purple number of thread-pool rejections and therefore about 30-40% of errors, but circuit is still closed. | Half-open state: the ratio of failed commands is more than 50%, the circuit breaker kicks in. After sleep window amount of time, the next request is let through. | 100 percent of the requests fail. The circuit is now permanently open. Retry after sleep time won't close circuit again, because the single request is too slow.



## Infrastructure Automation
[WIP]

## How to run all things

### Before you start
* Install docker and docker compose
* Export environment variables: `SW_CONFIG_SERVICE_PASSWORD`, `MSW_ROOT_PASSWORD`,  `MSW_DB_USER`, `MSW_DB_PASSWORD`, `MSW_SERVICE_ACCOUNT_PASSOWRD`, `MSW_DB_TEST_PASSWORD`.

### Production
In production mode, all images will be pulled from docker hub. 
```bash
docker-compose up -d
```

### Development 
For development mode, all source code will be compiled and packaged as a jar. These jar files will be used later for 
creating the image for every service. To build, use this command:
```$xslt
docker-compose -f docker-compose.yml -f docker-compose.dev.yml up
```

### Important Endpoint *
* [http://localhost:80](http://localhost:80) - Gateway
* [http://localhost:8761](http://localhost:8761) - Eureka Dashboard
* [http://localhost:9000/hystrix](http://localhost:9000/hystrix) - Hystrix Dashboard (paste Turbine stream link on the form)
* [http://localhost:8989](http://localhost:8989) - Turbine stream (source for the Hystrix Dashboard)
* [http://localhost:15672](http://localhost:15672) - RabbitMq management (default login/password: guest/guest)

### Kubernetes Deployment
[TBD]

## Contributing
[TBD]


*_This part is taken from [PiggyMetrics](https://github.com/sqshq/PiggyMetrics) with some adjustment because there is no significant differences in the way I use it_


