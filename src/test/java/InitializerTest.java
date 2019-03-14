import com.cyr1en.mcutils.initializers.Initializable;
import com.cyr1en.mcutils.initializers.annotation.Initialize;
import com.cyr1en.mcutils.initializers.annotation.process.Initializer;

import java.util.UUID;

public class InitializerTest implements Initializable {

  private InitializerTest() {
    Initializer.initAll(this);
  }

  @Initialize
  private void init3() {
    System.out.println("Init 3 Success. /w negative");
  }

  @Initialize
  private void init4() {
    System.out.println("Init 4 Success. /w negative");
  }

  @Initialize
  private void init5() {
    System.out.println("Init 5 Success. /w negative");
  }

  @Initialize(priority = 1)
  private void init1() {
    System.out.println("Init 1 Success!");
  }

  @Initialize(priority = 2)
  private void init2() {
    System.out.println("Init 2 Success");
  }

  public static void main(String[] args) {
    System.out.println(Integer.compare(-1,1));
    System.out.println(Integer.compare(4,3));
    UUID uuid = UUID.randomUUID();
    System.out.println(uuid);
    String stripped = uuid.toString().replaceAll("-", "");
    System.out.println(stripped);
    UUID newUUID = UUID.fromString(stripped.replaceFirst( "([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5" ));
    System.out.println(newUUID);
    //new InitializerTest();
  }


}
