#!/bin/sh

if [ "$1" = "start" ]
then
  # Build JAR packages (without tests, because database has not been defined yet)
  mvn clean package dockerfile:build -Dmaven.test.skip

  # Create docker network
  docker-compose -f bin/docker-compose.yml up -d
elif [ "$1" = "rm" ]
then
  # Remove docker network
  docker-compose -f bin/docker-compose.yml rm -f -s -v
elif [ "$1" = "stop" ]
then
  # Stop docker network
  docker-compose -f bin/docker-compose.yml stop
elif [ "$1" = "restart" ]
then
  # Remove docker network
  docker-compose -f bin/docker-compose.yml rm -f -s -v
  # Build JAR packages (without tests, because database has not been defined yet)
  mvn clean package dockerfile:build -Dmaven.test.skip
  # Create docker network
  docker-compose -f bin/docker-compose.yml up -d
fi


