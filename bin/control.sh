#!/bin/sh

if [ "$1" = "start" ]
then
  # Build JAR packages (without tests, because database has not been defined yet)
  mvn clean package dockerfile:build -Dmaven.test.skip

  if [ "$?" -ne 0 ];
  then
    echo "Maven Package Unsuccessful!"
    exit 1
  fi

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

  # If exists -f key make full rebuilding of containers
  # shellcheck disable=SC2039
  if [[ $* == *-f* ]]
  then
    # shellcheck disable=SC2039
    containers=(database config eureka gateway store-api);

    if [ -z "$3" ]
    then
      # Remove docker network
      docker-compose -f bin/docker-compose.yml rm -f -s -v
      # Build JAR packages (without tests, because database has not been defined yet)
      mvn clean package dockerfile:build -Dmaven.test.skip

      # If maven packaging has errors echo user
      if [ "$?" -ne 0 ]; then
        echo "Maven Package Unsuccessful!"
        exit 1
      fi

      # Create docker network
      docker-compose -f bin/docker-compose.yml up -d

    # If exists container name
    elif [[ " ${containers[*]} " =~ $3 ]];
    then
      # Remove certain container, that name equals parameter
      docker rm -f -v "$3"

      # If container isn't database rebuild images for it
      if [ "$3" != "database" ]
      then
        mvn clean package dockerfile:build -Dmaven.test.skip -pl "$3" -am
      fi

      # Launch this container
      docker-compose -f bin/docker-compose.yml up -d "$3"
    else
      echo "Invalid container name!"
    fi
  else
    docker-compose -f bin/docker-compose.yml restart
  fi
fi


