# How to experiment with Spring Cloud Dataflow

## Install Spring Cloud Dataflow

wget -O docker-compose.yml https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/main/src/docker-compose/docker-compose.yml;
wget -O docker-compose-rabbitmq.yml https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/main/src/docker-compose/docker-compose-rabbitmq.yml;
wget -O docker-compose-kafka.yml https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/main/src/docker-compose/docker-compose-kafka.yml;
wget -O docker-compose-postgres.yml https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/main/src/docker-compose/docker-compose-postgres.yml;
wget -O docker-compose-prometheus.yml https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/main/src/docker-compose/docker-compose-prometheus.yml
wget -O docker-compose-zipkin.yml https://raw.githubusercontent.com/spring-cloud/spring-cloud-dataflow/main/src/docker-compose/docker-compose-zipkin.yml


### Start Spring Cloud Dataflow

export DATAFLOW_VERSION=2.10.2
export SKIPPER_VERSION=2.9.2
docker-compose -f docker-compose.yml -f docker-compose-rabbitmq.yml -f docker-compose-postgres.yml up


### Start with kafka & zipkin

export DATAFLOW_VERSION=2.10.2
export SKIPPER_VERSION=2.9.2
docker-compose -f docker-compose.yml -f docker-compose-kafka.yml -f docker-compose-postgres.yml -f docker-compose-zipkin.yml up



## Getting and building a sample application

wget 'https://github.com/spring-cloud/spring-cloud-dataflow-samples/blob/main/dataflow-website/stream-developer-guides/streams/standalone-stream-sample/dist/usage-cost-stream-sample.zip?raw=true' -O usage-cost-stream-sample.zip


## Playing with the woodchuck application

    http --server.port=20015 | splitter --expression=payload.split(' ') | log
    curl http://localhost:20015 -H "Content-Type:text/plain"   -X POST -d "how much wood would a woodchuck chuck if a woodchuck could chuck wood"



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


    Stream definition:
        usage-detail-sender | usage-cost-processor | usage-cost-logger

    Stream definition with a polling delay of 2000ms:
        usage-detail-sender --spring.cloud.stream.poller.fixed-delay=2000 | usage-cost-processor | usage-cost-logger

    Exposed via http:
        Processor:
            http://localhost:20078/api/v1/configs
    