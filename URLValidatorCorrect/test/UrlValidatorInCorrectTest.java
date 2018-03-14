

import junit.framework.TestCase;

//You can use this as a skeleton for your 3 different test approach
//It is an optional to use this file, you can generate your own test file(s) to test the target function!
// Again, it is up to you to use this file or not!





public class UrlValidatorInCorrectTest extends TestCase {


   public UrlValidatorInCorrectTest(String testName) {
      super(testName);
   }
   
   /*this is for partition test usage*/
   String[] validAuthority = new String[]{"www.google.com","go.au","255.255.255.255","255.com"};
   String[] invalidAuthority = new String[]{"","1.2.3.4.5","go.a",".aaa"};
   String[] validScheme = new String[]{"http://","h3t://","ftp://",""};
   String[] invalidScheme = new String[]{"://","http:"};
   String[] validPort= new String[]{":80",":65535",""};
   String[] invalidPort = new String[]{":-1",":65636",":65a"};
   String[] validPath= new String[]{"/test1","/$23","/test1/file"};
   String[] invalidPath=new String[]{"/../","/..","/test1//file1"};
   String[] validQuery=new String[]{"","?action=view","?q=dmv"};

   private int printExpected(String url, boolean result_expected, boolean result_observed) {
	   //allow all test cases to run without hitting the assertion errors that stop test exectuion
	   // print out the URL and the PASS FAIL
//	   System.out.println(result_observed);
	   if (result_expected == result_observed) {
		   System.out.println(url + ": PASSED");
		   return 0;
	   }
	   else {
		   System.out.println(url + ": FAILED");
		   return 1;
	   }
   }
   
   public void testManualTest_0()
   {
      System.out.println("Manual test 0");
      UrlValidator urlVal = new UrlValidator(null,null, UrlValidator.ALLOW_ALL_SCHEMES);
      //A valid URL is made of <scheme>://<authority><port><path>?<query>
      int test_fails = 0;
      /*test valid scheme, empty authority, valid path, expect to be false */
      /*REPORT a bug finding here: http scheme allows empty authority */
      test_fails += printExpected("http:///path",false,urlVal.isValid("http:///path"));
      test_fails += printExpected("http://www.a-.gov",false,urlVal.isValid("http://www.a-.gov"));
      /*test valid scheme,authority,port, expect to be true */
      test_fails += printExpected("http://www.oregon.gov",true,urlVal.isValid("http://www.oregon.gov"));
      /*test valid scheme,authority,path,port, expect to be true */
      test_fails += printExpected("http://www.oregon.gov/ODOT",true,urlVal.isValid("http://www.oregon.gov/ODOT"));
      
      /*test valid scheme,authority,query,port, expect to be true */
      /*REPORT a bug finding here: as long as the path has two slashes, there will be errors */
      test_fails += printExpected("http://www.oregon.gov/Pages/search-results.aspx?q=dmv",true,urlVal.isValid("http://www.oregon.gov/Pages/search-results.aspx?q=dmv"));
      
      /*test valid scheme,authority,query,port, expect to be true */
      test_fails += printExpected("http://www.oregon.gov/search-results.aspx?q=dmv",true,urlVal.isValid("http://www.oregon.gov/search-results.aspx?q=dmv"));
      
      /*test incomplete but valid scheme, valid authority, port, expect to be true*/
      test_fails += printExpected("www.oregon.gov",false,urlVal.isValid("www.oregon.gov"));
      /*test invalid authority, valid scheme, port, expect to be false*/
      //REPORT a bug finding here: invalid authority will fail 
      test_fails += printExpected("http://www.a-.gov",false,urlVal.isValid("http://www.a-.gov"));
      
      /*test invalid scheme, valid authority, port, expect to be false*/
      /*REPORT a bug finding here: file:// https:// ftp:// scheme is not allowed, it will crush the program */
      //System.out.println(urlVal.isValid("file://www.oregon.gov"));
      test_fails += printExpected(":://www.oregon.gov",false,urlVal.isValid(":://www.oregon.gov"));
      //REPORT a bug finding here: empty scheme with incomplete authority will fail
      test_fails += printExpected("oregon.gov",false,urlVal.isValid("oregon.gov"));
      test_fails += printExpected("http://home",false,urlVal.isValid("http://home"));
      //System.out.println(urlVal.isValid("https://www.oregon.gov"));
      System.out.println("Manual test 0 completed..\n");
      assertTrue(test_fails == 0);
      
      
   }
   
