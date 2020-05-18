package com.madhu.RabbitMQ_BookStore.Clients

import java.util.UUID
import java.util.concurrent.{ArrayBlockingQueue, BlockingQueue}

import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client._

class RPCClient(host: String) {
  val factory = new ConnectionFactory()
  factory.setHost(host)
  val connection: Connection = factory.newConnection()
  val channel: Channel = connection.createChannel()
  val requestQueueName: String = "input_queue"
  val replyQueueName: String = channel.queueDeclare().getQueue

  def call(requestType: String, message: String): String = {
    val corrId = UUID.randomUUID().toString
    val props = new BasicProperties.Builder().correlationId(corrId).`type`(requestType)
      .replyTo(replyQueueName)
      .build()
    channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"))
    System.out.println(" [x] Sent " + requestType + " " + message + "")

    val responseCallback = new ResponseCallback(corrId)
    val cancelCallback = new Cancle
    channel.basicConsume(replyQueueName, true, responseCallback, cancelCallback)

    responseCallback.take()
  }

  def close() {
    connection.close()
  }
}

class ResponseCallback(val corrId: String) extends DeliverCallback {
  val response: BlockingQueue[String] = new ArrayBlockingQueue[String](1)

  override def handle(consumerTag: String, message: Delivery): Unit = {
    if (message.getProperties.getCorrelationId.equals(corrId)) {
      response.offer(new String(message.getBody, "UTF-8"))
    }
  }

  def take(): String = {
    response.take();
  }
}

class Cancle extends CancelCallback {
  override def handle(consumerTag: String): Unit = {
  }
}

