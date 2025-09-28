# Set up 
run mvn clean install
mvn spring-boot:run 

Query API at localhost:8080/sensors/ 

POST /sensors/send-reading 
BODY 
{
    "sensorId": 2,
    "metric": "TEMPERATURE",
    "value": 860
}

GET /sensors/all?metrics=&statistic=&begin=&end=
GET /sensors/?ids=&metrics=&statistic=&begin=&end=


# Swagger 
http://localhost:8080/swagger-ui/index.html

# Sample queries

Get me the avg temp, humidity, precipitation readings from sensor 1 and 2 betweem 20-09 and 30-09 

curl --location --request GET 'localhost:8080/sensors/query/?ids=1%2C2&metrics=TEMPERATURE%2CHUMIDITY%2CPRECIPITATION&statistic=AVG&begin=2025-09-20&end=2025-09-28' \
--header 'Content-Type: application/json' \
--data '{
    "sensorId": 2,
    "metric": "HUMIDITY",
    "value": 10
}'


Get me the min temp from all sensors betweem 20-09 and 30-09 

curl --location --request GET 'localhost:8080/sensors/query/all?null=null&metrics=TEMPERATURE&statistic=MIN&begin=2025-09-20&end=2025-09-28'


Send reading to API 

curl --location 'localhost:8080/sensors/send-reading' \
--header 'Content-Type: application/json' \
--data '{
    "sensorId": 1,
    "metric": "TEMPERATURE",
    "value": 30
}'

