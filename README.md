# Filters
This is a project showing Spring Boot used in combination with an SQL database and a modern front-end web framework to create a demo application and solve numerous design problems on the way.

Functionally, the application allows users to define filters with various associated criteria, save them in a persistent way and view all created filters in tabular format.

The project's front- and back-end are stored together here as a monorepo to present them in a single location. This work is under MIT license.

# Front-end

The front-end is realized as a single-page Vue.js application with styling provided by Vuetify. Even though Vue 3 is already the default option at the time of writing this, Vuetify still does not support it, so version 2 is used instead. The language of choice is JavaScript and communication with the back-end is done via JSON messages over a REST API using the Axios library. Much of the application behavior can be changed in a configuration file referenced at runtime without rebuilding the app. Unit and component tests are implemented with Jest.

## Functionality

**Filters table** is the main focus of the page and contains existing filters with their properties. This data is fetched from the back-end when the web page is loaded. While the filters are loading, a progress bar is displayed with an accompanying message. If the loading were to fail for any reason, an error message will be shown in the table. Likewise, users will be notified in case there is no data to show.

![Filters table](/images/filters_table.png "Filters table")

There are three columns in the table that are sortable by clicking on the corresponding header: name of the filter, criteria associated with it, and the match type. These properties are explained in the add filter section. By default only the first 10 filters are visible, but users can increase or decrease this value from the drop-down list below the table. Next to that are buttons for navigating through pages of the table.

**Add filter dialog** can be opened with the button in the top right corner. It will be disabled while the dialog is open to prevent multiple copies from being created. Users' browser window will be scrolled to the created dialog if it is out of view. The dialog itself can be in one of two modes: modal and non-modal, which can be set in runtime configuration. The screen capture below shows the non-modal variant, which does not require immediate user attention. Content of the modal version is identical, but stops users from interacting with the rest of the page until the pop-up dialog is closed.

![Add filter dialog](/images/add_filter_dialog.png "Add filter dialog")

From the dialog users can specify parameters for the filter being created. All entered data is validated and users asked to correct errors before sending any save requests to the back-end. Predefined options for drop-down lists and radio buttons along with default options are loaded from runtime config.

**Filter name** is a descriptive text for users to recognize the filter, but is not used for identification by the system. This field cannot be left empty and can be maximum of 64 characters in length.

**Criteria** area consists of number of rows, each representing a criterion. Users can add or remove these using the corresponding buttons. The deletion button will be disabled if there is only a single criterion as every filter must have at least one. A criterion has three mandatory properties that can be edited: type, comparison operator and comparison value.

* **Type** determines the field of a target item that this criterion applies to (and in turn the data type being compared). Included types are amount, title and date whose data types are integer, string and date respectively.
* **Comparison operator** is specified in the second drop-down list. It will be used to compare the target field (chosen from the first drop-down) to a value in the third input box. Note that available values here depend on the selection in the first box and are updated dynamically as users change their choices. For example available operators for amount fields include 'greater than' and 'less than' whereas date fields allow 'from' and 'until'.
* **Comparison value** is held in the third input box and its data type is also dependant on the first selection box. This is a numeric input for 'amount' criteria and a text field for 'title' criteria. A special case is the 'date' criteria which opens a pop-up date picker when clicking on the field.

**Match type** determines how criteria will be used to filter data. The default option of 'all' means that every criterion must be true for a given item to satisfy the constraints of the filter. Similarly 'any' requires at least one of the criteria to be true and 'none' requires all criteria to be false. This setting is required and is treated as an enum throughout the code.

**Saving** is possible after required properties of the filter have been set. Clicking the save button sends a request to the back-end to create a new filter. During this the form inputs will be disabled and a progress bar will appear. Users will be notified of the saving process outcome with a Vuetify snackbar styled notification. In case of a failure, the data in the form will remain unchanged and the saving process may be attempted again. If the saving is successful, the dialog will be closed and the table of existing filters on the main page will be refreshed with new data from the back-end. Alternatively the dialog can be closed without saving by clicking the close button at the bottom or in the top right corner.

## Architecture

