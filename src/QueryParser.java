import java.util.StringTokenizer;

public class QueryParser {

  public String parse(String query)
  {
    StringTokenizer stringTokenizer = new StringTokenizer(query);
    String intermediateCode ;
    intermediateCode = "";
    while(stringTokenizer.hasMoreTokens())
    {
      String token = stringTokenizer.nextToken();
      if(token.equals("create"))
      {
        intermediateCode+="create_table ";
        String tableName = stringTokenizer.nextToken();
        intermediateCode+=tableName;
        intermediateCode+='\n';
        int n = Integer.parseInt(stringTokenizer.nextToken());
        for(int i=0;i<n;i++)
        {
          String attributeType = stringTokenizer.nextToken();
          String attributeName = stringTokenizer.nextToken();
          intermediateCode+=("add_attribute "+attributeType+" "+attributeName+'\n');
        }
        intermediateCode+=("save_table "+tableName+'\n');
      }
      else if(token.equals("insert"))
      {
        // skip into keyword
        token = stringTokenizer.nextToken();
        String tableName = stringTokenizer.nextToken();
        
        intermediateCode+="insert_into "+tableName+" ";

        //skip values keyword
        token = stringTokenizer.nextToken();

        token = stringTokenizer.nextToken();

        intermediateCode+=token+'\n';

      }
      else if(token.equals("select"))
      {
        // skipping the *
        token = stringTokenizer.nextToken();
        
        //skipping from keyword
        token = stringTokenizer.nextToken();
        
        // table name
        token = stringTokenizer.nextToken();

        intermediateCode+="select_all_from "+token+'\n';
      }

    }
    return intermediateCode;
  }  
}
