package com.madhu.RabbitMQ_BookStore

import java.util.UUID
import java.util.concurrent.{ArrayBlockingQueue, BlockingQueue}

import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client._
import org.apache.log4j.BasicConfigurator

object Client {
  private val EXCHANGE_NAME = "direct_RequestType"

  def main(args: Array[String]): Unit = {
    BasicConfigurator.configure()
    var clientRPC: Client = null
    var response: String = null
    try {
      val requestType = args(0)
      val message = getMessage(args)
      clientRPC = new Client()
      println(" [x] Requesting " + requestType + "/" + message + " ")
      response = clientRPC.call(requestType, message)
      println(" [.] Response: '" + response + "'")
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      if (clientRPC != null) {
        try {
          clientRPC.close()
        } catch {
          case ignore: Exception =>
        }
      }
    }
  }

  private def getMessage(strings: Array[String]): String = {
    if (strings.length < 2) "All" else joinStrings(strings, " ", 1)
  }

  private def joinStrings(strings: Array[String], delimiter: String, startIndex: Int): String = {
    val length = strings.length
    if (length == 0) return ""
    if (length < startIndex) return ""
    val words = new StringBuilder(strings(startIndex))
    for (i <- startIndex + 1 until length) {
      words.append(delimiter).append(strings(i))
    }
    words.toString
  }

  class Client() {
    val factory = new ConnectionFactory()
    factory.setHost("localhost")
    val connection: Connection = factory.newConnection()
    val channel: Channel = connection.createChannel()

    channel.exchangeDeclare(EXCHANGE_NAME, "direct")
    val requestQueueName: String = "input_queue"
    val replyQueueName: String = channel.queueDeclare().getQueue

    def call(requestType: String, message: String): String = {
      val corrId = UUID.randomUUID().toString
      val props = new BasicProperties.Builder().correlationId(corrId)
        .replyTo(replyQueueName)
        .build()
      channel.basicPublish(EXCHANGE_NAME, requestType, props, message.getBytes("UTF-8"))
      System.out.println(" [x] Sent " + requestType + "/" + message + "")

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
}
