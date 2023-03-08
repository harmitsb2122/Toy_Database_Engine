import java.io.*;
import java.util.StringTokenizer;

public class RuntimeEngine {

  private Table createTable(String token)
  {
    String[] s = token.split(" ");
    Table table = new Table();
    table.name = s[1];
    return table;
  }

  private void addAttribute(Table table,String token){
    String[] s = token.split(" ");
    Attribute attribute = new Attribute();
    attribute.name = s[1];
    
    if(s[1].equals("int")){
        attribute.type = Integer.class;
    }
    else if(s[1].equals("int")){
      attribute.type = Float.class;
    }
    else if(s[1].equals("string")){
        attribute.type = String.class;
    }

    table.attributeList.add(attribute);
  }

  private void insertInto(String token){
      String [] s = token.split("[ ,]+");
      Table table = loadTable(s[1]);
      int idx = 0;
      Record record = new Record();
      for(int i = 2; i < s.length;i++,idx++)
      {
          if(table.attributeList.get(idx).type == Integer.class){
              record.values.add(Integer.parseInt(s[i]));   
          }
          else if(table.attributeList.get(idx).type == Float.class){
            record.values.add(Float.parseFloat(s[i]));   
          }
          else if(table.attributeList.get(idx).type == String.class){
              record.values.add(s[i].substring(1, s[i].length()-1));
          }
      }
      table.recordList.add(record);
      saveTable(table);
  }

  private void saveTable(Table table){
    Database db = new Database();
    db.saveData(table);
  }

  private Table loadTable(String tablename){ 
    Database db = new Database();
    return db.loadData(tablename);
  }

  public void displayTable(String token){ 
    String [] s = token.split(" ");
    Table table = loadTable(s[1]);

    //--------- DESERIALIZATION ---------
    try{

      FileInputStream fileInputStream = new FileInputStream(s[1]+".db");
      ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
      table = (Table) objectInputStream.readObject(); 
      objectInputStream.close();
      fileInputStream.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    finally{
      for(int i = 0; i < table.attributeList.size();i++)
      {
        System.out.print(table.attributeList.get(i).name);
      }
    }
  }

  public void execute(String code)
  {
    StringTokenizer stringTokenizer = new StringTokenizer(code,"\n");
    Table table = new Table();
    while(stringTokenizer.hasMoreTokens())
    {
      String token = stringTokenizer.nextToken();
      if(token.startsWith("create_table"))
      {
        table = createTable(token);
      }
      if(token.startsWith("insert_into"))
      {
        insertInto(token);
      }
      if(token.startsWith("add_attribute"))
      {
        addAttribute(table, token);
      }
      if(token.startsWith("save_table"))
      {
        saveTable(table);
      }
      if(token.startsWith("select_all_from"))
      {
        displayTable(token);
      }
  
    }
  }
}
