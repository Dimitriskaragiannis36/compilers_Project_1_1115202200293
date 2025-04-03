public class Translated {
  public static void main(String[] args) {
    System.out.println(findLangType("Java"));
    System.out.println(findLangType("Javascript"));
    System.out.println(findLangType("Typescript"));  }

  public static String findLangType(String langName) {
    return if ("Java".startsWith(langName))
    if (langName.startsWith("Java"))
    "Static"
  else 
    if (new StringBuilder("script").reverse().toString().startsWith(new StringBuilder(langName).reverse().toString()))
    "Dynamic"
  else 
    "Unknown"
  else 
    if (new StringBuilder("script").reverse().toString().startsWith(new StringBuilder(langName).reverse().toString()))
    "Probably Dynamic"
  else 
    "Unknown";
  }

}
