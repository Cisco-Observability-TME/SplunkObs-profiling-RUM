#!/usr/bin/env bash

#Force use of jvm 11
export JAVA_HOME=`/usr/libexec/java_home -v 11.0`

# Setup OTEL env variables
## Set the Service Name and APM environment name
export OTEL_RESOURCE_ATTRIBUTES=service.name=profiling-workshop-webapp,deployment.environment=mkuglerr_env01

# Send traces to the otlp grpc port (4317) of the locally running otel-collector
export OTEL_EXPORTER_OTLP_ENDPOINT=http://127.0.0.1:4318
export OTEL_JAVAAGENT_DEBUG=false

java -javaagent:splunk-otel-javaagent.jar \
-Dsplunk.profiler.enabled=true \
-Dsplunk.profiler.memory.enabled=true \
-Dsplunk.profiler.call.stack.interval=1000 \
-Dsplunk.metrics.endpoint=http://localhost:9943 \
-jar build/libs/profiling-workshop-all.jar


#docker run --name mygame mygame -p 9090:9090
