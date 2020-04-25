package com.madhu.bookStore.Controller

import java.net.URI

import com.madhu.bookStore.Model.{Book, Response}
import com.madhu.bookStore.Service.BookService
import com.madhu.bookStore.Utility.UrlSplitter


object BookController {


  def getRoute(url: URI): Response= {

    val pathList:List[String] = UrlSplitter.pathToList(url.getPath.toLowerCase())

    pathList.length match {
      case 1 if (url.getQuery == null) => BookService.getAllBooks()
      case 1 if (url.getQuery != null) => BookService.search(url.getQuery.split('=')(0).toLowerCase(),url.getQuery.split('=')(1).toLowerCase)
      case 3 if (pathList(1)=="book")=> BookService.searchByISBN(pathList(2).toLong)
      case _ => new Response("Invalid Request",400)
    }


  }

  def postRoute(book:Book,url : URI): Response={

    val pathList:List[String] = UrlSplitter.pathToList(url.getPath.toLowerCase())

    pathList.length match {
      case 2 if (pathList(1)=="book")=> BookService.insertBook(book)
      case _ => new Response ("Invalid Request",400)
    }


  }
}
