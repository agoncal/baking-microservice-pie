# Baking a Microservice Pi(e)

This is the code of the demos for the "Baking a Microservice Pie" talk given by [@radcortez](https://twitter.com/radcortez) and [@agoncal](https://twitter.com/agoncal).

Imagine you have several microservices exposing REST APIs. Imagine now that these microservices are spread all over and need to talk to each other. Imagine that you have a nice user interface interacting with these APIs where you can authenticate. And now, imagine that all this runs smoothly.

In this Deep Dive session, Roberto and Antonio will build, step by step, a full microservice architecture (using Java and different frameworks). This session will answer these questions:

* How to build, document and deploy several microservices spread on different nodes (we use a Raspberry PI cluster because the Cloud is too expensive)
* How to make those microservices talk to each other (Consul for registry and discovery)
* How to scale up, down, and deal with network failures (Ribbon and Zuul to the rescue)
* How to deal with high traffic (Hystrix, here you come)
* How to monitor this distributed system (Dropwizard metrics with the ELK stack)
* How to centralize configuration
* How to authenticate and manage authorization with JWT (Tribestream Access Gateway)
* How to have a centralized nice looking entry point (with Angular)

Its genesis comes from the [Tomitribe](http://www.tomitribe.com/) talk: [Microprofile JCache](https://github.com/tomitribe/microprofile-jcache)

## Slides

To get the slides of this presentation, just generate them by doing a `mvn clean process-resources` under the `slides` directory. The slides will then be generated under `setup/target/generated-docs/slides.html`.

## Setup

Instructions to set up the environments and run the demos can be found [here](setup/src/main/asciidoc/spine.adoc).

## Video

You can watch us perform the demos and the presentation [here](https://youtu.be/iH4i2q-HSvI).
