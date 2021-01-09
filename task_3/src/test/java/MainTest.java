import junit.framework.TestCase;
import org.junit.Assert;

public class MainTest extends TestCase {
    String bitAndByte ;
    String kByteAndByte ;
    String mByteAndKByte;

    String firstRequest;
    String secondRequest;
    String thirdRequest;

    double firstRequestActualResult;
    double secondRequestActualResult;
    double thirdRequestActualResult;

    String bitAndByteResult;
    String kByteAndByteResult;
    String mByteAndKByteResult;

    @Override
    protected void setUp() throws Exception{
         bitAndByte ="1 байт = 8 бит";
         kByteAndByte = "1 Кбайт = 1024 байт";
         mByteAndKByte = "1 Мбайт = 1024 Кбайт";

         firstRequest = "1 бит = ? байт";
         secondRequest = "1 байт = ? Кбайт";
         thirdRequest = "24 бит = ? Мбайт";

        firstRequestActualResult =(double) 1/8;
        secondRequestActualResult =(double) 1/1024;
        thirdRequestActualResult = (double) 24/(1024 * 1024 * 8);

         bitAndByteResult = Main.recordingAndProcessing(bitAndByte);
         kByteAndByteResult = Main.recordingAndProcessing(kByteAndByte);
         mByteAndKByteResult = Main.recordingAndProcessing(mByteAndKByte);

    }

    public void testRecordingAndProcessing(){
        Assert.assertTrue(bitAndByteResult.contains("[бит 8.0 Value{coefficient=1.0, name='байт', bigParent=null}, байт 1.0 null]"));

        Assert.assertTrue(kByteAndByteResult.contains("Кбайт 1.0 null") &&
                kByteAndByteResult.contains("байт 1024.0 Value{coefficient=1.0, name='Кбайт', bigParent=null}") &&
                kByteAndByteResult.contains("бит 8.0 Value{coefficient=1024.0, name='байт'," +
                        " bigParent=Value{coefficient=1.0, name='Кбайт', bigParent=null}}"));

        Assert.assertTrue(mByteAndKByteResult.contains("Кбайт 1024.0 Value{coefficient=1.0, name='Мбайт', bigParent=null}") &&
                        mByteAndKByteResult.contains("байт 1024.0 Value{coefficient=1024.0, name='Кбайт', bigParent=Value{coefficient=1.0," +
                                " name='Мбайт', bigParent=null}}") &&
                        mByteAndKByteResult.contains("Мбайт 1.0 null") &&
                        mByteAndKByteResult.contains("бит 8.0 Value{coefficient=1024.0, name='байт'," +
                                " bigParent=Value{coefficient=1024.0, name='Кбайт', bigParent=Value{coefficient=1.0, name='Мбайт', bigParent=null}}}")
        );
    }

    public void testCalculationOfResults(){
        String bitAndByteRequest = Main.calculationOfResults(firstRequest);
        String byteAndKByteRequest = Main.calculationOfResults(secondRequest);
        String bitAndMByteRequest = Main.calculationOfResults(thirdRequest);

        Assert.assertEquals(bitAndByteRequest,"1 бит = " + firstRequestActualResult + " байт");
        Assert.assertEquals(byteAndKByteRequest,"1 байт = " + secondRequestActualResult + " Кбайт");
        Assert.assertEquals(bitAndMByteRequest, "24 бит = " + thirdRequestActualResult + " Мбайт");

    }
}
