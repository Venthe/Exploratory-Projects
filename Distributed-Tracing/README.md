Spring Cloud Sleuth (org.springframework.cloud:spring-cloud-starter-sleuth), once added to the CLASSPATH, automatically instruments common communication channels:

    requests over messaging technologies like Apache Kafka or RabbitMQ (or any other Spring Cloud Stream binder
    HTTP headers received at Spring MVC controllers
    requests that pass through a Netflix Zuul microproxy
    requests made with the RestTemplate, etc.
