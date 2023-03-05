<div align="center">

# Url Shortener


Url shortener application with microservice architecture pattern
  
[How to run all things](#how-to-run-all-things) •
[How to use](#how-to-use) •
[Projects](#projects) •
[Roadmap](#roadmap) •
[Error response](#error-response)

![Tech stack](tech-stack.png)
    
<img src="url-shortener-project.png" width="500" height="500" />

</div>

## How to run all things

### Before you start
* Install docker and docker compose

### Run 
```$xslt
docker-compose up --build
```
## How to use

#### Create short url from long url
```
POST http://localhost:8080/api/v1/shortUrl
Content-Type: application/json

{
  "longUrl" : "https://www.google.com"
}
```
#### Short url response
```
HTTP/1.1 201 
Content-Type: application/json
    
{
  "shortUrl": "Ny1"
}
```

#### Redirect to long url 
```
GET http://localhost:8080/api/v1/shortUrl/Ny1
```
#### Redirection response
```
Redirections:
-> https://www.google.com/
HTTP/1.1 200 OK
```

## Projects

### Important Endpoints
* [http://localhost:8080](http://localhost:8080) - Api gateway
* [http://localhost:8761](http://localhost:8761) - Eureka dashboard
* [http://localhost:8888](http://localhost:8888) - Config server
* [http://localhost:15672](http://localhost:15672) - RabbitMq management (username/password: guest/guest)
* [http://localhost:9090](http://localhost:9090) - Prometheus 
* [http://localhost:3000](http://localhost:3000) - Grafana (username/password: admin/password)
* [http://localhost:9100](http://localhost:9100) - Node exporter
* [http://localhost:9411](http://localhost:9411) - Zipkin
* [http://localhost:5100](http://localhost:5100) - Url shortener service 
* [http://localhost:27017](http://localhost:27017) - MongoDB (username/password: rootuser/rootpass)
* [http://localhost:8081](http://localhost:8081) - Mongo express (username/password: rootuser/rootpass)
* [http://localhost:6379](http://localhost:6379) - Redis
* [http://localhost:5200](http://localhost:5200) - Range service 
* [http://localhost:3306](http://localhost:3306) - Mysql (username/password/database: range_app/p_range_app/url_shortener)

### Services
* Highly available and testable, independently deployable, capable of being developed by a small team.
* Collision is handled by fetching range from range service.
* Testcontainers for integration testing.
* Completed unit testing.

### Url shortener service
* If one range service goes down, other instances will be tried by OpenFeign.
* MongoDB can scale horizontally and share the load.
* Reduced response time by caching shortened url. Check cache first, if not found then check database.
* Implemented url validation
* [http://localhost:5100/swagger-ui/index.html#/](http://localhost:5100/swagger-ui/index.html#/) Swagger UI

### Range service
* Implemented pessimistic locking for avoiding collision in concurrent requests.
* [http://localhost:5200/swagger-ui/index.html#/](http://localhost:5200/swagger-ui/index.html#/) Swagger UI

### Config server
* Config server allows to externally store variables. Application properties of url shortener and range services can be updated using config server without restarting them.
1. Push commits in config repository
2. Send request: POST http://localhost:8888/actuator/busrefresh

### Observability 
* Used Prometheus and Grafana for monitoring and Zipkin for tracing.
* Grafana setup:
1. Add Prometheus data source with url http://host.docker.internal:9090
2. Upload JSON file from observability/prometheus/grafana-dashboard.json

### Api Gateway 
* Clients use APIs by Api Gateway instead of directly communicating with url shortener service. Api Gateway provides routing and load balancing.

### Discovery server
* Eureka server provides services discovery

## Roadmap
- [ ] Kubernetes
- [ ] Jenkis pipeline
- [ ] Mysql master slave replication
- [ ] Authentication and authorization
- [ ] Rate limiting with Spring Cloud Gateway
- [ ] ELK stack
- [ ] AWS

## Error response

#### 1. Request
```
POST /api/v1/shortUrl
Content-Type: application/json
{
  "longUrl" : ""
}
```
#### 1. Response
```
HTTP/1.1 400 
Content-Type: application/problem+json
    
{
  "type": "about:blank",
  "title": "ARGUMENT_VALIDATION_ERROR",
  "status": 400,
  "detail": "Url can not be empty",
  "instance": "/api/v1/shortUrl"
}
```

#### 2. Request
```
POST /api/v1/shortUrl
Content-Type: application/json
{
  "longUrl" : "ww.google"
}
```
#### 2. Response
```
HTTP/1.1 400 
Content-Type: application/problem+json
    
{
  "type": "about:blank",
  "title": "ARGUMENT_VALIDATION_ERROR",
  "status": 400,
  "detail": "Invalid URL format found",
  "instance": "/api/v1/shortUrl"
}
```

#### 3. Request
```
GET /api/v1/shortUrl/abcdef123456random
```
#### 3. Response
```
HTTP/1.1 404 
Content-Type: application/json
    
{
  "type": "about:blank",
  "title": "NOT_FOUND",
  "status": 404,
  "detail": "Long url is not found",
  "instance": "/api/v1/shortUrl/abcdef123456random"
}
```

#### Generic error response
```
HTTP/1.1 500 
Content-Type: application/json
{
  "type": "about:blank",
  "title": "SYSTEM_ERROR",
  "status": 500,
  "detail": "The server encountered an error and could not complete your request. Please try again later.",
  "instance": "/api/v1/shortUrl"
}
```
