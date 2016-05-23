package com.test

import org.eclipse.paho.client.mqttv3._
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import org.fusesource.hawtbuf.UTF8Buffer
import org.fusesource.mqtt.client.{MQTT, QoS}

object T {
	def main(args: Array[String]): Unit = {

			//     val brokerUrl="tcp://localhost:61616"
			//    val topic="TestTopic"
			//    
			//    var client: MqttClient = null
			//
			//    try {
			//      val persistence = new MemoryPersistence()
			//      
			//      client = new MqttClient(brokerUrl,"pahomqttpublish1",persistence)
			//       val connOpts = new MqttConnectOptions()
			//      connOpts.setCleanSession(true);
			//      connOpts.setUserName("admin")
			//      connOpts.setPassword("admin".toCharArray())
			//       println("Connecting to broker: "+client);
			//      client.connect()
			//    }catch{
			//      case e: MqttException => println("Exception Caught: " + e)
			//    }

			val mqtt = new MQTT()
					mqtt.setHost("tcp://localhost:1883");
					println("v21")
					//  mqtt.setCleanSession(true)
					//  mqtt.setKeepAlive(1000)
				//	mqtt.setUserName("admin")
				//	mqtt.setPassword("admin")
					//  mqtt.setWillMessage("my last will")

					val connection = mqtt.futureConnection()
					connection.connect().await()


					val topic = new UTF8Buffer("TestQueue")
					val msg = new UTF8Buffer("hello")
					//for (i <- 1 until 1000) {
					
					val future = connection.publish(topic, msg, QoS.AT_LEAST_ONCE, false)
					// }

					connection.disconnect().await()

	}
}