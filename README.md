# Filters
This is a project showing Spring Boot used in combination with an SQL database and a modern front-end web framework to create a demo application and solve numerous design problems on the way.

Functionally, the application allows users to define filters with various associated criteria, save them in a persistent way and view all created filters in tabular format.

# Front-end
![Filters page](/images/filters_page.png "Filters page")

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
