import java.io.File;

import org.testevol.domain.Project;
import org.testevol.domain.ProjectRepository;
import org.testevol.domain.RepositoryInfo;
import org.testevol.domain.Version;
import org.testevol.engine.ProjectController;
import org.testevol.infra.ProjectRepoFileSystem;
//
//import org.junit.Test;

public class TestBasic {

	@org.junit.Test
	public void test() {
		try {
            System.out.println("stuff1");

            Version version = new Version(new File("/Users/alipour/data/gson/"));
			version.setUp(new File("/Users/alipour/code/TestEvol/tool/config"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@org.junit.Test
	public void test1() {
		System.out.println("stuff");
		org.testevol.engine.ProjectController pc = new ProjectController("tt");


		RepositoryInfo n = new RepositoryInfo();
	}


	@org.junit.Test
	public void test2() {
		ProjectRepository pr = new ProjectRepoFileSystem("/Users/alipour/data/projects");
		try {

		    Project project = pr.getProject("gson", "user");
//			System.out.println(project.getVersionsList());
		    System.out.println(pr.exists("gson", "user2"));
		} catch (Exception e) {
            e.printStackTrace();

        }

	}
}
