package org.testevol.engine.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testevol.domain.Version;
import org.testevol.engine.domain.coverage.Coverage;
import org.testevol.engine.domain.coverage.CoverageUtil;
import org.testevol.engine.driver.TestCoverageDriver;
import org.testevol.engine.util.Utils;

public class CategoryC8 extends Category {

	public Map<String, Boolean> coverageHasImproved;
	
	public CategoryC8(Version oldVersion, Version version) {
		super(CategoryClassification.TESTADD_P, oldVersion, version);
		coverageHasImproved = new HashMap<String, Boolean>();
	}

	public void init() {
		
		File buildDir = version.getBuildDir();
		
		File newTestOldProgramPassedTests = new File(buildDir, getPassResultFileName(NEW_TESTS, OLD_PROGRAM));

		BufferedReader fileReaderNewTestOldProgramPassed = null;

		try {
			String test = null;

			fileReaderNewTestOldProgramPassed = new BufferedReader(
					new FileReader(newTestOldProgramPassedTests));
			while ((test = fileReaderNewTestOldProgramPassed.readLine()) != null) {
				addPossibleTest(test);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeBufferedReader(fileReaderNewTestOldProgramPassed);
		}
	}
	
	public void analyseCoverage(Results results, AddedTestMethods addedTestMethods) throws FileNotFoundException, IOException{
		if(getTestsOnThisCategory().isEmpty()){
			Utils.println("No tests in category C8, no coverage analysis needed.");
			return;
		}
		Utils.println(getTestsOnThisCategory().size()+" tests in category C8, starting coverage analysis.");
		
		CoverageUtil.deleteCoverageInfo();
		
		List<String> code = new ArrayList<String>();
		code.add(version.getCodeJar().getAbsolutePath());
		
		List<String> classpath = new ArrayList<String>();
		for(String path:version.getClassPath().split(File.pathSeparator)){
			if(path.endsWith(".jar")){
				classpath.add(path);
			}
		}
		
		String[] srcDirs = new String[]{version.getSourceDir().getAbsolutePath()};
		
		Set<String> testsExcluded = new HashSet<String>();
		testsExcluded.addAll(results.getAddedMethods());
		
		Coverage testSuiteCoverage = TestCoverageDriver.computeCoverage(	code,
																			srcDirs,
																			classpath,
																			Arrays.asList(version.getTestsJar().getAbsolutePath()),
																			null,
																			testsExcluded,
																			version.getDirectory().getAbsolutePath(),
																			true);
		
        for(String test:getTestsOnThisCategory()){

	        Set testsToInclude = new HashSet();
	        testsToInclude.add(test);
	        
			Coverage testCoverage = addedTestMethods.getCoverage(test);
			if(testCoverage == null){
				throw new RuntimeException("Did you executed coverage analysis for added test methods?");
			}
			coverageHasImproved.put(test, hasImprovedCoverage(testSuiteCoverage, testCoverage));
			
			System.out.println("Coverage:"+testCoverage.toString());
						
		}
	}
	
	public int getNumberOfTestsWithSameCoverage(){
		int testsSameCoverage=0;
		for(String test:getTestsOnThisCategory()){
			if(!coverageHasIncreasedWhenIncludedTest(test)){
				testsSameCoverage++;
			}
		}
		
		return testsSameCoverage;
	}
	
	public boolean hasImprovedCoverage(Coverage testSuiteCoverage, Coverage testCoverage) {
		Set<String> linesCoveredByTestSuite = testSuiteCoverage.getCoveredLines();
		boolean hasImprovedCoverage = false;
		for (String coveredLine : testCoverage.getCoveredLines()) {
			if (!linesCoveredByTestSuite.contains(coveredLine)) {
				System.out.println(coveredLine);
				hasImprovedCoverage = true;
				break;
			}
		}

		return hasImprovedCoverage;
	}
	
	public boolean coverageHasIncreasedWhenIncludedTest(String test){
		return coverageHasImproved.get(test) != null && coverageHasImproved.get(test);
	}
}