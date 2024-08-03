#!/usr/bin/env bash
./gradlew shadowJar
docker build -t mygame .