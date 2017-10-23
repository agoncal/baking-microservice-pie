# Demo 02

The Demo 02 has an extra service to do CRUD operations on Books (`book-api`). It needs the service to retrieve an identification number for a product (`number-api`). It contains:

* Services
    * book-api
    * number-api
* Client
    * Angular app

To build the Angular App, execute:

## Cleaning Docker images

If you need to clean all the Docker images, use the following commands:

* `docker image ls | grep baking`
* `/bin/bash -c 'docker image rm $(docker image ls -q "baking/*") -f'`


## Build Angular

```bash
ng build --prod
```

## Build Services

To build the sample just run the Maven command from the Demo 01 root folder:

```bash
mvn clean install
```

## Run

### Locally

To run the `number-api` locally, you can just run the uber jar sitting in the project target folder with all the dependencies already packed in. The file is generated in the Build step. From the `services/number-api` folder, run:

```bash
java -jar target/number-api-02-swarm.jar
```

To run the `book-api` locally, 

```bash
java -jar target/book-api-02.jar
```

The application is available in `http://localhost:8084/number-api/api/`.

For the Angular application, use the developper's model in local and run:

```bash
ng serve
```

### Docker

To run the Demo with Docker, first the Docker Images need to be built. Run the Maven command From the 
Demo 01 root folder:

```bash
mvn docker:build
```

Then to start the services and the clients, then run:

```bash
mvn docker:start
```

This will start all the Docker Images for this Demo in background. The application is available in 
`http://localhost:8080`.

To stop the services and the clients, just run:

```bash
mvn docker:stop
```

### Docker Compose

On the root folder you will find a `docker-compose.yml` file. This will execute all the needed Docker images

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

The application is available in `http://pi-grom-server-01:8080`.
