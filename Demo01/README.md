# Demo 01

The Demo 01 is just a simple service to retrieve an identification number for a product. It contains:

* Services
    * number-api
* Client
    * Angular app

To build the Angular App, execute:

```bash
ng build --prod
```


## Build

To build the sample just run the Maven command from the Demo 01 root folder:

```bash
mvn clean install
```

## Run

### Locally

To run the `number-api` locally, you can just run the uber jar sitting in the project target folder with all the dependencies already packed in. The file is generated in the Build step. From the `services/number-api` folder, run:

```bash
java -jar target/number-api-01-swarm.jar
```

The application is available in `http://localhost:8084/number-api/api/`.

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
