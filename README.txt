##############################################
############ TAJAWAL ASSIGNMENT ##############
##############################################

## Prerequisites

* Git (https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
* Java [Jre / JDK]
* Maven

## Tests

@@@@ Post Request [Total Cases = 5] @@@
1. MyHttpClient [Without Dependencies, built using HttpURLConnection]
    a. testPostSuccess: A positive Test with DYNAMIC body to verify Post request with essential assertions
    b. testPostEmptyBody: A negative Test with EMPTY body & essential assertions
    c. testPostInvalidBody: A negative Test with INVALID body & essential assertions

2. RestAssured [Cases developed with RestAssured framework]
    a. rstAsurdSuccess: A positive Test with DYNAMIC body to verify Post request with essential assertions
    b. rstAsurdNoBody: A negative Test with EMPTY body & essential assertions


@@@ GET Request [Total Cases = 6] @@@
1. MyHttpClient [Without Dependencies, built using HttpURLConnection]
    a.testValidParam: A positive Test with valid query parameters
    b.testNoParam: A negative Test with no query parameters & essential assertions
    c.testInvalidParam: A negative Test with invalid query parameters & essential assertions

2. RestAssured [Cases developed with RestAssured framework]
    a.rstAsurdValidParam: A positive Test with valid query parameters
    b.rstAsurdNoParam: A negative Test with no query parameters & essential assertions
    c.rstAsurdInValidParam: A negative Test with invalid query parameters & essential assertions

# Log4J