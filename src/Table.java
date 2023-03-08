import java.util.ArrayList;

public class Table {
  public String name;
  public ArrayList<Attribute> attributeList;
  public ArrayList<Record> recordList;

  Table()
  {
    attributeList = new ArrayList<>();
    recordList = new ArrayList<>();
  }
}
