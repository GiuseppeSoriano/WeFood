# WeFood

## Large-Scale And Multi-Structured Databases Project
##### Academic Year 2023/2024 - Group 13

Welcome to the repository for our Large-Scale And Multi-Structured Databases project. This repository encompasses a comprehensive collection of materials, scripts, and codebases developed as part of our coursework.

### Repository Structure Overview

In the following the structure of the repository:

-   **Dataset:** The Dataset directory is intricately designed to manage and process the data crucial for our project. It is meticulously structured into various components, each dedicated to a distinct phase of data handling:

    -    **DatasetStructure:** This subdirectory serves as the organizational backbone for our dataset. Within it, you will find:
            - **0Raw_Datasets:* Positioned at the beginning of the data processing pipeline, this folder is pivotal for analyzing the raw datasets. It's where the initial exploration of data occurs, involving the examination of its structure, identifying key attributes, and understanding the inherent patterns and anomalies. This analysis is foundational, as it informs the subsequent steps of data cleaning, transformation, and integration.
            - *Entity Folders:* Each entity that forms part of our databases has its dedicated folder within DatasetStructure. These folders contain processed and structured data, ready to be integrated into our databases. They represent the transformed state of the raw data, refined through our processing scripts to fit the specific requirements of our database schemas.
    
    -   **Script Notebook (`script.ipynb`):** Located in the main Dataset directory, this Jupyter notebook is instrumental for the final stages of data processing. It encompasses advanced data transformation and preparation scripts, ensuring that the data is optimally formatted and enriched for database integration.

    -   **Neo4j:** A specialized subdirectory focusing on the Neo4j graph database. Here, you'll find scripts tailored for constructing the graph database, including the creation of nodes, relationships, and the implementation of graph-specific data structures. These scripts are vital for populating the Neo4j database with data that has been processed and structured in the preceding steps.

-   **Documentation:** The Documentation directory contains a comprehensive set of documents explaining the project's design and architectural choices. It offers a detailed overview of the methodologies and strategies implemented to meet both functional and non-functional requirements. This section serves as a critical resource for understanding the project's structure, including database design, data processing, and system architecture. It acts as a guide through the project's development journey, from conception to final implementation.

-   **Implementation:** This is the primary directory for the project's practical components. It contains all the code related to the Server (written in Java using Spring Boot) and the Web Application (developed using Angular). This directory is crucial for understanding the actual implementation and functionality of our project.

-   **Slides:** The Slides directory is organized into two key subdirectories, providing a visual and narrative progression of our project:

    -   Original Idea: This subdirectory houses the PowerPoint slides that outline our initial concept for the project. It captures the early stage brainstorming, fundamental ideas, and the preliminary approach envisioned at the project's inception.

    -   Final Presentation: Here, you'll find the slides prepared for the final presentation of the project. This section reflects the evolved understanding, refined strategies, and the ultimate realization of our project goals. It serves as a culmination of our work, showcasing the journey from concept to completion.

    This structured approach in Slides not only offers a glimpse into the project's evolution but also provides an engaging way to understand the transition from original ideas to final implementations

-   Idea: Here PowerPoint slides prepared to present initial idea and the final discussion can be found.

-   collections_database: Here it can be found other python scripts used to process files cleaned in Data to produce the three files that are going to be used to fill databases: posts_database.json, ingredients_database.json and user_database_hashed.json.

-   vm: Here scripts to generate entities and relationships in neo4j can be found.

### Additional Resources
Data in DatasetStructure and vm can be downloaded from [Google Drive](https://drive.google.com/drive/folders/0AFJxQDWAEcpQUk9PVA) (access the Google Drive link using your official university-provided account). Please ensure to download these to fully understand the data processing carried out.


****

### Starting the Angular Web Application

#### Prerequisites

1.  **Node.js:** Ensure Node.js is installed on your system. You can download it from [Node.js website](https://nodejs.org/en).
2.  **npm (Node Package Manager):** Comes bundled with Node.js

#### Installation
1.  **Install Angular CLI:** Angular CLI is a command-line interface tool used to create and manage Angular applications. Install it globally using npm: 

``` Bash

npm install -g @angular/cli
```

2.  **Install Dependencies:** Navigate to the project directory and install the required dependencies: 

``` Bash

npm install
```

#### Running the Application
1.  **Start the Server:** Within the project directory, start the server using Angular CLI:

``` Bash

ng serve
``` 
2.  **Access the Application:** By default, the Angular application will run on port 4200. Access it via your web browser:
``` Bash

http://localhost:4200
```

##### Important: Running on a Different Port

If you need to run the Angular application on a different port, it's essential to update the server application accordingly. Specifically, modify the following classes to reflect the new web app address:

-   **`it.unipi.lsmsdb.wefood.SecurityConfiguration`**
-   **`it.unipi.lsmsdb.wefood.MyConfiguration`**

Replace **http://localhost:4200** with the new address you're using to run the web app.

#### Environment Specifications

Before starting the web application, please ensure your development environment meets the following specifications:

-   **Angular CLI:** 14.2.13
-   **Node.js:** 16.17.0
-   **npm (Node Package Manager):** 8.19.2

##### Angular Package Versions

-   **Angular:** 14.3.0
-   Additional key packages:
    -   **rxjs:** 7.5.7
    -   **typescript:** 4.7.4

It's recommended to align with these versions for compatibility and optimal performance.

### Configuring the Java Server and Web Application in IntelliJ IDEA

#### Prerequisites

-   **Java JDK 17:** Ensure JDK 17 is installed on your system.
-   **Maven:** Maven is used for build and dependency management.

#### IntelliJ IDEA Setup for Server

1.  **Open the Project:** Open IntelliJ IDEA and select 'Open' to open the server project from where you cloned it.
2.  **Delegate to Maven:** Go to Settings → Build, Execution, Deployment → Build Tools → Maven → Runner and delegate the build and run actions to Maven.

#### Running the Server in IntelliJ IDEA
1.  **Build the Project:** Right-click on the project in the Project Explorer and select 'Run Maven' → 'clean install' to build the project.
2.  **Run the Server:** Find the main application class with the public static void main method, right-click on it, and select 'Run' to start the server.

#### Changing Server Port
The default port for the Spring Boot server is 8080. To use a different port, update **server.port** in **application.properties**.

##### Important: Updating the Web Application

If you change the server port or address, you must manually update the web application to match the new server address. In your Angular webapp, go through all the services (found in `src/app/services/`) and update any instance of **localhost:8080** to the new address.