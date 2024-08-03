FROM openjdk:11
MAINTAINER mkuglerr@cisco.com 
ADD build/libs/profiling-workshop-all.jar /profiling-workshop-all.jar
ADD splunk-otel-javaagent.jar /splunk-otel-javaagent.jar
ENV OTEL_RESOURCE_ATTRIBUTES=service.name=profiling-workshop-webapp,deployment.environment=mkuglerr_env01
ENV OTEL_EXPORTER_OTLP_ENDPOINT=http://otelcol:4318
ENV OTEL_JAVAAGENT_DEBUG=false



CMD java -javaagent:splunk-otel-javaagent.jar \
-Dsplunk.profiler.enabled=true \
-Dsplunk.profiler.memory.enabled=true \
-Dsplunk.profiler.call.stack.interval=1000 \
-Dsplunk.metrics.endpoint=http://otelcol:9943 \
-jar /profiling-workshop-all.jar > var/log/jar.log


