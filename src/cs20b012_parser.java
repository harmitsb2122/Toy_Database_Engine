/**
 * Author CS20B012
 * cs20b012.query contains all the queries asked
 * To generate random queries - randomQueryGenerator.py is used
 * * To run :
 * * Type - javac cs20b012_parser.java
 * * Type - java cs20b012_parser
 * * If no file arguments are provided the cs20b012.query file would used as query file
 * * otherwise the file name can be given as argument
 * * Input - filename.query / cs20b012.query (if no filename is specified)
 * * Output - filename.query.code file 
 */
import java.io.*;
import java.util.StringTokenizer;

public class cs20b012_parser {
    /**
     * Wrapper function to read the contents from the query file , parse them and return the intermediate code
     * @param file
     * @return code
     */
    public static String parseQuery(String file)
    {
        BufferedReader reader;
        String code;
        code = "";
        try{
            reader = new BufferedReader(new FileReader(file));
            String query,cur_line;
            query = "";
            cur_line = reader.readLine();
            while(cur_line!=null)
            {
                query+=cur_line+'\n';
                cur_line = reader.readLine();
            }
            QueryParser parser = new QueryParser();
            code = parser.parse(query);
            reader.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return code;
    }

    public static void main(String[] args) throws Exception {     
        String intermediateCode;
        String intermediateCodeFilename = "cs20b012.query.code";
        if(args.length == 0)
        {
            intermediateCode = parseQuery("cs20b012.query");
        }
        else
        {
            intermediateCode = parseQuery(args[0]);
            intermediateCodeFilename = args[0]+".code";
        }        
        try 
        {
            FileWriter fWriter = new FileWriter(intermediateCodeFilename);
            fWriter.write(intermediateCode);
            fWriter.close();
        }
        catch (IOException e) {
            System.out.print(e.getMessage());
        }

    }
}

class QueryParser {

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

