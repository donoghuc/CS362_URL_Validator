

import junit.framework.TestCase;

//You can use this as a skeleton for your 3 different test approach
//It is an optional to use this file, you can generate your own test file(s) to test the target function!
// Again, it is up to you to use this file or not!



public class UrlValidatorTest extends TestCase {


   public UrlValidatorTest(String testName) {
      super(testName);
   }

   
   
   public void testManualTest()
   {
      System.out.println("Manual test starting here...");
      UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
      //A valid URL is made of <scheme>://<authority><port><path>?<query>

      /*test valid scheme,authority,port, expect to be true */
      System.out.println(urlVal.isValid("http://www.oregon.gov"));
      /*test valid scheme,authority,path,port, expect to be true */
      System.out.println(urlVal.isValid("http://www.oregon.gov/ODOT?q=co"));
      /*test valid scheme,authority,query,port, expect to be true */
      System.out.println(urlVal.isValid("http://www.oregon.gov/Pages/search-results.aspx?q=dmv"));
      /*test valid scheme,authority,query,port, expect to be true */
      System.out.println(urlVal.isValid("http://www.oregon.gov/search-results.aspx?q=dmv"));
      /*test incomplete but valid scheme, valid authority, port, expect to be true*/
      System.out.println(urlVal.isValid("www.oregon.gov"));
      /*test invalid authority, valid scheme, port, expect to be false*/
      System.out.println(urlVal.isValid("http://www.or-.gov"));
      /*test invalid scheme, valid authority, port, expect to be false*/
      /*REPORT a bug finding here: as long as the authority is invalid, the code will crush... */
      System.out.println(urlVal.isValid("gps://www.oregon.gov"));
      System.out.println(urlVal.isValid(":://www.oregon.gov"));
	   
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
