import junit.framework.TestCase;
import org.junit.Assert;

import java.io.File;

public class MainTest extends TestCase {

    String fileValue;
    File file;

    @Override
    protected void setUp() throws Exception{
        fileValue = "0";
        file = new File("out.txt");
    }

    public void testWriteAndRead(){
        Assert.assertEquals(Main.writeAndRead(file,fileValue),fileValue);
    }
}
