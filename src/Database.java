import java.io.*;
import java.nio.file.Files;

public class Database {

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
