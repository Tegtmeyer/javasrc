import com.darwinsys.util.*;

import java.io.*;

/**
 * ExecDemo shows how to execute an external program (in this case
 * the UNIX directory lister /bin/ls) and read its output.
 */
public class ExecDemoLs {
	/** The program to run */
	public static final String PROGRAM = "ls";

	public static void main(String argv[]) throws IOException { 

		Process p; 		// Process tracks one external native process
		BufferedReader is;	// reader for output of process
		String line;
		
		p = Runtime.getRuntime().exec(PROGRAM);

		Debug.println("exec", "In Main after exec");

		boolean done = false;

		// Optional: start a thread to wait for the process to terminate.
		// Don't just wait in main line, but here set a "done" flag and
		// use that to control the main reading loop below.
		Thread waiter = new Thread() {
			public void run() {
				try {
					p.waitFor();
				} catch (InterruptedException ex) {
					// OK, just quit.
					return;
				}
				System.out.println("Program terminated!");
				done = true;
			}
		}.start();

		// getInputStream gives an Input stream connected to 
		// the process p's standard output (and vice versa). We use
		// that to construct a BufferedReader so we can readLine() it.
		is = new BufferedReader(new InputStreamReader(p.getInputStream()));

		while (!done && ((line = is.readLine()) != null))
			System.out.println(line);
		
		Debug.println("exec", "In Main after EOF");

		return;
	}
}
