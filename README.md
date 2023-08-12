Vending machine to generate pdf
Process captured in logs
2023-08-12 05:22:58 [traceId=c9b7a756-4b2e-4dc5-ba50-8ba21abff74e] INFO  PdfController: - generatePDF API Called
2023-08-12 05:22:58 [traceId=c9b7a756-4b2e-4dc5-ba50-8ba21abff74e] INFO  PDFService: - PDFService is called to generate PDF
2023-08-12 05:22:58 [traceId=c9b7a756-4b2e-4dc5-ba50-8ba21abff74e] INFO  PDFService: - Get the relevant generator object based on the type passed as a request parameter in the controller
2023-08-12 05:22:58 [traceId=c9b7a756-4b2e-4dc5-ba50-8ba21abff74e] INFO  PDFService: - class com.pdfvending.services.impl.pdfservices.SanctionLetterGenerator is selected for the type SanctionLetter
2023-08-12 05:22:58 [traceId=c9b7a756-4b2e-4dc5-ba50-8ba21abff74e] INFO  SanctionLetterGenerator: - Validating the Sanction Letter Data
2023-08-12 05:22:58 [traceId=c9b7a756-4b2e-4dc5-ba50-8ba21abff74e] INFO  SanctionLetterGenerator: - Generating the Sanction Letter PDF
2023-08-12 05:22:58 [traceId=c9b7a756-4b2e-4dc5-ba50-8ba21abff74e] INFO  FlyingSaucerPdfConverter: - PDF Generation process started at 12-08-2023-05-22-58-146
2023-08-12 05:22:58 [traceId=c9b7a756-4b2e-4dc5-ba50-8ba21abff74e] INFO  FlyingSaucerPdfConverter: - PDF Generation process completed at 12-08-2023-05-22-58-150
2023-08-12 05:22:58 [traceId=c9b7a756-4b2e-4dc5-ba50-8ba21abff74e] INFO  PdfController: - Sending PDF as a response

