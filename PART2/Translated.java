public class Translated {
  public static void main(String[] args) {
name()
surname()
fullname(name(), "", surname())  }

  public static String name() {
    return "John";
  }


  public static String surname() {
    return "Doe";
  }


  public static String fullname(String first_name, String sep, String last_name) {
    return first_name + sep + last_name;
  }

}
