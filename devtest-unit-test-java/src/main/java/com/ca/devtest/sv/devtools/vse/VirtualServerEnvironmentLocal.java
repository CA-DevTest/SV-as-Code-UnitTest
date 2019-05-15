/**
 * 
 */
package com.ca.devtest.sv.devtools.vse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ca.devtest.sv.devtools.utils.RegistryRestAPI;
import com.ca.devtest.sv.devtools.utils.SvAsCodeConfigUtil;
import com.ca.devtest.sv.devtools.utils.Utility;

/**
 * @author gaspa03
 *
 */
public final class VirtualServerEnvironmentLocal implements VirtualServerEnvironment {

	private Process vseProcess = null;

	private String registry;
	private String vseName;
	private String registryHostName;
	private static final Logger LOG = LoggerFactory.getLogger(VirtualServerEnvironmentLocal.class);

	private static final Map<String, VirtualServerEnvironment> dicoVSE = new Hashtable<String, VirtualServerEnvironment>();

	/**
	 * 
	 */
	public VirtualServerEnvironmentLocal() {
		this(null, null);
	}

	/**
	 * @param aRegistry
	 * @param aVseName
	 * @return
	 */
	public static synchronized VirtualServerEnvironment getInstance(String aRegistry, String aVseName) {
		String key = aRegistry + aVseName;

		VirtualServerEnvironment instance = null;
		if (dicoVSE.containsKey(key)) {
			instance = dicoVSE.get(key);
		} else {
			instance = new VirtualServerEnvironmentLocal(aRegistry, aRegistry);
		}
		return instance;
	}

