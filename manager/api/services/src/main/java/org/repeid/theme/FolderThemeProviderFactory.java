package org.repeid.theme;

import org.repeid.Config;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;

import java.io.File;

public class FolderThemeProviderFactory implements ThemeProviderFactory {

	private FolderThemeProvider themeProvider;

	@Override
	public ThemeProvider create(RepeidSession sessions) {
		return themeProvider;
	}

	@Override
	public void init(Config.Scope config) {
		String d = config.get("dir");
		File rootDir = null;
		if (d != null) {
			rootDir = new File(d);
		}
		themeProvider = new FolderThemeProvider(rootDir);
	}

	@Override
	public void postInit(RepeidSessionFactory factory) {

	}

	@Override
	public void close() {

	}

	@Override
	public String getId() {
		return "folder";
	}
}
