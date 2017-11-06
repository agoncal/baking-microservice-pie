# Tribestream Access Gateway

TODO - To Complete.

## Configuration

###
* Set OAuth2 Profile
* Add Accounts
* Add Authentication / Authorization

### Routes
```
RewriteCond %{REQUEST_METHOD} POST [OR]
RewriteCond %{REQUEST_METHOD} PUT
RewriteRule "^/books(.*)$" "http://pi-grom-load-balancer:8081/book-api/api/books$1" [P]

RewriteCond %{REQUEST_METHOD} !POST
RewriteCond %{REQUEST_METHOD} !PUT
RewriteRule "^/books(.*)$" "http://pi-grom-load-balancer:8081/book-api/api/books$1" [P]
```
