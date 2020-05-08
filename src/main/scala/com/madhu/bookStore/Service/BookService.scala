package com.madhu.bookStore.Service

import com.madhu.bookStore.Model.Book
import com.madhu.bookStore.Repository.BookRepository

object BookService {
  def getAllBooks(): Array[Book] = BookRepository.getAllBooks.values.toArray

  def searchByISBN(isbn: Long): Array[Book] = BookRepository.searchByISBN(isbn).toArray

  def search(name: String, value: String): Array[Book] = BookRepository.search(name, value).toArray

  def insertBook(b: Book): Book = BookRepository.insertBook(b)

  def search(value:String) : Array[Book] ={BookRepository.search(value).toArray }
}
