package com.madhu.bookStore.Service

import com.google.gson.Gson
import com.madhu.bookStore.Model.Book
import com.madhu.bookStore.Repository.BookRepository


object BookService {

  def getAllBooks():String={
    new Gson().toJson(BookRepository.getAllBooks.values.toArray)

  }

  def searchByISBN(isbn:Long):String={

    BookRepository.searchByISBN(isbn) match {
      case null => "No book found"
      case (value:Book) => new Gson().toJson(value)
    }

  }

  def search(name:String, value:Any): String= {
    var result:String=null;
    if (BookRepository.search(name,value).isEmpty) {
      result = "No book found";
    }
    else{
      result = new Gson().toJson(BookRepository.search(name,value).toArray)
    }
    result
    }


  def insertBook(b:Book):Unit={
    BookRepository.insertBook(b)
  }


}
