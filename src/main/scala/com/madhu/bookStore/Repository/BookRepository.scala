package com.madhu.bookStore.Repository

import com.madhu.bookStore.Model.Book

import scala.collection.mutable

object BookRepository {
  val b1 = Book(9783161484100L, "HarryPotter", "JK Rowling")
  val b2 = Book(9781444907957L, "Famous Five", "Enid Blyton")
  val b3 = Book(97814449507957L, "Famous Five", "Enid Blyton")

  private var bookList = mutable.HashMap[Long, Book](b1.isbn -> (b1), b2.isbn -> (b2), b3.isbn -> (b3))

  def getAllBooks: mutable.HashMap[Long, Book] = bookList

  def insertBook(book: Book): Book = {

    def addBook: Book = if (searchByISBN(book.isbn) == None) addNewBook else updateExistingBookQuantity

    def addNewBook: Book = {
      book.quantity = 1;
      bookList += (book.isbn -> book)
      bookList(book.isbn)
    }

    def updateExistingBookQuantity: Book = {
      bookList(book.isbn).quantity += 1
      bookList(book.isbn)
    }

    addBook
  }

  def searchByISBN(isbn: Long): Option[Book] = bookList.values.find(book => book.isbn == isbn)

  def search(name: String, value: String): Iterable[Book] = name match {
    case "author" => bookList.values.filter(book => book.author.toLowerCase() == value)
    case "title" => bookList.values.filter(book => book.title.toLowerCase() == value)
  }
}
