

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

//You can use this as a skeleton for your 3 different test approach
//It is an optional to use this file, you can generate your own test file(s) to test the target function!
// Again, it is up to you to use this file or not!



public class UrlValidatorTest extends TestCase {
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
   
   public UrlValidatorTest(String testName) {
      super(testName);
   } 
   
   public void testManualTest()
   {
      System.out.println("Manual test starting here...");
      UrlValidator urlVal = new UrlValidator(null,null, UrlValidator.ALLOW_ALL_SCHEMES);
      //A valid URL is made of <scheme>://<authority><port><path>?<query>
      
      /*test valid scheme, empty authority, valid path, expect to be false */
      /*REPORT a bug finding here: http scheme allows empty authority */
      System.out.println(urlVal.isValid("http:///path"));
      System.out.println(urlVal.isValid("http://www.a-.gov"));
      /*test valid scheme,authority,port, expect to be true */
      System.out.println(urlVal.isValid("http://www.oregon.gov"));
      /*test valid scheme,authority,path,port, expect to be true */
      System.out.println(urlVal.isValid("http://www.oregon.gov/ODOT"));
      
      /*test valid scheme,authority,query,port, expect to be true */
      /*REPORT a bug finding here: as long as the path has two slashes, there will be errors */
      System.out.println(urlVal.isValid("http://www.oregon.gov/Pages/search-results.aspx?q=dmv"));
      
      /*test valid scheme,authority,query,port, expect to be true */
      System.out.println(urlVal.isValid("http://www.oregon.gov/search-results.aspx?q=dmv"));
      
      /*test incomplete but valid scheme, valid authority, port, expect to be true*/
      System.out.println(urlVal.isValid("www.oregon.gov"));
      /*test invalid authority, valid scheme, port, expect to be false*/
      //REPORT a bug finding here: invalid authority will fail 
      System.out.println(urlVal.isValid("http://www.a-.gov"));
      
      /*test invalid scheme, valid authority, port, expect to be false*/
      /*REPORT a bug finding here: file:// https:// ftp:// scheme is not allowed, it will crush the program */
      //System.out.println(urlVal.isValid("file://www.oregon.gov"));
      System.out.println(urlVal.isValid(":://www.oregon.gov"));
      //REPORT a bug finding here: empty scheme with incomplete authority will fail
      System.out.println(urlVal.isValid("oregon.gov"));
      System.out.println(urlVal.isValid("http://home"));
      //System.out.println(urlVal.isValid("https://www.oregon.gov"));
      
      
      
      System.out.println("Manual test completed..\n");
   }
   
   
   public void testYourFirstPartition()
   {
      System.out.println("First Partition test starting here...\n");
      UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
      String url;
      Boolean urlBool;
      //test a valid authority + path/port/query
      for(int itr=0;itr<validAuthority.length;itr++){
         for(int base=0;base<validPath.length;base++){
            url="http://"+validAuthority[itr]+validPath[base];
            urlBool=urlVal.isValid(url);
            if(true!=urlBool)
               System.out.printf("Failure: valid auth http://%s%s\n",validAuthority[itr],validPath[base]);
            else
               System.out.printf("Pass: valid auth http://%s%s\n",validAuthority[itr],validPath[base]);
            
         } 
      }
      System.out.println();
      for(int itr=0;itr<validAuthority.length;itr++){
         for(int base=0;base<validPort.length;base++){
            url="http://"+validAuthority[itr]+validPort[base];
            urlBool=urlVal.isValid(url);
            if(true!=urlBool)
               System.out.printf("Failure: valid auth http://%s%s\n",validAuthority[itr],validPort[base]);
            else
               System.out.printf("Pass: valid auth http://%s%s\n",validAuthority[itr],validPort[base]);
            
         } 
      }
      System.out.println();
      for(int itr=0;itr<validAuthority.length;itr++){
         for(int base=0;base<validQuery.length;base++){
            url="http://"+validAuthority[itr]+validQuery[base];
            urlBool=urlVal.isValid(url);
            if(true!=urlBool)
               System.out.printf("Failure: valid auth http://%s%s\n",validAuthority[itr],validQuery[base]);
            else
               System.out.printf("Pass: valid auth http://%s%s\n",validAuthority[itr],validQuery[base]);
            
         } 
      }
      System.out.println();
      //test a invalid authority + path/port/query
      for(int itr=0;itr<invalidAuthority.length;itr++){
         for(int base=0;base<validPath.length;base++){
            url="http://"+invalidAuthority[itr]+validPath[base];
            urlBool=urlVal.isValid(url);
            if(false!=urlBool)
               System.out.printf("Failure: invalid auth http://%s%s\n",invalidAuthority[itr],validPath[base]);
            else
               System.out.printf("Pass: invalid auth http://%s%s\n",invalidAuthority[itr],validPath[base]);
            
         } 
      }
      System.out.println();
      for(int itr=0;itr<invalidAuthority.length;itr++){
         for(int base=0;base<validPort.length;base++){
            url="http://"+invalidAuthority[itr]+validPort[base];
            urlBool=urlVal.isValid(url);
            if(false!=urlBool)
               System.out.printf("Failure: invalid auth http://%s%s\n",invalidAuthority[itr],validPort[base]);
            else
               System.out.printf("Pass: invalid auth http://%s%s\n",invalidAuthority[itr],validPort[base]);
            
         } 
      }
      System.out.println();
      for(int itr=0;itr<invalidAuthority.length;itr++){
         for(int base=0;base<validQuery.length;base++){
            url="http://"+invalidAuthority[itr]+validQuery[base];
            urlBool=urlVal.isValid(url);
            if(false!=urlBool)
               System.out.printf("Failure: invalid auth http://%s%s\n",invalidAuthority[itr],validQuery[base]);
            else
               System.out.printf("Pass: invalid auth http://%s%s\n",invalidAuthority[itr],validQuery[base]);
            
         } 
      }
      System.out.println("First Partition test complted...\n"); 
   }
   
