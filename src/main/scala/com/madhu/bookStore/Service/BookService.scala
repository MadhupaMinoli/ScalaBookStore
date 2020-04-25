package com.madhu.bookStore.Service

import com.google.gson.Gson
import com.madhu.bookStore.Model.{Book, Response}
import com.madhu.bookStore.Repository.BookRepository


object BookService {

  def getAllBooks():Response={

    new Response(new Gson().toJson(BookRepository.getAllBooks.values.toArray),200)

  }

  def searchByISBN(isbn:Long):Response={

    BookRepository.searchByISBN(isbn) match {
      case null => new Response("No Book was Found",404)
      case (value:Book) => new Response(new Gson().toJson(value),200)
    }

  }

  def search(name:String, value:Any): Response = {

    if (BookRepository.search(name,value)==null) {
     new Response("Invalid Query Term", 400);
    }
    else if (BookRepository.search(name,value).isEmpty) {
      new Response ("No Books were Found",200);
    }
    else{
      new Response(new Gson().toJson(BookRepository.search(name,value).toArray),200)
    }

  }


  def insertBook(b:Book):Response={
    new Response(new Gson().toJson(BookRepository.insertBook(b)),200)
  }


}
