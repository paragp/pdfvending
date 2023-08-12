Adding a New PDF Template:

1. HTML File Setup:
Place your HTML files in the directory: pdfvending\src\main\resources\templates.
For guidance, check the existing files in this directory.


2. Generator Classes:
Create new generator classes in the directory: pdfvending\src\main\java\com\pdfvending\services\impl\pdfservices.
For each HTML file, there should be a corresponding generator class.
Examples of existing generator classes include:
LoanStatementGenerator.java
SanctionLetterGenerator.java
WelcomeLetterGenerator.java
Ensure that each generator:
Has the correct service name.
Binds configuration parameters to the appropriate variables.

3. Configuration Setup:
Add the necessary configuration parameters to the application.properties file.
Existing parameters for reference:
sanctionletter.template.name=sanction-letter
welcomeletter.template.name=welcome-letter
loanstatement.template.name=loan-statement
(Note: These parameters correspond to the HTML file names. No need to include the file extension.)

Completion: Once these steps are completed, the new PDF can be generated via an API call. For API call details, refer to the provided Postman collection. When making the call, include the service name in the URL and provide a request body containing the necessary template parameters.