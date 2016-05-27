package org.repeid.theme;

import org.repeid.Config;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;
import org.repeid.util.JsonSerialization;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class JarThemeProviderFactory implements ThemeProviderFactory {

	protected static final String REPEID_THEMES_JSON = "META-INF/repeid-themes.json";
	protected static Map<Theme.Type, Map<String, ClassLoaderTheme>> themes = new HashMap<>();

	public static class ThemeRepresentation {
		private String name;
		private String[] types;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String[] getTypes() {
			return types;
		}

		public void setTypes(String[] types) {
			this.types = types;
		}
	}

	public static class ThemesRepresentation {
		private ThemeRepresentation[] themes;

		public ThemeRepresentation[] getThemes() {
			return themes;
		}

		public void setThemes(ThemeRepresentation[] themes) {
			this.themes = themes;
		}
	}

	@Override
	public ThemeProvider create(RepeidSession session) {
		return new JarThemeProvider(themes);
	}

	@Override
	public void init(Config.Scope config) {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			Enumeration<URL> resources = classLoader.getResources(REPEID_THEMES_JSON);
			while (resources.hasMoreElements()) {
				loadThemes(classLoader, resources.nextElement().openStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to load themes", e);
		}
	}

	@Override
	public void postInit(RepeidSessionFactory factory) {
	}

	@Override
	public void close() {
	}

	@Override
	public String getId() {
		return "jar";
	}

	protected void loadThemes(ClassLoader classLoader, InputStream themesInputStream) {
		try {
			ThemesRepresentation themesRep = JsonSerialization.readValue(themesInputStream, ThemesRepresentation.class);

			for (ThemeRepresentation themeRep : themesRep.getThemes()) {
				for (String t : themeRep.getTypes()) {
					Theme.Type type = Theme.Type.valueOf(t.toUpperCase());
					if (!themes.containsKey(type)) {
						themes.put(type, new HashMap<String, ClassLoaderTheme>());
					}
					themes.get(type).put(themeRep.getName(),
							new ClassLoaderTheme(themeRep.getName(), type, classLoader));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to load themes", e);
		}
	}

}
