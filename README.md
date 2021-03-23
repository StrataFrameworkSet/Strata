# Strata
Strata is a set of frameworks that provide a foundation for developing microservices and client applications in Java, C#, and Typescript. These frameworks include:

* **Strata.Foundation.Core** - The core foundational framework that provides: 
  * concurrency extensions and helpers
  * object mappers for serialization
  * dependency injection adapters and extensions
  * event processing (receivers, senders, listeners)
  * miscellaneous utilities
  * value objects (EmailAddress, StreetAddress, PhoneNumber, ...)
* **Strata.Foundation.Kafka** - An extension of **Strata.Foundation.Core** that provides:
  * Kafka-based object mappers
  * Kafka-based event processing
* **Strata.Domain.Core** - The core object-relation mapping framework that provides:
  * abstractions for the [Repository](https://www.martinfowler.com/eaaCatalog/repository.html) and [Unit-Of-Work](https://www.martinfowler.com/eaaCatalog/unitOfWork.html) design patterns
  * mechanism for implementing domain events
* **Strata.Domain.Hibernate** - An extension of **Strata.Domain.Core** that implements adapters for [Hibernate](http://hibernate.org)
* **Strata.Domain.Redis** - An extension of **Strata.Domain.Core** that implements adapters for [Redis](https://redis.io)
* **Strata.Domain.InMemory** - An extension of **Strata.Domain.Core** that implements adapters for in-memory map-based Repositories and Units-Of-Work (primarily used to create [Test Double](http://xunitpatterns.com/Test%20Double.html)s)
* **Strata.Application.Core** - The core microservice application framework that provides:
  * abstractions for microservices
  * dependency injection extensions for wiring microservices applications
  * aspect-oriented interceptors for transaction process, timing metrics, and logging
  * filters for cross origin resource sharing and service reply enrichment
  * [OpenAPI](https://www.openapis.org) endpoint documentation automation
* **Strata.Application.Netty** - An extension of **Strata.Application.Core** that implements adapters for [Netty](https://netty.io)-based microservices applications
* **Strata.Application.Vertx** - An extension of **Strata.Application.Core** that implements adapters for [Vert.x](https://vertx.io)-based microservices applications
* **Strata.Client.Core** - The core client framework that provides:
  * abstractions for the [Model-View-Presenter](https://code.tutsplus.com/tutorials/an-introduction-to-model-view-presenter-on-android--cms-26162) design pattern
  * abstractions for REST service clients
* **Strata.Client.RestEasy** - An extension of **Strata.Client.Core** that implements adapters for service clients using [RestEasy](https://resteasy.github.io)
* **Strata.Client.React** - An extension of **Strata.Client.Core** that implements adapters for model-view-presenter user interfaces using [React](https://reactjs.org)
* **Strata.Diagnostic.Core** - The core application diagnostics framework that provides:
  * mechanisms for constructing suites of diagnostic checks and reporting results
  * repository for storing diagnostic suites
  * set of frequently used diagnostics checks in the areas of: memory checks, path checks (file/directory exists, file size, permissions), database and network connection checks
