# citrine-challenge

Web service to perform Unit Conversion to SI from their widely used counterparts.

The known Units can be found in `conf/application.conf`

```
GET /units/si?units=(degree/minute)

{
    "unit_name": "(rad/s)",
    "multiplication_factor": .00029088820866572
}
```

## Built with

- Java 8
- Scala 2.13
- sbt 1.3.13

## Unit Tests

Some Unit Tests were provided and can be run using below command

```
sbt test
```

## Running

The common way of running sbt applications inside Docker is by using Docker plugin.

We do need to install Java and sbt on our machines in order to do that.

1. Install Java 8
2. Install sbt
3. `sbt docker:publishLocal`
4. `docker run -p 9000:9000 citrine-challenge:1.0-SNAPSHOT`
5. By having sbt installed, simple `sbt run` will also do it

## Authentication

TBD

## Results

```
200 OK
Successful outcome

400 BAD REQUEST
The requested units were not found or the input syntax is wrong

500 INTERNAL SERVER ERROR
Unexpected outcome
```
