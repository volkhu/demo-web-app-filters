# Filters
This is a project showing Spring Boot used in combination with an SQL database and a modern front-end web framework to create a demo application and solve numerous design problems on the way.

Functionally, the application allows users to define filters with various associated criteria, save them in a persistent way and view all created filters in tabular format.

# Front-end
The front-end is realized as a single-page Vue.js application with styling provided by Vuetify. Even though Vue 3 is already the default option at the time of writing this, Vuetify still does not support it, so version 2 is used instead. The language of choice is JavaScript and communication with the back-end is done via JSON messages over a REST API using the Axios library. Much of the application behavior can be changed in a configuration file referenced at runtime without rebuilding the app.
## Functionality
Screenshot of the web page that users can interact with:

![Filters page](/images/filters_page.png "Filters page")

**Filters table** is the main focus of the page and contains existing filters with their properties. This data is fetched from the back-end when the web page is loaded. While the filters are loading, a progress bar is displayed with an accompanying message. If the loading were to fail for any reason, an error message will be shown in the table. Likewise, users will be notified in case there is no data to show.

There are three columns in the table that are sortable by clicking on the corresponding header: name of the filter, criteria associated with it, and the match type. These properties are explained in the add filter section. By default only the first 10 filters are visible, but users can increase or decrease this value from the drop-down list below the table. Next to that are buttons for navigating through pages of the table.

**Add filter dialog** can be opened with the button in the top right corner. It will be disabled while the dialog is open to prevent multiple copies from being created. Users' browser window will be scrolled to the created dialog if it is out of view. The dialog itself can be in one of two modes: modal and non-modal, which can be set in runtime configuration. The screen capture above shows the non-modal variant, which does not require immediate user attention. Content of the modal version is identical, but stops users from interacting with the rest of the page until the pop-up dialog is closed.

From the dialog users can specify parameters for the filter being created. All entered data is validated and users asked to correct errors before sending any save requests to the back-end. Predefined options for drop-down lists and radio buttons along with default options are loaded from runtime config.

**Filter name** is a descriptive text for users to recognize the filter, but is not used for identification by the system. This field cannot be left empty and can be maximum of 64 characters in length.

**Criteria** area consists of number of rows, each representing a criterion. Users can add or remove these using the corresponding buttons. The deletion button will be disabled if there is only a single criterion as every filter must have at least one. A criterion has three mandatory properties that can be edited: type, comparison operator and comparison value.

* **Type** determines the field of a target item that this criterion applies to (and in turn the data type being compared). Included types are amount, title and date whose data types are integer, string and date respectively.
* **Comparison operator** is specified in the second drop-down list. It will be used to compare the target field (chosen from the first drop-down) to a value in the third input box. Note that available values here depend on the selection in the first box and are updated dynamically as users change their choices. For example available operators for amount fields include 'greater than' and 'less than' whereas date fields allow 'from' and 'until'.
* **Comparison value** is held in the third input box and its data type is also dependant on the first selection box. This is a numeric input for 'amount' criteria and a text field for 'title' criteria. A special case is the 'date' criteria which opens a pop-up date picker when clicking on the field.

**Match type** determines how criteria will be used to filter data. The default option of 'all' means that every criterion must be true for a given item to satisfy the constraints of the filter. Similarly 'any' requires at least one of the criterions to be true and 'none' requires all criteria to be false. This setting is required and is treated as an enum throughout the code.

**Saving** is possible after required properties of the filter have been set. Clicking the save button sends a request to the back-end to create a new filter. During this the form inputs will be disabled and a progress bar will appear. Users will be notified of the saving process outcome with a Vuetify snackbar styled notification. In case of a failure, the data in the form will remain unchanged and the saving process may be attempted again. If the saving is successful, the dialog will be closed and the table of existing filters on the main page will be refreshed with new data from the back-end. Alternatively the dialog can be closed without saving by clicking the close button at the bottom or in the top right corner.

## Architecture

## Configuration

## Installation

# Back-end

## Functionality

## Architecture

Database diagram generated using SchemaCrawler and Graphviz:

![Database diagram](/images/database_diagram.png "Database diagram")

(command used: `./schemacrawler --server=postgresql --host=localhost --port=5432 --user=postgres --password=postgrespw --database=postgres --command=schema --output-format=png --output-file=database_diagram.png --info-level=standard --portable-names`)

## Configuration

## Installation
