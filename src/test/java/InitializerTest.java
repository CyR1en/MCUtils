import com.cyr1en.mcutils.initializers.Initializable;
import com.cyr1en.mcutils.initializers.annotation.Initialize;
import com.cyr1en.mcutils.initializers.annotation.process.Initializer;

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
    new InitializerTest();
  }


}
