import java.util.Scanner;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Solution {

  final static Scanner input = new Scanner(System.in);
  final static String baseURL = "https://www.ecs.soton.ac.uk/people/";

  public static void main(String[] args) {

    String URL = makeURL();
    String page = fetchWebpageHTML(URL);
    parseForName(page);

  }

  //takes user input to generate the
  public static String makeURL() {
    String username = input.nextLine();
    String URL = baseURL + username;
    return URL;
  }

  //gets the html of the requested url
  public static String fetchWebpageHTML(String URL) {
    try {
      URL URLObj = new URL(URL);

      /*
      BufferReader is more primitive can only return strings.
      Were as Scanner can parse for ints etc.
      BufferReader has a 8192 char buffer vs 1024 for scanner.
      BufferReader is synchronous so can be threaded.
      */
      BufferedReader URLReader = new BufferedReader(
        new InputStreamReader(URLObj.openStream()));
      //Scanner URLScanner = new Scanner(URLObj.openStream());
      StringBuffer stringBuffer = new StringBuffer();

      String text;
      while ((text = URLReader.readLine()) != null) {
        stringBuffer.append(text);
      }

      URLReader.close();

      String page = stringBuffer.toString();

      return page;


    } catch (MalformedURLException URLError) {
      System.out.println("Malformed URL");
    } catch (IOException IOError) {
      System.out.println("IO error");
    }

    return "No page";

  }

  //parses the webpage html for the name.
  public static String parseForName(String page) {

    try {
      int nameIndex = page.indexOf("property=\"name\"");
      int closeTagIndex = page.indexOf(">", nameIndex);
      int openTagIndex = page.indexOf("<", nameIndex);

      String name = page.substring(closeTagIndex + 1, openTagIndex);

      System.out.println("name: " + name);

      return name;
    } catch (StringIndexOutOfBoundsException OOBError) {
      System.out.println("There is no name on this page. Try another.");
      main(null);
      return null;
    }
  }
}
