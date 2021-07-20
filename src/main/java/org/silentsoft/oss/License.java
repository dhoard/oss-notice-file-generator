package org.silentsoft.oss;

import java.io.*;
import java.nio.file.Path;

public class License {
	
	private String name;

	private String[] aliases;
	
	private String content;
	
	public License(String licenseName, Path licenseFilePath) throws FileNotFoundException {
		this(licenseName, licenseFilePath.toFile());
	}
	
	public License(String licenseName, File licenseFile) throws FileNotFoundException {
		this.name = licenseName;
		this.content = read(new FileReader(licenseFile));
	}
	
	public License(String licenseName, InputStream licenseFileInputStream) {
		this.name = licenseName;
		this.content = read(new InputStreamReader(licenseFileInputStream));
	}
	
	private String read(Reader reader) {
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				buffer.append(line);
				buffer.append("\r\n");
			}
			bufferedReader.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return buffer.toString();
	}
	
	public String getName() {
		return this.name;
	}

	public String[] getAliases() {
		return this.aliases;
	}
	
	public String getContent() {
		return this.content;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("______\r\n");
		buffer.append("\r\n");
		buffer.append(String.format("__%s__\r\n", name));
		buffer.append("\r\n");
		buffer.append("```");
		buffer.append("\r\n");
		buffer.append(content);
		buffer.append("```");
		return buffer.toString();
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (getClass().isInstance(obj)) {
			return toString().equals(getClass().cast(obj).toString());
		}
		
		return super.equals(obj);
	}

	public static License of(String nameOrAlias) {
		return LicenseDictionary.get(nameOrAlias);
	}

}
