# Local Docker Registry

A Local Docker Registry is used to speed up deployment and to not rely on a possible slow Internet access to the 
Docker Hub. The Docker Images used are not really that big, but they could take sometime to upload on a slow network.

## Setup

The Docker documentation already has a comprehensive guide to setup a Local Docker Registry: https://docs.docker.com/registry/deploying/

### TL;DR

#### Certificates

The Local Docker Registry requires certificates to allow clients to download Docker Images. Here is how you generate 
one:
```bash 
openssl req -newkey rsa:4096 -nodes -sha256 -keyout docker-registry.key -x509 -days 365 -out docker-registry.crt
```

#### Run
The Local Docker Registry runs like any other Docker Image: 
```bash  
docker run -d -p 5000:5000 --restart=always --name docker-registry -v `pwd`/certs:/certs -e REGISTRY_HTTP_TLS_CERTIFICATE=/certs/docker-registry.crt -e REGISTRY_HTTP_TLS_KEY=/certs/docker-registry.key registry:2
```

This can be executed from the folder `setup/ansible/docker-registry`. A certificate is already generated in the folder `setup/ansible/docker-registry/certs`.

#### Distribute Certificates

The self signed certificates must be added into the Docker configuration running in the PI's, so the PI's can download
Docker images from the Local Docker Registry. The Ansible script 
`setup/ansible/docker-registry/install-registry-cert.yaml` will handle that for you.

#### Box running Local Docker Registry

The Docker process running the Local Docker Registry also requires to trust the Registry. For Macs, the easiest way is 
to add the address the Local Docker Registry address into the Mac Docker Daemon. Go to Preferences menu item, 
Daemon tab and in Insecure Registries section add `docker-registry:5000`.

For the Docker Images to be uploaded to the Local Docker Registry, the Image name needs to be prefixed with 
`docker-registry:5000`. This will be the hostname used to upload the Docker Images. A specific hostname is required, so
that the Docker daemons running on the PI's know how to reach the dedicated Local Docker Registry. 
