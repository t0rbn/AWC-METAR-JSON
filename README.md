# AWC METAR JSON API Gateway
Get [METAR](https://en.wikipedia.org/wiki/METAR) (METeorological Aerodrome Reports) information from [aviationweather.gov](https://www.aviationweather.gov/) using [their API](https://www.aviationweather.gov/dataserver/example?datatype=metar) converted from XML to JSON because it's [insert current year here]

## Setup
1. Install Java 16 and gradle
2. Build the project with `gradle clean build`
3. Run `java -jar build/libs/awc.json-0.0.1-SNAPSHOT.jar`

## API
The API will start on port `8080`. There's only one endpoint at `localhost:8080/metar?q={station}` where `station` is the [ICAO](https://en.wikipedia.org/wiki/International_Civil_Aviation_Organization) code of the location you want to request METAR for. 

### Example
To get METAR for the Los Angeles International Airport (ICAO: KLAX), simply make this request:
```
curl "localhost:8080/metar?q=KLAX" 
```
The response looks like this:
```
{
    "rawText":"KLAX 032053Z 27013KT 10SM FEW010 FEW100 SCT150 21/16 A2993 RMK AO2 SLP132 T02110161 58008 $",
    "tempCelsius":21.1,
    "dewPointCelsius":16.1,
    "windDirectionDegrees":270.0,
    "windSpeedKnots":13.0,
    "visibilityMiles":10.0,
    "altimeterHG":29.929134,
    "skyCoverage":"FEW"
} 
```