   public void testYourSecondPartition(){
      System.out.println("Second Partition test starting here...\n");
      UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES+UrlValidator.ALLOW_2_SLASHES);
      String url;
      Boolean result;
      //test an invalid scheme + authority + Null/path/port/query
      for(int itr=0;itr<invalidScheme.length;itr++){
         for(int base=0;base<validAuthority.length;base++){
            url=(invalidScheme[itr]+validAuthority[base]).toString();
            result=urlVal.isValid(url);
            if(false!=result)
               System.out.printf("Failure: invalid scheme %s\n",url);
            else
               System.out.printf("Pass: invalid scheme %s\n",url);  
         }
         
      }
      System.out.println();
      for(int itr=0;itr<invalidScheme.length;itr++){
         for(int base=0;base<validPort.length;base++){
            url=(invalidScheme[itr]+"www.abc.com"+validPort[base]).toString();
            result=urlVal.isValid(url);
            if(false!=result)
               System.out.printf("Failure: invalid scheme %s\n",url);
            else
               System.out.printf("Pass: invalid scheme %s\n",url);  
         }
         
      }
      System.out.println();
      
      for(int itr=0;itr<invalidScheme.length;itr++){
         for(int base=0;base<validPath.length;base++){
            url=(invalidScheme[itr]+"www.abc.com"+validPath[base]).toString();
            result=urlVal.isValid(url);
            if(false!=result)
               System.out.printf("Failure: invalid scheme %s\n",url);
            else
               System.out.printf("Pass: invalid scheme %s\n",url);  
         }
      }
      System.out.println();
      
      //test a valid scheme + authority + NULL/path/port/query
      /*BUG report: any schema other than http will run into crush as a result of RegexValidator authorityValidator failure*/
      for(int itr=0;itr<validScheme.length;itr++){
         for(int base=0;base<validAuthority.length;base++){
            url=(validScheme[itr]+validAuthority[base]).toString();
            result=urlVal.isValid(url);
            if(true!=result)
               System.out.printf("Failure: valid scheme %s\n",url);
            else
               System.out.printf("Pass: valid scheme %s\n",url);  
         }
         //System.out.printf("Due to RegexValidator authorityValidator failure, scheme other than http will crush the program.\n ");
      }
      System.out.println();
      
      for(int itr=0;itr<validScheme.length;itr++){
         for(int base=0;base<validPort.length;base++){
            url=(validScheme[itr]+"www.abc.com"+validPort[base]).toString();
            result=urlVal.isValid(url);
            if(true!=result)
               System.out.printf("Failure: valid scheme %s\n",url);
            else
               System.out.printf("Pass: valid scheme %s\n",url);  
         }
         //System.out.printf("Due to RegexValidator authorityValidator called by isValidAuthority() function, scheme other than http will crush the program.\n ");
      }
      System.out.println();
      
      for(int itr=0;itr<validScheme.length;itr++){
         for(int base=0;base<validPath.length;base++){
            url=(validScheme[itr]+"www.abc.com"+validPath[base]).toString();
            result=urlVal.isValid(url);
            if(true!=result)
               System.out.printf("Failure: valid scheme %s\n",url);
            else
               System.out.printf("Pass: valid scheme %s\n",url);  
         }
         //System.out.printf("Due to RegexValidator authorityValidator called by isValidAuthority() function, scheme other than http will crush the program.\n ");
      }
      System.out.println();
      
      System.out.println("Second Partition test complted...\n");

   }
   //You need to create more test cases for your Partitions if you need to 

   public void testIsValid()
   {
	   //You can use this function for programming based testing

   }
   


}
