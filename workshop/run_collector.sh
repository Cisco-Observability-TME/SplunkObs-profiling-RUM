#!/usr/bin/env bash
export SPLUNK_TOKEN="DvUejX0LLmm33hVW6qkRAg"
export SPLUNK_REALM="us1"
docker run -d --rm --name collector \
    -v "$(pwd)/etc/collector.yaml":/etc/otel/config.yaml \
    -p 4317:4317 \
    -p 4318:4318 \
    -e SPLUNK_TOKEN=$SPLUNK_TOKEN \
    -e SPLUNK_REALM=$SPLUNK_REALM \
    otel/opentelemetry-collector-contrib:0.36.0


docker run --rm -e SPLUNK_ACCESS_TOKEN=DvUejX0LLmm33hVW6qkRAg -e SPLUNK_REALM=us1 \
    -v "${PWD}/etc/splunk-collector-config.yaml":/etc/collector.yaml \
    -p 13133:13133 -p 14250:14250 \
    -p 14268:14268 -p 4317:4317 -p 6060:6060 -p 4318:4318 -p 8888:8888 \
    -p 9080:9080 -p 9411:9411 -p 9943:9943 \
    --name otelcol quay.io/signalfx/splunk-otel-collector:latest 
    # A volume mount might be required to load the custom configuration file.
    
    # Use a semantic versioning (semver) tag instead of the ``latest`` tag.
    # Semantic versioning is a formal convention for determining the version
    # number of new software releases.

    docker run --rm -e SPLUNK_ACCESS_TOKEN=DvUejX0LLmm33hVW6qkRAg -e SPLUNK_REALM=us1 \
    -p 13133:13133 -p 14250:14250 -p 14268:14268 -p 4317:4317 -p 6060:6060 \
    -p 7276:7276 -p 8888:8888 -p 9080:9080 -p 9411:9411 -p 9943:9943 \
    --name otelcol quay.io/signalfx/splunk-otel-collector:latest