# Demo 01

The Demo is just a simple service to retrieve an identification number for a product (`number-api`). It contains:

* Services
    * number-api (Wildfly Swarm on port 8084)
* Client
    * Angular app (NGinx on port 8080)

## Build

To build the sample just run the Maven command from the Demo root folder:

```bash
mvn clean install
```

This should build everything.

### Angular App

If you only need to build the Angular App, from the `clients/angular` folder, execute:

```bash
ng build --prod
```

### Cleaning Docker images

If you need to clean all the Docker images, use the following commands:

* `docker image ls | grep baking`
* `/bin/bash -c 'docker image rm $(docker image ls -q "baking/*") -f'`

## Run

You can run the Demo either Locally, with Docker, with Docker Compose, or in the PI Cluster.

The services are available at:
* `http://localhost:8084/number-api/api/`

The client is available at:
* `http://localhost:8080`

### Locally

To run the `number-api` locally, you just need to run the uber jar sitting in the project target folder. This jar 
already contains all the required dependencies. The file is generated in the Build step. From  
the root folder of the service run:

```bash
java -jar target/number-api-01-swarm.jar
```

You can execute the following HTTP requests

```
### Demo 01

GET http://localhost:8084/number-api/api/numbers/book
Accept: */*
Cache-Control: no-cache

### Demo 01 (Swagger)

GET http://localhost:8084/number-api/swagger.json
Accept: */*
Cache-Control: no-cache
```

For the Angular application, use the developper's model in local and run:

```bash
ng serve


http://localhost:4200
```

### Docker

To run the Demo with Docker, first the Docker Images need to be built. Run the Maven command From the Demo root folder:

```bash
mvn docker:build
```

Then to start the services and the clients, then run:

```bash
mvn docker:start
```

This will start all the Docker Images for this Demo in background.

To stop the services and the clients, just run:

```bash
mvn docker:stop
```

### Docker Compose

On the root folder you will find a `docker-compose.yml` file. This will execute all the needed Docker images.

### Raspberry PI

To run on the Raspberry PI Cluster, first follow the steps in [Setup Raspberry PI Cluster](../setup/README.md). As 
described in the Setup, a Local Docker Registry is used to ease the deployment.

Push the Local built Docker Image into the Local Docker Registry: 

```bash
mvn docker:push '-Ddocker.filter=rpi'
```

#### Ansible
The Docker Images are then deployed into the PI's with [Ansible](http://ansible.com). From the `ansible` folder, run:

```bash
ansible-playbook -i hosts deploy.yaml -vvv
```

The services are available at:
* `http://pi-grom-server-01:8084/number-api/api/`

The client is available at:
* `http://pi-client-01:8080`
