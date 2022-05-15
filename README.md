# Filters
This is a project showing Spring Boot used in combination with an SQL database and a modern front-end web framework to create a demo application and solve numerous design problems on the way.

Functionally, the application allows users to define filters with various associated criteria, save them in a persistent way and view all created filters in tabular format.

# Front-end
The front-end is realized as a single-page Vue.js application with styling provided by Vuetify. Even though Vue 3 is already the default option at the time of writing this, Vuetify still does not support it, so version 2 is used instead. The language of choice is JavaScript and communication with the back-end is done via JSON messages over a REST API using the Axios library. To an extent the behavior of the application can be changed in a configuration file referenced at runtime without rebuilding the app.
## Functionality
Screenshot of the web page that users can interact with:

![Filters page](/images/filters_page.png "Filters page")

The main area of the page consists of a table showing existing filters and their properties. This data is fetched from the back-end when the web page is loaded. While the filters are loading, a progress bar is displayed with an accompanying message. If the loading were to fail for any reason, an error message will be shown in the table. Likewise, users will be notified in case there is no data to show.

There are three columns in the table that are sortable by clicking on the corresponding header: name of the filter, criteria associated with it, and the match type. These properties are explained in the add filter section. By default only the first 10 filters are visible, but users can increase or decrease this value from the drop-down list below the table. Next to that are buttons for navigating through pages of the table.

The button on the top right opens the add filter dialog. This button will be disabled while the dialog is open to prevent multiple copies from being created. Users' browser window will be scrolled to the created dialog if it is out of view. The dialog itself can be in one of two modes: modal and non-modal, which can be set in runtime configuration. The screen capture above shows the non-modal variant, which does not require immediate user attention. Content of the modal version is identical, but stops users from interacting with the rest of the page until the pop-up dialog is closed.

On the add filter dialog users can specify parameters for the filter being created. All entered data is validated and users asked to correct errors before sending any save requests to the back-end. Predefined options for drop-down lists and radio buttons along with default options are loaded from runtime config.

Filter name is a descriptive text for users to recognize the filter, but is not used for identification by the system. This field cannot be left empty and can be maximum of 64 characters in length.

Criteria area consists of an unlimited amount of rows, each representing a criterion. Users can add or remove these using the corresponding buttons. The deletion button will be disabled if there is only a single criterion as every filter must have at least one.

A criterion has three mandatory properties that can be edited: type, comparison operator and comparison value. Type determines the field of a target item that the criterion applies to (and in turn the data type being compared). Included types are amount, title and date whose data types are integer, string and date respectively. The second drop-down list specifies the operator that will be used to compare the target field (chosen from the first drop-down) to a comparison value in the third input box. Note that available values here depend on the selection in the first box and are updated dynamically as users change their choices. For example available operators for amount fields include 'greater than' and 'less than' whereas date fields allow 'from' and 'until'. The third input box holds the comparison value and its type is also dependant on the first selection box. This is a numeric input for 'amount' criteria and a text field for 'title' criteria. A special case is the 'date' criteria which opens a pop-up date picker when clicking on the field.

Match type determines how criteria will be used to filter data. The default option of 'all' means that every criterion must be true for a given item to satisfy the constraints of the filter. Similarly 'any' requires at least one of the criterions to be true and 'none' requires all criteria to be false. This setting is required and is treated as an enum throughout the code.

Once the filter properties have been set, users can click on the save button that sends a request to create a new filter to the back-end. During this the form inputs will be disabled and a progress bar will appear. Users will be notified of the saving process outcome with a Vuetify snackbar styled notification. In case of a failure, the data in the form will remain untouched and the saving process may be attempted again. If the saving is successful, the dialog will be closed and the table of existing filters on the main page will be refreshed with new data from the back-end. Alternatively the dialog can be exited without saving by clicking the close button at the bottom or in the top right corner.

# Back-end
Database diagram generated using SchemaCrawler and Graphviz:

![Database diagram](/images/database_diagram.png "Database diagram")

(command used: `./schemacrawler --server=postgresql --host=localhost --port=5432 --user=postgres --password=postgrespw --database=postgres --command=schema --output-format=png --output-file=database_diagram.png --info-level=standard --portable-names`)

# Features
* View a list of filters with their criteria
* Add and delete criteria for each filter
* Each filter must have a minimum of 1 criterion
* 3 different criteria types: amount, title, date (number, text, date)
* The default criteria type is "Amount"
* Every criteria type has different comparison operators
* Filter dialog operates in non-modal mode
* Database tables are created and test data inserted on startup
* Test data contains 2 filters
* Communication via REST API and JSON

# Possible improvements
* Verify data on the back end
* Add unit and integration tests
* Support modal dialog mode
* Fix dialog width, allow user to resize height and add a scrollbar

# Usage
Front-end: `npm install` and `npm run serve` in the front-end folder to start the dev server

Back-end: install dependencies via Maven and run Spring Boot application

# Technologies / tools used
Front-end: Visual Studio Code, Vue.js v2, Vuetify, axios

Back-end: IntelliJ Idea, Spring Boot, PostgreSQL
