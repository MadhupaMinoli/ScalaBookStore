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

Client file should be run with two arguiments `<RequestType>` `<Message>`

There are only three Request Types.
* GET
* SEARCH
* ADD

#### GET `<< >>`

eg: GET

By giving empty message this will return all the books in JSON format

#### GET `<<isbn>>`

eg: GET 9781444907957

This will return a single book defined by the isbn, response should be in JSON format

#### SEARCH `<<query term>>`

eg: SEARCH  Enid Blyton

This will search the title and author fields by given `<<search term>>` and return list of books as JSON

#### ADD `<<book>>`

eg: ADD {`\`"isbn`\`":97814449507957,`\`"title`\`":`\`"Famous Five`\`",`\`"author`\`":`\`"Enid Blyton`\`"}

This will expect a single book is in JSON, and will create it in the backend. 


