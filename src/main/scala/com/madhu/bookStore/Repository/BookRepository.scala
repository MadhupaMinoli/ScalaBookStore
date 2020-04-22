package com.madhu.bookStore.Repository

import com.madhu.bookStore.Model.Book

import scala.collection.mutable

object BookRepository {
  val b1 =Book(9783161484100L, "HarryPotter", "JK Rowling")
  val b2 =Book(9781444907957L, "Famous Five", "Enid Blyton")
  val b3 =Book(9781444907957L, "Famous Five", "Enid Blyton")
  private val bookList = mutable.HashMap[Long, Book](b1.isbn->(b1),b2.isbn ->(b2));


  def getAllBooks:mutable.HashMap[Long, Book]= {

    bookList}

  def insertBook(book:Book):Unit={

     searchByISBN(book.isbn) match {
       case Some(value) => {
         bookList(value.isbn).quantity +=1;
       }
       case None => {
         bookList+=(book.isbn->book);
       }
     }

  }

  def searchByISBN(isbn:Long):Option[Book]={

      bookList.values.find(book=> book.isbn==isbn)



  }







}
