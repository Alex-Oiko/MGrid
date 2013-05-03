import org.icenigrid.gridsam.client.common.ClientSideJobManager;
import org.icenigrid.gridsam.core.*;
import org.icenigrid.gridsam.core.jsdl.JSDLSupport;
import org.icenigrid.schema.jsdl.y2005.m11.*; 

import org.apache.xmlbeans.XmlException; 

import java.io.*;
import java.util.*;

public class GridSAMExample {

	private static String ftpServer = System.getProperty("ftp.server");
	private static String gridsamServer = System.getProperty("gridsam.server");

	public static void main(String[] args)
		throws JobManagerException, SubmissionException, UnsupportedFeatureException, UnknownJobException,
			IOException, XmlException, InterruptedException {

		System.out.println("Creating a new client Job Manager...");
		ClientSideJobManager jobManager = new ClientSideJobManager(
			new String[] { "-s", gridsamServer },
			ClientSideJobManager.getStandardOptions());

		System.out.println("Creating JSDL description...");
		String xJSDLString  = createJSDLDescription("/bin/echo", "hello world");
		JobDefinitionDocument xJSDLDocument =
			JobDefinitionDocument.Factory.parse(xJSDLString);

		System.out.println("Submitting job to Job Manager...");
		JobInstance job = jobManager.submitJob(xJSDLDocument);
		String jobID = job.getID();

		// Get and report the status of job until complete
		System.out.println("Job ID: " + jobID);
		String jobStage="";
		
		while(!jobStage.equals("done")){
			jobStage = jobManager.findJobInstance(jobID).getLastKnownStage().getState().toString();
			System.out.println("state is "+jobStage);
		}
		// ...
	}

	private static String createJSDLDescription(String execName, String args) {
		return "<JobDefinition xmlns=\"http://schemas.ggf.org/jsdl/2005/11/jsdl\">"+
    "<JobDescription>"+
        "<JobIdentification>"+
            "<JobName>Alex Job</JobName>"+
            "<Description>For ze coursework</Description>"+
            "<JobAnnotation>no annotation</JobAnnotation>"+
            "<JobProject>gridsam project</JobProject>"+
        "</JobIdentification>"+
        "<Application>"+
            "<POSIXApplication xmlns=\"http://schemas.ggf.org/jsdl/2005/11/jsdl-posix\">"+
                "<Executable>"+execName+"</Executable>"+
                "<Argument>"+args+"</Argument>"+
                "<Output>stdout.txt</Output>"+
                "<Error>stderr.txt</Error>"+
                "<Environment name=\"FIRST_INPUT\">dir1/file1.txt</Environment>"+
            "</POSIXApplication>"+
        "</Application>"+
        "<DataStaging>"+
            "<FileName>stdout.txt</FileName>"+
            "<CreationFlag>overwrite</CreationFlag>"+
            "<DeleteOnTermination>true</DeleteOnTermination>"+
            "<Target>"+
                "<URI>ftp://anonymous:anonymous@localhost:55521/stdout.txt</URI>"+
            "</Target>"+
        "</DataStaging>"+
    "</JobDescription>"+
"</JobDefinition>";
   }
}
