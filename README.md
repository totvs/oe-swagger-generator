# oe-swagger-generator
OpenEdge API - [Swagger](https://swagger.io/) Document Generator

This repository contains a Swagger Document Generator Plug-in for using on OpenEdge ABL projects. 

# Automation for Documentation of APIs using Swagger
The Swagger parser read a external YAML file and get the procedures number and methods number for validate with a PROGRESS file.
### YAML file example
```
swagger: "2.0"
info:
  description: "This is a sample for YAML file"
  version: "1.0.0"
  title: "Sample API Totvs-REST"
  program: programName.p
basePath: "/tstUn/v1/"
program: programName.p
...   
paths:
  /tstUniApiDatasul:
    get:
      procedure: pi-get
    ...
```
The tag **basePath** contains where the PROGRESS file is. And the tag **program** have the name of PROGRESS file to read.
### PROGRESS file example
```
...
PROCEDURE pi-get:
    DEF INPUT  PARAM jsonInput  AS JsonObject NO-UNDO.
    DEF OUTPUT PARAM jsonOutput AS JsonObject NO-UNDO.
    
    ASSIGN jsonOutput = jsonInput.
END.
...
```
So the YAML file need have the same number of procedures and methods, for then comparing with PROGRESS file. The comparing with PROGRESS file is based with the numbers of procedures and methods find in the YAML file and find in the PROGRESS file. When the comparing is **True** the YAML file is convert to JSON file.