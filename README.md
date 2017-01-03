##How To Run Test:
Clone the repository and execute following command

mvn clean surefire-report:report
It will start a
##Description:
This is a maven based project written in Java which uses testNg and Selenium. when you execute test cases (above mentioned command )it will test sorting of train prices by launching UI and via REST APIs both. 

This is a data-driven framework which has capability to execute your test cases parallel. So, when you run it, it will fork number of threads configured by property "data-provider-thread-count" in testNg suites. Currently its set as four. 

There are two test classes -
###TrainRestAPITests
    This will test desired functionality via REST APIs (/v5/searches and /v5/results) exposed by GoEuro.
###TrainSearchTests
   This will open goeuro.com in browser and test the required functionality.
   As it has already configured two browsers, so it will test all test cases provided in TestData.xml
###TestCases/TestData- 
        QA can add test cases at TestData.xml. You can also create your own xml file (MyData.xml) and annotate it on test case like below 
        @MethodArguments("dataFilePath=src/test/resources/MyData.xml")
        
        
##Test Plan
  I added a feature List which I will plan to test as a feature tester.
        
 

