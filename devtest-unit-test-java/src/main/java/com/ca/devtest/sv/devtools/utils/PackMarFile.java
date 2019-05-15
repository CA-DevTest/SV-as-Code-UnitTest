/**
 * 
 */
package com.ca.devtest.sv.devtools.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author gaspa03
 *
 */
public class PackMarFile {

	/**
	 * @param workingFolder
	 * @param config
	 * @return
	 * @throws IOException
	 */
	public static File packVirtualService(File workingFolder, String serviceName,
			@SuppressWarnings("rawtypes") Map config) throws IOException {

		File virtualServiceArchive = File.createTempFile(serviceName, ".mar");
		ZipOutputStream zip = null;
		try {

			FileOutputStream fileWriter = null;
			/*
			 * create the output stream to zip file result
			 */
			fileWriter = new FileOutputStream(virtualServiceArchive);
			zip = new ZipOutputStream(fileWriter);

			/*
			 * add the folder to the zip
			 */
			addFolderToZip("", serviceName, workingFolder, zip, config);

				ZipEntry entry = new ZipEntry(".marinfo");
				zip.putNextEntry(entry);
				byte[] data = IOUtils.toByteArray(getMarinfoTpl());
				data = VelocityRender.render(IOUtils.toString(data, Charset.defaultCharset().name()), config)
						.getBytes();
				zip.write(data, 0, data.length);
				zip.closeEntry();

				entry = new ZipEntry(".maraudit");
				zip.putNextEntry(entry);
				data = IOUtils.toByteArray(getMarAuditTpl());
				data = VelocityRender.render(IOUtils.toString(data, Charset.defaultCharset().name()), config)
						.getBytes();
				zip.write(data, 0, data.length);
				zip.closeEntry();

				entry = new ZipEntry(serviceName+"/lisa.project");
				zip.putNextEntry(entry);
				data = IOUtils.toByteArray(getLisaProjectTpl());
				data = VelocityRender.render(IOUtils.toString(data, Charset.defaultCharset().name()), config)
						.getBytes();
				zip.write(data, 0, data.length);
				zip.closeEntry();
			

		} finally {

			if (null != zip) {
				zip.flush();
				zip.close();
			}
		}
		return virtualServiceArchive;
	}

	/**
	 * @return
	 */
	private static InputStream getLisaProjectTpl() {
		
		return Thread.currentThread().getContextClassLoader().getResourceAsStream("mar/lisa.project.tpl");
	}

	/**
	 * @return
	 */
	private static InputStream getMarAuditTpl() {

		return Thread.currentThread().getContextClassLoader().getResourceAsStream("mar/maraudit.tpl");
	}

	/**
	 * @return
	 */
	private static InputStream getMarinfoTpl() {

		return Thread.currentThread().getContextClassLoader().getResourceAsStream("mar/marinfo.tpl");
	}

	/*
	 * recursively add files to the zip files
	 */
	private static void addFileToZip(String path, String serviceName, File folder, ZipOutputStream zip, boolean flag,
			@SuppressWarnings("rawtypes") Map config) throws IOException {

		/*
		 * if the folder is empty add empty folder to the Zip file
		 */
		if (flag == true) {
			zip.putNextEntry(new ZipEntry(path + "/" + folder.getName() + "/"));
		} else { /*
					 * if the current name is directory, recursively traverse it
					 * to get the files
					 */
			if (folder.isDirectory()) {
				/*
				 * if folder is not empty
				 */
				addFolderToZip(path, serviceName, folder, zip, config);
			} else {
				/*
				 * write the file to the output
				 */
				if (shouldPack(folder)) {

					String ressource = path + "/" + folder.getName();

					zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));

					byte[] data = IOUtils.toByteArray(new FileInputStream(folder));
					data = VelocityRender.render(IOUtils.toString(data, Charset.defaultCharset().name()), config)
							.getBytes();
					zip.write(data, 0, data.length);
					updateConfig(config, ressource,serviceName);
					// fixing bug
					zip.closeEntry();
				}

			}
		}
	}

	/**
	 * add in the context file reference
	 * 
	 * @param config
	 * @param ressource
	 * @param serviceName 
	 */
	private static void updateConfig(Map config, String ressource, String serviceName) {

		ressource=StringUtils.replace(ressource, serviceName+"/", "");
		if (FilenameUtils.wildcardMatch(ressource, "*.vsm")) {
			
			config.put("vsmLocation", ressource);
		}
		if (FilenameUtils.wildcardMatch(ressource, "*.config")) {
			config.put("configLocation", ressource);
		}
	}

	private static boolean shouldPack(File file) {

		return FilenameUtils.wildcardMatch(file.getName(), "*.vsi")
				|| FilenameUtils.wildcardMatch(file.getName(), "*.vsm")
				|| FilenameUtils.wildcardMatch(file.getName(), "*.config");
	}

	/*
	 * add folder to the zip file
	 */
	private static void addFolderToZip(String path, String serviceName, File srcFolder, ZipOutputStream zip,
			@SuppressWarnings("rawtypes") Map config) throws IOException {

		/*
		 * check the empty folder
		 */
		if (srcFolder.list().length == 0) {

			addFileToZip(path, serviceName, srcFolder, zip, true, config);
		} else {
			/*
			 * list the files in the folder
			 */
			// FilenameFilter filter = new WildcardFileFilter(new String[] {
			// "*.vsm", "*.vsi", "*.config" });
			String[] files = srcFolder.list();

			for (String fileName : files) {
				if (path.equals("")) {
					addFileToZip(serviceName, serviceName, new File(srcFolder + "/" + fileName), zip, false, config);
				} else {
					addFileToZip(path + "/" + srcFolder.getName(), serviceName, new File(srcFolder + "/" + fileName),
							zip, false, config);
				}
			}
		}
	}
}
