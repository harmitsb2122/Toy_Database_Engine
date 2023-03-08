import java.io.Serializable;
import java.util.ArrayList;

public class Record implements Serializable{
  public ArrayList<Object> values;
  Record(){
    values = new ArrayList<>();
  }
}