   public void testManualTest_1()
   {
	   // This test shows the regex bug, basically anythin that is a "nested" link is falsely identified as invalid
	   System.out.println("manual test 1");
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   int test_fails = 0;
	   test_fails += printExpected("http://www.google.com",true,urlVal.isValid("http://www.google.com"));
	   test_fails += printExpected("http://www.google.com/thing/",true,urlVal.isValid("http://www.google.com/thing/"));
	   test_fails += printExpected("http://www.google.com/thing/another",true,urlVal.isValid("http://www.google.com/thing/another"));
	   assertTrue(test_fails == 0);
//	   assertTrue(urlVal.isValid("http://www.google.com"));
//	   assertTrue(urlVal.isValid("http://www.google.com/thing/"));
//	   assertTrue(urlVal.isValid("http://www.google.com/thing/another"));
	   
	   
   }
   
   public void testManualTest_2()
   {   //This test shows the toLower _ toUpper bug
	   // should be able to define custom schemes. in this case we allow only "http" and "bad" 
	   // this implies that "https" should not be allowed
	   System.out.println("manual test 2");
	   String[] schemes = {"http","bad"};
	   UrlValidator urlVal = new UrlValidator(schemes, 0);
	   int test_fails = 0;
	   test_fails += printExpected("http://www.google.com",true,urlVal.isValid("http://www.google.com"));
	   test_fails += printExpected("bad://www.google.com",true,urlVal.isValid("bad://www.google.com"));
	   test_fails += printExpected("https://www.google.com",false,urlVal.isValid("https://www.google.com"));
	   assertTrue(test_fails == 0);
	   
//	   assertTrue(urlVal.isValid("http://www.google.com"));
//	   assertTrue(urlVal.isValid("bad://www.google.com"));
//	   assertTrue(!urlVal.isValid("https://www.google.com"));
	   
   }
   
  public void testmanualTest_3()
  {   
	  System.out.println("manual test 3");
	  UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	  int test_fails = 0;
	  test_fails += printExpected("file:///C:/Users/John/Desktop/donoghue_transcript_OSU.html",true,urlVal.isValid("file:///C:/Users/John/Desktop/donoghue_transcript_OSU.html"));
	  
	  assertTrue(test_fails == 0);
	  
//	  assertTrue(urlVal.isValid("file:///C:/Users/John/Desktop/donoghue_transcript_OSU.html"));
	    
  }
   
   
  public void testYourFirstPartition()
  {
     System.out.println("First Partition test starting here...\n");
     UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
     String url;
     int test_fails = 0;
     //test a valid authority + path/port/query
     for(int itr=0;itr<validAuthority.length;itr++){
        for(int base=0;base<validPath.length;base++){
           url="http://"+validAuthority[itr]+validPath[base];
           test_fails += printExpected(url,true,urlVal.isValid(url)); 
        } 
     }
     System.out.println();
     for(int itr=0;itr<validAuthority.length;itr++){
        for(int base=0;base<validPort.length;base++){
           url="http://"+validAuthority[itr]+validPort[base];
           test_fails += printExpected(url,true,urlVal.isValid(url)); 
        } 
     }
     System.out.println();
     for(int itr=0;itr<validAuthority.length;itr++){
        for(int base=0;base<validQuery.length;base++){
           url="http://"+validAuthority[itr]+validQuery[base];
           test_fails += printExpected(url,true,urlVal.isValid(url)); 
        } 
     }
     System.out.println();
     //test a invalid authority + path/port/query
     for(int itr=0;itr<invalidAuthority.length;itr++){
        for(int base=0;base<validPath.length;base++){
           url="http://"+invalidAuthority[itr]+validPath[base];
           test_fails += printExpected(url,false,urlVal.isValid(url)); 
        } 
     }
     System.out.println();
     for(int itr=0;itr<invalidAuthority.length;itr++){
        for(int base=0;base<validPort.length;base++){
           url="http://"+invalidAuthority[itr]+validPort[base];
           test_fails += printExpected(url,false,urlVal.isValid(url)); 
        } 
     }
     System.out.println();
     for(int itr=0;itr<invalidAuthority.length;itr++){
        for(int base=0;base<validQuery.length;base++){
           url="http://"+invalidAuthority[itr]+validQuery[base];
           test_fails += printExpected(url,false,urlVal.isValid(url)); 
        } 
     }
     assertTrue(test_fails == 0); 
  }
  
