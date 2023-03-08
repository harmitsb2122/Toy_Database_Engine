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
    else if(s[2].equals("string")){
        attribute.type = String.class;
    }

    table.attributeList.add(attribute);
  }

  private void insertInto(String token){
      String [] s = token.split("[ ,]+");
      Table table = loadTable(s[1]);
      int idx = 0;
      Record row = new Record();
      for(int i = 2; i < s.length;i++,idx++)
      {
          if(table.attributeList.get(idx).type == Integer.class){
              row.values.add(Integer.parseInt(s[i]));   
          }
          else if(table.attributeList.get(idx).type == String.class){
              row.values.add(s[i].substring(1, s[i].length()-1));
          }
      }
      table.recordList.add(row);
      saveTable(table);
  }

  private void saveTable(Table table){
    // TODO
  }

  public Table loadTable(String tablename){
    // TODO 
    return new Table();
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
        // insertInto(token);
      }
      if(token.startsWith("add_attribute"))
      {
        addAttribute(table, token);
      }
      if(token.startsWith("save_table"))
      {
        saveTable(table);
      }
  
    }
  }
}
