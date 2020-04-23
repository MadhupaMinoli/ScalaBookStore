package com.madhu.bookStore.Controller

import java.net.URI

import com.google.gson.Gson
import com.madhu.bookStore.Model.Book
import com.madhu.bookStore.Service.BookService
import com.madhu.bookStore.Utility.UrlSplitter


object BookController {


  def getRoute(url: URI): String= {

    val pathList:List[String] = UrlSplitter.pathToList(url.getPath.toLowerCase())

    pathList.length match {
      case 1 if (url.getQuery == null) => BookService.getAllBooks()
      case 1 if (url.getQuery != null) => BookService.search(url.getQuery.split('=')(0).toLowerCase(),url.getQuery.split('=')(1).toLowerCase)
      case 3 if (pathList(1)=="book")=> BookService.searchByISBN(pathList(2).toLong)

    }


  }

  def postRoute(book:Book):Unit={

    BookService.insertBook(book)
  }
}
