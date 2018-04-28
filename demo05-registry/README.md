# Demo 05

This Demo uses a Registry server (Consul) so that all services (`book-api` and `number-api`) can register and lookup 
for each other. The Angular application also uses the registry. It contains:

* Infrastructure
    * ElasticSearch
    * Logstash
    * Kibana (port 5601)
    * Consul (port 8500 with `consul agent -dev -advertise 127.0.0.1`)
* Services
    * book-api (TomEE on port 8081)
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
* `http://localhost:8081/book-api/api/`
* `http://localhost:8084/number-api/api/`

The client is available at:
* `http://localhost:8080`

Kibana:
* `http://localhost:5601/`

### Locally

To run the `number-api` and `book-api` locally, you just need to run the uber jar sitting in the project target folder. 
These jars already contains all the required dependencies. The files are generated in the Build step. From the each of 
the root folders of the services run:

To run `number-api` locally.

```bash
java -jar target/number-api-05-swarm.jar
```

To run the `book-api` locally, 

```bash
java -jar target/book-api-05.jar
```

For the Angular application, use the developer's model in local and run:

```bash
ng serve
```

You also required `Consul` to run this sample. The easiest way is to run it in a Docker container with:

```
docker run -d --name=consul -p 8500:8500 consul
```

### Docker

To run the Demo with Docker, first the Docker Images need to be built. Run the Maven command From the 
Demo 05 root folder:

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

On the root folder you will find a `docker-compose.yml` file. This will execute all the needed Docker images, including
the ELK stack and Consul.

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

### ELK
The ELK stack also runs in a Docker container. Please refer to the [documentation](http://elk-docker.readthedocs.io/) if
you have any issue running the ELK stack. 

The Raspberry PI's cannot run the ELK stack due to insuficient hardware resources. So for this Demo, you should run the
Docker Compose file to start up the ELK stack and then run the Raspberry PI's services. You can run just the ELK stack 
with:

```bash
docker-compose up elk
```

#### Kibana
You need to setup Kibana for accessing the services logs. Go to `http://localhost:5601/` and you should create an index
based on timestamp. The option is only available after log is sent to Logstash, so make sure you invoke something on the
services to generate some log.

There is also a pre build dashboard that you can import. In the `setup/elk` folder you can find two files, 
`status_code.painless` and `export.json`.

Go to Kibana into `Management` and then `Index Patterns` and add a `Scripted Field` named `status_code`. Set the 
`type` and `format` to `string` and then paste the content of the `status_code.painless` file in the `script` section.

To import the dashboard, go to `Management` and `Saved Objects`. Hit `Import` and pick the file `export.json`. 
You may need to reassociate the newly created index when doing the import.
