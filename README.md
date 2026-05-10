## Setup & Installation

### Step 1 — Install
- PostgreSQL: https://www.postgresql.org/download/

### Step 2 — Clone the Repository
git clone https://github.com/17Tek/Exoplanet-Visualizer.git

### Step 3 — Set Up the Database
1. Open pgAdmin or psql
2. Create a new database called `exoplanets`
3. Open a terminal in the project folder and run:
psql -U postgres exoplanets < schema.sql
4. Import the dataset (make sure you are in the project folder):
psql -U postgres exoplanets -c "\copy exoplanet_dataset FROM 'nasa_exoplanet_intelligence.csv' CSV HEADER"

### Step 4 — Configure the App
1. Navigate to `src/main/resources/`
2. Rename `application.properties.example` to `application.properties`
3. Open it and fill in your PostgreSQL password:
spring.datasource.url=jdbc:postgresql://localhost:5432/exoplanets
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD_HERE

### Step 5 — Run the App
1. Open the project in IntelliJ IDEA or NetBeans
2. Wait for Maven to download dependencies
3. Run `Launcher.java`


https link for SQL code implementation:
https://github.com/17Tek/Exoplanet-Visualizer/blob/main/src/main/java/com/tek/dataproject/Repositories/ExoplanetRepository.java

//Java Docs refereences:
https://docs.oracle.com/javafx/2/charts/bar-chart.htm
