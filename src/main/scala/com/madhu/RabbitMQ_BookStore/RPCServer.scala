package com.madhu.RabbitMQ_BookStore

import java.util.concurrent.CountDownLatch

import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client._

import scala.util.{Failure, Success, Try}

object RPCServer {

  private val INPUT_QUEUE_NAME = "input_queue"

  def main(args: Array[String]): Unit = {
    val connection: Connection = null

    Try(new ConnectionFactory) match {
      case Success(factory) => {
        factory.setHost("localhost")
        val connection = factory.newConnection
        val channel = connection.createChannel
        channel.queueDeclare(INPUT_QUEUE_NAME, false, false, false, null)
        channel.basicQos(1)
        val latch = new CountDownLatch(1)
        val serverCallback = new ServerCallback(channel, latch)
        val cancelCallback = new Cancle
        channel.basicConsume(INPUT_QUEUE_NAME, false, serverCallback, cancelCallback)
        println(" [x] Awaiting Requests....................")
        latch.await()
      }
      case Failure(exception) => exception.printStackTrace()
    }

    if (connection != null) {
      Try(connection.close()) match {
        case Success(value) => print(" [.] Successfully Connection is Closed")
        case Failure(exception) => print(" [.] " + exception.toString)
      }
    }
  }

  class ServerCallback(val ch: Channel, val latch: CountDownLatch) extends DeliverCallback {
    override def handle(consumerTag: String, delivery: Delivery): Unit = {
      var response: String = null
      val replyProps = new BasicProperties.Builder().correlationId(delivery.getProperties.getCorrelationId).build
      val message = new String(delivery.getBody, "UTF-8")

      Try(RequestController.handle(delivery.getProperties.getType, message)) match {
        case Success(value) => response = "" + value
        case Failure(exception) => {
          println(" [.] " + exception.toString)
          response = ""
        }
      }

      ch.basicPublish("", delivery.getProperties.getReplyTo, replyProps, response.getBytes("UTF-8"))
      ch.basicAck(delivery.getEnvelope.getDeliveryTag, false)
      latch.countDown()
    }
  }

  class Cancle extends CancelCallback {
    override def handle(consumerTag: String): Unit = {
    }
  }
}