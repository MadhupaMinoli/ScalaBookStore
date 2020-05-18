package com.madhu.RabbitMQ_BookStore.Clients

import org.apache.log4j.BasicConfigurator

import scala.util.{Failure, Success, Try}

object GetClient {
  def main(args: Array[String]): Unit = {
    BasicConfigurator.configure()
    var getClientRPC: RPCClient = null
    var response: String = null
    val host = if (args.isEmpty) "localhost" else args(0)
    Try(new RPCClient(host)) match {
      case Failure(exception) => exception.printStackTrace()
      case Success(value) => {
        getClientRPC = value
        val message = "9781444907957"
        println(" [x] Requesting " + message + " ")
        response = getClientRPC.call("GET", message)
        println(" [.] Received " + response + "")
      }
    }
    if (getClientRPC != null) {
      Try(getClientRPC.close()) match {
        case Failure(exception) => print("" + exception.toString)
        case Success(value) => print("Successfully getClientRPC is closed")
      }
    }
  }
}
