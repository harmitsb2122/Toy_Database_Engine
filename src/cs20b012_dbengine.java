/**
 * Author CS20B012
 * cs20b012.query.code contains the intermediate code
 * 
 * * To run :
 * * Type - javac cs20b012_dbengine.java
 * * Type - java cs20b012_dbengine
 * * If no file arguments are provided the cs20b012.query.code file would used as code file
 * * otherwise the file name can be given as argument
 * * Input - filename.query.code / cs20b012.query.code (if no filename is specified)
 * * Output - .db files for the tables specified (serialization of Table object) 
 * * , .csv (deserialized Tables are stored in Database.csv)
 * *  
 */
import java.io.*;
import java.util.Date; 
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.io.Serializable;
import java.util.ArrayList;
import java.nio.file.Files;

public class cs20b012_dbengine {
  public static void main(String[] args) {
    try 
    {
        FileWriter csvWriter = new FileWriter("Database.csv");
        csvWriter.write(""); // write an empty string to clear the file
        csvWriter.flush();
        csvWriter.close();
    }
    catch (Exception e) {
        System.out.println(e);
    }  
    BufferedReader reader;
    String intermediateCode = "",fileName="";
    if(args.length == 0)
    {
        fileName = "cs20b012.query.code";
    }
    else
    {
        fileName = args[0];
    }        
    try{
        reader = new BufferedReader(new FileReader(fileName));
        String cur_line = "";
        cur_line = reader.readLine();
        while(cur_line!=null)
        {
            intermediateCode+=cur_line+'\n';
            cur_line = reader.readLine();
        }
        reader.close();
    }
    catch(IOException e)
    {
        e.printStackTrace();
    }
    RuntimeEngine engine = new RuntimeEngine();
    engine.execute(intermediateCode);   
  }
}


class RuntimeEngine {

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
    attribute.name = s[2];
    
    if(s[1].equals("int")){
        attribute.type = Integer.class;
    }
    else if(s[1].equals("float")){
      attribute.type = Float.class;
    }
    else if(s[1].equals("string")){
        attribute.type = String.class;
    }
    else if(s[1].equals("date"))
    {
      attribute.type = Date.class;
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
          else if(table.attributeList.get(idx).type == Date.class){
            String SDate = s[i].substring(1, s[i].length()-1);
            try{

              Date date = new SimpleDateFormat("dd/MM/yyyy").parse(SDate);
              record.values.add(date);
            }
            catch(Exception e)
            {
              System.out.println(e);
            }
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

    // DESERIALIZATION 
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
      String tableText = "Table Name - ";
      tableText+=table.name+"\n\n";

      for(int i = 0; i < table.attributeList.size();i++)
      {
        tableText += table.attributeList.get(i).name+"\t\t";
      }
      tableText+="\n";
      for(int i = 0; i < table.recordList.size();i++)
      {
        for(int j=0;j<table.recordList.get(i).values.size();j++)
        {
          tableText += table.recordList.get(i).values.get(j)+"\t\t";
        }
        tableText+="\n";
      }
      System.out.print(tableText);
      try{

        FileWriter csvWriter = new FileWriter("Database" + ".csv",true);
        csvWriter.append("Table Name - " + table.name + "\n");
        for (Attribute attr : table.attributeList) {
            csvWriter.append(attr.name + ",");
        }
        csvWriter.append("\n");
        for (Record record : table.recordList) {
            for (Object value : record.values) {
                csvWriter.append(value + ",");
            }
            csvWriter.append("\n");
        }
        csvWriter.append("\n");
        csvWriter.flush();
        csvWriter.close();
      }
      catch(Exception e)
      {
        System.out.println(e);
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

class Table implements Serializable{
  public String name;
  public ArrayList<Attribute> attributeList;
  public ArrayList<Record> recordList;

  Table()
  {
    attributeList = new ArrayList<>();
    recordList = new ArrayList<>();
  }
}

class Attribute implements Serializable{
  public Class type;
  public String name;
  Attribute()
  {
    type = null;
    name = null;
  }
}

class Record implements Serializable{
  public ArrayList<Object> values;
  Record(){
    values = new ArrayList<>();
  }
}

class Database {

  public void saveData(Table table){
    try {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(table);
        byte[] byteArray = byteStream.toByteArray();
        FileOutputStream outputStream = new FileOutputStream(new File(table.name+".db"));
        outputStream.write(byteArray);
        objectStream.close();
        outputStream.close();
    }
    catch(Exception e){
        e.printStackTrace();
    }
}

public Table loadData(String tableName){
    Table table = null;

    try{
        byte[] byte_array = Files.readAllBytes(new File(tableName+".db").toPath());
        ByteArrayInputStream byteStream = new ByteArrayInputStream(byte_array);
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        table = (Table)objectStream.readObject();
        objectStream.close();
    }
    catch(Exception e){
        e.printStackTrace();
    }
    return table;
}
}