  public void testYourSecondPartition(){
     System.out.println("Second Partition test starting here...\n");
     UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES+UrlValidator.ALLOW_2_SLASHES);
     String url;
//     Boolean result;
     int test_fails = 0;
     //test an invalid scheme + authority + Null/path/port/query
     for(int itr=0;itr<invalidScheme.length;itr++){
        for(int base=0;base<validAuthority.length;base++){
           url=(invalidScheme[itr]+validAuthority[base]).toString();
           test_fails += printExpected(url,false,urlVal.isValid(url)); 
        }
        
     }
     System.out.println();
     for(int itr=0;itr<invalidScheme.length;itr++){
        for(int base=0;base<validPort.length;base++){
           url=(invalidScheme[itr]+"www.abc.com"+validPort[base]).toString();
           test_fails += printExpected(url,false,urlVal.isValid(url)); 
        }
        
     }
     System.out.println();
     
     for(int itr=0;itr<invalidScheme.length;itr++){
        for(int base=0;base<validPath.length;base++){
           url=(invalidScheme[itr]+"www.abc.com"+validPath[base]).toString();
           test_fails += printExpected(url,false,urlVal.isValid(url)); 
        }
     }
     System.out.println();
     
     //test a valid scheme + authority + NULL/path/port/query
     /*BUG report: any schema other than http will run into crush as a result of RegexValidator authorityValidator failure*/
     for(int itr=0;itr<validScheme.length;itr++){
        for(int base=0;base<validAuthority.length;base++){
           url=(validScheme[itr]+validAuthority[base]).toString();
           test_fails += printExpected(url,true,urlVal.isValid(url)); 
        }
        //System.out.printf("Due to RegexValidator authorityValidator failure, scheme other than http will crush the program.\n ");
     }
     System.out.println();
     
     for(int itr=0;itr<validScheme.length;itr++){
        for(int base=0;base<validPort.length;base++){
           url=(validScheme[itr]+"www.abc.com"+validPort[base]).toString();
           test_fails += printExpected(url,true,urlVal.isValid(url)); 
        }
        //System.out.printf("Due to RegexValidator authorityValidator called by isValidAuthority() function, scheme other than http will crush the program.\n ");
     }
     System.out.println();
     
     for(int itr=0;itr<validScheme.length;itr++){
        for(int base=0;base<validPath.length;base++){
           url=(validScheme[itr]+"www.abc.com"+validPath[base]).toString();
           test_fails += printExpected(url,true,urlVal.isValid(url)); 
        }
        //System.out.printf("Due to RegexValidator authorityValidator called by isValidAuthority() function, scheme other than http will crush the program.\n ");
     }
     System.out.println();
     assertTrue(test_fails == 0); 
     System.out.println("Second Partition test complted...\n");

  }
   
   public void testIsValid()
   {
	   //You can use this function for programming based testing

   }
   


}
