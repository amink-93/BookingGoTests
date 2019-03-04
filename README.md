# BookingGoTest

## Requirements 
Java - 1.8
Maven - 3


## Usage

Download a clone of the project using:

```
git clone https://github.com/amink-93/BookingGoTest
```
Navigate to the correct directory

``` 
cd BookingGoTests
```

Build the project using:

```
mvn clean install
```

Run the unit tests using:

```
mvn test
```


## Part 1

Navigate to the correct directory

``` 
cd BookingGoTests
```

To start a search run the following command with your desired parameters:

```
java -jar cliapp/target/cliapp-1.0-SNAPSHOT-jar-with-dependencies.jar {pickup} {dropoff} {numPassengers}
```

Where 

    {pickup} = latitude longitude (as a double)
    
    {dropoff} = latitude longitude (as a double)
    
    {numPassengers} = the number of passngers taking the journey
    
### Example

```
java -jar cliapp/target/cliapp-1.0-SNAPSHOT-jar-with-dependencies.jar 51.470020 -0.454295 51.00 1.00 1
```


## Part 2

Navigate to the correct directory 
``` 
cd BookingGoTest-master
```

Start the server using:

```
java -jar api/target/api-0.0.1-SNAPSHOT.jar
```

Then navigate to the following url:

```
http://localhost:8080/search?pickup={latitude} {longitude}&dropoff={latitude} {longitude}&numPassengers={numberOfPassengers}
```

### Example

```
http://localhost:8080/search?&pickup=51.470020,-0.454295&dropoff=51.00,1.00&numPassengers=1
```

    
    
