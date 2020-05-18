# ScalaBookStore

simple Web application to manage a bookstore. Bookstore has 4 main functionality
1. Add a new book
2. List all books
3. Search book by title or author
4. Get a single book by ISBN

## WEB API

baseURL = localhost:8000

#### GET /books/book/`<<isbn>>`

This will return a single book defined by the isbn, response should be in JSON format

#### GET /books?q=`<<search team>>`

This will search the title and author fields by given `<<search term>>` and return list of books as JSON

#### GET /books/

Return all the books in JSON format

#### POST /books/book

This method will expect a single book, and will create it in the backend. (Posted payload is in JSON)

## RabbitMQ Messaging API

Client files should be run with an arguments `<host>`

There are three client object files for each operation
* GET
* SEARCH
* ADD

#### getClient File 
if message can be given as empty `<< >>`

this will return all the books in JSON format

#### getClient File 
 
if message can be given as  `<<isbn>>`

This will return a single book defined by the isbn, response should be in JSON format

#### searchClient File

if message can be given as `<<query term>>` 

This will search the title and author fields by given `<<search term>>` and return list of books as JSON

#### addClient File
if message can be given as `<<book>>`

This will expect a single book is in JSON, and will create it in the backend. 


