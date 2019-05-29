FROM java:8
ADD target/rabbitmq-producer-0.1.0.jar rabbitmq-producer-0.1.0.jar
ENV SPRING_RABBITMQ_ADDRESSES="amqp://quest@172.20.0.2:5672" 
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/rabbitmq-producer-0.1.0.jar"]
