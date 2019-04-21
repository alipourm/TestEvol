import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.testevol.domain.Project;
import org.testevol.domain.ProjectRepository;
import org.testevol.domain.RepositoryInfo;
import org.testevol.domain.Version;
import org.testevol.engine.DataAnalysis;
import org.testevol.engine.ProjectController;
import org.testevol.infra.ProjectRepoFileSystem;
import org.testevol.versioncontrol.GitImpl;
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
    @Ignore
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

	@Test
	public void test3() {
	    try {

            RepositoryInfo repositoryInfo = new RepositoryInfo();
            repositoryInfo.setUrl("git://github.com/testevol/google-gson.git");

            ProjectRepository pr = new ProjectRepoFileSystem("/tmp/gson");

            Project project = pr.getProject("/tmp/gson", "");

            GitImpl git = new GitImpl(repositoryInfo);
            List<Version> vlist = git.checkout(new File(
                    "/tmp/gson"), Arrays
                    .asList("v.1.1", "v.1.0"));

            List v = Arrays.asList("v.1.1", "v.1.0");

            File execFolder = new File("/tmp/exec");

            DataAnalysis da = new DataAnalysis("/Users/alipour/code/TestEvol/tool/config", project, vlist, new File("/tmp/exec"), true);
            da.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
//            System.err.println(e.sta);
        }
	}
}
