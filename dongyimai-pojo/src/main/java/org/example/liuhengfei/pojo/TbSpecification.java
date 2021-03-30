package org.example.liuhengfei.pojo;

import java.io.Serializable;
import java.util.List;

public class TbSpecification implements Serializable {
    private Long id;

    private String specName;

    private List<TbSpecificationOption> optionList;

    private static final long serialVersionUID = 1L;

    public List<TbSpecificationOption> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<TbSpecificationOption> optionList) {
        this.optionList = optionList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName == null ? null : specName.trim();
    }
}