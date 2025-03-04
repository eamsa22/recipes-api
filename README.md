
### **Project Structure:**

- The project is structured into **three main packages**, each dedicated to a specific functionality (**Cocktail Web Service, Recipe Web Service, and Menu Web Service**), along with additional packages such as:
  - **Model:** Contains Java classes representing the entities returned in responses.
  - **Commons:** Includes shared Java classes used across different components.

We have implemented a **multi-layered architecture**, incorporating several **Design Patterns**:
- **Front Controller DP**, which utilizes a Dispatcher.
- **Template Method DP** and **Strategy DP** for better code modularity and reusability.

Additionally, a **main class** has been developed to start the server. In the **test package**, we have implemented various unit and integration tests to ensure the proper functioning of the web services—all tests pass successfully.

---

### **Maven Commands:**

- To start the web service, use the following command:  
  **`mvn clean compile exec:java`**  
  This command performs three actions:
  1. **Cleans** the `target` directory.
  2. **Compiles** the project.
  3. **Starts** the server on port **8080**.

  Alternatively, you can execute them separately:
  * **`mvn clean`** → Cleans the target directory.
  * **`mvn compile`** → Compiles the project.
  * **`mvn exec:java`** → Launches the server.

- To generate the **Swagger** API documentation in JSON format, use:  
  **`mvn clean compile swagger:resolve`**  
  This command performs the following:
  * **clean** → Removes previously generated files.
  * **compile** → Compiles the source code.
  * **swagger:resolve** → Uses the Swagger plugin to generate the OpenAPI definition file.

  The generated file can be found in **`target/openAPI`** under the name **`openapi.json`**.

- To execute tests, run:  
  **`mvn test`**  
  ⚠️ **Note:** The server must already be running in another terminal window before executing the tests.

---

### **Manual Testing:**

- Using **Postman** or a **web browser** (except for POST requests):

  **In XML format:**
  * **GET** `http://localhost:8080/recipe/meal/{cuisineType}` → Retrieves a recipe based on the specified cuisine type.
  * **GET** `http://localhost:8080/recipe/drink` → Retrieves a random cocktail recipe (without the **alcoholic** filter).
  * **GET** `http://localhost:8080/recipe/drink?alcoholic=true` → Retrieves a random **alcoholic** cocktail recipe.
  * **GET** `http://localhost:8080/recipe/drink?alcoholic=false` → Retrieves a random **non-alcoholic** cocktail recipe.

  **In JSON format:**
  * **GET** `http://localhost:8080/v2/recipe/meal/{cuisineType}` → Retrieves a recipe based on the specified cuisine type.
  * **GET** `http://localhost:8080/v2/recipe/drink` → Retrieves a random cocktail recipe (without the **alcoholic** filter).
  * **GET** `http://localhost:8080/v2/recipe/drink?alcoholic=true` → Retrieves a random **alcoholic** cocktail recipe.
  * **GET** `http://localhost:8080/v2/recipe/drink?alcoholic=false` → Retrieves a random **non-alcoholic** cocktail recipe.
  * **POST** `http://localhost:8080/menu` → Retrieves a **full menu** based on criteria provided in the request body (JSON format). The **"cuisineType"** filter is required.

---

⚠️ **Reminder:** Before performing manual tests, ensure that the server is already running! 🚀

