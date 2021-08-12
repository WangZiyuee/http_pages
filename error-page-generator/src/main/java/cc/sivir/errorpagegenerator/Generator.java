package cc.sivir.errorpagegenerator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.freemarker.SpringTemplateLoader;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qingke
 **/
@Slf4j
public class Generator {

    public static void main(String[] args) {
        try {
            List<String> list = new ArrayList<>();
            String outDirPath = "../error_pages";
            File outDir = new File(outDirPath);
            log.info("outDir mkdir {}", outDir.mkdir());
            ResourceLoader resourceLoader = new DefaultResourceLoader();

            // template
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
            TemplateLoader templateLoader = new SpringTemplateLoader(resourceLoader, "classpath:/templates/");
            configuration.setTemplateLoader(templateLoader);
            Template template = configuration.getTemplate("error_page_template.ftl");

            // config
            Resource configFile = resourceLoader.getResource("classpath:httpStatus.json");
            String config = StreamUtils.copyToString(configFile.getInputStream(), StandardCharsets.UTF_8);
            JsonNode data = JacksonUtil.parseJsonNode(config);
            // is4xxClientError || is5xxServerError
            ArrayNode arrayNode = (ArrayNode) data.get("httpStatus");
            for (JsonNode item : arrayNode) {
                log.info("{}", "=====================start=====================");
                // model
                HttpStatusModel httpStatusModel = new HttpStatusModel();
                httpStatusModel.setValue(item.get("value").asText());
                httpStatusModel.setReasonPhrase(item.get("reasonPhrase").asText());

                // target
                File target = new File(outDir, item.get("value").asText() + ".html");
                if (!target.exists()) {
                    log.info("createNewFile {}", target.createNewFile());
                }else {
                    log.info("delete {}", target.delete());
                }

                String result = FreeMarkerTemplateUtils.processTemplateIntoString(template, httpStatusModel);
                InputStream inputStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
                OutputStream outputStream = new FileOutputStream(target);
                FileCopyUtils.copy(inputStream, outputStream);
                log.info("{} ok", target.getName());
                String a = "error_page "+ item.get("value").asText() + " /error_pages/" + item.get("value").asText() + ".html;";
                list.add(a);
                log.info("{}", "=====================end=====================");
            }

            for (String s : list) {
                System.out.println(s);
            }
            //     location ^~ /error_pages {
            //        internal;
            //        root   /data/nginx/html/error_pages/;
            //    }
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
