# House Price Estimator using web scraping and multiple regression

(Out of date due to zoopla updates)

The program takes a location as input and can scrape a specific number of house listings from zoopla for:
- Price
- Distance from a station
- Number of bedrooms/bathrooms/living rooms

Multiple regression can then be used to extrapolate the cost of a house in that location, given the variables above. (Currently using built-in excel function)

## Usage
```shell
javac HousePriceEstimator.java
```
```shell
java HousePriceEstimator
```
**URL must be set internally**


### Example CSV output:
<p align="center">
  <img src="https://github.com/oliver7011/House-Price-Estimator/blob/main/example_csvOutput.PNG" title="hover text">
</p>

## To Do:
- Integrate Multiple Regression feature into program
- Create GUI
- Allow users to enter specific postcode
- Additional characteristics (e.g plot size)