	/**
	 * @param aRegistry
	 *            registry name if null use tcp://127.0.0.1:2010/Registry
	 * @param aVseName
	 *            Vse Name if null use : "vse-" + ${userName};
	 */
	private VirtualServerEnvironmentLocal(String aRegistry, String aVseName) {
		super();

		this.registry = SvAsCodeConfigUtil.registryUrl();
		this.vseName = SvAsCodeConfigUtil.deployServiceToVse(aVseName);
		this.registryHostName = SvAsCodeConfigUtil.registryHost(aRegistry);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ca.devtest.sv.devtools.vse.VirtualServerEnvironment#start(java.lang.
	 * String)
	 */
	@Override
	public boolean start() throws RuntimeException {

		boolean error = false;
		try {
			LOG.info("Starting  Local VSE :" + vseName + "....");
			install(SvAsCodeConfigUtil.devTestHome());
			vseProcess = buildProcess(SvAsCodeConfigUtil.devTestHome(), vseName, registry).start();
			LOG.info("VSE :" + vseName + " pid :" + vseProcess);
		} catch (Exception e) {
			if (null != vseProcess) {
				vseProcess.destroyForcibly();
			}
			throw new RuntimeException("Error: During starting VSE", e);
		}
		int iteration = 10;
		do {
			if (!vseProcess.isAlive()) {

				vseProcess.destroyForcibly();

				throw new RuntimeException(" Could not start vse: " + vseName + ", please check if registry : "
						+ registry + " is up and running");
			}
			if (iteration == 0) {
				vseProcess.destroyForcibly();
				throw new RuntimeException(" VSE " + vseName + " is not started, please check if registry : " + registry
						+ " is up and running");
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			iteration--;
		} while (!isStarted());
		return vseProcess.isAlive();

	}

	/**
	 * Request Registry to get VSE status
	 * 
	 * @return
	 */
	private boolean isStarted() {
		boolean started = false;
		try {
			started = RegistryRestAPI.isVseStarted(registryHostName, vseName);
		} catch (Exception e) {

		}
		return started;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ca.devtest.sv.devtools.vse.VirtualServerEnvironment#stop()
	 */
	@Override
	public boolean stop() throws RuntimeException {
		try {
			if (null == vseProcess)
				throw new RuntimeException("Error: You should start VSE before stoped!!");

			RegistryRestAPI.stopVSE(registryHostName, vseName);
			vseProcess.destroy();

		} catch (Exception e) {
			throw new RuntimeException("Error: During stop VSE", e);
		}

		return vseProcess.isAlive();
	}

	/**
	 * unzip DevTest jar if not exist
	 * 
	 * @param directory
	 *            target directory
	 * @param version
	 * @throws IOException
	 */
	private void install(File directory) {

		// to access a Jar content from http
		String lisa_properties = "lisa.properties";
		URL url = Thread.currentThread().getContextClassLoader().getResource("lisa.properties");
		if (null != url) {
			try {
				JarURLConnection conn = (JarURLConnection) url.openConnection();
				URL urlJar = conn.getJarFileURL();
				File zipFile = new File(urlJar.getFile());
				// if the output directory doesn't exist, create it
				if (!directory.exists())
					directory.mkdirs();
				File lisaProperties = new File(directory, "lisa.properties");
				if (!lisaProperties.exists()) {

					// buffer for read and write data to file
					byte[] buffer = new byte[2048];

					FileInputStream fInput = new FileInputStream(zipFile);
					ZipInputStream zipInput = new ZipInputStream(fInput);

					ZipEntry entry = zipInput.getNextEntry();

					while (entry != null) {
						String entryName = entry.getName();
						File file = new File(directory, entryName);

						System.out.println("Unzip file " + entryName + " to " + file.getAbsolutePath());

						// create the directories of the zip directory
						if (entry.isDirectory()) {
							File newDir = new File(file.getAbsolutePath());
							if (!newDir.exists()) {
								boolean success = newDir.mkdirs();
								if (success == false) {
									System.out.println("Problem creating Folder");
								}
							}
						} else {
							FileOutputStream fOutput = new FileOutputStream(file);
							int count = 0;
							while ((count = zipInput.read(buffer)) > 0) {
								// write 'count' bytes to the file output stream
								fOutput.write(buffer, 0, count);
							}
							fOutput.close();
						}
						// close ZipEntry and take the next one
						zipInput.closeEntry();
						entry = zipInput.getNextEntry();
					}

					// close the last ZipEntry
					zipInput.closeEntry();

					zipInput.close();
					fInput.close();
				}
			} catch (IOException e) {
				// TODO LOG
			}

		}
	}

	/**
	 * @param devTestHome
	 * @return
	 * @throws IOException
	 */
	private ProcessBuilder buildProcess(File devTestHome, String vseName, String registry) throws IOException {

		String separator = System.getProperty("file.separator");
		String devTestHome_Env = devTestHome.getCanonicalPath();

		String HeapDumpPath = "-XX:HeapDumpPath=" + devTestHome_Env + separator + "tmp";
		String HeapDumpOnOutOfMemoryError = "-XX:+HeapDumpOnOutOfMemoryError";
		String Xmx = "-Xmx256m";
		String javaEndor = "-Djava.endorsed.dirs=" + devTestHome_Env + separator + "lib" + separator + "endorsed";
		String lisa_home = "-DLISA_HOME=" + devTestHome_Env;
		String lisa_tmp = "-Dlisa.tmpdir=" + devTestHome_Env + separator + "tmp";
		String java_tmpdir = "-Djava.io.tmpdir=" + devTestHome_Env + separator + "tmp";
		String lisa_log = "-DLISA_LOG=" + vseName + ".log";
		String file_encoding = "-Dfile.encoding=UTF-8";
		String jmx = "-Dcom.sun.management.jmxremote";
		String security = "-Djava.security.policy=" + devTestHome_Env + separator + "lisa.permissions";
		String ipv4Stack = "-Djava.net.preferIPv4Stack=true";
		String language = "-Duser.language=en";
		String brokerPort ="-Dlisa.pathfinder.broker.port=3009";
		String brokerHost="-Dlisa.pathfinder.broker.host="+registryHostName;
		String bindToAddress = "-Dlisa.net.bindToAddress=127.0.0.1";
		// int port = FreePortFinder.nextFreePort(2013, 2050);
		int port = 2013;
		String hostName= "127.0.0.1";
		String vse = "--name=tcp://"+hostName+":" + port + "/" + vseName;
		String registryName = "--registry=" + registry;
		String path = System.getProperty("java.home") + separator + "bin" + separator + "java";
		String[] arguments = new String[] { path, "-server", HeapDumpOnOutOfMemoryError, HeapDumpPath, Xmx, javaEndor,
				lisa_home, lisa_log, file_encoding, jmx, security, ipv4Stack, language, bindToAddress, lisa_tmp,brokerHost,brokerPort,
				java_tmpdir, "-classpath", classpath(devTestHome),
				"com.itko.lisa.coordinator.VirtualServiceEnvironmentImpl", vse, registryName };
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(arguments);		
		return processBuilder;
	}

	/**
	 * @param path
	 * @return a classpath string from Path
	 */
	private String classpath(File path) {
		String[] extensions = new String[] { "jar", "zip" };
		StringBuilder builder = new StringBuilder();
		File libFolder = new File(path, "lib");
		Collection<File> jars = FileUtils.listFiles(libFolder, extensions, true);
		for (File jar : jars) {
			builder.append(jar.getAbsolutePath());
			builder.append(Utility.classpathSeparator());
		}

		return builder.toString();
	}

	/*
	 * Request Registry to get VSE status
	 */
	@Override
	public boolean isRunning() throws RuntimeException {

		return vseProcess != null ? vseProcess.isAlive() : false;
	}
}
