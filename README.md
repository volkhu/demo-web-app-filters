# demo-web-app-filters
Demo web application displaying a list of user defined filters saved in an SQL database.

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

# Technologies / tools used
Front-end: Visual Studio Code, Vue.js v2, Vuetify, axios
Back-end: IntelliJ Idea, Spring Boot, PostgreSQL
