import com.company.Main;
import junit.framework.TestCase;
import org.junit.Assert;


public class MainTest extends TestCase {

    int value;
    String actualResponse;
    int availableProcessors;

    @Override
    protected void setUp() throws Exception{
        value = 15;
        actualResponse ="1\n" +
                "2\n" +
                "foo \n" +
                "4\n" +
                "bar\n" +
                "foo \n" +
                "7\n" +
                "8\n" +
                "foo \n" +
                "bar\n" +
                "11\n" +
                "foo \n" +
                "13\n" +
                "14\n" +
                "foo bar";
        availableProcessors = Runtime.getRuntime().availableProcessors();
    }

    public void testFirstMethod(){
        StringBuilder firstMethodResponse=Main.firstMethod(value);
        Assert.assertTrue(firstMethodResponse.toString().contains(actualResponse));
    }

    public void testSecondMethod(){
        StringBuilder secondMethodResponse=Main.secondMethod(value);
        Assert.assertTrue(secondMethodResponse.toString().contains(actualResponse));
    }

    public void testThirdMethod(){
        StringBuilder thirdMethodResponse=Main.thirdMethod(value);
        Assert.assertTrue(thirdMethodResponse.toString().contains(actualResponse));
    }

    public void testFourthMethod() throws InterruptedException {
        StringBuilder fourthMethodResponse=Main.fourthMethod(value,availableProcessors);
        Assert.assertTrue(fourthMethodResponse.toString().contains(actualResponse));
    }

}
