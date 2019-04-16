import java.io.File;

import org.testevol.domain.Version;



public class Test {

	@org.junit.Test
	public void test() {
		try {
			Version version = new Version(new File("/Users/alipour/data/gson/"));
			version.setUp(new File("/Users/alipour/code/TestEvol/tool/config"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
