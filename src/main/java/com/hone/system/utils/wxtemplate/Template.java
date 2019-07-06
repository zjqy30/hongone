package com.hone.system.utils.wxtemplate;

/**
 * Created by LiJia on 2017/11/6.
 * 微信模板信息
 */
import java.util.List;

public class Template {

    // 消息接收方
    private String toUser;
    // 模板id
    private String templateId;
    // 模板消息详情链接
    private String url;
    // 消息顶部的颜色
    private String topColor;
    private String form_id;
    private String page;
    // 参数列表
    private List<TemplateResponse> templateParamList;


    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopColor() {
        return topColor;
    }

    public void setTopColor(String topColor) {
        this.topColor = topColor;
    }

    public String toJSON() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append(String.format("\"touser\":\"%s\"", this.toUser)).append(",");
        buffer.append(String.format("\"page\":\"%s\"", this.page)).append(",");
        buffer.append(String.format("\"form_id\":\"%s\"", this.form_id)).append(",");
        buffer.append(String.format("\"template_id\":\"%s\"", this.templateId)).append(",");
        buffer.append(String.format("\"url\":\"%s\"", this.url)).append(",");
        buffer.append(String.format("\"topcolor\":\"%s\"", this.topColor)).append(",");
        buffer.append("\"data\":{");
        TemplateResponse param = null;
        for (int i = 0; i < this.templateParamList.size(); i++) {
            param = templateParamList.get(i);
            // 判断是否追加逗号
            if (i < this.templateParamList.size() - 1){

                buffer.append(String.format("\"%s\": {\"value\":\"%s\",\"color\":\"%s\"},", param.getName(), param.getValue(), param.getColor()));
            }else{
                buffer.append(String.format("\"%s\": {\"value\":\"%s\",\"color\":\"%s\"}", param.getName(), param.getValue(), param.getColor()));
            }

        }
        buffer.append("}");
        buffer.append("}");
        return buffer.toString();
    }

    public List<TemplateResponse> getTemplateParamList() {
        return templateParamList;
    }

    public void setTemplateParamList(List<TemplateResponse> templateParamList) {
        this.templateParamList = templateParamList;
    }

    @Override
    public String toString() {
        return "Template{" +
                "toUser='" + toUser + '\'' +
                ", templateId='" + templateId + '\'' +
                ", url='" + url + '\'' +
                ", topColor='" + topColor + '\'' +
                ", form_id='" + form_id + '\'' +
                ", page='" + page + '\'' +
                ", templateParamList=" + templateParamList +
                '}';
    }
}