**The start point** of the web app is in `src/main.js` which loads the JSON runtime config file over a web request and makes it available to Vue components via a mixin, before initializing the Vue instance. Code for mixins is in `src/mixins`.

Source code for pages is located in `src/views` and their sub-components such as the add filter dialog in `src/components`. Calls to the back-end are handled through a filter service in `src/services`.

**Unit (component) tests** are implemented with Jest in `tests/unit`. `setup.js` in the `tests` folder is responsible for providing a Vuetify instance and runtime configuration to the tests.

## Configuration

The application features a runtime configuration file at `src/public/runtimeConfig.json`, which sets the following parameters:  
* `"API_BASE_URL": "http://localhost:8080/api"` - the base address of the API to which routes are appended when making requests.
* `"MODAL_DIALOG": true` - whether to show the add filter dialog as a pop-up (modal) or part of the page (non-modal).
* `"DEFAULT_NAME": ""` - default value for filter name on the add filter dialog.
* `"DEFAULT_CRITERION_TYPE": "AMOUNT"` - default selection for criterion type on the add filter dialog.
* `"DEFAULT_MATCH_TYPE": "ALL"` - default selection for match type on the add filter dialog.
* `"CRITERION_TYPES": { ... }` - key-value pairs defining available criterion types and their properties.
    * `"AMOUNT": { ... }` - properties for specified criteria type. Defined are `AMOUNT`, `TITLE` and `DATE`.
        * `"NAME": "Amount"` - human-readable identifier.
        * `"OPERATORS": { ... }` - key-value pairs defining available comparison operators and their names, for example `"GREATER_THAN": "greater than"`.
        * `"VALUE_FORMAT": "NUMBER"` - data type being compared. `NUMBER`, `TEXT` and `DATE` are available.
        * `"DEFAULT_VALUE": 0` - default setting for the comparison value field.
* `"MATCH_TYPES": { ... }` - key-value pairs specifying possible filter match type values, e.g. `"ALL": "All"`.

Additionally, the development server is customized to run on port 3000 to avoid conflicts. This can be changed by editing the `"serve": "vue-cli-service serve --port 3000"` line in `package.json`.

## Installation

1. Ensure that Node.js including npm is installed. Version 16 was used for development.
2. Go to the `filters-frontend` directory.
3. Run the `npm install` command to setup the project.
4. Run automated unit tests with `npm run test:unit`.
5. Open `src/public/runtimeConfig.json` and set `API_BASE_URL` to your back-end endpoint.
6. Start the development version of the app by running `npm run serve`.
7. Visit the shown URL like `http://localhost:3000/` and verify the app.
8. Build the compiled version by issuing `npm run build`.
9. Go to the created `dist` directory containing the built files.
10. Make further changes to `runtimeConfig.json` in that folder if necessary.
11. Deploy the contents of the `dist` folder to a web server.

# Back-end

The back-end is based on Spring Boot and written in Java. Data is stored in a PostgreSQL database. Unit tests are handled with JUnit 5.

## Functionality

**The API** handles filters as JSON objects with the following structure:
```
{
    "id": 3,
    "name": "New Filter from UI",
    "matchType": "ALL",
    "criteria": [
        {
            "id": 4,
            "type": "AMOUNT",
            "operator": "GREATER_THAN",
            "value": 4
        },
        ...
    ]
}
```
where the `criteria` array can contain criteria objects of type. Appropriate conversions are applied on both client and server side based on the `type` field. This also allows for simple verification that the criterion list is not empty.

**Requests** are accepted on two routes:
* `GET /filters` responds with a JSON array of all filters in the database. The response is an empty array if there is none.
* `POST /filters` expects a single filter in JSON notation without the id field, as that will be generated when the filter is saved to the database. The server will respond with the full details of the created filter including the id. All fields are validated. If the client provides invalid data, 400 Bad Request code is returned instead with a list of unsuitable fields and related explanations.

## Architecture

