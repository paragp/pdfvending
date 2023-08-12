**Introducing PDFVending: Your One-Stop PDF Generation Solution**

Imagine a vending machine, but instead of snacks, it dispenses perfectly formatted PDFs. Welcome to PDFVending!

At the heart of PDFVending is the principle of simplicity: Choose a template, feed in the specific data, and watch as your PDF gets crafted instantly. Gone are the days of wrestling with tedious boilerplate coding for every new PDF generation requirement. With PDFVending, all the groundwork is already laid out for you.

Rapid Template Onboarding: With this intuitive framework, integrating new templates becomes a breeze. You can swiftly onboard, code minimally, and in no time at all, your template is live and ready on PDFVending, awaiting users to pour in data and get their desired PDFs.

Empower your business processes with speed, efficiency, and precision. Dive into the future of PDF generation with PDFVending.

**Adding a New PDF Template:**

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

Once these steps are completed, the new PDF can be generated via an API call. For API call details, refer to the provided Postman collection. When making the call, include the service name in the URL and provide a request body containing the necessary template parameters.
