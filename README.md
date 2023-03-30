# How to experiment with Spring Cloud Dataflow

## Install Spring Cloud Dataflow

Get all necessary docker-compose files:

    wget -O docker-compose.yml https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/main/src/docker-compose/docker-compose.yml;
    wget -O docker-compose-rabbitmq.yml https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/main/src/docker-compose/docker-compose-rabbitmq.yml;
    wget -O docker-compose-kafka.yml https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/main/src/docker-compose/docker-compose-kafka.yml;
    wget -O docker-compose-postgres.yml https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/main/src/docker-compose/docker-compose-postgres.yml;
    wget -O docker-compose-prometheus.yml https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/main/src/docker-compose/docker-compose-prometheus.yml
    wget -O docker-compose-zipkin.yml https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/main/src/docker-compose/docker-compose-zipkin.yml


### Start Spring Cloud Dataflow

Setup without tracing

```
export DATAFLOW_VERSION=2.10.2
export SKIPPER_VERSION=2.9.2
docker-compose -f docker-compose.yml -f docker-compose-rabbitmq.yml -f docker-compose-postgres.yml up
```

### Start with kafka & zipkin

Use zipkin as distributed tracing server.

```
export DATAFLOW_VERSION=2.10.2
export SKIPPER_VERSION=2.9.2
docker-compose -f docker-compose.yml -f docker-compose-kafka.yml -f docker-compose-postgres.yml -f docker-compose-zipkin.yml up
```


## Programming examples

    https://dataflow.spring.io/docs/recipes/functional-apps/scst-function-bindings/


## Getting and building a sample application

    wget 'https://github.com/spring-cloud/spring-cloud-dataflow-samples/blob/main/dataflow-website/stream-developer-guides/streams/standalone-stream-sample/dist/usage-cost-stream-sample.zip?raw=true' -O usage-cost-stream-sample.zip


## Playing with the woodchuck application

    http --server.port=20015 | splitter --expression=payload.split(' ') | log
    curl http://localhost:20015 -H "Content-Type:text/plain"   -X POST -d "how much wood would a woodchuck chuck if a woodchuck could chuck wood"


## Spring starter application (included here)

Build application:

    in usage-cost-stream-sample:
        ./mvnw clean package

Registering an application:

    usage-detail-sender
    source
    file://home/cnb/scdf/apps/usage-cost-stream-sample/usage-detail-sender/target/usage-detail-sender-0.0.1-SNAPSHOT.jar
    
    usage-cost-processor
    processor
    file://home/cnb/scdf/apps/usage-cost-stream-sample/usage-cost-processor/target/usage-cost-processor-0.0.1-SNAPSHOT.jar

    usage-cost-logger
    sink
    file://home/cnb/scdf/apps/usage-cost-stream-sample/usage-cost-logger/target/usage-cost-logger-0.0.1-SNAPSHOT.jar


Or alternatively in one go:
```
source.usage-detail-sender=file://home/cnb/scdf/apps/usage-cost-stream-sample/usage-detail-sender/target/usage-detail-sender-0.0.1-SNAPSHOT.jar
processor.usage-cost-processor=file://home/cnb/scdf/apps/usage-cost-stream-sample/usage-cost-processor/target/usage-cost-processor-0.0.1-SNAPSHOT.jar
processor.usage-cost-calculator=file://home/cnb/scdf/apps/usage-cost-stream-sample/usage-cost-processor/target/usage-cost-processor-0.0.1-SNAPSHOT.jar
sink.usage-cost-logger=file://home/cnb/scdf/apps/usage-cost-stream-sample/usage-cost-logger/target/usage-cost-logger-0.0.1-SNAPSHOT.jar
```
```
source.usage-detail-sender-qc=file://home/cnb/scdf/apps/usage-cost-stream-sample/usage-detail-sender/target/usage-detail-sender-0.0.1-SNAPSHOT.jar
processor.usage-cost-processor-qc=file://home/cnb/scdf/apps/usage-cost-stream-sample/usage-cost-processor/target/usage-cost-processor-0.0.1-SNAPSHOT.jar
processor.usage-cost-calculator-qc=file://home/cnb/scdf/apps/usage-cost-stream-sample/usage-cost-processor/target/usage-cost-processor-0.0.1-SNAPSHOT.jar
sink.usage-cost-logger-qc=file://home/cnb/scdf/apps/usage-cost-stream-sample/usage-cost-logger/target/usage-cost-logger-0.0.1-SNAPSHOT.jar
```

    Stream definition:
        usage-detail-sender | usage-cost-processor | usage-cost-logger
    
    Stream definition with a polling delay of 2000ms:
        usage-detail-sender | usage-cost-processor | usage-cost-calculator | usage-cost-logger
        usage-detail-sender --spring.cloud.stream.poller.fixed-delay=4000 | usage-cost-processor | usage-cost-calculator | usage-cost-logger
        usage-detail-sender-qc --spring.cloud.stream.poller.fixed-delay=4000 | usage-cost-processor-qc | usage-cost-calculator-qc | usage-cost-logger-qc
        usage-detail-sender --spring.cloud.stream.poller.fixed-delay=2000 | usage-cost-processor | usage-cost-calculator | lbl:usage-cost-calculator | usage-cost-logger

    Exposed via http:
        Processor:
            http://localhost:20078/api/v1/configs
    




Wichtigsten Systeme "nehmen" und Katalogisieren Step-by-step
    soap ev. nur als auflistung
    swagger und übersicht für die wichtigsten Systeme
    wir fangen damit 
        zeigen es


Viel Domain-Code ist dupliziert
    identifizieren und definieren was ein eigener Service
    auflisten / dokumentieren
    beim nächsten mal wird es nicht mehr selber geschrieben, sonder überlegt wie wiederverwenden


    


