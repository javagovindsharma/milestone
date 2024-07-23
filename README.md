# Milestone Project


1. Commands to start Zookeeper and Kafka :
   
   

    .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

    .\bin\windows\kafka-server-start.bat .\config\server.properties
2. to show the tpoics message
   .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic attendance_records --from-beginning

   # Milestone Project


1. download Kafka

   https://downloads.apache.org/kafka/3.7.0/kafka_2.12-3.7.0.tgz
2. Download mysql and application.properties have setup our database configuration
3. Commands to start Zookeeper and Kafka :



    .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

    .\bin\windows\kafka-server-start.bat .\config\server.properties

3. To Show Kafka


    .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic attendance_records --from-beginning
4.  download postmain First take token.
5.   Authlogin  
     curl --location 'http://localhost:8080/auth/login' \

--header 'Content-Type: application/json' \
--data '{
"email":"admin",
"password":"123"
}'
6. IN/OUT take
   curl --location 'http://localhost:8080/attendance/swipe' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwOTQ4NjkwNSwiZXhwIjoxNzA5NTA0OTA1fQ.eeiEcnBpCWSYOfWUPZTMdcgmzxe2BToC9kZ2iuukK6MV4XnqNnKWkJc0Fv8TpFcEenZikaqTQyOq5mRpA-FmDA' \
   --header 'Content-Type: application/json' \
   --data '{
   "employeeId":1202,
   "swipeType":"OUT"
   }'
7. curl --location 'http://localhost:8080/attendance/totalHours/1201' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwOTQ4NjkwNSwiZXhwIjoxNzA5NTA0OTA1fQ.eeiEcnBpCWSYOfWUPZTMdcgmzxe2BToC9kZ2iuukK6MV4XnqNnKWkJc0Fv8TpFcEenZikaqTQyOq5mRpA-FmDA'
