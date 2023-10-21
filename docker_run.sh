#!/bin/bash
rm -rf Docker/Application/*.jar
mvn clean -DskipTests package
cp target/*.jar Docker/Application/app.jar
cd Docker && docker-compose up