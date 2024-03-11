# Milestone Project


1. Commands to start Zookeeper and Kafka :
   
   

    .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

    .\bin\windows\kafka-server-start.bat .\config\server.properties
2. to show the tpoics message
   .\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic attendance_records --from-beginning