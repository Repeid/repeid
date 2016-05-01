package org.repeid.theme;

import freemarker.cache.URLTemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.repeid.Config;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

public class FreeMarkerUtil {

    private ConcurrentHashMap<String, Template> cache;

    @Context
    private ServletContext context;

    public FreeMarkerUtil() {
        if (Config.scope("theme").getBoolean("cacheTemplates", true)) {
            cache = new ConcurrentHashMap<>();
        }
    }

    public String processTemplate(String templateName) throws FreeMarkerException {
        try {
            Template template;
            if (cache != null) {
                template = cache.get(templateName);
                if (template == null) {
                    template = getTemplate(templateName);
                    if (cache.putIfAbsent(templateName, template) != null) {
                        template = cache.get(templateName);
                    }
                }
            } else {
                template = getTemplate(templateName);
            }

            Writer out = new StringWriter();
            template.process(Collections.emptyMap(), out);
            return out.toString();
        } catch (Exception e) {
            throw new FreeMarkerException("Failed to process template " + templateName, e);
        }
    }

    private Template getTemplate(String templateName) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setServletContextForTemplateLoading(context, "/WEB-INF");
        return cfg.getTemplate(templateName);
    }

}
