# Java Spring E-commerce project

Trying to do a simple e-commerce java application. For now the application has the following fetures:
```
  Light UI with thymeleaf and JQuery + Bootstrap
  Working with relational database
  L18n
  Rest api for all e-commerce stuff (checkout  is not emplemented yet)
  Ability to Log in with git hub account. (Git hub integration)
  Caching using redis database
  Sending email with feedback  
```

```
used stack:
  Spring (Boot, Data, Security)
  Swagger
  Redis cache
  oauth2
  MySQL
  hateoas
  thymeleaf
  JQuery, Bootstrap
  ```

API:
```
/product
  GET / - List of products
  POST / - Add product - required : String name , String groupId, String userId
  GET /{id} - View product
  POST /{id} - Update product
  GET /{id}/images - View product images
  GET /image/{id}- View image
  POST /{id}/uploadimage - Upload product image

/group
  GET / - List of groups
  POST / - Add group
  GET /{id} - View group
  POST /{id} - Update group

/order
  GET / - List of orders
  POST / - Add order
  GET /{id} - View order
  POST /{id} - Update order

/cart
  POST / - Create cart
  GET /{id} - Get items for card with ID = {id}
  POST /{id} - Add CartItem to cart with ID {id}
  DELETE /{id}/{product_id} - Remove product with ID {product_id} from cart with ID {id}
  POST /{id}/quantity - Updates cart item, i.e. set product quantity
  POST /{id}/order - Create order from cart
  ```
  
  First of all you have to go to ``` appliction.prperties ``` and change all personal properties to yours
  
  To run application just use: 
  ```
  mvn spring-boot:run
  ```
  
   Spring Swagger: 
   ```
   http://localhost:8080/swagger-ui.html
   ```
