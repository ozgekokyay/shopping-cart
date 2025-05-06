# Shopping Cart Application
The Shopping Cart Application is a checkout REST application with functions like add different types of items to a cart, adding digital items, value-added services, and applying promotions.
### Prerequisites

- Java JDK 17
- Spring Boot 
- Maven
- 
## 📌 Features

- Add, remove, and reset cart items
- Support for item types: `DefaultItem`, `DigitalItem`, `VasItem`
- Enforces business rules such as:
  - Max 10 unique items (excluding VAS)
  - Max 30 total items
  - Max total price: ₺500,000
  - DigitalItem-only carts
  - VASItem restrictions based on item category
- Displays cart details with promotion support
- JSON input/output via Postman

  
## Available Endpoints

The Shopping Cart Application provides the following endpoints for various operations:

- Add an item to the cart: [http://localhost:8080/cart/items/add](http://localhost:8080/cart/items/add)
- Reset the cart: [http://localhost:8080/cart/reset](http://localhost:8080/cart/reset)
- Add a value-added service item to the cart: [http://localhost:8080/cart/items/add/vas-item](http://localhost:8080/cart/items/add/vas-item)
- Display the contents of the cart: [http://localhost:8080/cart/display](http://localhost:8080/cart/display)
- Remove an item from the cart: [http://localhost:8080/cart/remove](http://localhost:8080/cart/remove)

Please use these URLs to interact with the application and perform various cart-related operations.

