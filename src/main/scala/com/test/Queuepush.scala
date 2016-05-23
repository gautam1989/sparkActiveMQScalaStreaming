package com.test

import org.apache.activemq.ActiveMQConnectionFactory;
 
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

object Queuepush {
  def main(args: Array[String]): Unit = {
    val connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:1883");
   
                // Create a Connection
                val connection = connectionFactory.createConnection();
                connection.start();
 
                // Create a Session
                val session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
 
                // Create the destination (Topic or Queue)
                val destination = session.createQueue("TestQueue");
                
                // Create a MessageProducer from the Session to the Topic or Queue
                val producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
 
                // Create a messages
                val text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
                val message = session.createTextMessage(text);
 
                // Tell the producer to send the message
                println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
                producer.send(message);
 
                // Clean up
                session.close();
                connection.close();
  }
}