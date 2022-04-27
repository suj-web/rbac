package com.example.rbac.code.generator.service.impl;

import com.example.rbac.code.generator.pojo.ColumnClass;
import com.example.rbac.code.generator.pojo.RespBean;
import com.example.rbac.code.generator.pojo.TableClass;
import com.example.rbac.code.generator.service.IGenerateCodeService;
import com.example.rbac.code.generator.utils.DBUtils;
import com.google.common.base.CaseFormat;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author suj
 * @create 2022/4/26
 */
@Service
public class GenerateCodeServiceImpl implements IGenerateCodeService {

    Configuration cfg = null;

    {
        cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setTemplateLoader(new ClassTemplateLoader(GenerateCodeServiceImpl.class,"/templates"));
        cfg.setDefaultEncoding("UTF-8");
    }

    @Override
    public RespBean generateCode(List<TableClass> tableClassList, String realPath) {
        try {
            Template pojoTemplate = cfg.getTemplate("Pojo.java.ftl");
            Template mapperJavaTemplate = cfg.getTemplate("Mapper.java.ftl");
            Template mapperXmlTemplate = cfg.getTemplate("Mapper.xml.ftl");
            Template serviceTemplate = cfg.getTemplate("Service.java.ftl");
            Template serviceImplTemplate = cfg.getTemplate("ServiceImpl.java.ftl");
            Template controllerTemplate = cfg.getTemplate("Controller.java.ftl");
            Template vueTemplate = cfg.getTemplate("Vue.vue.ftl");
            Connection connection = DBUtils.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            for(TableClass tableClass: tableClassList) {
                ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableClass.getTableName(), null);
                ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), null, "t_admin");
                List<ColumnClass> columnClassList = new ArrayList<>();
                while (columns.next()) {
                    String column_name = columns.getString("COLUMN_NAME");
                    String type_name = columns.getString("TYPE_NAME");
                    String remarks = columns.getString("REMARKS");
                    ColumnClass columnClass = new ColumnClass();
                    columnClass.setRemark(remarks);
                    columnClass.setColumnName(column_name);
                    columnClass.setType(type_name);
                    columnClass.setPropertyName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, column_name));
                    primaryKeys.beforeFirst();
                    while (primaryKeys.next()) {
                        String pkName = primaryKeys.getString("COLUMN_NAME");
                        if(column_name.equals(pkName)) {
                            columnClass.setIsPrimary(true);
                        }
                    }
                    columnClassList.add(columnClass);
                }
                tableClass.setColumns(columnClassList);
                String path = realPath+"/"+tableClass.getPackageName().replace(".","/");
                generate(pojoTemplate, tableClass, path+"/pojo/");
                generate(mapperJavaTemplate, tableClass, path+"/mapper/");
                generate(mapperXmlTemplate, tableClass, path+"/mapper/");
                generate(serviceTemplate, tableClass, path+"/service/");
                generate(serviceImplTemplate, tableClass, path+"/service/impl/");
                generate(controllerTemplate, tableClass, path+"/controller/");
                generate(vueTemplate, tableClass, path+"/vue/");
            }
            return RespBean.success("代码已生成",realPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("代码生成失败");
    }

    private void generate(Template template, TableClass tableClass, String path) throws IOException, TemplateException {
        File folder = new File(path);
        if(!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = path+"/"+tableClass.getPojoName() + template.getName().replace(".ftl","").replace("Pojo","").replace("Vue","");
        if(template.getName().startsWith("Service.java")){
            fileName = path+"/I"+tableClass.getPojoName() + template.getName().replace(".ftl","");
        }
        FileOutputStream fos = new FileOutputStream(fileName);
        OutputStreamWriter out = new OutputStreamWriter(fos);
        template.process(tableClass, out);
        fos.close();
        out.close();
    }
}
