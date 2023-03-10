<h1 align="center">
  <br>
  <img src="https://i.ibb.co/3S7K4tL/logo.png" alt="SoftWeb" width="200">
  <br>
  SoftWeb-API
  <br>
</h1>

<h4 align="center">API of multiplatform application store <i>SoftWeb</i></h4>

<p align="center">
  <a href="#key-features">Key Features</a> •
  <a href="#languages-and-tools">Languages and Tools</a> •
  <a href="#how-to-use">How To Use</a>
</p>

## Key Features

* View applications
* Download application
* Promote your product as a developer
* Get statistics about your applications
* Cross-platform
    - Windows, macOS and Linux.

### Languages and Tools

The main structure is based on Java (Spring). Desktop application is developed with Java framework - JavaFX.

<div>
  <img src="https://github.com/devicons/devicon/blob/master/icons/java/java-original-wordmark.svg" title="Java" alt="Java" width="40" height="40"/>&nbsp;
  <img src="https://github.com/devicons/devicon/blob/master/icons/spring/spring-original-wordmark.svg" title="Spring" alt="Spring" width="40" height="40"/>&nbsp;
  <img src="https://github.com/devicons/devicon/blob/master/icons/postgresql/postgresql-original-wordmark.svg" title="PostgreSQL"  alt="PostgreSQL" width="40" height="40"/>&nbsp;
</div>

## How To Use

### Launch

To clone and run this application, you'll need [Git](https://git-scm.com) and [Docker](https://www.docker.com/) installed on your computer. From your command line:

```bash
# Clone this repository
$ git clone https://github.com/baixa/softweb-api

# Go into the repository
$ cd softweb-api

# Run installation script (build jar and run docker-compose)
$ sh bin/control.sh start
```

### Base commands

Base commands to manage containers 

```bash
# Build jars and docker images and creates network
$ sh bin/control.sh start

# Stops containers and complete removes docker network and images
$ sh bin/control.sh rm

# Stops all containers
$ sh bin/control.sh stop

# Restart containers
$ sh bin/control.sh restart # Only reboot all containers
$ sh bin/control.sh restart -f # Remove all containers and create them from scratch
$ sh bin/control.sh restart -f container_name # Remove container by name and create it from scratch
```

### Simplification

If you want, you can create an alias of `sh bin/control.sh` command

```bash
 # Create alias
 $ echo alias sw=\'sh bin/control.sh\' >> ~/.bashrc
 
 # Refresh .bashrc
 $ source .bashrc
 
 # Use simplified commands
 $ sw start 
```

### Postman

1. Launch Postman and click __Import__ button
2. Find and select [collection](bin/postman/store.postman_collection.json)
3. Imported collection contains all base requests to Store-API

### Swagger Open API

SpringDoc (Swagger) is available at link: http://localhost:8180/v1/api-swagger/swagger-ui.html 
Due to inconsistencies with the Gateway mechanism (in development), slight malfunctions are possible.

---

> GitHub [@baixa](https://github.com/baixa)

