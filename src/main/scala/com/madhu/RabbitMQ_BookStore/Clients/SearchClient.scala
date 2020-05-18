package com.madhu.RabbitMQ_BookStore.Clients

import org.apache.log4j.BasicConfigurator

import scala.util.{Failure, Success, Try}

object SearchClient {
  def main(args: Array[String]): Unit = {
    BasicConfigurator.configure()
    var searchClientRPC: RPCClient = null
    var response: String = null
    val host = if (args.isEmpty) "localhost" else args(0)
    Try(new RPCClient(host)) match {
      case Failure(exception) => exception.printStackTrace()
      case Success(value) => {
        searchClientRPC = value
        val message = "Enid Blyton"
        println(" [x] Requesting " + message + " ")
        response = searchClientRPC.call("SEARCH", message)
        println(" [.] Received " + response + "")
      }
    }

    if (searchClientRPC != null) {
      Try(searchClientRPC.close()) match {
        case Failure(exception) => print("" + exception.toString)
        case Success(value) => print("Succesfully SearchClient is Closed")
      }
    }
  }

}