**Structure** of the back-end is divided into numerous packages:
* `config` - classes responsible for configuring e.g. the model mapper or CORS.
* `controller` - handling the actual routes that clients use.
* `dto` - data transfer objects to decouple internal entities from the exposed API. Model mapper with custom converters is used to map between these.
* `model` - internal entities representing data as stored in the database.
* `repository` and `service` - storing and retrieving entities in various ways.

**Database** diagram generated using SchemaCrawler and Graphviz:

![Database diagram](/images/database_diagram.png "Database diagram")

(command used: `./schemacrawler --server=postgresql --host=localhost --port=5432 --user=postgres --password=postgrespw --database=postgres --command=schema --output-format=png --output-file=database_diagram.png --info-level=standard --portable-names`)

As evident, one filter can contain many criteria. Criterion is defined as a base entity from which various criteria with different data types inherit from. Chosen inheritance strategy is JOINED, meaning each sub-class has its own table that does not contain its parent's fields, but rather references to an entry in the parent (base) table. This guarantees an unique ID for each criterion while also allowing different data types for different criteria. Varchar fields `match_type` and `operator` are representing Java enums in a string format.

**Unit tests** can be found in `src/test`. Most notable are the tests that verify conversions between entities and DTOs, ensuring their properties get carried over properly in either direction.

## Configuration

The back-end can be run with multiple profiles and there are different config files for each. Profiles can be chosen by setting the `SPRING_PROFILES_ACTIVE` environment variable to `dev` or `prod`.

**Development profile** settings are located in `src/main/resources/application-dev.properties`:
* `spring.datasource.initialization-mode=always` - populate the database with initial data at every startup.
* `spring.datasource.data=classpath:data-dev.sql` - specify the SQL file containing said data.
* `spring.jpa.hibernate.ddl-auto=update` - create database schema from JPA entities and update automatically when changes are made.
* `spring.jpa.defer-datasource-initialization=true` - wait until Hibernate has created the database schema before loading initial data.

**Production profile** settings are located in `src/main/resources/application-prod.properties`:
* `spring.jpa.hibernate.ddl-auto=validate` - verify that the correct schema exists in the database at application startup, since in this profile it must be created by manually importing the `schema-prod.sql` file from the same folder. This is derived from the automatically created dev profile schema.

Both configuration files define database credentials and also allowed CORS origins as such:
* `filters.cors.allowed-origins=http://localhost:3000,http://192.168.1.2:3000`

## Installation

1. Make sure that a JDK is installed. OpenJDK 18 was used for development.
2. Ensure that the `JAVA_HOME` environment variable is set to the JDK directory.
3. Open the `filters-backend` folder.
4. Run `./mvnw clean install` to setup the project via the included Maven wrapper. This also runs unit tests and compiles the application.
5. Set `SPRING_PROFILES_ACTIVE` environment variable to `dev` to select the development version of the application. In PowerShell this can be done with `$env:SPRING_PROFILES_ACTIVE="dev"`.
6. Open `src/main/resources/application-dev.properties` and set desired PostgreSQL database credentials on lines 2-4.
7. Additionally set `filters.cors.allowed-origins` to the front-end URL where requests should be allowed from.
8. Run the application by executing `./mvnw spring-boot:run`. By default it is initialized on port 8080. At this step the previously specified database is automatically initialized with a schema and sample data.
9. Verify that the server is working by testing the front-end or using a REST client such as Insomnia. For example `GET http://localhost:8080/api/filters` (or another equivalent URL) should now be reachable.
10. Close the server and set `SPRING_PROFILES_ACTIVE` environment variable to `prod` to activate the production profile.
11. Configure production database credentials in `src/main/resources/application-prod.properties` on lines 2-4.
12. Set allowed CORS origins in `filters.cors.allowed-origins`.
13. Manually import `src/main/resources/schema-prod.sql` to the chosen production database.
14. Package the application using `./mvnw clean package spring-boot:repackage`.
15. Deploy the JAR file created in the `target` directory.
16. Optionally a copy of `application-prod.properties` can be put into the same directory or into a `config` subfolder to change parameters without rebuilding the JAR.
17. Run the server with `java -jar filters-backend-1.0.jar` or equivalent. As configured it will validate the imported schema on startup.
