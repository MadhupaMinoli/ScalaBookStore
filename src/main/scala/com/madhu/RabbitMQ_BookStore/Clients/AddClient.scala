package com.madhu.RabbitMQ_BookStore.Clients

import com.madhu.bookStore.Model.Book
import com.madhu.bookStore.Utility.JsonParser
import org.apache.log4j.BasicConfigurator

import scala.util.{Failure, Success, Try}

object AddClient {
  def main(args: Array[String]): Unit = {
    BasicConfigurator.configure()
    var addClientRPC: RPCClient = null
    var response: String = null
    val host = if (args.isEmpty) "localhost" else args(0)
    Try(new RPCClient(host)) match {
      case Success(value) => {
        addClientRPC=value
        val newBook = Book(9781444907957L, "Famous Five", "Enid Blyton")
        val message = JsonParser.objectToJson(newBook)
        println(" [x] Requesting " + message + " ")
        response = addClientRPC.call("ADD", message)
        println(" [.] Received " + response + "")
      }
      case Failure(exception) => exception.printStackTrace()
    }

    if (addClientRPC != null) {
      Try(addClientRPC.close()) match {
        case Failure(exception) => print("" + exception.toString)
        case Success(value) => print("Succesfully AddClient is Closed")
      }
    }
  }
}
