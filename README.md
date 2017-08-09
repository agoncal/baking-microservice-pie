# Baking a Microservice Pi(e)

This is the code of the demos for the "Baking a Microservice Pie" talk given by @radcortez and @agoncal.

The idea of the talk and the code, is to slowly build an entire microservice architecture (and deploy it on a cluster of Raspberry Pi). It is a DVD/CD/Book store where you can search for items, buy them (using a shopping cart) and see the inventory stock.

Its genesis comes from the [Tomitribe](http://www.tomitribe.com/) talk: [Microprofile JCache](https://github.com/tomitribe/microprofile-jcache)

## Architecture 

### Use cases

![Anonymous user use-case](http://www.plantuml.com/plantuml/png/TLBBQiCm4BpxA_ROGXBerOU0OvTGA24qfATUL5jrJ6EjqOFGlwyaIxKKgITRCxFl8Qr43cN5aFsCg4G9H00nu0mLXHAq9OB2muxZJN_h1beYoHNxHwDLxOpNJQBS8NvdjOP8rCrxFh76YoBe2DRhRLWrfWBELlalq6Pbz3QHdhhqKDPAI1mfNvRWaM0DhzWFp7Le_oCAbLmu_KZ69Am3DNL4KfWuVWrNwlJ7-RK4L5q7WwGZI0HnmMaQMAytn7q1pTVwbLI8jD0YavJ9-5Och8W6TCkP1xfdF6NcIJXB-kQpaRxHNC6meVjpLDqui458JlGpCAQ1WuFKR-ScTQL1GqzQLSQH1e_zQ9Mf6mriNVCdt5D6kJdaosbtMC7vMA_WWy5pTR1Ntf-UWAdnatBXKlW0xsSpYDk3QpUB1qQ-O-P-N31iDjkmo1ASPyo8CV4B)
![Customer use-case](http://www.plantuml.com/plantuml/png/TLBBQiCm4BpxA_ROGXBerOU0OvTGA24qfATUL5jrJ6EjqOFGlwyaIxKKgITRCxFl8Qr43cN5aFsCg4G9H00nu0mLXHAq9OB2muxZJN_h1beYoHNxHwDLxOpNJQBS8NvdjOP8rCrxFh76YoBe2DRhRLWrfWBELlalq6Pbz3QHdhhqKDPAI1mfNvRWaM0DhzWFp7Le_oCAbLmu_KZ69Am3DNL4KfWuVWrNwlJ7-RK4L5q7WwGZI0HnmMaQMAytn7q1pTVwbLI8jD0YavJ9-5Och8W6TCkP1xfdF6NcIJXB-kQpaRxHNC6meVjpLDqui458JlGpCAQ1WuFKR-ScTQL1GqzQLSQH1e_zQ9Mf6mriNVCdt5D6kJdaosbtMC7vMA_WWy5pTR1Ntf-UWAdnatBXKlW0xsSpYDk3QpUB1qQ-O-P-N31iDjkmo1ASPyo8CV4B)
![Administrator use-case](http://www.plantuml.com/plantuml/png/TLBBQiCm4BpxA_ROGXBerOU0OvTGA24qfATUL5jrJ6EjqOFGlwyaIxKKgITRCxFl8Qr43cN5aFsCg4G9H00nu0mLXHAq9OB2muxZJN_h1beYoHNxHwDLxOpNJQBS8NvdjOP8rCrxFh76YoBe2DRhRLWrfWBELlalq6Pbz3QHdhhqKDPAI1mfNvRWaM0DhzWFp7Le_oCAbLmu_KZ69Am3DNL4KfWuVWrNwlJ7-RK4L5q7WwGZI0HnmMaQMAytn7q1pTVwbLI8jD0YavJ9-5Och8W6TCkP1xfdF6NcIJXB-kQpaRxHNC6meVjpLDqui458JlGpCAQ1WuFKR-ScTQL1GqzQLSQH1e_zQ9Mf6mriNVCdt5D6kJdaosbtMC7vMA_WWy5pTR1Ntf-UWAdnatBXKlW0xsSpYDk3QpUB1qQ-O-P-N31iDjkmo1ASPyo8CV4B)


Adding PlantUML diagram in readme
### Technology used

### Raspberry Pi cluster

## Structure of the GitHub repository 

### Structure of the demos

This talk has several demos. Each one builds on top of the other. For example, `Demo02` is built on `Demo01` but brings the solution to a problem (eg. _How do we register microservices ?_). The last `Demo` folder (.i.e `Demo99`) is the final application, up and running.

* `Demo01` : We build several microservices (DVD, CD, Book, ShoppingCart, NumberGenerator and Inventory). Each service depends on another one. _Problem: The URLs are hard coded._
* `Demo02` : We add a registry service so that each microservice car register itself and discover the others. _Problem: How can we scale._
* `Demo03` : We need a client load-balancer so each microservice can choose from several instances of another service. _Problem: Now we have services everywhere, how do we monitor them._
* `Demo05` : We need centralized monitoring. _Problem: we need to secure these services with JWT in a centralize way._
* `Demo06` : We need a centralized gateway dealing with authentication. _Problem: under load, some services do not answer and block the other._
* `Demo07` : We need to implement the circuit breaker pattern. _Problem: Debugging is hard, we are lost between all services calls._
* `Demo08` : We need a tracking system
* `Demo99` : Final application

### Structure of the code 

```
+-- services
|   +-- book-api
|   |     +-- src
|   |       +-- main
|   |       +-- test
|   +-- cd-api
|   +-- dvd-api
|   +-- shoppingcart-api
|   +-- inventory-api
|   +-- numbergenerator-api
|   +-- monitoring
|   +-- registry
|   +-- tracing
|   +-- health
+-- client
|   +-- angular application
|   +-- jax rs client to test services
|   +-- curl commands
+-- deployment
|   +-- ansible scripts
|   +-- docker config
```
