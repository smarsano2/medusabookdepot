package com.medusabookdepot.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.medusabookdepot.controller.files.FileManager;
import com.medusabookdepot.view.*;

public class Main {

	private final GUI firstFrame;

	public Main() {
		this.firstFrame = new GUI();
		this.firstFrame.launcher(new String[] {});
	}

	public static void main(String[] args) {
		/*
		// Directory in which the files are stored 
		File directory = new File(FileManager.getDirectoryPath());
		
		// If the directory doesn't already exist, it gets created and the demo data from res folder gets pasted into it
		if (!directory.exists()){
			directory.mkdir();
			try {
				FileUtils.copyDirectory(new File(Main.class.getResource("/.xml/").getPath().toString()), new File(FileManager.getDirectoryPath()+"xml"+System.getProperty("file.separator")));
			} catch (IOException e) {
				System.out.println("Demo data not present in res");
			}
			System.out.println("Demo data loaded");
		}
		*/
		File directory = new File(FileManager.getDirectoryPath());
		if (!directory.exists()){
			directory.mkdir();
			try {
				ExportResource("/movements.xml");
				ExportResource("/books.xml");
				ExportResource("/depots.xml");
				ExportResource("/customers.xml");
			} catch (Exception e) {
				System.out.println("Demo data not loaded!!");
			}
			System.out.println("Demo data loaded");
		}
		
		new Main();
		
	}
	
	/**
    * Export a resource embedded into a Jar file to the local file path.
    *
    * @param resourceName ie.: "/SmartLibrary.dll"
    * @return The path to the exported resource
    * @throws Exception
    */
	static public void ExportResource(String resourceName) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        try {
            stream = Main.class.getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            //jarFolder = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(FileManager.getDirectoryPath() +resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }
    }

}
