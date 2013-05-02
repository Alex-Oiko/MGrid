package examples.read;

import java.awt.FlowLayout;
import java.awt.TextArea;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import mgridapplet.MGridApplet;


public class ReadChars extends MGridApplet {
	TextArea textBox;

	public void initApplet(int parameterNum, String charac) {
		int occurancies=0,c;
		
		/*start of user interface code*/
		setLayout(new FlowLayout());
		textBox = new TextArea( 30, 60 );
		textBox.append("Starting Timezone Applet...\n");
		textBox.setEditable(false);
		add(textBox);
		this.setVisible(true);

		
		if(charac==null||charac.equals(""))
			System.out.println("klein");
		
		char array[]=charac.toCharArray();
		char character = array[0];
		try{
			InputStream in = getClass().getResourceAsStream("textfile.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			textBox.append("Reading the file.\n");
		
			while( (c=br.read()) != -1){
				if(c==(int)character){
					occurancies++;
				}
			}
			in.close();
		}
		catch(Exception e){
			System.out.print("hello");
		}
		textBox.append("Number of occurancies is "+occurancies);
		textBox.append("Sending formatted time back to mgrid!");

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		//submit the results back to the M-Grid server
		String results = "Number of occurancies of character "+character+" is "+occurancies;
		submitResults(results);
		
		/*end of job code*/
        
	}
}
