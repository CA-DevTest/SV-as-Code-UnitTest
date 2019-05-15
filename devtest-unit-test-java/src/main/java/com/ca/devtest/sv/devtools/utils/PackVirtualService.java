/**
 * 
 */
package com.ca.devtest.sv.devtools.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.StringUtils;

/**
 * @author gaspa03
 *
 */
public class PackVirtualService {

	/**
	 * @param rrpairsFolder
	 * @param servicePropertiesFile
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static File packVirtualService(File rrpairsFolder,  Map config,String vrsContent, String servicePropertiesContent)
			throws IOException {

		String tmpDir = System.getProperty("java.io.tmpdir");
		
		
		File virtualServiceArchive = File.createTempFile("virtualServiceArchive", ".zip");//new File(new StrBuilder(tmpDir).append("/virtualServiceArchive.zip").toString());
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(virtualServiceArchive));
			FilenameFilter filter = new WildcardFileFilter(new String[]{"*-req.*","*-rsp.*"});
			File[] lstFile = rrpairsFolder.listFiles(filter);
			ZipEntry entry = null;

			for (File file : lstFile) {
				entry = new ZipEntry(file.getName());
				out.putNextEntry(entry);
				byte[] data = IOUtils.toByteArray(new FileInputStream(file));
				data=VelocityRender.render(IOUtils.toString(data,Charset.defaultCharset().name()), config).getBytes();
				out.write(data, 0, data.length);
				out.closeEntry();

			}
			if (!StringUtils.isEmpty(vrsContent)) {
				File vrsFile = File.createTempFile("virtualService", ".vrs");

				FileUtils.writeStringToFile(vrsFile, vrsContent, "UTF-8");
				entry = new ZipEntry("virtualService.vrs");
				out.putNextEntry(entry);
				byte[] data = IOUtils.toByteArray(new FileInputStream(vrsFile));
				out.write(data, 0, data.length);
				out.closeEntry();
				vrsFile.deleteOnExit();
			}
			if (!StringUtils.isEmpty(servicePropertiesContent)) {
				File servicePropertiesFile = File.createTempFile("serviceProperties", ".xml");

				FileUtils.writeStringToFile(servicePropertiesFile, servicePropertiesContent, "UTF-8");
				entry = new ZipEntry("serviceProperties.xml");
				out.putNextEntry(entry);
				byte[] data = IOUtils.toByteArray(new FileInputStream(servicePropertiesFile));
				out.write(data, 0, data.length);
				out.closeEntry();
				servicePropertiesFile.deleteOnExit();
			}
		} finally {
			if (null != out)
				out.close();
		}
		return virtualServiceArchive;
	}

	

}
