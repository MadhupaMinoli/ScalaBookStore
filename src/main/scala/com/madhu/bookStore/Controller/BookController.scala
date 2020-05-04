package com.madhu.bookStore.Controller

import java.net.URI

import com.madhu.bookStore.Model.{Book, Response}
import com.madhu.bookStore.Service.BookService
import com.madhu.bookStore.Utility.{JsonParser, ResponseCreator, UrlSplitter}


object BookController {
  def getRoute(url: URI): Response = {

    val pathList: List[String] = UrlSplitter.pathToList(url.getPath.toLowerCase())

    pathList.length match {
      case 1 if (url.getQuery == null)
      => ResponseCreator.successResponse(JsonParser.objectToJson(BookService.getAllBooks()))
      case 1 if (url.getQuery != null)
      => ResponseCreator.successResponse(JsonParser.objectToJson(BookService.search(url.getQuery.split('=')(0).toLowerCase(), url.getQuery.split('=')(1).toLowerCase)))
      case 3 if (pathList(1) == "book")
      => ResponseCreator.successResponse(JsonParser.objectToJson(BookService.searchByISBN(pathList(2).toLong)))
      case _ => ResponseCreator.InvalidResponse
    }
  }



  def postRoute(book: Book, url: URI): Response = {

    val pathList: List[String] = UrlSplitter.pathToList(url.getPath.toLowerCase())

    pathList.length match {
      case 2 if (pathList(1) == "book")
      => ResponseCreator.successResponse(JsonParser.objectToJson(BookService.insertBook(book)))
      case _ => ResponseCreator.InvalidResponse
    }
  }
}
