# SWFWMD Permitting Demo

A simple Spring Boot application that demonstrates a basic permit application form for the **Southwest Florida Water Management District (SWFWMD)**. The application collects a user’s name, address, county, and permit type, then stores the permit data in a MySQL database. It also provides a placeholder method for calling the USPS web API to validate the address.

---

## Features

- **Spring Boot Application**  
  Self-contained, runs on an embedded Tomcat server.

- **Form Input**  
  Collects name, address, county, and a single permit type (Water Use Permit or Environmental Resource Permit).

- **Italics & Blue Text for "SWFWMD"**  
  Per the requirement, all "SWFWMD" references are displayed italicized and in blue.

- **USPS Address Validation (Placeholder)**  
  Demonstrates how to build and parse a USPS Web API request/response.

- **Database Integration**  
  Uses Spring Data JPA to store permits and addresses in MySQL.

- **Minimal HTML/CSS**  
  Simple form rendering and minimal styling out-of-the-box.

---

## Prerequisites

1. **Java 17+**  
   - Verify with `java -version`.

2. **Maven**  
   - Verify with `mvn -v`.

3. **MySQL**  
   - Create a schema/database (e.g. `swfwmd_db`) and note your credentials.

4. **(Optional) USPS Web Tools User ID**  
   - For real USPS API calls, sign up at [USPS Web Tools](https://www.usps.com/business/web-tools-apis/welcome.htm).

