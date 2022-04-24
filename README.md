# demo-web-app-filters
Demo web application displaying a list of user defined filters saved in an SQL database.

# Filter table
![Filter table](/images/filter_table.png "Filter table")

# Add filter dialog
![Add filter dialog](/images/add_filter_dialog.png "Add filter dialog")

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
