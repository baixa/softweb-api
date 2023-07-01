# Version Schedule
## Version 1.0.1-SNAPSHOT "Controllers" (**RELEASED**)
> App contains controllers, that provides CRUD operations with basic entities. Also application must be launched as docker container in relevant network with data source.

### Tasks
* [x] #2
* [x] #4
* [x] #5


## Version 1.0.2-SNAPSHOT "Eureka" (**RELEASED**)
> App contains discovery service, that manage others modules

### Tasks
* [x] #12
* [x] #13
* [x] #14

## Version 1.0.3 "Sirius" (**RELEASED**)
> App contains authorization and provides access to endpoints by users' roles. Fix controllers to return DTOs instead of Entities. Authorization is included in the application module. A configuration module was also created, where the basic configuration of the store module was transferred

### Tasks
* [x] #8
* [x] #10
* [x] #27
* [x] #15
* [x] #16

## Version 1.0.4 "Polaris" (**RELEASED**)
> Added a request forwarding module and configured filters and receiving configs from the configuration server. Added categories of applications and filled in the initial data for further development of the web client

### Tasks

* [x] #53
* [x] #34
* [x] #33
* [x] #37
* [x] #38
* [x] #39
* [x] #56
* [x] #66


## Version 1.0.5 "Castor"
> Modification of the configuration module. Added support for loading the configuration of their git repository and vault storage. The Eureka server was also improved by moving its configuration into the appropriate module. Added authorization improvements. Created a separate module responsible for user authentication. Added support for JWT and OAuth. Also, the module configuration has been moved to the server-config module.

### Tasks
* [x] #6
* [x] #9
* [x] #11
* [x] #17
* [x] #18 - **Optional**
* [ ] #19:
    + [ ] #20
    + [ ] #21 - **Optional**

## Version 1.0.6 "Pollux"
> Evaluation of the application and the interaction of its modules. Correction of errors and bugs in the system. Preparing to set up desktop client interaction

### Tasks
* Testing Application module:
    + [ ] #40
    + [ ] #41
* Testing Authorization module:
    + [ ] #42
    + [ ] #43
* Testing Eureka module:
    + [ ] #44
    + [ ] #45
* Testing Config module:
    + [ ] #46
    + [ ] #47
* Testing Gateway module:
    + [ ] #48
    + [ ] #49
* [x] #50
  <h3 align=center>IN DEV<h3>