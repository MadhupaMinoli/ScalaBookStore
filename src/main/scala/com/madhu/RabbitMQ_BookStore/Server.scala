package com.madhu.RabbitMQ_BookStore

import java.util.concurrent.CountDownLatch

import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client._

object Server {
  private val EXCHANGE_NAME = "direct_RequestType"
  private val INPUT_QUEUE_NAME = "input_queue"

  def main(args: Array[String]): Unit = {
    var connection: Connection = null
    var channel: Channel = null
    try {
      val factory = new ConnectionFactory
      factory.setHost("localhost")
      val connection = factory.newConnection
      val channel = connection.createChannel
      channel.exchangeDeclare(EXCHANGE_NAME, "direct")
      channel.queueDeclare(INPUT_QUEUE_NAME, false, false, false, null)

      val requestTypes: Array[String] = Array[String]("GET", "SEARCH", "ADD")
      for (requestType <- requestTypes) {
        channel.queueBind(INPUT_QUEUE_NAME, EXCHANGE_NAME, requestType)
      }

      channel.basicQos(1)
      val latch = new CountDownLatch(1)
      val serverCallback = new ServerCallback(channel, latch)
      val cancelCallback = new Cancle
      channel.basicConsume(INPUT_QUEUE_NAME, false, serverCallback, cancelCallback)
      println(" [x] Awaiting Requests....................")
      latch.await()
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      if (connection != null) {
        try {
          connection.close()
        } catch {
          case ignore: Exception =>
        }
      }
    }
  }

  class ServerCallback(val ch: Channel, val latch: CountDownLatch) extends DeliverCallback {

    override def handle(consumerTag: String, delivery: Delivery): Unit = {
      var response: String = null
      val replyProps = new BasicProperties.Builder().correlationId(delivery.getProperties.getCorrelationId).build

      try {
        val message = new String(delivery.getBody, "UTF-8")
        response = "" + RequestController.handle(delivery.getEnvelope.getRoutingKey, message)
      } catch {
        case e: Exception => {
          println(" [.] " + e.toString)
          response = ""
        }
      } finally {
        ch.basicPublish("", delivery.getProperties.getReplyTo, replyProps, response.getBytes("UTF-8"))
        ch.basicAck(delivery.getEnvelope.getDeliveryTag, false)
        latch.countDown()
      }
    }
  }

  class Cancle extends CancelCallback {
    override def handle(consumerTag: String): Unit = {
    }
  }
}
