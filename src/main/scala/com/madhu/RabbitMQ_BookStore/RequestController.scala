package com.madhu.RabbitMQ_BookStore

import com.madhu.bookStore.Service.BookService
import com.madhu.bookStore.Utility.JsonParser

object RequestController {
  def handle(routingKey: String, message: String): String = {
    routingKey match {
      case "GET" => if (message == "All") JsonParser.objectToJson(BookService.getAllBooks())
      else JsonParser.objectToJson(BookService.searchByISBN(message.toLong))
      case "SEARCH" => JsonParser.objectToJson(BookService.search(message.toLowerCase))
      case "ADD" => JsonParser.objectToJson(BookService.insertBook(JsonParser.jsonToBook(message)))
    }
  }
}
