import java.io.*;

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
        RuntimeEngine engine = new RuntimeEngine();
        engine.execute(intermediateCode);
    }
}