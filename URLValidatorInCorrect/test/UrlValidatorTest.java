

import junit.framework.TestCase;

//You can use this as a skeleton for your 3 different test approach
//It is an optional to use this file, you can generate your own test file(s) to test the target function!
// Again, it is up to you to use this file or not!





public class UrlValidatorTest extends TestCase {


   public UrlValidatorTest(String testName) {
      super(testName);
   }

   
   
   public void testManualTest_1()
   {
	   // This test shows the regex bug, basically anythin that is a "nested" link is falsely identified as invalid
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   assertTrue(urlVal.isValid("http://www.google.com"));
	   assertTrue(urlVal.isValid("http://www.google.com/thing/"));
	   assertTrue(urlVal.isValid("http://www.google.com/thing/another"));
	   
   }
   
   public void testManualTest_2()
   {   //This test shows the toLower _ toUpper bug
	   // should be able to define custom schemes. in this case we allow only "http" and "bad" 
	   // this implies that "https" should not be allowed
	   System.out.println("manual test");
	   String[] schemes = {"http","bad"};
	   UrlValidator urlVal = new UrlValidator(schemes, 0);
	   assertTrue(urlVal.isValid("http://www.google.com"));
	   assertTrue(urlVal.isValid("bad://www.google.com"));
	   assertTrue(!urlVal.isValid("https://www.google.com"));
	   
   }
   
   
   public void testYourFirstPartition()
   {
	 //You can use this function to implement your First Partition testing	   

   }
   
   public void testYourSecondPartition(){
		 //You can use this function to implement your Second Partition testing	   

   }
   //You need to create more test cases for your Partitions if you need to 
   
   public void testIsValid()
   {
	   //You can use this function for programming based testing

   }
   


}
