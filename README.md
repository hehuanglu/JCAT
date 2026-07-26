
# LICO Tool Installation Guide

An concolic-testing tool for Java projects.


## Introduction

This README provides instructions for installing and running the LICO tool.

## Service Port Configuration

The system is composed of multiple Spring Boot–based microservices, each running on a dedicated network port to support service isolation and inter-service communication.

The default port configuration is defined as follows:

- `unitTesting`: 8006
- `upload-project-service`: 8020


## System Requirements

Before running the tool, ensure that the following software components are installed and properly configured:

- **Java Development Kit (JDK)** (version 11)
- **Apache Maven**
- **Z3 SMT Solver with Java bindings**
- **Postman** (for invoking and testing RESTful APIs)

⚠️ All required tools must be correctly configured in the system environment
(e.g., JAVA_HOME, MAVEN_HOME, and native library paths for Z3).


## Step 1: Clone the Repository

Clone the repository containing the tool source code:

    git clone <repository-url>

Navigate to the project root directory:

    cd <project-directory>


## Step 2: Install and Configure Required Dependencies

### CIA Tool
Replace the `mrmathami` folder in `Users/admin/.m2/repository` with the version
downloaded from https://drive.google.com/drive/u/0/folders/1c_FdlggwXR_xfr1hdL4L-zvnwr5ND5hA[link].

### Java Development Kit (JDK)

Install a supported JDK version and verify the installation:

    java -version

### Apache Maven

Install Apache Maven and verify the installation:

    mvn -version


### Z3 SMT Solver (Java Bindings)

1. Install the Z3 SMT Solver with Java bindings.
2. Ensure that the Z3 native library is accessible at runtime
3. Verify the installation:

    z3 --version


### Postman

Install Postman to send HTTP requests and interact with the tool’s RESTful APIs.


## Step 3: Build the Maven Modules

Each module listed below is a separate Maven project and contains its own `pom.xml`.

Open a terminal in **each** of the following directories and run:

```bash
mvn clean install
```

Build the modules in the following order:

1. `cfg4`
2. `mrmathami.cia.java.core`
3. `mrmathami.cia.java.jdt`
4. `mrmathami.utils`
5. `unitTesting`
6. `upload-project-service`

Running `mvn clean install` installs each module into your local Maven repository (`~/.m2/repository`), making it available as a dependency for the other modules.


## Step 4: Run Spring Boot Services

Navigate to each of the following service modules:

    - `unitTesting`
    - `upload-project-service`

For each module, start the Spring Boot application using one of the supported methods.

### Using Maven:
    mvn spring-boot:run

### Using an IDE:
Alternatively, launch the application by running the main class annotated with `@SpringBootApplication` from an IDE (e.g., IntelliJ IDEA).

⚠️ Ensure that all listed services are started successfully and are properly registered and connected before proceeding to the next step.


## Step 6: Execute the Tool Using the Experimental Datasets

The repository already includes several experimental Java projects under the
`experienceData` directory:

```
experienceData/
├── Array.GeeksForGeeks
├── Array.LeetCode
├── Array.TheAlgorithms
├── String.GeeksForGeeks
├── String.LeetCode
└── String.TheAlgorithms
```

To analyse one of these datasets, use an API client such as Postman and invoke
the following RESTful APIs in sequence.

### 1. Load and preprocess the selected dataset

```
http://localhost:8020/api/upload-project-service/process?parser=&user=&project=
```

Set the `project` parameter to the name of the dataset directory (e.g.,
`Array.LeetCode` or `String.TheAlgorithms`). This step prepares the selected
project for analysis.

### 2. Generate unit tests

```
http://localhost:8006/api/unit-testing-service/unit?nameProject=<project>&coverageType=statement&targetId=1
```

Replace `<project>` with the processed project name and specify:

- `coverageType`: `statement`, `branch`, or `mcdc`
- `targetId`: identifier of the target method to test

For example:

```
http://localhost:8006/api/unit-testing-service/unit?nameProject=Array.LeetCode.project&coverageType=statement&targetId=1
```
