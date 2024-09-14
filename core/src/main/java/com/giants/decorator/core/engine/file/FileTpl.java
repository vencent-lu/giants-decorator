/**
 * 
 */
package com.giants.decorator.core.engine.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.TemplateFileNotFindException;
import com.giants.decorator.core.template.TplEntity;

/**
 * @author vencent.lu
 *
 */
public class FileTpl implements TplEntity {

	private static final long serialVersionUID = 2125396119281198698L;
	
	private String name;
	private File file;
	
	/**
	 * 
	 * @param name name
	 * @param file file
	 */
	public FileTpl(String name, File file) {
		super();
		this.name = name;
		this.file = file;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.template.TplEntity#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.template.TplEntity#loadContent()
	 */
	@Override
	public String loadContent() throws TemplateException {
		if (!this.file.exists()) {
			throw new TemplateFileNotFindException(this.name, this.file);
		}
		RandomAccessFile fis = null;
		FileChannel fcin = null;
		FileLock flin = null;
		try {
			fis = new RandomAccessFile(this.file, "rw");
			fcin = fis.getChannel();
			try {
				flin = fcin.lock();
				if (flin != null) {
					flin.release();
					flin = null;
				}
			} catch (IOException e) {
				flin = null;
			}

			byte[] buf = new byte[1024 * 4];
			StringBuilder sb = new StringBuilder();
			while (fis.read(buf) != -1) {
				sb.append(new String(buf, "UTF-8"));
				buf = new byte[1024 * 4];
			}
			return sb.toString().trim();
		} catch (IOException e) {
			flin = null;
			throw new TemplateFileNotFindException(this.name, this.file, e);
		} finally {
			try {
				fcin.close();
				fis.close();
			} catch (IOException e) {
			}
			fcin = null;
			fis = null;
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.template.TplEntity#lastModifiedTime()
	 */
	@Override
	public long lastModifiedTime() {
		return this.file.lastModified();
	}

}
