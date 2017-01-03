##How To Run Test:
Clone the repository and execute following command

mvn clean surefire-report:report

##Description:
This is a maven based project written in Java which uses testNg and Selenium. when you execute test cases (above mentioned command )it will test sorting of train prices on search results page.
This is a data-driven framework which has capability to execute your test cases parallelly.
There are two test classes -
    ###TrainRestAPITests
        This will test desired functionality via REST APIs (/v5/searches and /v5/results) exposed by GoEuro.
    ###TrainSearchTests
        This will open goeuro.com in browser and test the required functionality.
    ###TestData.xml
        QA can add test cases here.

