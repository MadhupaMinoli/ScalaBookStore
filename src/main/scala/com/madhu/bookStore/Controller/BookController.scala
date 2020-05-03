package com.madhu.bookStore.Controller

import java.net.URI

import com.madhu.bookStore.Model.{Book, Response}
import com.madhu.bookStore.Service.BookService
import com.madhu.bookStore.Utility.{JsonParser, UrlSplitter}


object BookController {


  def getRoute(url: URI): Response= {

    val pathList:List[String] = UrlSplitter.pathToList(url.getPath.toLowerCase())

    pathList.length match {
      case 1 if (url.getQuery == null)
            => createSuccessResponse(JsonParser.objectToJson(BookService.getAllBooks()))
      case 1 if (url.getQuery != null)
            => createSuccessResponse(JsonParser.objectToJson(BookService.search(url.getQuery.split('=')(0).toLowerCase(),url.getQuery.split('=')(1).toLowerCase)))
      case 3 if (pathList(1)=="book")
            => createSuccessResponse(JsonParser.objectToJson(BookService.searchByISBN(pathList(2).toLong)))
      case _ => createInvalidResponse
    }


  }

  def postRoute(book:Book,url : URI): Response={

    val pathList:List[String] = UrlSplitter.pathToList(url.getPath.toLowerCase())

    pathList.length match {
      case 2 if (pathList(1)=="book")
            => createSuccessResponse(JsonParser.objectToJson(BookService.insertBook(book)))
      case _ => createInvalidResponse
    }



  }
  def createSuccessResponse(string: String): Response= {
    new Response(string,200)
  }

  def createInvalidResponse: Response= {
    new Response ("Invalid Request",400)
  }
}
