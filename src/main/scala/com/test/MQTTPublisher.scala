package com.test

import org.eclipse.paho.client.mqttv3._
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

//import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
//import org.apache.spark.streaming.mqtt._
//import org.apache.spark.SparkConf

object MQTTPublisher {
   def main(args: Array[String]) {
    if (args.length < 2) {
      //System.err.println("Usage: MQTTPublisher <MqttBrokerUrl> <topic>")
    //  System.exit(1)
    }

   // StreamingExamples.setStreamingLogLevels()

    val brokerUrl="tcp://localhost:1883"
    val topic="TestTopic"
    val queue="TestQueue"
    //val Seq(brokerUrl, topic) = args.toSeq

    var client: MqttClient = null

    try {
      val persistence = new MemoryPersistence()
     
      client = new MqttClient(brokerUrl,"pahomqttpublish1",persistence)
       val connOpts = new MqttConnectOptions()
      connOpts.setCleanSession(true);
      connOpts.setUserName("admin")
      connOpts.setPassword("admin".toCharArray())
     // connOpts.setCleanSession(true)
       println("Connecting to broker: "+client);
      client.connect()

      
      
      val msgtopic = client.getTopic(topic)
      
      val msgContent = "1 gautam \n 2 verma \n 1 sani"
      val message = new MqttMessage(msgContent.getBytes("utf-8"))
      
      var i=0
      while (i<1) {
        try {
          msgtopic.publish(message)
          
          println(s"Published data. topic: ${msgtopic.getName()}; Message: $message")
          i+=1
        } catch {
          case e: MqttException if e.getReasonCode == MqttException.REASON_CODE_MAX_INFLIGHT =>
            Thread.sleep(10)
            println("Queue is full, wait for to consume data from the message queue")
        }
      }
    } catch {
      case e: MqttException => println("Exception Caught: " + e)
    } finally {
      if (client != null) {
        client.disconnect()
      }
    }
  }
}