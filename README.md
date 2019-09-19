# Train-Fee-Service
This respository is for project assignment of Service Oriented Programming, KMITL  

## Introduction  
This service give you the fare for going from one station to another station of Bangkok's train system

## Description  
This service will return the fare needed between 2 station either in the same line or different line. It will calculate the route that can be taken and return result of all the possible route. you have an option to make the service to return the least amount of time needed or the least amount of fare needed. you can view all the station in Bangkok's train system.

## List of method

| Method's Name | Description |
| --- | --- |
| viewstation | Get all station in Bangkok's train system |
| viewstation/{line} | Get all station in that train line |
| {station1}/to/{station2} | Get all possible route from station1 to station2 |
| {station1}/to/{station2}/lowestfare | Get route that have the lowest fare |
| {station1}/to/{station2}/lowesttime | Get route that use the lowest time |

## Example  
### Ex1 :  
(method GET) service.trainfare.com/viewstation
```JSON
{  
  "BTS" : ["Siam", "Asok", "etc"],  
  "MRT" : ["Taopuun", "Sukumwit", "etc"],  
  "Airlink" : ["Ladkrabang", "Baan Thap Chang", "etc"]  
}
```
### Ex2 :
(method GET) service.trainfare.com/Siam/to/Ladkrabang
```JSON
{
  "possibleRoutes" : 2,
  "routes" : [
    {
      "fare" : 45,
      "route" : "Change from BTS to Airlink at Paya Thai",
      "time" : 20
    },
    {
      "fare" : 60,
      "route" : "Change from BTS to MRT at Asok then change from MRT to Airlink at Phetchaburi",
      "time" : 30
    }
  ]
}
```  
### Ex3 :  
(method GET) service.trainfare.com/Siam/to/LadKrabang/lowestfare
```JSON
{
  "fare" : 45,
  "route" : "Change from BTS to Airlink at Paya Thai",
  "time" : 20
}
```  
### Ex4 :  
(method GET) service.trainfare.com/Siam/to/LadKrabang/lowesttime
```JSON
{
  "fare" : 45,
  "route" : "Change from BTS to Airlink at Paya Thai",
  "time" : 20
}
```

## Task

- [ ] Collect resource
- [ ] BTS fare calculator
- [ ] MRT fare calculator
- [ ] Airlink fare calculator
- [ ] View all stations
- [ ] View selected train line
- [ ] Connect all possible route
- [ ] Show lowest fare
- [ ] Show lowest time


## Members
- Thanapon  Wongprasert     60070031
- Napon     Tunglukmongkol  60070037
- Pitchayut Deachnu         60070063
- Poowis    kumpai          60070184
